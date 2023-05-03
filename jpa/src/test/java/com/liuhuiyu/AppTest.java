package com.liuhuiyu;

import com.liuhuiyu.jpa.DaoOperator;
import com.liuhuiyu.test.TestBase;
import org.junit.jupiter.api.Test;

import java.util.Collections;


/**
 * Unit test for simple App.
 */
public class AppTest extends TestBase {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        Object obj = new Object[]{1, "abc"};
        Abc abc = new Abc(obj);
        Abc def = objToClass(Abc::new, obj);
    }

    private <T> T objToClass(DaoOperator<T> daoOperator, Object obj) {
        return daoOperator.objectToT(obj);
    }


    static class Abc {
        int index;
        String name;

        public Abc(Object obj) {
            Object[] objects = (Object[]) obj;
            index = (int) objects[0];
            name = objects[1].toString();
        }
    }

    @Test
    public void out() {
        LOG.info(String.join(";\n", Collections.nCopies(6, "{}")), 1, 2, 3, 4, 5, 6);
    }

    @Test
    public void sql() {
        final String sql = "SELECT T.ID, T.AREA_ID, T.AREA_NAME, T.SITE_ID, T.SITE_NAME, T.PERSON_ID, T.ORIGINAL_PERSON_ID, T.PERSON_NAME, T.PERSON_PC_NO, T.COMPANY_NAME, T.FIRST_TIME, T.UPDATE_TIME, T.EXPIRATION_TIME" +
                " FROM ZNAF_SITE_PERSON T";
        final String[] split = sql.split("[, ]");
    }
}