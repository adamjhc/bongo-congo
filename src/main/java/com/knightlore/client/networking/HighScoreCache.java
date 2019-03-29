package com.knightlore.client.networking;

import com.knightlore.networking.server.HighScoreResponse;
import com.knightlore.networking.server.HighScoreResponseObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Store for retrieved highscore from server
 *
 * @author Lewis Relph
 */
public class HighScoreCache {

  public static HighScoreCache instance = new HighScoreCache();
  public int cache = 0;
  private List<HighScoreResponseObject> scores;

  /**
   * Default constructor
   */
  public HighScoreCache() {
    this.scores = new ArrayList<>();
  }

  /**
   * Getter for scores list
   * @return
   */
  public List<HighScoreResponseObject> getScores() {
    return this.scores;
  }

  /**
   * Setter for scores
   * @param response
   */
  public void setScores(HighScoreResponse response) {
    this.scores = response.scores;

    // Implement cache so client can recognise update
    cache++;
  }
}
