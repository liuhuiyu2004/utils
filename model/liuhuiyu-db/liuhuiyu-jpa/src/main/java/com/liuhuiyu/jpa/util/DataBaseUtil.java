package com.liuhuiyu.jpa.util;

import com.liuhuiyu.core.util.IgnoredException;
import com.liuhuiyu.util.ObjectToFieldValue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * 数据库工具类
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-10-19 14:09
 */
public class DataBaseUtil {
    public static <T> T rowToT(ResultSet rs, Class<T> clazz) {
        T obj;
        try {
            obj = clazz.newInstance();
            final Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                int columnIndex = IgnoredException.getOrElse(() -> rs.findColumn(field.getName()), -1);
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod) || columnIndex < 0) {
                    continue;
                }
                Object rowValue = rs.getObject(columnIndex);
                final Optional<Object> valueOfFieldType = new ObjectToFieldValue(field, rowValue).getValueOfFieldType();
                if (valueOfFieldType.isPresent()) {
                    field.setAccessible(true);
                    field.set(obj, valueOfFieldType.get());
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static <T> T objToT(Object objData, Class<T> clazz, SqlResolution sqlResolution) {
        T obj;
        if (objData instanceof Object[]) {
            Object[] objArray = (Object[]) objData;
            try {
                obj = clazz.newInstance();
                final Field[] fields = obj.getClass().getDeclaredFields();
                for (Field field : fields) {
                    int mod = field.getModifiers();
                    int columnIndex = sqlResolution.getFieldIndex(field.getName());
                    if (Modifier.isStatic(mod) || Modifier.isFinal(mod) || columnIndex < 0 || columnIndex >= objArray.length) {
                        continue;
                    }
                    Object rowValue = objArray[columnIndex];
                    final Optional<Object> valueOfFieldType = new ObjectToFieldValue(field, rowValue).getValueOfFieldType();
                    if (valueOfFieldType.isPresent()) {
                        field.setAccessible(true);
                        field.set(obj, valueOfFieldType.get());
                    }
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else {
//            Object objArray = (Object) objData;
            try {
                obj = clazz.newInstance();
                final Field[] fields = obj.getClass().getDeclaredFields();
                for (Field field : fields) {
                    int mod = field.getModifiers();
                    int columnIndex = sqlResolution.getFieldIndex(field.getName());
                    if (Modifier.isStatic(mod) || Modifier.isFinal(mod) || columnIndex != 0) {
                        continue;
                    }
//                    Object rowValue = objData;
                    final Optional<Object> valueOfFieldType = new ObjectToFieldValue(field, objData).getValueOfFieldType();
                    if (valueOfFieldType.isPresent()) {
                        field.setAccessible(true);
                        field.set(obj, valueOfFieldType.get());
                    }
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
//            return (T) new ObjectToFieldValue(ObjectToFieldValue.getTypeName(clazz), objData).getValueOfFieldType().orElseGet(null);
        }

        return obj;
    }

    /**
     * 使用参数值Map，填充pStat
     *
     * @param pStat PreparedStatement
     * @param list  命名参数的值表，其中的值可以比较所需的参数多。
     *              Created DateTime 2021-03-22 14:10
     */
    public static void fillParameters(PreparedStatement pStat, List<Object> list) {
        setParameters(pStat, list.toArray());
//        for (int i = 0; i < list.size(); i++) {
//            try {
//                pStat.setObject(i, list.get(i));
//            }
//            catch (SQLException throwables) {
//                throw new RuntimeException("填充参数出错，原因：", throwables);
//            }
//        }
    }

    public static void setParameters(PreparedStatement stmt, Object... values) {
        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                try {
                    setParameter(stmt, (i + 1), values[i]);
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void setParameter(PreparedStatement stmt, int index, Object value) throws SQLException {
        if (value == null) {
            stmt.setNull(index, Types.NULL);
        }
        else if (value instanceof String) {
            stmt.setString(index, (String) value);
        }
        else if (value instanceof Integer) {
            stmt.setInt(index, (Integer) value);
        }
        else if (value instanceof Double) {
            stmt.setDouble(index, (Double) value);
        }
        else if (value instanceof byte[]) {
            stmt.setBytes(index, (byte[]) value);
        }
        else if (value instanceof Boolean) {
            stmt.setBoolean(index, (Boolean) value);
        }
        else if (value instanceof Float) {
            stmt.setFloat(index, (Float) (value));
        }
        else if (value instanceof BigDecimal) {
            stmt.setDouble(index, ((BigDecimal) value).doubleValue());
        }
        else if (value instanceof Timestamp) {
            stmt.setTimestamp(index, (Timestamp) value);
        }
        else if (value instanceof Long) {
            stmt.setLong(index, (Long) value);
        }
        else {
            throw new SQLException("使用了没有处理过的字段类型 - " + value.getClass().getName());
        }
    }

    /**
     * 将指定值转换为与类字段相同类型的值
     *
     * @param field 字段
     * @param value 值
     * @return java.lang.Object
     * @author LiuHuiYu
     * Created DateTime 2023-07-28 16:19
     */
    private static Object getValue(Field field, Object value) {
        if (value == null) {
            return null;
        }
        if (field.getType().getName().equals(String.class.getName())) {
            return value.toString();
        }
        if (value instanceof BigDecimal) {
            BigDecimal v = (BigDecimal) value;
            if (field.getType().getName().equals(int.class.getName()) ||
                    field.getType().getName().equals(Integer.class.getName())) {
                return v.intValue();
            }
            else if (field.getType().getName().equals(long.class.getName()) ||
                    field.getType().getName().equals(Long.class.getName())) {
                return v.longValue();
            }
            else if (field.getType().getName().equals(float.class.getName()) ||
                    field.getType().getName().equals(Float.class.getName())) {
                return v.floatValue();
            }
            else if (field.getType().getName().equals(double.class.getName()) ||
                    field.getType().getName().equals(Double.class.getName())) {
                return v.doubleValue();
            }
            else if (field.getType().getName().equals(byte.class.getName()) ||
                    field.getType().getName().equals(Byte.class.getName())) {
                return v.byteValue();
            }
            else if (field.getType().getName().equals(short.class.getName()) ||
                    field.getType().getName().equals(Short.class.getName())) {
                return v.shortValue();
            }
        }
        return value;
    }
}
