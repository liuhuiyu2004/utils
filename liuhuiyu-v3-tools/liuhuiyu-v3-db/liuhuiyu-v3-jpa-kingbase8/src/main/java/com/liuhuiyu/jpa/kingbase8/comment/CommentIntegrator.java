package com.liuhuiyu.jpa.kingbase8.comment;

import com.liuhuiyu.jpa.comment.Comment;
import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * 注解集成
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2021-11-19 11:36
 */
@Component
public class CommentIntegrator implements Integrator {
    public static final CommentIntegrator INSTANCE = new CommentIntegrator();

    public CommentIntegrator() {
        super();
    }

    /**
     * Perform comment integration.
     *
     * @param metadata        The "compiled" representation of the mapping information
     * @param sessionFactory  The session factory being created
     * @param serviceRegistry The session factory's service registry
     */
    @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        processComment(metadata);
    }

    /**
     * Not used.
     *
     * @param sessionFactoryImplementor     The session factory being closed.
     * @param sessionFactoryServiceRegistry That session factory's service registry
     */
    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactoryImplementor, SessionFactoryServiceRegistry sessionFactoryServiceRegistry) {
    }

    /**
     * Process comment annotation.
     *
     * @param metadata process annotation of this {@code Metadata}.
     */
    private void processComment(Metadata metadata) {
        for (PersistentClass persistentClass : metadata.getEntityBindings()) {
            // Process the Comment annotation is applied to Class
            Class<?> clz = persistentClass.getMappedClass();
            if (clz.isAnnotationPresent(Comment.class)) {
                Comment comment = clz.getAnnotation(Comment.class);
                persistentClass.getTable().setComment(comment.value());
            }

            // Process Comment annotations of identifier.
            Property identifierProperty = persistentClass.getIdentifierProperty();
            if (identifierProperty != null) {
                fieldComment(persistentClass, identifierProperty.getName());
            }
            else {
                org.hibernate.mapping.Component component = persistentClass.getIdentifierMapper();
                if (component != null) {
                    for (Property property : component.getProperties()) {
                        fieldComment(persistentClass, property.getName());
                    }
                }
            }

            for (Property property : persistentClass.getProperties()) {
                fieldComment(persistentClass, property.getName());
            }
        }
    }

    /**
     * Process @{code comment} annotation of field.
     *
     * @param persistentClass Hibernate {@code PersistentClass}
     * @param columnName      name of field
     */
    private void fieldComment(PersistentClass persistentClass, String columnName) {
        try {
            Field field = getField(persistentClass.getMappedClass(), columnName);
            if (field != null && field.isAnnotationPresent(Comment.class)) {
                String comment = field.getAnnotation(Comment.class).value();
                String sqlColumnName = persistentClass.getProperty(columnName).getValue().getColumns().getFirst().getText();
//                Iterator<org.hibernate.mapping.Column> columnIterator = persistentClass.getTable().getColumnIterator();
                for (Object next : persistentClass.getTable().getColumns()) {
                    if (next instanceof org.hibernate.mapping.Column column) {
                        if (sqlColumnName.equalsIgnoreCase(column.getName())) {
                            column.setComment(comment);
                            break;
                        }
                    }
                }
            }
        }
        catch (SecurityException ignored) {
        }
    }

    /**
     * 字段获取
     *
     * @param clazz      类
     * @param columnName 字段名称
     * @return java.lang.reflect.Field 指定字段名称的字段

     * Created DateTime 2023-10-20 20:26
     */
    private Field getField(Class clazz, String columnName) {
        try {
            return clazz.getDeclaredField(columnName);
        }
        catch (Exception ex) {
            if (clazz.getSuperclass() == null) {
                return null;
            }
            else {
                return getField(clazz.getSuperclass(), columnName);
            }
        }
    }
}