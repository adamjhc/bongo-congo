package com.knightlore.server.database.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

/**
 * Singular attribute, allows model to define their columns in the database
 */
public class Attribute {

  public Integer type;
  public Object value;
  public boolean primary;
  public boolean fillable = false;
  public boolean isDirty = true;
  public boolean isCreated = false;

  public Attribute(int type, Object value) {
    this.type = type;
    this.value = value;
    this.primary = false;
  }

  public Attribute(int type, Object value, boolean primary) {
    this.type = type;
    this.value = value;
    this.primary = primary;
  }

  public Attribute(int type, Object value, boolean primary, boolean isDirty) {
    this.type = type;
    this.value = value;
    this.primary = primary;
    this.isDirty = isDirty;
  }

  public PreparedStatement addAttribute(PreparedStatement stmt, int i) throws SQLException {
    this.primary = false;
    if (this.value == null) {
      stmt.setNull(i, Types.INTEGER);
      return stmt;
    }
    switch (this.type) {
      case Types.INTEGER:
        stmt.setInt(i, (Integer) this.value);
        break;

      case Types.VARCHAR:
        stmt.setString(i, (String) this.value);
        break;

      case Types.TIMESTAMP:
        stmt.setTimestamp(i, (Timestamp) this.value);
        break;

      default:
        stmt.setString(i, (String) this.value);
        break;
    }

    return stmt;
  }
}
