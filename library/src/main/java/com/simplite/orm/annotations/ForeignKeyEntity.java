package com.simplite.orm.annotations;

import com.simplite.orm.DBObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ForeignKeyEntity {
    String valueColumnName();

    String fkColumnName();

    Class<? extends DBObject> entityClass();

    String[] options() default {};
}
