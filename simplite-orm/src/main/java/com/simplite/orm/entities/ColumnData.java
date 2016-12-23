package com.simplite.orm.entities;

import com.simplite.orm.annotations.Column;
import com.simplite.orm.annotations.ForeignKey;
import com.simplite.orm.annotations.ForeignKeyArray;
import com.simplite.orm.annotations.PrimaryKey;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ColumnData {

    private String name;
    private Field field;
    private Method setter;
    private String[] options;

    public ColumnData(Field field, Method setter, Column annotation) {
        this.name = annotation.name();
        this.field = field;
        this.setter = setter;
        this.options = annotation.options();
    }

    ColumnData(Field field, Method setter, PrimaryKey annotation) {
        this.name = annotation.columnName();
        this.field = field;
        this.setter = setter;
        this.options = annotation.options();
    }

    ColumnData(Field field, Method setter, ForeignKey annotation) {
        this.name = annotation.fkColumnName();
        this.field = field;
        this.setter = setter;
        this.options = annotation.options();
    }

    ColumnData(Field field, Method setter, ForeignKeyArray annotation) {
        this.name = annotation.fkColumnName();
        this.field = field;
        this.setter = setter;
        this.options = annotation.options();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Method getSetter() {
        return setter;
    }

    public void setSetter(Method setter) {
        this.setter = setter;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getOptionsString() {
        if (options != null && options.length > 0) {
            String optionsString = "";

            for (String option : options) {
                if (!optionsString.equals(""))
                    optionsString = optionsString + ",";
                optionsString = optionsString + option;
            }

            return optionsString;
        }
        return "";
    }
}
