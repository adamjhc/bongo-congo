package com.knightlore.client.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

  public static String readFileAsString(String filename) {
    String pathname = "src/main/java/" + filename;

    StringBuilder stringBuilder = new StringBuilder();

    try (BufferedReader reader = new BufferedReader(new FileReader(new java.io.File(pathname)))) {
      String line;

      while ((line = reader.readLine()) != null) {
        stringBuilder.append(line);
        stringBuilder.append("\n");
      }
    } catch (IOException ex) {
      ex.getMessage();
    }

    return stringBuilder.toString();
  }
}
