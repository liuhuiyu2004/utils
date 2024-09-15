package com.liuhuiyu.core.util;

import com.liuhuiyu.core.time.LocalDateUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 将 Object 转换成 基本类型封装型
 *
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/9/14 14:42
 */
public class ObjectToPrimitiveBoxed {
    //region 数据类型

    /**
     * 数据类型映射
     * Created DateTime 2023-08-05 21:18
     */
    static final Map<String, String> DATA_TYPE_MAP;

    static {
        DATA_TYPE_MAP = new HashMap<>();
        //int
        DATA_TYPE_MAP.put(int.class.getName(), int.class.getName());
        DATA_TYPE_MAP.put(Integer.class.getName(), int.class.getName());
        //long
        DATA_TYPE_MAP.put(long.class.getName(), long.class.getName());
        DATA_TYPE_MAP.put(Long.class.getName(), long.class.getName());
        //double
        DATA_TYPE_MAP.put(double.class.getName(), double.class.getName());
        DATA_TYPE_MAP.put(Double.class.getName(), double.class.getName());
        //float
        DATA_TYPE_MAP.put(float.class.getName(), float.class.getName());
        DATA_TYPE_MAP.put(Float.class.getName(), float.class.getName());
        //char
        DATA_TYPE_MAP.put(char.class.getName(), char.class.getName());
        DATA_TYPE_MAP.put(Character.class.getName(), char.class.getName());
        //boolean
        DATA_TYPE_MAP.put(boolean.class.getName(), boolean.class.getName());
        DATA_TYPE_MAP.put(Boolean.class.getName(), boolean.class.getName());
        //byte
        DATA_TYPE_MAP.put(byte.class.getName(), byte.class.getName());
        DATA_TYPE_MAP.put(Byte.class.getName(), byte.class.getName());
        //short
        DATA_TYPE_MAP.put(short.class.getName(), short.class.getName());
        DATA_TYPE_MAP.put(Short.class.getName(), short.class.getName());
        //String
        DATA_TYPE_MAP.put(String.class.getName(), String.class.getName());
        //BigDecimal
        DATA_TYPE_MAP.put(BigDecimal.class.getName(), BigDecimal.class.getName());
        //LocalDateTime
        DATA_TYPE_MAP.put(LocalDateTime.class.getName(), LocalDateTime.class.getName());
        //LocalDate
        DATA_TYPE_MAP.put(LocalDate.class.getName(), LocalDate.class.getName());
        //LocalTime
        DATA_TYPE_MAP.put(LocalTime.class.getName(), LocalTime.class.getName());
        //Timestamp
        DATA_TYPE_MAP.put(Timestamp.class.getName(), Timestamp.class.getName());
    }

    /**
     * 获取指定类型名称<p>
     * author LiuHuiYu<p>
     * Created DateTime 2024/9/14 15:00
     *
     * @param type Class<?>
     * @return java.lang.String
     */
    public static String getTypeName(Class<?> type) {
        final String typeName = DATA_TYPE_MAP.get(type.getName());
        Assert.assertNotNull(typeName, new RuntimeException("未设定的数据类型" + type.getName() + "。"));
        return typeName;
    }

    //endregion

    /**
     * 原始数据按照映射转换为指定类型数据<p>
     * author LiuHuiYu<p>
     * Created DateTime 2024/9/14 16:28
     *
     * @param obj  原始数据
     * @param map  转换映射表
     * @param type 转换后的类型
     * @return T
     */
    private static <T> T objectToT(Object obj, Map<String, Function<Object, T>> map, Class<T> type) {
        if (obj == null) {
            throw new IllegalArgumentException("无法转换 null值");
        }
        final String typeName = getTypeName(obj.getClass());
        final Function<Object, T> function = map.get(typeName);
        Assert.assertNotNull(function, new RuntimeException("无法将值" + obj + "转换成[" + type.getName() + "]."));
        return function.apply(obj);
    }

