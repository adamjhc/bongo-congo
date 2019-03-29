package com.knightlore.server.database.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Repository to hold all the attributes a model has and data regarding them
 * @author Lewis Relph
 */
public class AttributeBag {

  public HashMap<String, Attribute> attributes = new HashMap<>();

  public ArrayList<String> getDirty() {
    ArrayList<String> dirty = new ArrayList<>();

    for (Map.Entry<String, Attribute> current : this.attributes.entrySet()) {
      if (current.getValue().isDirty) dirty.add(current.getKey());
    }

    return dirty;
  }

  /**
   * Retrieve arraylist of all current attribute objects
   *
   * @return
   */
  public HashMap<String, Attribute> getAttributes() {
    return this.attributes;
  }

  /**
   * Retrieve specifi attribute object from key
   *
   * @param key
   * @return
   */
  public Attribute getAttribute(String key) {
    return this.attributes.get(key);
  }

  /**
   * Save/overwrite attribute
   *
   * @param key
   * @param attribute
   */
  public void setAttribute(String key, Attribute attribute) {
    attribute.isDirty = true;
    this.attributes.put(key, attribute);
  }

  /**
   * Update attributes from bag based on result NB resultset must include all keys
   *
   * @param result
   * @throws SQLException
   */
  public void setAttributes(ResultSet result) throws SQLException {
    for (String key : this.attributes.keySet()) {
      Attribute current = this.attributes.get(key);
      current.value = result.getObject(key);
      this.attributes.put(key, current);
    }
  }

  /**
   * Mark all keys provided as clean
   *
   * @param keys
   */
  public void markAsClean(Iterable<String> keys) {
    for (String key : keys) {
      Attribute current = this.attributes.get(key);
      current.isDirty = false;
    }
  }

  /** Mark all keys provided as clean */
  public void markAsClean() {
    this.markAsClean(this.attributes.keySet());
  }

  /**
   * Add new attribute, note will not mark as dirty
   *
   * @param name
   * @param attribute
   */
  public void addAttribute(String name, Attribute attribute) {
    this.attributes.put(name, attribute);
  }

  /**
   * Set the primary key value for a statement
   *
   * @param statement
   * @param primaryValue
   * @return
   * @throws SQLException
   */
  public PreparedStatement setStatementPrimaryKey(PreparedStatement statement, Object primaryValue)
      throws SQLException {
    statement = this.setExplicitStatementValues(1, statement, this.getPrimaryType(), primaryValue);
    return statement;
  }

  /**
   * Return the primary key key
   *
   * @return
   */
  public String getPrimaryKey() {
    for (Map.Entry<String, Attribute> attribute : this.attributes.entrySet()) {
      if (attribute.getValue().primary) return attribute.getKey();
    }

    return null;
  }

  /**
   * Retrieve primary key type
   *
   * @return
   */
  public int getPrimaryType() {
    for (Map.Entry<String, Attribute> attribute : this.attributes.entrySet()) {
      if (attribute.getValue().primary) return attribute.getValue().type;
    }

    // Default to int
    return Types.INTEGER;
  }

  /**
   * Retrieve all non-primary attributes
   *
   * @return
   */
  public ArrayList<String> fillable() {
    ArrayList<String> fillable = new ArrayList<>();

    for (Map.Entry<String, Attribute> attribute : this.attributes.entrySet()) {
      if (!attribute.getValue().primary) {
        fillable.add(attribute.getKey());
      }
    }

    return fillable;
  }

  /**
   * Generate commas for the number of fillable attribute to update (FOR CREATE)
   *
   * @return
   */
  public String generateAttributeString() {
    return this.generateAttributeString(this.fillable());
  }

  public String generateAttributeString(Iterable<String> attributeKeys) {
    // Retrieve fillables
    String attributeString = "";
    for (String attribute : attributeKeys) {
      attributeString += "`" + attribute + "`,";
    }

    return attributeString.substring(0, attributeString.length() - 1);
  }

  public String generateQuestionMarks() {
    return this.generateQuestionMarks(this.fillable().size());
  }

  /**
   * Generate question marks for the number of fillable attribute to update
   *
   * @return
   */
  public String generateQuestionMarks(int quantity) {
    // Retrieve fillables
    String commas = "";
    for (int i = 0; i < quantity; i++) {
      commas += "?,";
    }

    return commas.substring(0, commas.length() - 1);
  }

  public PreparedStatement setStatementVariables(PreparedStatement stmt) throws SQLException {
    return this.setStatementVariables(stmt, fillable());
  }

  public PreparedStatement setStatementVariables(PreparedStatement stmt, ArrayList<String> keys)
      throws SQLException {
    int i = 1;
    for (String attribute : keys) {
      stmt = this.attributes.get(attribute).addAttribute(stmt, i);
      i++;
    }

    return stmt;
  }

  public String generateUpdateStringSection() {
    // No sets provided, update all dirty
    return this.generateUpdateStringSection(this.getDirty());
  }

  public String generateUpdateStringSection(Iterable<String> keys) {
    String statement = "";

    for (String key : keys) {
      Attribute attribute = this.attributes.get(key);

      statement += key + "=?,";
    }

    return statement.substring(0, statement.length() - 1);
  }

  public PreparedStatement setUpdateStatemntVariables(PreparedStatement stmt) throws SQLException {
    return this.setUpdateStatemntVariables(stmt, this.getDirty());
  }

  private PreparedStatement setExplicitStatementValues(
      int i, PreparedStatement stmt, int type, Object value) throws SQLException {
    switch (type) {
      case Types.INTEGER:
        stmt.setInt(i, (Integer) value);
        break;
      case Types.VARCHAR:
        stmt.setString(i, (String) value);
        break;
    }
    return stmt;
  }

  public PreparedStatement setUpdateStatemntVariables(PreparedStatement stmt, Iterable<String> keys)
      throws SQLException {
    int i = 1;
    for (String key : keys) {
      Attribute current = this.attributes.get(key);
      this.setExplicitStatementValues(i, stmt, current.type, current.value);
      i++;
    }

    // Attach PID for where clause
    Attribute primaryKey = this.getAttribute(this.getPrimaryKey());
    switch (this.getPrimaryType()) {
      case Types.INTEGER:
        stmt.setInt(i, (Integer) primaryKey.value);
        break;

      case Types.VARCHAR:
        stmt.setString(i, (String) primaryKey.value);
        break;
    }
    return stmt;
  }
}
