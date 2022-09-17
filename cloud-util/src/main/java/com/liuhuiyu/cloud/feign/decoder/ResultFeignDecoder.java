package com.liuhuiyu.cloud.feign.decoder;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import com.liuhuiyu.util.exception.LhyException;
import com.liuhuiyu.util.map.MapUtil;
import com.liuhuiyu.util.model.Result;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-09-17 15:11
 */
public class ResultFeignDecoder implements Decoder {
    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        if (response.body() == null) {
            throw new DecodeException(response.status(), "没有返回有效的数据", response.request());
        }

        final InputStreamReader reader =(InputStreamReader) response.body().asReader(Util.UTF_8);
        String bodyStr = Util.toString(reader);
        //对结果进行转换
        final Map<String, Object> map = MapUtil.mapOfJsonString(bodyStr);
        Result<Object> result = Result.getResult(map);
        //如果返回错误，且为内部错误，则直接抛出异常
        if (result.isSuccess()) {
            // 获取接口接收类型
            JavaType javaType = TypeFactory.defaultInstance().constructType(type);
            // 判断是否分页对象
            if (type.getTypeName().contains("org.springframework.data.domain.PageImpl")) {
                // 获取分页泛型类型
                JavaType boundType = javaType.getBindings().getBoundType(0);
                // 获取数据
                Object data = map.get("data");
                // 结果集转Map
                Map<String, Object> dataMap = MapUtil.mapObjectToStringKeyMap(data);
                // 获取数据结果集
                List<Object> content = new ArrayList<>();
                if (dataMap.get("content") instanceof Collection<?>) {
                    Collection<?> list = (Collection<?>) dataMap.get("content");
                    content.addAll(list);
                } else {
                    throw new LhyException("无法解析非List数据");
                }
                // 数据转换
                List<Object> collect = content.stream().map(v -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        String json = new Gson().toJson(v);
                        return objectMapper.readValue(json, boundType);
                    } catch (JsonProcessingException e) {
                        throw new LhyException("无法解析List数据转换");
                    }
                }).collect(Collectors.toList());
                // 封装PageImpl
                MapUtil mapUtil = new MapUtil(MapUtil.mapObjectToStringKeyMap(result.getData()));
                int number = mapUtil.getIntegerValue("number", 0);
                int size = mapUtil.getIntegerValue("size", 0);
                PageRequest pageable = PageRequest.of(number, size);
                long totalElements = mapUtil.getLongValue("totalElements", 0L);
                return new PageImpl<>(collect, pageable, totalElements);
            }
            // 其他类型转换
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new Gson().toJson(result.getData()) , javaType);
        }else {
            throw new LhyException(result.getMsg());
        }
    }
}
