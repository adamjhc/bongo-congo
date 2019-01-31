package com.knightlore.server.util;

public class ErrorHandler {
    public static boolean showErrors = true;
    public static boolean showStatements = true;

    public static void show(Exception error){
        if(showErrors)
            System.out.println("[ERROR]:  " + error);
    }

    public static void logSQL(String statement){
        if(showStatements)
            System.out.println("[STATEMENT]: " + statement);
    }
}
