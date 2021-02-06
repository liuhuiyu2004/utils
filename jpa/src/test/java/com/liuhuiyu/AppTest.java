package com.liuhuiyu;

import static org.junit.Assert.assertTrue;

import com.liuhuiyu.jpa.DaoOperator;
import lombok.Data;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        Object obj = new Object[]{1, "abc"};
        Abc abc = new Abc(obj);
        Abc def=objToClass(Abc::new,obj);
        assertTrue(true);
    }

    private <T> T objToClass(DaoOperator<T> daoOperator,Object obj){
        return daoOperator.objectToT(obj);
    }

    @Data
    static class Abc {
        int index;
        String name;

        public Abc(Object obj) {
            Object[] objects = (Object[]) obj;
            index = (int) objects[0];
            name = objects[1].toString();
        }
    }
}
