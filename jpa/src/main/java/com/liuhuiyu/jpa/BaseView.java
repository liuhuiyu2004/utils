package com.liuhuiyu.jpa;


import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2020-07-07 10:03
 */
@Log4j2
//@Component
public abstract class BaseView {
    private final EntityManagerFactory emf;
    EntityManager em;

    public BaseView(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        if (em == null) {
            synchronized (BaseView.class) {
                if (em == null) {
                    em = emf.createEntityManager();
                }
            }
        }
        if (!em.isOpen()) {
            em = emf.createEntityManager();
        }
        return em;
    }

    protected List<?> getQueryList(String sql, Map<String, Object> parameterMap, Pageable pageable) {
        Query query = this.getEntityManager().createNativeQuery(sql);
        if (parameterMap != null) {
            for (String key : parameterMap.keySet()) {
                query.setParameter(key, parameterMap.get(key));
            }
        }
        if (pageable != null) {
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        }
        return query.getResultList();
    }

    protected Long getCount(String sql, @NotNull Map<String, Object> parameterMap) {
        Query query = this.getEntityManager().createNativeQuery(sql);
        for (String key : parameterMap.keySet()) {
            query.setParameter(key, parameterMap.get(key));
        }
        Object obj = query.getSingleResult();
        return Long.parseLong(obj.toString());
    }

    /**
     * 第一条信息的第一列查询
     *
     * @param baseSql      语句
     * @param parameterMap 参数map
     * @return 第一条信息的第一列
     */
    protected Object getSingleResult(String baseSql, Map<String, Object> parameterMap) {
        Query query = this.getEntityManager().createNativeQuery(baseSql);
        if (parameterMap != null) {
            for (String key : parameterMap.keySet()) {
                query.setParameter(key, parameterMap.get(key));
            }
        }
        try {
            return query.getSingleResult();
        }
        catch (Exception ex) {
            return null;
        }
    }

    /**
     * 将查询字符串转换成like字符串
     *
     * @param value 原始字符串
     * @return like字符串
     */
    protected String getLikeString(String value) {
        return "%" + value + "%";
    }

    protected <T> List<T> getList(DaoOperator<T> b, String sql, Map<String, Object> parameterMap, Pageable pageable) {
        List<?> list = getQueryList(sql, parameterMap, pageable);
        List<T> resList = new ArrayList<>();
        for (Object obj : list) {
            resList.add(b.objectToT(obj));
        }
        return resList;
    }

    protected <T> T getSingle(DaoOperator<T> b, String sql, Map<String, Object> parameterMap) {
        Object obj = getSingleResult(sql, parameterMap);
        return b.objectToT(obj);
    }
}
