package com.liuhuiyu.core.data.change;

import com.liuhuiyu.core.util.Assert;
import com.liuhuiyu.test.TestBase;
import org.junit.jupiter.api.Test;

/**
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-02-07 14:34
 */
class ChangeNoticeManageTest extends TestBase {
    @Test
    public void test() {
        A a = new A();
        new B(a, 1);
        new B(a, -1);
        B b3 = new B(a, 2);
        B b4 = new B(a, -3);
        B b5 = new B(a, -4);
        a.setId(1);
        a.setId(3);
        b3.unReg();
        b4.unReg();
        b5.unReg();
        a.setId(5);
    }

    static class A {
        private final ChangeNoticeManage<Integer> changeNoticeManage;
        private final ChangeNotice<Integer> changeNotice;

        public ChangeNoticeManage<Integer> getChangeNoticeManage() {
            return this.changeNoticeManage;
        }

        public A() {
            changeNotice = new ChangeNotice<>(this);
            changeNoticeManage = new ChangeNoticeManage<>(changeNotice);
        }

        public void setId(Integer id) {
            if (id < 0) {
                changeNotice.changeNotice(id, ChangeData.DataStatus.A);
            }
            else if (id < 5) {
                changeNotice.changeNotice(id, ChangeData.DataStatus.E);
            }
            else if (id < 100) {
                changeNotice.changeNotice(id, ChangeData.DataStatus.D);
            }
            else if (id < 500) {
                changeNotice.changeNotice(id, ChangeData.DataStatus.U, "other");
            }
            else {
                this.changeNotice.changeNotice(id);
            }
        }
    }

    static class B implements IChangeNotice<Integer> {
        A a;

        public B(A a, int id) {
            this.a = a;
            this.id = id;
            key = "v" + this.id;
            LOG.info("key:{}", key);
            a.getChangeNoticeManage().reg(this);
        }

        public void unReg() {
            a.getChangeNoticeManage().unReg(this);
        }

        Integer id;
        String key;

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public void changeNotice(Object sender, ChangeData<Integer> changeData) {
            if (id > 0) {
                Assert.assertTrue(changeData.getData() >= 3, "数字太小");
            }
            else {
                Assert.assertTrue(changeData.getData() <= 3, "数字太d大");
            }
            LOG.info("{}数据变更：{}", key, changeData.data);
        }
    }
}