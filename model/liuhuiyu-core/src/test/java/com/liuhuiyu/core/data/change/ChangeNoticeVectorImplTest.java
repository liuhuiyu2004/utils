package com.liuhuiyu.core.data.change;

import com.liuhuiyu.test.TestBase;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-02-23 14:38
 */
class ChangeNoticeVectorImplTest extends TestBase {
    @Test
    public void test() throws InterruptedException {
        Aa1 aa1 = new Aa1("aa1");
        Random random = new Random();
        ChangeNoticeVectorImpl<Integer> changeNoticeVector = new ChangeNoticeVectorImpl<>("aaa", (b) -> {
            try {
                Thread.sleep(1000);
                LOG.info("{}:ChangeNoticeVectorImpl:{}",b.getSender(), b.getChangeData().data);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        ChangeNoticeBaseImpl<Integer> changeNoticeBase = new ChangeNoticeBaseImpl<>("bbb", (b) -> {
            try {
                Thread.sleep(1000);
                LOG.info("{}:ChangeNoticeVectorImpl:{}",b.getSender(), b.getChangeData().data);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        aa1.regMan(changeNoticeVector);
        aa1.regMan(changeNoticeBase);
        for (int i = 0; i < 15; i++) {
            aa1.setId(random.nextInt(), i);
        }
        Thread.sleep(100_000);
    }


    static class Aa1 {
        public void regMan(IChangeNotice<Integer> iChangeNotice) {
            this.changeNoticeMan.reg(iChangeNotice);
        }

        //        private final ChangeNoticeManage<Integer> changeNoticeManage;
//        private final ChangeNotice<Integer> changeNoticeCar;
        private final ChangeNotice<Integer> changeNoticeMan;


        String name;
        Integer id;

        public Aa1(String name) {
            this.name = name;
            this.changeNoticeMan = new ChangeNotice<>(this);
        }

        public void setId(Integer id, int i) {
            LOG.info("设置{}[{}]的值为{}", name, i, id);
            this.id = id;
            this.changeNoticeMan.changeNotice(id);
        }

        public String getName() {
            return this.name;
        }
    }
}