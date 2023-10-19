package com.liuhuiyu.util;

import com.liuhuiyu.core.util.IfRun;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * 将对象值转换为字段类型的值
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-08-05 22:54
 */
public class ObjectToFieldValue {
    Field field;
    Object value;

    public ObjectToFieldValue(Field field, Object value) {
        this.field = field;
        this.value = value;
    }

    public Optional<Object> getValueOfFieldType() {
        if (value == null) {
            return Optional.empty();
        }
        final String typeName = getTypeName(field.getType());
        Object obj = IfRun.create()
                .elseIf(typeName.equals(int.class.getName()), () -> this.getInt())
                .elseIf(typeName.equals(long.class.getName()), () -> this.getLong())
                .elseIf(typeName.equals(float.class.getName()), () -> this.getFloat())
                .elseIf(typeName.equals(double.class.getName()), () -> this.getDouble())
                .elseIf(typeName.equals(byte.class.getName()), () -> this.getByte())
                .elseIf(typeName.equals(short.class.getName()), () -> this.getShort())
                .elseIf(typeName.equals(boolean.class.getName()), () -> this.getBoolean())
                .elseIf(typeName.equals(char.class.getName()), () -> this.getChar())
                .elseIf(typeName.equals(String.class.getName()), () -> this.getString())
                .elseIf(typeName.equals(BigDecimal.class.getName()), () -> this.getBigDecimal())
                .orElseThrow(() -> new DataBaseException("无法将值" + value + "转换成Integer."));
        return Optional.ofNullable(obj);
    }

    private Integer getInt() {
        return getInt(this.value);
    }

    public static Integer getInt(Object value) {
        final String typeName = getTypeName(value.getClass());
        return (Integer) IfRun.create()
                .elseIf(typeName.equals(int.class.getName()), () -> value)
                .elseIf(typeName.equals(long.class.getName()), () -> ((Long) value).intValue())
                .elseIf(typeName.equals(float.class.getName()), () -> ((Float) value).intValue())
                .elseIf(typeName.equals(double.class.getName()), () -> ((Double) value).intValue())
                .elseIf(typeName.equals(byte.class.getName()), () -> ((Byte) value).intValue())
                .elseIf(typeName.equals(short.class.getName()), () -> ((Short) value).intValue())
                .elseIf(typeName.equals(String.class.getName()), () -> Integer.parseInt((String) value))
                .elseIf(typeName.equals(boolean.class.getName()), () -> ((Boolean) value) ? 1 : 0)
                .elseIf(typeName.equals(char.class.getName()), () -> Integer.parseInt(String.valueOf((char) value)))
                .elseIf(typeName.equals(BigDecimal.class.getName()), () -> ((BigDecimal) value).intValue())
                .orElseThrow(() -> new DataBaseException("无法将值" + value + "转换成Integer."));
    }

    private Long getLong() {
        return getLong(this.value);
    }

    public static Long getLong(Object value) {
        final String typeName = getTypeName(value.getClass());
        return (Long) IfRun.create()
                .elseIf(typeName.equals(int.class.getName()), () -> ((Integer) value).longValue())
                .elseIf(typeName.equals(long.class.getName()), () -> value)
                .elseIf(typeName.equals(float.class.getName()), () -> ((Float) value).longValue())
                .elseIf(typeName.equals(double.class.getName()), () -> ((Double) value).longValue())
                .elseIf(typeName.equals(byte.class.getName()), () -> ((Byte) value).longValue())
                .elseIf(typeName.equals(short.class.getName()), () -> ((Short) value).longValue())
                .elseIf(typeName.equals(String.class.getName()), () -> Long.parseLong((String) value))
                .elseIf(typeName.equals(boolean.class.getName()), () -> ((Boolean) value) ? 1L : 0L)
                .elseIf(typeName.equals(char.class.getName()), () -> Long.parseLong(String.valueOf((char) value)))
                .elseIf(typeName.equals(BigDecimal.class.getName()), () -> ((BigDecimal) value).longValue())
                .orElseThrow(() -> new DataBaseException("无法将值" + value + "转换成Long."));
    }

    private Float getFloat() {
        return getFloat(this.value);
    }

    public static Float getFloat(Object value) {
        final String typeName = getTypeName(value.getClass());
        return (Float) IfRun.create()
                .elseIf(typeName.equals(int.class.getName()), () -> ((Integer) value).floatValue())
                .elseIf(typeName.equals(long.class.getName()), () -> ((Long) value).floatValue())
                .elseIf(typeName.equals(float.class.getName()), () -> value)
                .elseIf(typeName.equals(double.class.getName()), () -> ((Double) value).floatValue())
                .elseIf(typeName.equals(byte.class.getName()), () -> ((Byte) value).floatValue())
                .elseIf(typeName.equals(short.class.getName()), () -> ((Short) value).floatValue())
                .elseIf(typeName.equals(String.class.getName()), () -> Float.parseFloat((String) value))
                .elseIf(typeName.equals(boolean.class.getName()), () -> ((Boolean) value) ? 1F : 0F)
                .elseIf(typeName.equals(char.class.getName()), () -> Float.parseFloat(String.valueOf((char) value)))
                .elseIf(typeName.equals(BigDecimal.class.getName()), () -> ((BigDecimal) value).floatValue())
                .orElseThrow(() -> new DataBaseException("无法将值" + value + "转换成Float."));
    }

