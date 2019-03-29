package com.knightlore.server.database.model;

/**
 * Conditions used in where statements, simple datatype
 *
 * @author Lewis Relph
 */
public class Condition {

  String key;
  String condition;
  Object operand;

  public Condition(String key, String condition, Object operand) {
    this.key = key;
    this.condition = condition;
    this.operand = operand;
  }
}
