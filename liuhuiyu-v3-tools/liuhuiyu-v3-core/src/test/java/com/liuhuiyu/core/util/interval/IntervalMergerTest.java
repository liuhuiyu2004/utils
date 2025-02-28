package com.liuhuiyu.core.util.interval;

import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LiuHuiYu * @version v1.0.0.0
 * Created DateTime 2024/7/14 11:02
 */
class IntervalMergerTest extends AbstractTest {

    @Test
    @DisplayName("测试列表区间")
    public void test() {
        List<Data> intervals = new ArrayList<>();
        intervals.add(new Data(10, 12));
        intervals.add(new Data(8, 10));
        intervals.add(new Data(1, 3));
        intervals.add(new Data(2, 7));
        intervals.add(new Data(2, 6));
        intervals.add(new Data(15, 18));
        intervals.add(new Data(18, 21));
        LOG.info("size num:{}", intervals.size());
        LOG.info("--------------------------------------");
        Optional<Interregional<Data>> intervalMerger1 = IntervalMerger.getFirstMergeIntervalsDetail(intervals, Comparator.comparing(Data::getB), (v, v1) -> v.getE().compareTo(v1.getB()), Comparator.comparing(Data::getE));
        intervalMerger1.ifPresent(v -> LOG.info("findFirst list num:{},min:{},max{}", v.getElementList().size(), v.getMinValue().getB(), v.getMaxValue().getE()));
        Optional<Interregional<Data>> intervalMerger2 = IntervalMerger.getFirstMergeIntervals(intervals, Data::getB, Data::getE, Integer::compareTo);
        intervalMerger2.ifPresent(v -> LOG.info("findFirst list num:{},min:{},max{}", v.getElementList().size(), v.getMinValue().getB(), v.getMaxValue().getE()));
        LOG.info("--------------------------------------");
        final List<Interregional<Data>> interregionals = IntervalMerger.mergeIntervalsDetail(intervals, Comparator.comparing(Data::getB), (v, v1) -> v.getE().compareTo(v1.getB()), Comparator.comparing(Data::getE));
        interregionals.forEach(intervalMerger ->
                LOG.info("list num:{},min:{},max{}", intervalMerger.getElementList().size(), intervalMerger.getMinValue().getB(), intervalMerger.getMaxValue().getE())
        );
        LOG.info("--------------------------------------");
        final List<Interregional<Data>> interregionals2 = IntervalMerger.mergeIntervals(intervals, Data::getB, Data::getE, Integer::compareTo);
        interregionals2.forEach(intervalMerger ->
                LOG.info("list num:{},min:{},max{}", intervalMerger.getElementList().size(), intervalMerger.getMinValue().getB(), intervalMerger.getMaxValue().getE())
        );
    }

    @Test
    @DisplayName("测试无元素列表区间")
    public void test2() {
        List<Data> intervals = new ArrayList<>();
        LOG.info("size num:{}", intervals.size());
        LOG.info("--------------------------------------");
        Optional<Interregional<Data>> intervalMerger1 = IntervalMerger.getFirstMergeIntervalsDetail(intervals, Comparator.comparing(Data::getB), (v, v1) -> v.getE().compareTo(v1.getB()), Comparator.comparing(Data::getE));
        intervalMerger1.ifPresent(v -> LOG.info("findFirst list num:{},min:{},max{}", v.getElementList().size(), v.getMinValue().getB(), v.getMaxValue().getE()));
        Optional<Interregional<Data>> intervalMerger2 = IntervalMerger.getFirstMergeIntervals(intervals, Data::getB, Data::getE, Integer::compareTo);
        intervalMerger2.ifPresent(v -> LOG.info("findFirst list num:{},min:{},max{}", v.getElementList().size(), v.getMinValue().getB(), v.getMaxValue().getE()));
        LOG.info("--------------------------------------");
        final List<Interregional<Data>> interregionals = IntervalMerger.mergeIntervalsDetail(intervals, Comparator.comparing(Data::getB), (v, v1) -> v.getE().compareTo(v1.getB()), Comparator.comparing(Data::getE));
        interregionals.forEach(intervalMerger ->
                LOG.info("list num:{},min:{},max{}", intervalMerger.getElementList().size(), intervalMerger.getMinValue().getB(), intervalMerger.getMaxValue().getE())
        );
        LOG.info("--------------------------------------");
        final List<Interregional<Data>> interregionals2 = IntervalMerger.mergeIntervals(intervals, Data::getB, Data::getE, Integer::compareTo);
        interregionals2.forEach(intervalMerger ->
                LOG.info("list num:{},min:{},max{}", intervalMerger.getElementList().size(), intervalMerger.getMinValue().getB(), intervalMerger.getMaxValue().getE())
        );
    }
    @Test
    @DisplayName("测试空列表区间")
    public void test3() {
        List<Data> intervals =null;
        LOG.info("size num: null");
        LOG.info("--------------------------------------");
        Optional<Interregional<Data>> intervalMerger1 = IntervalMerger.getFirstMergeIntervalsDetail(intervals, Comparator.comparing(Data::getB), (v, v1) -> v.getE().compareTo(v1.getB()), Comparator.comparing(Data::getE));
        intervalMerger1.ifPresent(v -> LOG.info("findFirst list num:{},min:{},max{}", v.getElementList().size(), v.getMinValue().getB(), v.getMaxValue().getE()));
        Optional<Interregional<Data>> intervalMerger2 = IntervalMerger.getFirstMergeIntervals(intervals, Data::getB, Data::getE, Integer::compareTo);
        intervalMerger2.ifPresent(v -> LOG.info("findFirst list num:{},min:{},max{}", v.getElementList().size(), v.getMinValue().getB(), v.getMaxValue().getE()));
        LOG.info("--------------------------------------");
        final List<Interregional<Data>> interregionals = IntervalMerger.mergeIntervalsDetail(intervals, Comparator.comparing(Data::getB), (v, v1) -> v.getE().compareTo(v1.getB()), Comparator.comparing(Data::getE));
        interregionals.forEach(intervalMerger ->
                LOG.info("list num:{},min:{},max{}", intervalMerger.getElementList().size(), intervalMerger.getMinValue().getB(), intervalMerger.getMaxValue().getE())
        );
        LOG.info("--------------------------------------");
        final List<Interregional<Data>> interregionals2 = IntervalMerger.mergeIntervals(intervals, Data::getB, Data::getE, Integer::compareTo);
        interregionals2.forEach(intervalMerger ->
                LOG.info("list num:{},min:{},max{}", intervalMerger.getElementList().size(), intervalMerger.getMinValue().getB(), intervalMerger.getMaxValue().getE())
        );
    }
}

class Data {
    Integer b;
    Integer e;

    public Data(Integer b, Integer e) {
        this.b = b;
        this.e = e;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    public Integer getE() {
        return e;
    }

    public void setE(Integer e) {
        this.e = e;
    }
}