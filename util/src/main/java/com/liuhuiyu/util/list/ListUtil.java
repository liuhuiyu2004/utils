package com.liuhuiyu.util.list;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象转换成List
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-04-14 10:59
 */
public class ListUtil {

    public static List<Object> objectToList(Object obj) {
        List<Object> resList;
        if (obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            resList=new ArrayList<>(list.size());
            resList.addAll(list);
        }
        else {
            throw new RuntimeException("无法转换");
        }
        return resList;
    }
}
