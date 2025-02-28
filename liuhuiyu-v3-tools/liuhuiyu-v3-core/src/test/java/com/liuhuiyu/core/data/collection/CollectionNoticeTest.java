package com.liuhuiyu.core.data.collection;

import com.liuhuiyu.core.thread.ThreadPoolExecutorBuilder;
import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.Test;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-03-31 7:51
 */
class CollectionNoticeTest extends AbstractTest {
    @Test
    public void test() throws InterruptedException {
        Aa1 aa1 = new Aa1("Ak1");
        CollectionConsumerBaseImpl<Integer> baseConsumer1 = new CollectionConsumerBaseImpl<>("C1", (data) -> this.outPrint("C1", data, 1000));
        baseConsumer1.setExecutorService(ThreadPoolExecutorBuilder.create().threadName("C1-").builder());
        CollectionConsumerBaseImpl<Integer> baseConsumer2 = new CollectionConsumerBaseImpl<>("C2", (data) -> this.outPrint("C2", data, 100));
        baseConsumer2.setExecutorService(ThreadPoolExecutorBuilder.create().threadName("C2-").builder());
        CollectionConsumerVectorImpl<Integer> vectorConsumer1 = new CollectionConsumerVectorImpl<>("V1", (data) -> this.outPrint("V1", data, 1000));
        vectorConsumer1.setExecutorService(ThreadPoolExecutorBuilder.create().threadName("V1-").builder());
        CollectionConsumerVectorImpl<Integer> vectorConsumer2 = new CollectionConsumerVectorImpl<>("V2", (data) -> this.outPrint("V2", data, 1200));
        vectorConsumer2.setExecutorService(ThreadPoolExecutorBuilder.create().threadName("V2-").builder());
        aa1.collectionNoticeReg(baseConsumer1);
        aa1.collectionNoticeReg(baseConsumer2);
        aa1.collectionNoticeReg(vectorConsumer1);
        aa1.collectionNoticeReg(vectorConsumer2);
//        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            aa1.setId(i, i);
        }
        Thread.sleep(100_000);
    }

    private <T> void outPrint(String name, CollectionConsumerData<T> data, long time) {
        try {
            Thread.sleep(time);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        LOG.info("{}->{};{};{};{}",data.getSender(), name, data.getCollectionData().getData(), data.getCollectionData().getCollectionModel(),data.getCollectionData().isCollectionModel("abc"));
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
            this.collectionNotice.collectionNotice(id);
        }

        public String getName() {
            return this.name;
        }

    }
}