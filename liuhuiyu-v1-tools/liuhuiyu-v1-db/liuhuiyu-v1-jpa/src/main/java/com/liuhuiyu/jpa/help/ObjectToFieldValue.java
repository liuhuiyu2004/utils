package com.liuhuiyu.jpa.help;

import com.liuhuiyu.core.time.LocalDateUtil;
import com.liuhuiyu.core.util.Assert;
import com.liuhuiyu.core.util.IfRun;
import com.liuhuiyu.core.util.ObjectToPrimitiveBoxed;
import com.liuhuiyu.jpa.exception.DataBaseException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.function.Function;

/**
 * 将对象值转换为字段类型的值
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-08-05 22:54
 */
public class ObjectToFieldValue {
    /**
     * 要转换成的格式
     * Created DateTime 2024-01-13 22:46
     */
    String typeName;
    Object value;

    public ObjectToFieldValue(Field field, Object value) {
        this.typeName = ObjectToPrimitiveBoxed.getTypeName(field.getType());
        this.value = value;
    }

    public ObjectToFieldValue(String typeName, Object value) {
        this.typeName = typeName;
        this.value = value;
    }

    static Map<String, Function<ObjectToFieldValue, Object>> functionMap;

    static {
        functionMap = new HashMap<>();
        functionMap.put(int.class.getName(), ObjectToFieldValue::getInteger);
        functionMap.put(long.class.getName(), ObjectToFieldValue::getLong);
        functionMap.put(float.class.getName(), ObjectToFieldValue::getFloat);
        functionMap.put(double.class.getName(), ObjectToFieldValue::getDouble);
        functionMap.put(byte.class.getName(), ObjectToFieldValue::getByte);
        functionMap.put(short.class.getName(), ObjectToFieldValue::getShort);
        functionMap.put(boolean.class.getName(), ObjectToFieldValue::getBoolean);
        functionMap.put(char.class.getName(), ObjectToFieldValue::getChar);
        functionMap.put(String.class.getName(), ObjectToFieldValue::getString);
        functionMap.put(BigDecimal.class.getName(), ObjectToFieldValue::getBigDecimal);
        functionMap.put(LocalDateTime.class.getName(), ObjectToFieldValue::getLocalDateTime);
        functionMap.put(LocalDate.class.getName(), ObjectToFieldValue::getLocalDate);
        functionMap.put(LocalTime.class.getName(), ObjectToFieldValue::getLocalTime);
        functionMap.put(Timestamp.class.getName(), ObjectToFieldValue::getTimestamp);
        functionMap.put(byte[].class.getName(), ObjectToFieldValue::getBytes);
    }

    public Optional<Object> getValueOfFieldType() {
        if (value == null) {
            return Optional.empty();
        }
        final Function<ObjectToFieldValue, Object> changeFunction = functionMap.get(this.typeName);
        Assert.assertNotNull(changeFunction, new DataBaseException("无法将值" + value + "转换成[" + this.typeName + "]."));
        Object obj = changeFunction.apply(this);
        return Optional.ofNullable(obj);
    }

    private Integer getInteger() {
        return ObjectToPrimitiveBoxed.getInteger(this.value);
    }

    private Long getLong() {
        return ObjectToPrimitiveBoxed.getLong(this.value);
    }

    private Float getFloat() {
        return ObjectToPrimitiveBoxed.getFloat(this.value);
    }

    private Double getDouble() {
        return ObjectToPrimitiveBoxed.getDouble(this.value);
    }

    private Byte getByte() {
        return ObjectToPrimitiveBoxed.getByte(this.value);
    }

    private Short getShort() {
        return ObjectToPrimitiveBoxed.getShort(this.value);
    }

    private Boolean getBoolean() {
        return ObjectToPrimitiveBoxed.getBoolean(this.value);
    }

    private String getChar() {
        return ObjectToPrimitiveBoxed.getChar(this.value);
    }

    private String getString() {
        if (value instanceof Clob) {
            Clob clob = (Clob) value;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                final long length = clob.length();
                int len = 1024;
                for (long pos = 1; pos <= length; pos += len) {
                    len = (length - (pos - 1)) >= len ? len : (int) (length - (pos - 1));
                    if (len > 0) {
                        stringBuilder.append(clob.getSubString(pos, len));
                    }
                    else {
                        break;
                    }
                }
            }
            catch (SQLException ignored) {

            }
            return stringBuilder.toString();
        }
        return ObjectToPrimitiveBoxed.getString(this.value);
    }

    private BigDecimal getBigDecimal() {
        return ObjectToPrimitiveBoxed.getBigDecimal(this.value);
    }

    private LocalDateTime getLocalDateTime() {
        return ObjectToPrimitiveBoxed.getLocalDateTime(this.value);
    }

    private LocalDate getLocalDate() {
        return ObjectToPrimitiveBoxed.getLocalDate(this.value);
    }

    private LocalTime getLocalTime() {
        return ObjectToPrimitiveBoxed.getLocalTime(this.value);
    }

    private Timestamp getTimestamp() {
        return ObjectToPrimitiveBoxed.getTimestamp(this.value);
    }

    private byte[] getBytes() {
        if (value instanceof Blob blob) {
            try (InputStream inputStream = blob.getBinaryStream();
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                return outputStream.toByteArray();
            }
            catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if (value instanceof byte[]) {
            return (byte[]) value;
        }else if (value instanceof Byte[] byteArray) {
            byte[] primitiveByteArray = new byte[byteArray.length];
            for (int i = 0; i < byteArray.length; i++) {
                primitiveByteArray[i] = byteArray[i];
            }
            return primitiveByteArray;
        }
        else {
            return new byte[0];
        }
    }
}
