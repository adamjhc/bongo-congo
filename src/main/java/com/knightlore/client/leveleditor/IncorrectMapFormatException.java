package com.knightlore.client.leveleditor;

public class IncorrectMapFormatException extends Exception {

  public IncorrectMapFormatException() {
    super();
  }

	IncorrectMapFormatException(String message) {
    super(message);
  }
}
