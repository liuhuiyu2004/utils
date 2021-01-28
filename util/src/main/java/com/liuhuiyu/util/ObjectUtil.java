package com.liuhuiyu.util;

/**
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


//    public static Float parseFloat(Object o) {
//        if (!check(o)) {
//            return null;
//        }
//        if (o instanceof Float) {
//            return (Float) o;
//        }
//        return Float.parseFloat(o.toString());
//    }
//
//    public static Double parseDouble(Object o) {
//        if (!check(o)) {
//            return null;
//        }
//        if (o instanceof Double) {
//            return (Double) o;
//        }
//        return Double.parseDouble(o.toString());
//    }
//
//    public static Boolean parseBoolean(Object o) {
//        if (!check(o)) {
//            return null;
//        }
//        if (o instanceof Boolean) {
//            return (Boolean) o;
//        }
//        return Boolean.parseBoolean(o.toString());
//    }
//
//    public static Date parseDate(Object o, String dateFormat) throws Exception {
//        if (!check(o)) {
//            return null;
//        }
//        if (o instanceof Date) {
//            return (Date) o;
//        }
//        if (o instanceof Long) {
//            return new Date((Long) o);
//        }
//        if (o instanceof String) {
//            return new SimpleDateFormat(dateFormat).parse((String) o);
//        }
//        throw new Exception("Sorry,I don't know how to parse this value to a date");
//    }
//
//    public static <T> void clone(T t1, T t2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
//        Method[] t_methods = t1.getClass().getDeclaredMethods();
//        for (Method m : t_methods) {
//            if (m.getName().startsWith("set")) {
//                m.invoke(t2, new Object[]{
//                        t1.getClass().getMethod(m.getName().replaceFirst("s", "g"), null).invoke(t1, null)
//                });
//            }
//        }
//    }
}