    private Double getDouble() {
        return getDouble(this.value);
    }

    private Double getDouble(Object value) {
        final String typeName = getTypeName(value.getClass());
        return (Double) IfRun.create()
                .elseIf(typeName.equals(int.class.getName()), () -> ((Integer) value).doubleValue())
                .elseIf(typeName.equals(long.class.getName()), () -> ((Long) value).doubleValue())
                .elseIf(typeName.equals(float.class.getName()), () -> ((Float) value).doubleValue())
                .elseIf(typeName.equals(double.class.getName()), () -> value)
                .elseIf(typeName.equals(byte.class.getName()), () -> ((Byte) value).doubleValue())
                .elseIf(typeName.equals(short.class.getName()), () -> ((Short) value).doubleValue())
                .elseIf(typeName.equals(String.class.getName()), () -> Double.parseDouble((String) value))
                .elseIf(typeName.equals(boolean.class.getName()), () -> ((Boolean) value) ? 1D : 0D)
                .elseIf(typeName.equals(char.class.getName()), () -> Double.parseDouble(String.valueOf((char) value)))
                .elseIf(typeName.equals(BigDecimal.class.getName()), () -> ((BigDecimal) value).doubleValue())
                .orElseThrow(() -> new DataBaseException("无法将值" + value + "转换成Double."));
    }

    private Byte getByte() {
        return getByte(this.value);
    }

    public static Byte getByte(Object value) {
        final String typeName = getTypeName(value.getClass());
        return (Byte) IfRun.create()
                .elseIf(typeName.equals(int.class.getName()), () -> ((Integer) value).byteValue())
                .elseIf(typeName.equals(long.class.getName()), () -> ((Long) value).byteValue())
                .elseIf(typeName.equals(float.class.getName()), () -> ((Float) value).byteValue())
                .elseIf(typeName.equals(double.class.getName()), () -> ((Double) value).byteValue())
                .elseIf(typeName.equals(byte.class.getName()), () -> value)
                .elseIf(typeName.equals(short.class.getName()), () -> ((Short) value).byteValue())
                .elseIf(typeName.equals(String.class.getName()), () -> Byte.parseByte((String) value))
                .elseIf(typeName.equals(boolean.class.getName()), () -> ((Boolean) value) ? 1 : 0)
                .elseIf(typeName.equals(char.class.getName()), () -> Byte.parseByte(String.valueOf((char) value)))
                .elseIf(typeName.equals(BigDecimal.class.getName()), () -> ((BigDecimal) value).byteValue())
                .orElseThrow(() -> new DataBaseException("无法将值" + value + "转换成Byte."));
    }

    private Short getShort() {
        return getShort(this.value);
    }

    public static Short getShort(Object value) {
        final String typeName = getTypeName(value.getClass());
        return (Short) IfRun.create()
                .elseIf(typeName.equals(int.class.getName()), () -> ((Integer) value).shortValue())
                .elseIf(typeName.equals(long.class.getName()), () -> ((Long) value).shortValue())
                .elseIf(typeName.equals(float.class.getName()), () -> ((Float) value).shortValue())
                .elseIf(typeName.equals(double.class.getName()), () -> ((Double) value).shortValue())
                .elseIf(typeName.equals(byte.class.getName()), () -> ((Byte) value).shortValue())
                .elseIf(typeName.equals(short.class.getName()), () -> value)
                .elseIf(typeName.equals(String.class.getName()), () -> Short.parseShort((String) value))
                .elseIf(typeName.equals(boolean.class.getName()), () -> ((Boolean) value) ? 1 : 0)
                .elseIf(typeName.equals(char.class.getName()), () -> Short.parseShort(String.valueOf((char) value)))
                .elseIf(typeName.equals(BigDecimal.class.getName()), () -> ((BigDecimal) value).shortValue())
                .orElseThrow(() -> new DataBaseException("无法将值" + value + "转换成Byte."));
    }

    private Boolean getBoolean() {
        return getBoolean(this.value);
    }

    public static final String[] TRUE_STRING = {"1", "true", "t", "yes", "y"};
    public static final String[] FALSE_STRING = {"0", "false", "f", "no", "n", "not"};

