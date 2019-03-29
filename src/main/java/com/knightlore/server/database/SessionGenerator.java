package com.knightlore.server.database;

import java.util.concurrent.ThreadLocalRandom;

public class SessionGenerator {

  /**
   * Code for generating a session key randomly
   *
   * @return
   */
  public static String generateActivationKey() {
    String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    String toReturn = "";
    for (int i = 0; i < 32; i++) {
      int randomNum = ThreadLocalRandom.current().nextInt(1, letters.length());
      toReturn += letters.charAt(randomNum);
    }

    return toReturn;
  }
}
