package com.knightlore.networking.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Response to requesting highscore, holds list of highscore objects
 *
 * @author Lewis Relph
 */
public class HighScoreResponse {

  public List<HighScoreResponseObject> scores;

  public HighScoreResponse() {
    this.scores = new ArrayList<>();
  }

  public void addScore(String username, int score) {
    scores.add(new HighScoreResponseObject(username, score));
  }
}