    public static Boolean getBoolean(Object value) {
        final String typeName = getTypeName(value.getClass());
        return (Boolean) IfRun.create()
                .elseIf(typeName.equals(int.class.getName()), () -> ((Integer) value) == 1)
                .elseIf(typeName.equals(long.class.getName()), () -> ((Long) value) == 1)
                .elseIf(typeName.equals(float.class.getName()), () -> ((Float) value) == 1)
                .elseIf(typeName.equals(double.class.getName()), () -> ((Double) value) == 1)
                .elseIf(typeName.equals(byte.class.getName()), () -> ((Byte) value) == 1)
                .elseIf(typeName.equals(short.class.getName()), () -> ((Short) value) == 1)
                .elseIf(typeName.equals(String.class.getName()), () -> {
                    String v = ((String) value).toLowerCase().trim();
                    if (Arrays.asList(TRUE_STRING).contains(v)) {
                        return true;
                    }
                    else if (Arrays.asList(FALSE_STRING).contains(v)) {
                        return false;
                    }
                    return Boolean.parseBoolean((String) value);
                })
                .elseIf(typeName.equals(boolean.class.getName()), () -> value)
                .elseIf(typeName.equals(char.class.getName()), () -> Boolean.parseBoolean(String.valueOf((char) value)))
                .elseIf(typeName.equals(BigDecimal.class.getName()), () -> ((BigDecimal) value).intValue() == 1)
                .orElseThrow(() -> new DataBaseException("无法将值" + value + "转换成Byte."));
    }

    private String getChar() {
        return getChar(this.value);
    }

    public static String getChar(Object value) {
        throw new DataBaseException("不支持Char转换" + value);
    }

    private String getString() {
        return getString(this.value);
    }

    public static String getString(Object value) {
        return value.toString();
    }

    private BigDecimal getBigDecimal() {
        return getBigDecimal(this.value);
    }

    public static BigDecimal getBigDecimal(Object value) {
        final String typeName = getTypeName(value.getClass());
        return (BigDecimal) IfRun.create()
                .elseIf(typeName.equals(int.class.getName()), () -> new BigDecimal((int) value))
                .elseIf(typeName.equals(long.class.getName()), () -> new BigDecimal((long) value))
                .elseIf(typeName.equals(float.class.getName()), () -> BigDecimal.valueOf((float) value))
                .elseIf(typeName.equals(double.class.getName()), () -> BigDecimal.valueOf((double) value))
                .elseIf(typeName.equals(byte.class.getName()), () -> new BigDecimal((byte) value))
                .elseIf(typeName.equals(short.class.getName()), () -> new BigDecimal((short) value))
                .elseIf(typeName.equals(String.class.getName()), () -> new BigDecimal((String) value))
                .elseIf(typeName.equals(boolean.class.getName()), () -> new BigDecimal((Boolean) value ? 1 : 0))
                .elseIf(typeName.equals(char.class.getName()), () -> new BigDecimal(String.valueOf((char) value)))
                .elseIf(typeName.equals(BigDecimal.class.getName()), () -> value)
                .orElseThrow(() -> new DataBaseException("无法将值" + value + "转换成Byte."));
    }

    public static String getTypeName(Class<?> type) {
        for (Map.Entry<String, List<String>> entry : DATA_TYPE_MAP.entrySet()) {
            if (entry.getValue().contains(type.getName())) {
                return entry.getKey();
            }
        }
        throw new DataBaseException("未设定的数据类型" + type.getName() + "。");
    }

    /**
     * 数据类型映射
     * Created DateTime 2023-08-05 21:18
     */
    static final Map<String, List<String>> DATA_TYPE_MAP = new HashMap<>();

    static {
        init();
    }

    static void init() {
        DATA_TYPE_MAP.put(int.class.getName(), Arrays.asList(int.class.getName(), Integer.class.getName()));
        DATA_TYPE_MAP.put(long.class.getName(), Arrays.asList(long.class.getName(), Long.class.getName()));
        DATA_TYPE_MAP.put(float.class.getName(), Arrays.asList(float.class.getName(), Float.class.getName()));
        DATA_TYPE_MAP.put(double.class.getName(), Arrays.asList(double.class.getName(), Double.class.getName()));
        DATA_TYPE_MAP.put(byte.class.getName(), Arrays.asList(byte.class.getName(), Byte.class.getName()));
        DATA_TYPE_MAP.put(short.class.getName(), Arrays.asList(short.class.getName(), Short.class.getName()));
        DATA_TYPE_MAP.put(boolean.class.getName(), Arrays.asList(boolean.class.getName(), Boolean.class.getName()));
        DATA_TYPE_MAP.put(char.class.getName(), Collections.singletonList(char.class.getName()));
        DATA_TYPE_MAP.put(String.class.getName(), Collections.singletonList(String.class.getName()));
        DATA_TYPE_MAP.put(BigDecimal.class.getName(), Collections.singletonList(BigDecimal.class.getName()));
    }
}
