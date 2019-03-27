package com.knightlore.networking;

import java.util.ArrayList;
import java.util.List;

public class HighScoreResponse {

  public List<HighScoreResponseObject> scores;

  public HighScoreResponse() {
    this.scores = new ArrayList<>();
  }

  public void addScore(String username, int score) {
    scores.add(new HighScoreResponseObject(username, score));
  }
}