    //region integer
    static final Map<String, Function<Object, Integer>> INTEGER_MAP;

    static {
        INTEGER_MAP = new HashMap<>();
        INTEGER_MAP.put(int.class.getName(), (value) -> (Integer) value);
        INTEGER_MAP.put(long.class.getName(), (value) -> ((Long) value).intValue());
        INTEGER_MAP.put(float.class.getName(), (value) -> ((Float) value).intValue());
        INTEGER_MAP.put(double.class.getName(), (value) -> ((Double) value).intValue());
        INTEGER_MAP.put(byte.class.getName(), (value) -> ((Byte) value).intValue());
        INTEGER_MAP.put(short.class.getName(), (value) -> ((Short) value).intValue());
        INTEGER_MAP.put(String.class.getName(), (value) -> Integer.parseInt((String) value));
        INTEGER_MAP.put(boolean.class.getName(), (value) -> ((Boolean) value) ? 1 : 0);
        INTEGER_MAP.put(char.class.getName(), (value) -> Integer.parseInt(String.valueOf((char) value)));
        INTEGER_MAP.put(BigDecimal.class.getName(), (value) -> ((BigDecimal) value).intValue());
    }

    public static Integer getInteger(Object value) {
        return objectToT(value, INTEGER_MAP, Integer.class);
    }
    //endregion

    //region long
    static final Map<String, Function<Object, Long>> LONG_MAP;

    static {
        LONG_MAP = new HashMap<>();
        LONG_MAP.put(int.class.getName(), (value) -> ((Integer) value).longValue());
        LONG_MAP.put(long.class.getName(), (value) -> (Long) value);
        LONG_MAP.put(float.class.getName(), (value) -> ((Float) value).longValue());
        LONG_MAP.put(double.class.getName(), (value) -> ((Double) value).longValue());
        LONG_MAP.put(byte.class.getName(), (value) -> ((Byte) value).longValue());
        LONG_MAP.put(short.class.getName(), (value) -> ((Short) value).longValue());
        LONG_MAP.put(String.class.getName(), (value) -> Long.parseLong((String) value));
        LONG_MAP.put(boolean.class.getName(), (value) -> ((Boolean) value) ? 1L : 0L);
        LONG_MAP.put(char.class.getName(), (value) -> Long.parseLong(String.valueOf((char) value)));
        LONG_MAP.put(BigDecimal.class.getName(), (value) -> ((BigDecimal) value).longValue());
    }

    public static Long getLong(Object value) {
        return objectToT(value, LONG_MAP, Long.class);
    }
    //endregion

    //region float
    static final Map<String, Function<Object, Float>> FLOAT_MAP;

    static {
        FLOAT_MAP = new HashMap<>();
        FLOAT_MAP.put(int.class.getName(), (value) -> ((Integer) value).floatValue());
        FLOAT_MAP.put(long.class.getName(), (value) -> ((Long) value).floatValue());
        FLOAT_MAP.put(float.class.getName(), (value) -> (Float) value);
        FLOAT_MAP.put(double.class.getName(), (value) -> ((Double) value).floatValue());
        FLOAT_MAP.put(byte.class.getName(), (value) -> ((Byte) value).floatValue());
        FLOAT_MAP.put(short.class.getName(), (value) -> ((Short) value).floatValue());
        FLOAT_MAP.put(String.class.getName(), (value) -> Float.parseFloat((String) value));
        FLOAT_MAP.put(boolean.class.getName(), (value) -> ((Boolean) value) ? 1F : 0F);
        FLOAT_MAP.put(char.class.getName(), (value) -> Float.parseFloat(String.valueOf((char) value)));
        FLOAT_MAP.put(BigDecimal.class.getName(), (value) -> ((BigDecimal) value).floatValue());
    }

    public static Float getFloat(Object value) {
        return objectToT(value, FLOAT_MAP, Float.class);
    }
    //endregion

