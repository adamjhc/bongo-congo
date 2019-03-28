package com.knightlore.server.database;

import com.knightlore.util.Config;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {

    static java.sql.Connection connection;

    /**
     * Establish connection to the database
     * @return
     */
    public static boolean connect(){
        // Connection URL
        String url = "jdbc:mysql://"+ Config.databaseHost().get()+"/"+Config.databaseName().get()+"?user="+Config.databaseUser().get()+"&password="+ Config.databasePassword().get();

        System.out.println(url);
        try{
            java.sql.Connection conn = DriverManager.getConnection(url);
            connection = conn;
            return true;
        }catch (SQLException e) {
            System.out.println(e);
        }

        return false;
    }

    public static java.sql.Connection getConnection(){
        return connection;
    }
}
