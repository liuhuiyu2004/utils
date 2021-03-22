//package com.liuhuiyu.view;
//
///**
// * @author LiuHuiYu
// * @version v1.0.0.0
// * Created DateTime 2021-01-22 10:21
// */
//@Deprecated
//public abstract class BaseView {
//
//    public static String objectToString(Object obj) {
//        return objectToString(obj,"");
//    }
//
//    public static Integer objectToInt(Object obj) {
//        return objectToInt(obj,0,0);
//    }
//
//    public static String objectToString(Object obj,String nullValue) {
//        if (obj == null) {
//            return nullValue;
//        }
//        else {
//            return obj.toString();
//        }
//    }
//
//    public static Integer objectToInt(Object obj,Integer nullValue,Integer errValue) {
//        if (obj == null) {
//            return nullValue;
//        }
//        else {
//            try {
//                return Integer.parseInt(obj.toString());
//            } catch (Exception e) {
//                return errValue;
//            }
//        }
//    }
//
//}
