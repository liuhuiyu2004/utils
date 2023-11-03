package com.liuhuiyu.core.util;

import java.util.List;
import java.util.function.Function;

/**
 * 结构工具类
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-10-27 11:21
 */
public class StructureUtil {

    /**
     * id是树结构的节点id（包含当前节点对比）
     *
     * @param findId         要查找的id
     * @param nowId          当前Id
     * @param getChildIdList 当前id的子id列表获取
     * @return boolean
     * @author LiuHuiYu
     * Created DateTime 2023-10-27 11:11
     */
    public static <D> boolean idIsTreeChildId(D findId, D nowId, Function<D, List<D>> getChildIdList) {
        if (findId.equals(nowId)) {
            return true;
        }
        for (D id : getChildIdList.apply(nowId)) {
            if (id.equals(findId) || idIsTreeChildId(findId, id, getChildIdList)) {
                return true;
            }
        }
        return false;
    }
}
