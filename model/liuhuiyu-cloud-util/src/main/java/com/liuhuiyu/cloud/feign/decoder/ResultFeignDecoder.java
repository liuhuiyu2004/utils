package com.liuhuiyu.cloud.feign.decoder;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import com.liuhuiyu.dto.Result;
import com.liuhuiyu.json.map.MapUtil;
import com.liuhuiyu.spring.util.ResultUtil;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-09-17 15:11
 */
public class ResultFeignDecoder implements Decoder {
    public static final String RESULT_DATA_FIELD_NAME = "data";
    public static final String PAGE_IMPL_CLASS_NAME = "org.springframework.data.domain.PageImpl";
    public static final String OPTIONAL_CLASS_NAME = "java.util.Optional";
    public static final String PAGE_IMPL_CONTENT_FIELD_NAME = "content";
    public static final String PAGE_IMPL_NUMBER_FIELD_NAME = "number";
    public static final String PAGE_IMPL_SIZE_FIELD_NAME = "size";
    public static final String PAGE_IMPL_TOTAL_ELEMENTS_FIELD_NAME = "totalElements";

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {

        if (response.body() == null) {
            throw new DecodeException(response.status(), "没有返回有效的数据", response.request());
        }

        final InputStreamReader reader = (InputStreamReader) response.body().asReader(Util.UTF_8);
        String bodyStr = Util.toString(reader);
        //对结果进行转换
        final Map<String, Object> map = MapUtil.mapOfJsonString(bodyStr);
        Result<Object> result = ResultUtil.getResult(map);
        //如果返回错误，且为内部错误，则直接抛出异常
        if (result.isSuccess()) {
            // 获取接口接收类型
            JavaType javaType = TypeFactory.defaultInstance().constructType(type);
            // 判断是否分页对象
            if (type.getTypeName().contains(PAGE_IMPL_CLASS_NAME)) {
                // 获取分页泛型类型
                JavaType boundType = javaType.getBindings().getBoundType(0);
                // 获取数据
                Object data = map.get(RESULT_DATA_FIELD_NAME);
                // 结果集转Map
                Map<String, Object> dataMap = MapUtil.mapObjectToStringKeyMap(data);
                // 获取数据结果集
                List<Object> content = new ArrayList<>();
                if (dataMap.get(PAGE_IMPL_CONTENT_FIELD_NAME) instanceof Collection<?>) {
                    Collection<?> list = (Collection<?>) dataMap.get(PAGE_IMPL_CONTENT_FIELD_NAME);
                    content.addAll(list);
                }
                else {
                    throw new RuntimeException("无法解析非List数据");
                }
                // 数据转换
                List<Object> collect = content.stream().map(v -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        String json = new Gson().toJson(v);
                        return objectMapper.readValue(json, boundType);
                    }
                    catch (JsonProcessingException e) {
                        throw new RuntimeException("无法解析List数据转换");
                    }
                }).collect(Collectors.toList());
                // 封装PageImpl
                MapUtil mapUtil = new MapUtil(MapUtil.mapObjectToStringKeyMap(result.getData()));
                int number = mapUtil.getIntegerValue(PAGE_IMPL_NUMBER_FIELD_NAME, 0);
                int size = mapUtil.getIntegerValue(PAGE_IMPL_SIZE_FIELD_NAME, 0);
                PageRequest pageable = PageRequest.of(number, size);
                long totalElements = mapUtil.getLongValue(PAGE_IMPL_TOTAL_ELEMENTS_FIELD_NAME, 0L);
                return new PageImpl<>(collect, pageable, totalElements);
            }
            else if (type.getTypeName().contains(OPTIONAL_CLASS_NAME)) {
                // 获取分页泛型类型
                JavaType boundType = javaType.getBindings().getBoundType(0);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                Object data = map.get(RESULT_DATA_FIELD_NAME);
                String json = new Gson().toJson(data);
                final Object o = objectMapper.readValue(json, boundType);
                return Optional.ofNullable(o);
            }
            // 其他类型转换
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(new Gson().toJson(result.getData()), javaType);
        }
        else {
            throw new RuntimeException(result.getMsg());
        }
    }
}