    //region double
    static final Map<String, Function<Object, Double>> DOUBLE_MAP;

    static {
        DOUBLE_MAP = new HashMap<>();
        DOUBLE_MAP.put(int.class.getName(), (value) -> ((Integer) value).doubleValue());
        DOUBLE_MAP.put(long.class.getName(), (value) -> ((Long) value).doubleValue());
        DOUBLE_MAP.put(float.class.getName(), (value) -> ((Float) value).doubleValue());
        DOUBLE_MAP.put(double.class.getName(), (value) -> (Double) value);
        DOUBLE_MAP.put(byte.class.getName(), (value) -> ((Byte) value).doubleValue());
        DOUBLE_MAP.put(short.class.getName(), (value) -> ((Short) value).doubleValue());
        DOUBLE_MAP.put(String.class.getName(), (value) -> Double.parseDouble((String) value));
        DOUBLE_MAP.put(boolean.class.getName(), (value) -> ((Boolean) value) ? 1D : 0D);
        DOUBLE_MAP.put(char.class.getName(), (value) -> Double.parseDouble(String.valueOf((char) value)));
        DOUBLE_MAP.put(BigDecimal.class.getName(), (value) -> ((BigDecimal) value).doubleValue());
    }

    public static Double getDouble(Object value) {
        return objectToT(value, DOUBLE_MAP, Double.class);
    }
    //endregion

    //region byte
    static final Map<String, Function<Object, Byte>> BYTE_MAP;

    static {
        BYTE_MAP = new HashMap<>();
        BYTE_MAP.put(int.class.getName(), (value) -> ((Integer) value).byteValue());
        BYTE_MAP.put(long.class.getName(), (value) -> ((Long) value).byteValue());
        BYTE_MAP.put(float.class.getName(), (value) -> ((Float) value).byteValue());
        BYTE_MAP.put(double.class.getName(), (value) -> ((Double) value).byteValue());
        BYTE_MAP.put(byte.class.getName(), (value) -> (Byte) value);
        BYTE_MAP.put(short.class.getName(), (value) -> ((Short) value).byteValue());
        BYTE_MAP.put(String.class.getName(), (value) -> Byte.parseByte((String) value));
        BYTE_MAP.put(boolean.class.getName(), (value) -> (((Boolean) value) ? (byte) 1 : (byte) 0));
        BYTE_MAP.put(char.class.getName(), (value) -> Byte.parseByte(String.valueOf((char) value)));
        BYTE_MAP.put(BigDecimal.class.getName(), (value) -> ((BigDecimal) value).byteValue());
    }

    public static Byte getByte(Object value) {
        return objectToT(value, BYTE_MAP, Byte.class);
    }

    //endregion

    //region short

    static final Map<String, Function<Object, Short>> SHORT_MAP;

    static {
        SHORT_MAP = new HashMap<>();
        SHORT_MAP.put(int.class.getName(), (value) -> ((Integer) value).shortValue());
        SHORT_MAP.put(long.class.getName(), (value) -> ((Long) value).shortValue());
        SHORT_MAP.put(float.class.getName(), (value) -> ((Float) value).shortValue());
        SHORT_MAP.put(double.class.getName(), (value) -> ((Double) value).shortValue());
        SHORT_MAP.put(byte.class.getName(), (value) -> ((Byte) value).shortValue());
        SHORT_MAP.put(short.class.getName(), (value) -> (Short) value);
        SHORT_MAP.put(String.class.getName(), (value) -> Short.parseShort((String) value));
        SHORT_MAP.put(boolean.class.getName(), (value) -> ((Boolean) value) ? (short) 1 : (short) 0);
        SHORT_MAP.put(char.class.getName(), (value) -> Short.parseShort(String.valueOf((char) value)));
        SHORT_MAP.put(BigDecimal.class.getName(), (value) -> ((BigDecimal) value).shortValue());
    }

