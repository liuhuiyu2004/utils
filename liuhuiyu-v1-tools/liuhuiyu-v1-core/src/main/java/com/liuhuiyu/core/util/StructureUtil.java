package com.liuhuiyu.core.util;

import com.liuhuiyu.core.util.model.TreeStructure;

import java.util.List;
import java.util.Optional;
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

    /**
     * 获取树结构路径
     *
     * @param parentId      上级树编号id
     * @param pathDelimiter 分隔符
     * @param getParentTree 根据id获取指定id的树结构基础信息
     * @return java.lang.String
     * Created DateTime 2023-11-03 12:18
     */
    public static <T> String getTreePath(T parentId, String pathDelimiter, Function<T, Optional<TreeStructure<T>>> getParentTree) {
        StringBuilder pathBuilder = new StringBuilder();
        T tmpId = parentId;
        while (tmpId != null) {
            Optional<TreeStructure<T>> parent = getParentTree.apply(tmpId);
            if (parent.isPresent() && parent.get().getName() != null) {
                pathBuilder.insert(0, pathDelimiter + parent.get().getName());
                tmpId = parent.get().getParentId();
            }
            else {
                break;
            }
        }
        return pathBuilder.toString();
    }
}
