package com.liuhuiyu.json.util.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Base64;

/**
 * 字节码json转换<p>
 * Created on 2025/3/16 21:12
 *
 * @author liuhuiyu
 * @version 1.0
 * @since 21
 */
public class ByteArrayDeserializer implements JsonDeserializer<byte[]> {
    /**
     * 功能描述<p>
     * author liuhuiyu<p>
     * Created DateTime 2025/3/16 21:13
     *
     * @param json    json Element
     * @param typeOfT 转换类型
     * @param context json解析上下文
     * @return byte[]
     */
    @Override
    public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (json.isJsonPrimitive()) {
            String base64String = json.getAsString();
            return Base64.getDecoder().decode(base64String);
        }
        throw new JsonParseException("Expected a Base64 encoded string but was " + json);
    }
}