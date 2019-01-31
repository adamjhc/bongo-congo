package com.knightlore.server.database.model;

public class Condition {

    String key;
    String condition;
    Object operand;

    public Condition (String key, String condition, Object operand){
        this.key = key;
        this.condition = condition;
        this.operand = operand;
    }
}
