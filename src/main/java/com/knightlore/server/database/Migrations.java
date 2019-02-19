package com.knightlore.server.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Migrations {



    public static void migrate(){
        Connection con = com.knightlore.server.database.Connection.getConnection();

        // Reset migrations
        String sql = "DROP TABLE IF EXISTS registration_keys";

        try{
            Statement stmt = con.createStatement();
            // create a new table
            stmt.execute(sql);
        }catch(SQLException e){
            System.out.println("SQL ERROR IN TABLE CREATE" + e);
        }

        sql = "CREATE TABLE registration_keys ("
                + "	id SERIAL PRIMARY KEY,"
                + "	key VARCHAR(64) NOT NULL,"
                + "	key_allowance INT"
                + ");";

        try{
            Statement stmt = con.createStatement();
            // create a new table
            stmt.execute(sql);
        }catch(SQLException e){
            System.out.println("SQL ERROR IN TABLE CREATE" + e);
        }

    }

}
