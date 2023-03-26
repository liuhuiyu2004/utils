package com.liuhuiyu.jpa.oracle.dao;

import com.liuhuiyu.dto.Sort;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * oracle 排序生成
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-03-26 21:17
 */
public class OracleOrder {
    Map<String, String> orderMap = new HashMap<>();
    String defOrder;

    /**
     * 排序
     *
     * @param defOrder 默认排序字段及排序
     * @author LiuHuiYu
     * Created DateTime 2023-03-26 21:40
     */
    public OracleOrder(String defOrder) {
        this.defOrder = defOrder;
    }

    public OracleOrder(String field, Sort.Direction direction) {
        this.defOrder = field + " " + direction;
    }

    public void addOrder(String key, String value) {
        orderMap.put(key, value);
    }

    public String getOrder(List<Sort> sortList) {
        StringBuilder orderBuilder = new StringBuilder(" ORDER BY ");
        AtomicReference<String> delimiter = new AtomicReference<>("");
        if (sortList != null && sortList.size() > 0) {
            sortList.stream().sorted(Comparator.comparingInt(Sort::getIndex))
                    .forEachOrdered(v -> {
                        if (orderMap.containsKey(v.getName())) {
                            orderBuilder
                                    .append(delimiter)
                                    .append(orderMap.get(v.getName()))
                                    .append(" ")
                                    .append(v.getDirection());
                            delimiter.set(",");
                        }
                    });
        }
        if (!StringUtils.hasText(delimiter.get())) {
            if (StringUtils.hasText(this.defOrder)) {
                orderBuilder.append(defOrder);
            }
            else {
                return "";
            }
        }
        return orderBuilder.toString();
    }
}
