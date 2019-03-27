package com.knightlore.client.networking;

import com.knightlore.networking.HighScoreResponse;
import com.knightlore.networking.HighScoreResponseObject;
import java.util.ArrayList;
import java.util.List;

public class HighScoreCache {

  public static HighScoreCache instance = new HighScoreCache();
  public int cache = 0;
  private List<HighScoreResponseObject> scores;

  public HighScoreCache() {
    this.scores = new ArrayList<>();
  }

  public List<HighScoreResponseObject> getScores() {
    return this.scores;
  }

  public void setScores(HighScoreResponse response) {
    this.scores = response.scores;
    cache++;
  }
}
