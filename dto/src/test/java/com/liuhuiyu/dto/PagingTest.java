package com.liuhuiyu.dto;


import java.util.ArrayList;
import java.util.List;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2022-05-28 14:57
 */
public class PagingTest {
    public void test() {
        List<Sort> list = new ArrayList<>();
        list.add(new Sort(0, "id", Sort.Direction.ASC));
        Sort sort = new Sort();
        sort.setIndex(1);
        sort.setName("cname");
        sort.setDirection(Sort.Direction.DESC);
        list.add(sort);
        Paging paging = new Paging();
        paging.setAllInOne(true);
        paging.setPageIndex(0);
        paging.setPageSize(10);
        paging.setSort(list);
        paging.getSort().forEach(sortItem -> System.out.println("" + sortItem.getIndex() + "" + sortItem.getDirection()));
        System.out.println(paging.getPageIndex());
    }
}