package com.simplite.orm.entities;

public class Condition {
    public enum Operator {
        EQUAL("="),
        GREATER(">"),
        LESS("<");

        private String operator;

        Operator(String operator) {
            this.operator = operator;
        }

        @Override
        public String toString() {
            return operator;
        }
    }

    private String column;
    private Object value;
    private Operator operator;

    public Condition() {
    }

    public Condition(String column, Object value, Operator operator) {
        this.column = column;
        this.value = value;
        this.operator = operator;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return column + operator.toString() + value;
    }
}
