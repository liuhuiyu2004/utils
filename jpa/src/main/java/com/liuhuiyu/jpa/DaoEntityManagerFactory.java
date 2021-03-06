package com.liuhuiyu.jpa;

import com.liuhuiyu.spring_util.SpringUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * 在初始化的时候加入  @Bean public DaoEntityManagerFactory DaoEntityManagerFactoryBean(){  return new DaoEntityManagerFactory();  }
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-03-05 9:10
 */
public class DaoEntityManagerFactory {
    private final EntityManagerFactory emf;
    EntityManager em;

    private static DaoEntityManagerFactory instance;

    public DaoEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static DaoEntityManagerFactory getInstance() {
        if (instance == null) {
            synchronized (DaoEntityManagerFactory.class) {
                if (instance == null) {
                    instance = SpringUtil.getBean(DaoEntityManagerFactory.class);
                }
            }
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        if (em == null) {
            synchronized (DaoEntityManagerFactory.class) {
                if (em == null) {
                    em = emf.createEntityManager();
                }
            }
        }
        if (!em.isOpen()) {
            em = emf.createEntityManager();
        }
        return em;
    }
}
