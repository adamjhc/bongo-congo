package com.knightlore.server.database.model;


import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public abstract class Model {

    public String table;
    AttributeBag attributes;
    String identifier;
    protected ArrayList<Condition> conditions = new ArrayList<>();
    public String createStatement;
    private boolean isSaved;

    private Connection conn = com.knightlore.server.database.Connection.getConnection();

    public Optional<Integer> randomKey(){
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT " + this.attributes.getPrimaryKey() +" FROM " + this.table + " ORDER BY random() LIMIT 1");
            ResultSet results = stmt.executeQuery();

            if(results.next()){
                return Optional.of(results.getInt(this.attributes.getPrimaryKey()));
            }
        }catch (SQLException e){
            ErrorHandler.show(e);
        }

        return Optional.empty();
    }

    public Optional<ResultSet> getAll(){
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + this.table);
            ResultSet results = stmt.executeQuery();

            return Optional.of(results);
        }catch (SQLException e){
            ErrorHandler.show(e);
        }

        return Optional.empty();
    }

    public boolean save(){
        // Save to db
        // Check not new
        if(this.isSaved){
            return false;
        }

        // Select attributes to save
        // Iterate each attribute
        String statement = "INSERT INTO " + this.table + '(';

        statement += this.attributes.generateAttributeString();
        statement += ") VALUES (";
        statement += this.attributes.generateQuestionMarks();
        statement += ")";

        ErrorHandler.logSQL(statement);

        try{
            PreparedStatement stmt = conn.prepareStatement(statement);
            this.attributes.setStatementVariables(stmt);
            stmt.execute();
        }catch(SQLException e){
            ErrorHandler.show(e);
            return false;
        }

        this.attributes.markAsClean();
        return true;

    }

    public boolean find(Object primary){
        this.isSaved = true;
        return this.loadAttributes(primary);
    }

    public boolean loadAttributes(Object primary){
        String statement = "SELECT ";

        // Fill columns
        statement += this.attributes.generateAttributeString(this.attributes.attributes.keySet());

        statement += " FROM " + this.table + " WHERE " + this.attributes.getPrimaryKey() + " = ?";

        try{
            PreparedStatement stmt = conn.prepareStatement(statement);
            stmt = this.attributes.setStatementPrimaryKey(stmt, primary);

            ResultSet result = stmt.executeQuery();


            if(result.next()){
                // Save all results to com.knightlore.server.database.game
                attributes.setAttributes(result);

                // Mark all columns as clean
                this.attributes.markAsClean();

                return true;
            }

        }catch(SQLException e){
            ErrorHandler.show(e);
        }

        return false;
    }

    public void setAttribute (String key, Object value){
        Attribute selected = this.attributes.getAttribute(key);
        selected.value = value;
        this.attributes.setAttribute(key, selected);
    }

    public Object getAttribute(String key){
        return this.attributes.getAttribute(key).value;
    }

    public boolean update(){
        // Check dirty attributes exist
        if(attributes.getDirty().size() > 0){
            String statement = "UPDATE " + this.table + " SET ";

            statement += this.attributes.generateUpdateStringSection();

            statement += " WHERE " + this.attributes.getPrimaryKey() + "=?";

            try{
                PreparedStatement stmt = conn.prepareStatement(statement);
                stmt = this.attributes.setUpdateStatemntVariables(stmt);
                stmt.execute();

            }catch(SQLException e){
                ErrorHandler.show(e);
            }

        }

        return true;
    }

    private PreparedStatement bindWhereParams(PreparedStatement stmt) throws SQLException{
        int i = 1;
        for(Condition condition: this.conditions){
            switch(this.attributes.getAttribute(condition.key).type){
                case Types.INTEGER:
                    stmt.setInt(1, (Integer) condition.operand);
                    break;

                case Types.VARCHAR:
                    stmt.setString(1, (String) condition.operand);
                    break;
            }
            i++;
        }

        return stmt;
    }

    private String whereStatement(){
        String statement = "";
        if(this.conditions.size() > 0){
            statement += " WHERE ";
            for(Condition condition: this.conditions){
                statement += "`" + condition.key+ "`" + condition.condition + "?" + " AND ";
            }
            statement = statement.substring(0, statement.length() - 4);
        }

        return statement;
    }

    private int getAggrigate(String statement){
        try{
            PreparedStatement stmt = conn.prepareStatement(statement);
            ErrorHandler.logSQL(statement);
            stmt = this.bindWhereParams(stmt);
            ResultSet results = stmt.executeQuery();

            while(results.next()){
                return results.getInt(1);
            }

        }catch(SQLException e){
            ErrorHandler.show(e);
        }

        return 0;
    }

    public int count(){
        String statement = "SELECT COUNT(" + this.attributes.getPrimaryKey() + ") FROM " + this.table;

        statement += this.whereStatement();

        return this.getAggrigate(statement);
    }

    public int sum(String key){
        String statement = "SELECT SUM(" + key + ") FROM " + this.table;

        statement += this.whereStatement();

        return this.getAggrigate(statement);

    }

    public void where(Condition condition){
        // save condition
        this.conditions.add(condition);
    }

    public ArrayList<Model> get(){
        return this.get(new ArrayList<>());
    }

    public ArrayList<Model> get(ArrayList<String> columns) {
        // Perform where with conditions
        ArrayList<Model> models = new ArrayList<>();

        // Beginning of select statement
        String statement = "SELECT ";

        if(columns.isEmpty()){
            statement += "*";
        }else{
            for(String column: columns){
                statement += column + ",";
            }

            // Remove last
            statement = statement.substring(0, statement.length() - 1);
        }

        statement += " FROM " + table;
        statement += " " + this.whereStatement();

        // Now bind
        try{
            PreparedStatement stmt = conn.prepareStatement(statement);

            stmt = this.bindWhereParams(stmt);

            ResultSet results = stmt.executeQuery();


            while(results.next()){
                // Check if all columns used
                Model currentM = this.createNewInstance();

                if(!columns.isEmpty()){
                    for(String key : columns){
                        currentM.setAttribute(key, results.getObject(key));
                    }
                }else{
                    currentM.loadAttributes(results.getObject(currentM.attributes.getPrimaryKey()));
                }

                models.add(currentM);

            }

            return models;

        }catch (SQLException e){
            ErrorHandler.show(e);
        }


        return new ArrayList<>();
    }

    public Optional<Model> first(){
        return this.first(new ArrayList<>());
    }

    public Optional<Model> first(ArrayList<String> columns) {
        // Run get
        ArrayList<Model> models = this.get(columns);

        // Check if empty
        if(models.isEmpty()){
            return Optional.empty();
        }

        // Return first
        return Optional.of(models.get(0));
    }

    public abstract Model createNewInstance();


}
