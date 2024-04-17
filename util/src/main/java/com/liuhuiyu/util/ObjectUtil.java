package com.liuhuiyu.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 此函数已经作废 尽量使用
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-01-28 8:23
 */
public class ObjectUtil {
    public static boolean isNull(Object o) {
        return o == null;
    }

    public static boolean equals(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        return o1.equals(o2);
    }

    public static Integer parseInteger(Object o) {
        return parseInteger(o, 0, 0);
    }

    public static Integer parseInteger(Object o, Integer defValue, Integer nullValue) {
        if (isNull(o)) {
            return nullValue;
        }
        if (o instanceof Integer) {
            return (Integer) o;
        } else {
            try {
                return Integer.parseInt(o.toString());
            } catch (Exception ex) {
                return defValue;
            }
        }
    }

    public static Long parseLong(Object o) {
        return parseLong(o.toString(), 0L, 0L);
    }

    public static Long parseLong(Object o, Long defValue, Long nullValue) {
        if (isNull(o)) {
            return nullValue;
        }
        if (o instanceof Long) {
            return (Long) o;
        } else {
            try {
                return Long.parseLong(o.toString());
            } catch (Exception ex) {
                return defValue;
            }
        }
    }

    public static String parseString(Object o) {
        return parseString(o, "", "");
    }

    public static String parseString(Object o, String defValue, String nullValue) {
        if (isNull(o)) {
            return nullValue;
        } else {
            if (o instanceof String) {
                return (String) o;
            } else {
                return o.toString();
            }
        }
    }
}
