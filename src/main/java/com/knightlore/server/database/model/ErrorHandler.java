package com.knightlore.server.database.model;

/**
 * Error handler for SQL errors, used for debugging Often we just want to debug sql not all of l4j,
 * so this is toggled on and off
 *
 * @author Lewis Relph
 */
public class ErrorHandler {

  public static void show(Exception e) {
    System.out.println(e);
  }

  public static void logSQL(String e) {
    System.out.println(e);
  }
}