    public static Short getShort(Object value) {
        return objectToT(value, SHORT_MAP, Short.class);
    }
    //endregion

    //region boolean

    static final Map<String, Function<Object, Boolean>> BOOLEAN_MAP;
    public static final String[] TRUE_STRING = {"1", "true", "t", "yes", "y"};
    public static final String[] FALSE_STRING = {"0", "false", "f", "no", "n", "not"};

    static {
        BOOLEAN_MAP = new HashMap<>();
        BOOLEAN_MAP.put(int.class.getName(), (value) -> ((Integer) value).equals(1));
        BOOLEAN_MAP.put(long.class.getName(), (value) -> ((Long) value) == 1);
        BOOLEAN_MAP.put(float.class.getName(), (value) -> ((Float) value) == 1);
        BOOLEAN_MAP.put(double.class.getName(), (value) -> ((Double) value) == 1);
        BOOLEAN_MAP.put(byte.class.getName(), (value) -> ((Byte) value) == 1);
        BOOLEAN_MAP.put(short.class.getName(), (value) -> ((Short) value) == 1);
        BOOLEAN_MAP.put(String.class.getName(), (value) -> {
            String v = ((String) value).toLowerCase().trim();
            if (Arrays.asList(TRUE_STRING).contains(v)) {
                return true;
            }
            else if (Arrays.asList(FALSE_STRING).contains(v)) {
                return false;
            }
            return Boolean.parseBoolean((String) value);
        });
        BOOLEAN_MAP.put(boolean.class.getName(), (value) -> (Boolean) value);
        BOOLEAN_MAP.put(char.class.getName(), (value) -> getBoolean(String.valueOf((char) value)));
        BOOLEAN_MAP.put(BigDecimal.class.getName(), (value) -> ((BigDecimal) value).intValue() == 1);

    }

    public static Boolean getBoolean(Object value) {
        return objectToT(value, BOOLEAN_MAP, Boolean.class);
    }
    //endregion

    //region char
    public static String getChar(Object value) {
        throw new RuntimeException("不支持Char转换" + value);
    }
    //endregion

    //region String

    static final Map<String, Function<Object, String>> STRING_MAP;

    static {
        STRING_MAP = new HashMap<>();
        STRING_MAP.put(int.class.getName(), (value) -> Integer.toString((int) value));
        STRING_MAP.put(LocalDateTime.class.getName(), (value) -> ((LocalDateTime) value).toString());
        STRING_MAP.put(Timestamp.class.getName(), (value) -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(((Timestamp) value)));
    }

    public static void setCustomConversionToString(Function<Object, String> func) {
        customConversionToString = func;
    }

    private static Function<Object, String> customConversionToString;

    public static String getString(Object value) {
        if (customConversionToString == null) {
            try {
                return objectToT(value, STRING_MAP, String.class);
            }
            catch (Exception ex) {
                return value.toString();
            }
        }
        else {
            return customConversionToString.apply(value);
        }
    }
    //endregion

    //region BigDecimal
    static final Map<String, Function<Object, BigDecimal>> BIG_DECIMAL_MAP;

    static {
        BIG_DECIMAL_MAP = new HashMap<>();
        BIG_DECIMAL_MAP.put(int.class.getName(), (value) -> new BigDecimal((int) value));
        BIG_DECIMAL_MAP.put(long.class.getName(), (value) -> new BigDecimal((long) value));
        BIG_DECIMAL_MAP.put(float.class.getName(), (value) -> BigDecimal.valueOf((float) value));
        BIG_DECIMAL_MAP.put(double.class.getName(), (value) -> BigDecimal.valueOf((double) value));
        BIG_DECIMAL_MAP.put(byte.class.getName(), (value) -> new BigDecimal((byte) value));
        BIG_DECIMAL_MAP.put(short.class.getName(), (value) -> new BigDecimal((short) value));
        BIG_DECIMAL_MAP.put(String.class.getName(), (value) -> new BigDecimal((String) value));
        BIG_DECIMAL_MAP.put(boolean.class.getName(), (value) -> new BigDecimal((Boolean) value ? 1 : 0));
        BIG_DECIMAL_MAP.put(char.class.getName(), (value) -> new BigDecimal(String.valueOf((char) value)));
        BIG_DECIMAL_MAP.put(BigDecimal.class.getName(), (value) -> (BigDecimal) value);
    }

