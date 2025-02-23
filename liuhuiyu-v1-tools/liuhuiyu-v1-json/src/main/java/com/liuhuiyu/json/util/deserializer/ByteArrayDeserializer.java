package com.liuhuiyu.json.util.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Base64;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2025/2/14 17:00
 */

public class ByteArrayDeserializer implements JsonDeserializer<byte[]> {

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