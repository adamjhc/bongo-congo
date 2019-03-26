package com.knightlore.game.server;

import com.knightlore.game.GameModel;

public class GameManager extends Thread {

  GameModel model;
  GameServer server;

  public GameManager(GameModel model, GameServer server) {
    this.model = model;
    this.server = server;
  }

  @Override
  public void run() {
    while (true) {
      model.serverUpdate();
    }
  }
}
