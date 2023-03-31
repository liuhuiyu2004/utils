package com.liuhuiyu.core.data.collection;

import com.liuhuiyu.test.TestBase;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-03-31 7:51
 */
class CollectionNoticeTest extends TestBase {
    @Test
    public void test() throws InterruptedException {
        Aa1 aa1 = new Aa1("Ak1");
        CollectionConsumerBaseImpl<Integer> baseConsumer1 = new CollectionConsumerBaseImpl<>("C1", (data) -> this.outPrint("C1", data,1000));
        CollectionConsumerBaseImpl<Integer> baseConsumer2 = new CollectionConsumerBaseImpl<>("C2", (data) -> this.outPrint("C2", data,100));
        CollectionConsumerVectorImpl<Integer> vectorConsumer1 = new CollectionConsumerVectorImpl<>("V2", (data) -> this.outPrint("V2", data,1000));
        aa1.collectionNoticeReg(baseConsumer1);
        aa1.collectionNoticeReg(baseConsumer2);
        aa1.collectionNoticeReg(vectorConsumer1);
//        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            aa1.setId(i, i);
        }
        Thread.sleep(100_000);
    }

    private <T> void outPrint(String name, CollectionConsumerData<T> data,long time) {
        try {
            Thread.sleep(time);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        LOG.info("{};{};{}", name, data.getCollectionData().getData(), data.getCollectionData().getCollectionModel());
    }

    static class Aa1 implements ICollectionNoticeReg<Integer> {

        //        private final ChangeNoticeManage<Integer> changeNoticeManage;
//        private final ChangeNotice<Integer> changeNoticeCar;
        private final CollectionNotice<Integer> collectionNotice;

        @Override
        public void collectionNoticeReg(ICollectionConsumer<Integer> iCollectionConsumer) {
            this.collectionNotice.collectionNoticeReg(iCollectionConsumer);
        }

        @Override
        public void collectionNoticeUnReg(String collectionConsumerKey) {
            this.collectionNotice.collectionNoticeUnReg(collectionConsumerKey);
        }


        String name;
        Integer id;

        public Aa1(String name) {
            this.name = name;
            this.collectionNotice = new CollectionNotice<>(this);
        }

        public void setId(Integer id, int i) {
            LOG.info("设置{}[{}]的值为{}", name, i, id);
            this.id = id;
            this.collectionNotice.collectionNotice(id, "ssssssssss");
        }

        public String getName() {
            return this.name;
        }

    }
}