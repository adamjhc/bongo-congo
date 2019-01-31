package com.knightlore.server.database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {

    static java.sql.Connection connection;

    public static boolean connect(){
        String url = "jdbc:mysql://localhost/grouptask_server?user=root&password=password";

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
