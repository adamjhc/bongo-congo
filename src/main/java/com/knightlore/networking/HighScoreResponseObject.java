package com.knightlore.networking;

/**
 * Singular highscore, holds username and score associated
 *
 * @author Lewis Relph
 */
public class HighScoreResponseObject {

  public String username;
  public int score;

  public HighScoreResponseObject(String username, int score) {
    this.username = username;
    this.score = score;
  }
}
