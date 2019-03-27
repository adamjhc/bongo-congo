package com.knightlore.networking;

public class HighScoreResponseObject {

  public String username;
  public int score;

  public HighScoreResponseObject(String username, int score) {
    this.username = username;
    this.score = score;
  }
}
