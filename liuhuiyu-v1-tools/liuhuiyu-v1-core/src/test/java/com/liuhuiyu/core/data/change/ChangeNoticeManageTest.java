package com.liuhuiyu.core.data.change;

import com.liuhuiyu.core.util.IgnoredException;
import com.liuhuiyu.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-02-07 14:34
 */
class ChangeNoticeManageTest extends AbstractTest {
    public static final int STOP_TIME = 1000;

    @Test
    public void test() throws InterruptedException {
        Aa1 aa1 = new Aa1("aa");

//        A a = new A(10);
        new B(aa1);
        Random random = new Random();
        int max = 1000;
        for (int i = 0; i < max; i++) {
//            a.setAa1Id(random.nextInt(9), random.nextInt(100));
            aa1.setId(random.nextInt(100), i);
//            aa1.setId(0);
        }
        Thread.sleep(STOP_TIME * max);
    }


    static class Aa1 {
        public void regMan(IChangeNotice<Integer> iChangeNotice) {
            this.changeNoticeMan.reg(iChangeNotice);
        }
//        public void regCar(IChangeNotice<Integer> iChangeNotice) {
//            this.changeNoticeCar.reg(iChangeNotice);
//        }

        //        private final ChangeNoticeManage<Integer> changeNoticeManage;
//        private final ChangeNotice<Integer> changeNoticeCar;
        private final ChangeNotice<Integer> changeNoticeMan;

//        public ChangeNoticeManage<Integer> getChangeNoticeManage() {
//            return this.changeNoticeManage;
//        }

        String name;
        Integer id;

        public Aa1(String name) {
            this.name = name;
//            this.changeNoticeCar = new ChangeNotice<>(this);
            this.changeNoticeMan = new ChangeNotice<>(this);
//            this.changeNoticeManage = new ChangeNoticeManage<>(changeNotice);
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

    /**
     * 我是通知接受者
     *
     * @author LiuHuiYu
     * Created DateTime 2023-02-16 18:56
     */
    static class B {
        Bb bb;
        Bb1 bb1;

        public B(Aa1 a) {
            bb = new Bb();
            bb1 = new Bb1();
            a.regMan(bb1);
        }

        private void ss() {
        }

        class Bb implements IChangeNotice<Integer> {
            @Override
            public String getKey() {
                return "VBb";
            }

            @Override
            public void changeNotice(Object sender, ChangeData<Integer> changeData) {
                Aa1 a = (Aa1) sender;
                LOG.info("开始{}:{}数据变更：{}", getKey(), a.getName(), changeData.data);
                try {
                    Thread.sleep(STOP_TIME);
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                LOG.info("结束{}:{}数据变更：{}", getKey(), a.getName(), changeData.data);
                ss();
            }
        }

        static class Bb1 implements IChangeNotice<Integer> {
            @Override
            public String getKey() {
                return "VBc";
            }

            @Override
            public void changeNotice(Object sender, ChangeData<Integer> changeData) {
                IgnoredException.run(() -> {
                    Aa1 a = (Aa1) sender;
                    LOG.info("开始{}:{}数据变更：{}", getKey(), a.getName(), changeData.data);
                    try {
                        Thread.sleep(STOP_TIME);
                    }
                    catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    LOG.info("结束{}:{}数据变更：{}", getKey(), a.getName(), changeData.data);
//                    throw new OutOfMemoryError("");
                }, (ex) -> LOG.error("我是异常信息", ex));
            }
        }
    }


    /**
     * 我是通知接受者
     *
     * @author LiuHuiYu
     * Created DateTime 2023-02-16 18:56
     */
    static class Ka {
        Bb bb;
        Bb1 bb1;

        public Ka(Aa1 a) {
            bb = new Bb();
            bb1 = new Bb1();
            a.regMan(bb1);
        }

        private void ss() {
        }

        class Bb implements IChangeNotice<Integer> {
            @Override
            public String getKey() {
                return "VBb";
            }

            @Override
            public void changeNotice(Object sender, ChangeData<Integer> changeData) {
                Aa1 a = (Aa1) sender;
                LOG.info("{}:{}数据变更：{}", getKey(), a.getName(), changeData.data);
                ss();
            }
        }

        static class Bb1 implements IChangeNotice<Integer> {
            @Override
            public String getKey() {
                return "VBb";
            }

            @Override
            public void changeNotice(Object sender, ChangeData<Integer> changeData) {
                IgnoredException.run(() -> {
                    Aa1 a = (Aa1) sender;
                    LOG.info("{}:{}数据变更：{}", getKey(), a.getName(), 16 / changeData.data);
//                    throw new OutOfMemoryError("");
                }, (ex) -> LOG.error("我是异常信息", ex));
            }
        }
    }
//
//    /**
//     * 我是通知接受者
//     *
//     * @author LiuHuiYu
//     * Created DateTime 2023-02-16 18:56
//     */
//    static class B2 {
//        Bb bb;
//
//        public B2(A a) {
//            bb = new Bb();
//            a.reg(bb);
//        }
//
//        static class Bb implements IChangeNotice<Integer> {
//            @Override
//            public String getKey() {
//                return "VBb2";
//            }
//
//            @Override
//            public void changeNotice(Object sender, ChangeData<Integer> changeData) {
//                Aa1 a = (Aa1) sender;
//                LOG.info("{}:{}数据变更：{}",getKey(), a.getName(), changeData.data);
//            }
//        }
//    }
}