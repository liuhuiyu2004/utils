package com.liuhuiyu.view.view;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.function.Function;

/**
 * 顺序获取对象序列
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-03-09 17:30
 */
public class ObjectArray {
    private final Object[] objects;
    private int index = 0;

    public ObjectArray(Object obj) {
        this.objects = (Object[]) obj;
    }

    public void reset() {
        this.index = 0;
    }

    public Object get() {
        if (this.index >= objects.length) {
            throw new RuntimeException("当前要获取的数据数量" + (this.index + 1) + "超出了原始数据数量" + this.objects.length);
        }
        return objects[this.index++];
    }

    public String getString() {
        return getString("");
    }

    public String getString(String defValue) {
        return getT((obj) -> {
            if (obj instanceof String) {
                return (String) obj;
            }
            else if (obj instanceof Clob) {
                Clob clob = (Clob) obj;
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    final long length = clob.length();
                    int len = 1024;
                    for (long pos = 1; pos <= length; pos += len) {
                        len = (length - (pos-1)) >= len ? len : (int) (length - (pos-1));
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
            else {
                return obj.toString();
            }
        }, defValue);
    }

    public Integer getInteger() {
        return getInteger(0);
    }

    public Integer getInteger(Integer defValue) {
        return getT((obj) -> {
            if (obj instanceof Number) {
                return ((Number) obj).intValue();
            }
            else {
                return Integer.parseInt(obj.toString());
            }
        }, defValue);
    }

    public Long getLong() {
        return getLong(0L);
    }

    public Long getLong(Long defValue) {
        return getT((obj) -> {
            if (obj instanceof Number) {
                return ((Number) obj).longValue();
            }
            else {
                return Long.parseLong(obj.toString());
            }
        }, defValue);
    }

    public Boolean getBoolean() {
        return getBoolean(false);
    }

    public Boolean getBoolean(Boolean defValue) {
        return getT((obj) -> {
            if (obj instanceof Boolean) {
                return (Boolean) obj;
            }
            else if (obj instanceof Number) {
                return ((Number) obj).intValue() == 1;
            }
            else if (obj instanceof String) {
                String v = (String) obj;
                return "y".equals(v.toLowerCase(Locale.ROOT)) ||
                        "true".equals(v.toLowerCase(Locale.ROOT)) ||
                        !"0".equals(v.toLowerCase(Locale.ROOT));
            }
            else {
                return Boolean.parseBoolean(obj.toString());
            }
        }, defValue);
    }

    public BigDecimal getBigDecimal() {
        return getBigDecimal(new BigDecimal("0"));
    }

    public BigDecimal getBigDecimal(BigDecimal defValue) {
        return getT((obj) -> {
            if (obj instanceof BigDecimal) {
                return (BigDecimal) obj;
            }
            else if (obj instanceof Number) {
                return new BigDecimal((obj).toString());
            }
            else {
                return new BigDecimal(obj.toString());
            }
        }, defValue);
    }


    public Timestamp getTimestamp() {
        return getTimestamp(Timestamp.valueOf(LocalDateTime.MIN));
    }

    public Timestamp getTimestamp(Timestamp defValue) {
        return getT((obj) -> {
            if (obj instanceof Timestamp) {
                return (Timestamp) obj;
            }
            if ("oracle.sql.TIMESTAMP".equals(obj.getClass().getName())) {
                return Timestamp.valueOf(obj.toString());
            }
            else {
                return defValue;
            }
        }, defValue);
    }


    public BigInteger getBigInteger() {
        return getBigInteger(BigInteger.ZERO);
    }

    public BigInteger getBigInteger(BigInteger defValue) {
        return getT((obj) -> {
            if (obj instanceof BigInteger) {
                return (BigInteger) obj;
            }
            if (obj instanceof Number) {
                return BigInteger.valueOf(((Number) obj).longValue());
            }
            else {
                return defValue;
            }
        }, defValue);
    }

    private <T> T getT(Function<Object, T> function, T defValue) {
        Object obj = this.get();
        if (obj == null) {
            return defValue;
        }
        else {
            try {
                return function.apply(obj);
            }
            catch (Exception ex) {
                return defValue;
            }
        }
    }
}