    public static BigDecimal getBigDecimal(Object value) {
        return objectToT(value, BIG_DECIMAL_MAP, BigDecimal.class);
    }

    //endregion

    //region LocalDateTime
    static final Map<String, Function<Object, LocalDateTime>> LOCAL_DATE_TIME_MAP;

    static {
        LOCAL_DATE_TIME_MAP = new HashMap<>();
        LOCAL_DATE_TIME_MAP.put(int.class.getName(), (value) -> LocalDateTime.ofInstant(Instant.ofEpochMilli((int) value), ZoneId.systemDefault()));
        LOCAL_DATE_TIME_MAP.put(long.class.getName(), (value) -> LocalDateTime.ofInstant(Instant.ofEpochMilli((long) value), ZoneId.systemDefault()));
        LOCAL_DATE_TIME_MAP.put(float.class.getName(), (value) -> LocalDateTime.ofInstant(Instant.ofEpochMilli((int) value), ZoneId.systemDefault()));
        LOCAL_DATE_TIME_MAP.put(double.class.getName(), (value) -> LocalDateTime.ofInstant(Instant.ofEpochMilli((long) value), ZoneId.systemDefault()));
        LOCAL_DATE_TIME_MAP.put(byte.class.getName(), (value) -> LocalDateTime.ofInstant(Instant.ofEpochMilli((int) value), ZoneId.systemDefault()));
        LOCAL_DATE_TIME_MAP.put(short.class.getName(), (value) -> LocalDateTime.ofInstant(Instant.ofEpochMilli((int) value), ZoneId.systemDefault()));
        LOCAL_DATE_TIME_MAP.put(boolean.class.getName(), (value) -> {
            throw new RuntimeException("不支持转换为LocalDateTime");
        });
        LOCAL_DATE_TIME_MAP.put(char.class.getName(), (value) -> {
            throw new RuntimeException("不支持转换为LocalDateTime");
        });
        LOCAL_DATE_TIME_MAP.put(String.class.getName(), (value) -> {
            String str = ((String) value).trim();
            return LocalDateUtil.stringToDateTime(str);
        });
        LOCAL_DATE_TIME_MAP.put(BigDecimal.class.getName(), (value) -> LocalDateTime.ofInstant(Instant.ofEpochMilli(((BigDecimal) value).longValue()), ZoneId.systemDefault()));
        LOCAL_DATE_TIME_MAP.put(LocalDateTime.class.getName(), (value) -> (LocalDateTime) value);
        LOCAL_DATE_TIME_MAP.put(LocalDate.class.getName(), (value) -> LocalDateTime.from((LocalDate) value));
        LOCAL_DATE_TIME_MAP.put(LocalTime.class.getName(), (value) -> LocalDateTime.from((LocalTime) value));
        LOCAL_DATE_TIME_MAP.put(Timestamp.class.getName(), (value) -> ((Timestamp) value).toLocalDateTime());
    }

    //endregion

    public static LocalDateTime getLocalDateTime(Object value) {
        return objectToT(value, LOCAL_DATE_TIME_MAP, LocalDateTime.class);
    }


    public static LocalDate getLocalDate(Object value) {
        return getLocalDateTime(value).toLocalDate();
    }

    public static LocalTime getLocalTime(Object value) {
        return getLocalDateTime(value).toLocalTime();
    }

    public static Timestamp getTimestamp(Object value) {
        return Timestamp.valueOf(getLocalDateTime(value));
    }
}
