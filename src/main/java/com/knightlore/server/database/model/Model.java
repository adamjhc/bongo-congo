package com.knightlore.server.database.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Abstract class of database model
 *
 * @author Lewis Relph
 */
public abstract class Model {

  public String table;
  AttributeBag attributes;
  String identifier;
  protected ArrayList<Condition> conditions = new ArrayList<>();
  private boolean isSaved;
  public OrderBy order;
  public String orderColumn;

  private Connection conn = com.knightlore.server.database.Connection.getConnection();

  /**
   * Set flag for order by direction and column
   *
   * @param column
   * @param order
   */
  public void orderBy(String column, OrderBy order) {
    this.orderColumn = column;
    this.order = order;
  }

  /**
   * Retrieve all models from the database
   *
   * @return
   */
  public Optional<ResultSet> getAll() {
    try {
      PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + this.table);
      ResultSet results = stmt.executeQuery();

      return Optional.of(results);
    } catch (SQLException e) {
      ErrorHandler.show(e);
    }

    return Optional.empty();
  }

  /**
   * Insert into the database based on internal attributes
   *
   * @return
   */
  public boolean save() {
    // Ensure model is fresh
    if (this.isSaved) {
      return false;
    }

    //  Build SQL string
    String statement = "INSERT INTO " + this.table + '(';

    // Add attributes string
    statement += this.attributes.generateAttributeString();
    statement += ") VALUES (";

    // Add question mark place holders for values
    statement += this.attributes.generateQuestionMarks();
    statement += ")";

    ErrorHandler.logSQL(statement);

    // Prepare and link
    try {
      PreparedStatement stmt = conn.prepareStatement(statement);
      this.attributes.setStatementVariables(stmt);
      stmt.execute();
    } catch (SQLException e) {
      ErrorHandler.show(e);
      return false;
    }

    // Model matches database, mark as clean
    this.attributes.markAsClean();
    return true;
  }

  /**
   * Update attributes to match database for provided primary key
   *
   * @param primary
   * @return
   */
  public boolean find(Object primary) {
    this.isSaved = true;
    return this.loadAttributes(primary);
  }

  /**
   * Load attributes based on primary key from database into model
   *
   * @param primary
   * @return
   */
  public boolean loadAttributes(Object primary) {
    String statement = "SELECT ";

    // Build statement
    statement += this.attributes.generateAttributeString(this.attributes.attributes.keySet());
    statement += " FROM " + this.table + " WHERE " + this.attributes.getPrimaryKey() + " = ?";

    try {
      PreparedStatement stmt = conn.prepareStatement(statement);
      stmt = this.attributes.setStatementPrimaryKey(stmt, primary);

      ResultSet result = stmt.executeQuery();

      if (result.next()) {
        // Set attribute
        attributes.setAttributes(result);

        // Mark all columns as clean
        this.attributes.markAsClean();

        return true;
      }

    } catch (SQLException e) {
      ErrorHandler.show(e);
    }

    return false;
  }

  /**
   * Set attribute
   *
   * @param key
   * @param value
   */
  public void setAttribute(String key, Object value) {
    Attribute selected = this.attributes.getAttribute(key);
    selected.value = value;
    this.attributes.setAttribute(key, selected);
  }

  /**
   * Retrieve attribute from set attributes
   *
   * @param key
   * @return
   */
  public Object getAttribute(String key) {
    return this.attributes.getAttribute(key).value;
  }

  /**
   * Make update query if variables have changed from that in the database
   *
   * @return
   */
  public boolean update() {
    // Check dirty attributes exist (no point updating clean)
    if (attributes.getDirty().size() > 0) {
      String statement = "UPDATE " + this.table + " SET ";

      statement += this.attributes.generateUpdateStringSection();

      statement += " WHERE " + this.attributes.getPrimaryKey() + "=?";

      try {
        PreparedStatement stmt = conn.prepareStatement(statement);
        stmt = this.attributes.setUpdateStatemntVariables(stmt);
        stmt.execute();

      } catch (SQLException e) {
        ErrorHandler.show(e);
      }
    }

    return true;
  }

  /**
   * Attach WHERE parameters to the PreparedStatement object based on internal vars
   *
   * @param stmt
   * @return
   * @throws SQLException
   */
  private PreparedStatement bindWhereParams(PreparedStatement stmt) throws SQLException {
    int i = 1;
    for (Condition condition : this.conditions) {
      switch (this.attributes.getAttribute(condition.key).type) {
        case Types.INTEGER:
          stmt.setInt(i, (Integer) condition.operand);
          break;

        case Types.VARCHAR:
          stmt.setString(i, (String) condition.operand);
          break;
      }
      i++;
    }

    return stmt;
  }

  /**
   * Build up SQL representation of WHERE section of query based on internal vars
   *
   * @return
   */
  private String whereStatement() {
    String statement = "";
    if (this.conditions.size() > 0) {
      statement += " WHERE ";
      for (Condition condition : this.conditions) {
        statement += "`" + condition.key + "`" + condition.condition + "?" + " AND ";
      }
      statement = statement.substring(0, statement.length() - 4);
    }

    return statement;
  }

  /**
   * Retrieve query from database based on aggrigate provided
   *
   * @param statement
   * @return
   */
  private int getAggrigate(String statement) {
    try {
      PreparedStatement stmt = conn.prepareStatement(statement);
      ErrorHandler.logSQL(statement);
      stmt = this.bindWhereParams(stmt);
      ResultSet results = stmt.executeQuery();

      while (results.next()) {
        return results.getInt(1);
      }

    } catch (SQLException e) {
      ErrorHandler.show(e);
    }

    return 0;
  }

  /**
   * Add aggrigate "COUNT" to database query
   *
   * @param key
   * @return
   */
  public int count() {
    String statement = "SELECT COUNT(" + this.attributes.getPrimaryKey() + ") FROM " + this.table;

    statement += this.whereStatement();

    return this.getAggrigate(statement);
  }

  /**
   * Add aggrigate "SUM" to database query
   *
   * @param key
   * @return
   */
  public int sum(String key) {
    String statement = "SELECT SUM(" + key + ") FROM " + this.table;

    statement += this.whereStatement();

    return this.getAggrigate(statement);
  }

  /**
   * Add condition to database query (WHERE)
   *
   * @param condition
   */
  public void where(Condition condition) {
    // save condition
    this.conditions.add(condition);
  }

  /**
   * Retrieve arraylist of models based on criteria previously set
   *
   * @return
   */
  public ArrayList<Model> get() {
    return this.get(new ArrayList<>());
  }

  /**
   * Retrieve arraylist of models based on criteria previously set
   *
   * @param columns
   * @return
   */
  public ArrayList<Model> get(ArrayList<String> columns) {
    // Perform where with conditions
    ArrayList<Model> models = new ArrayList<>();

    // Beginning of select statement
    String statement = "SELECT ";

    if (columns.isEmpty()) {
      // No columns provided, use wildcard
      statement += "*";
    } else {
      for (String column : columns) {
        statement += column + ",";
      }
      // Remove last
      statement = statement.substring(0, statement.length() - 1);
    }

    statement += " FROM " + table;
    statement += " " + this.whereStatement();

    // Add order by
    if (order != null) {
      statement += " ORDER BY " + orderColumn + " ";

      switch (order) {
        case ASC:
          statement += "ASC";
          break;

        case DESC:
          statement += "DESC";
          break;

        case RAND:
          statement += "RAND()";
          break;
      }
    }

    // Now bind
    try {
      PreparedStatement stmt = conn.prepareStatement(statement);
      stmt = this.bindWhereParams(stmt);

      ResultSet results = stmt.executeQuery();

      while (results.next()) {
        // Check if all columns used
        Model currentM = this.createNewInstance();

        if (!columns.isEmpty()) {
          for (String key : columns) {
            currentM.setAttribute(key, results.getObject(key));
          }
        } else {
          currentM.loadAttributes(results.getObject(currentM.attributes.getPrimaryKey()));
        }

        models.add(currentM);
      }

      return models;

    } catch (SQLException e) {
      ErrorHandler.show(e);
    }
    return new ArrayList<>();
  }

  /**
   * Retrieve the first model with the saved criteria
   *
   * @return
   */
  public Optional<Model> first() {
    return this.first(new ArrayList<>());
  }

  /**
   * Retrieve the first model with the saved criteria
   *
   * @param columns
   * @return optional containing model or none
   */
  public Optional<Model> first(ArrayList<String> columns) {
    // Run get
    ArrayList<Model> models = this.get(columns);

    // Check if empty
    if (models.isEmpty()) {
      return Optional.empty();
    }

    // Return first
    return Optional.of(models.get(0));
  }

  /**
   * Create new instance of current model implementation
   *
   * @return
   */
  public abstract Model createNewInstance();
}
