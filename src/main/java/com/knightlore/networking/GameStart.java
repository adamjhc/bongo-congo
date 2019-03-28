package com.knightlore.networking;

import com.knightlore.game.GameModel;

/**
 * Response to game starting, send over the model
 *
 * @author Lewis Relph
 */
public class GameStart {

  public GameModel model;

  public GameStart(GameModel model) {
    this.model = model;
  }
}
