package com.simplite.orm.entities;

import com.simplite.orm.DBObject;
import com.simplite.orm.annotations.ForeignKey;
import com.simplite.orm.annotations.ForeignKeyArray;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FkData extends ColumnData {

    private Class<? extends DBObject> fkClass;
    private String valueColumn;

    public FkData(Field field, ForeignKey annotation, Method setter) {
        super(field, setter, annotation);
        this.fkClass = annotation.entityClass();
        this.valueColumn = annotation.valueColumnName();
    }

    public FkData(Field field, ForeignKeyArray annotation, Method setter) {
        super(field, setter, annotation);
        this.fkClass = annotation.entityClass();
        this.valueColumn = annotation.valueColumnName();
    }

    public Class<? extends DBObject> getFkClass() {
        return fkClass;
    }

    public String getValueColumn() {
        return valueColumn;
    }
}
