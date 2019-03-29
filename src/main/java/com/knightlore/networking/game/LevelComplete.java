package com.knightlore.networking.game;

/**
 * Response to game starting, send over the model
 *
 * @author Lewis Relph
 */
public class LevelComplete {

  public int score;

  public LevelComplete(int score) {
    this.score = score;
  }
}
