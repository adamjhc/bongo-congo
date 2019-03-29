package com.knightlore.game.server;

import com.google.gson.Gson;
import com.knightlore.client.gui.engine.Timer;
import com.knightlore.game.GameModel;

/**
 * Thread for periodically updating the model server side, handle enemy movement etc
 *
 * @author Lewis Relph
 */
public class GameManager extends Thread {

  private static final int TARGET_UPS = 60;

  GameModel model;
  GameServer server;

  public GameManager(GameModel model, GameServer server) {
    this.model = model;
    this.server = server;
  }

  @Override
  public void run() {
    float elapsedTime;
    float accumulator = 0f;
    float interval = 1f / TARGET_UPS;
    Timer timer = new Timer();
    Gson gson = new Gson();

    while (server.running) {
      elapsedTime = timer.getElapsedTime();

      accumulator += elapsedTime;

      while (accumulator >= interval) {
        model.serverUpdate(interval);

        accumulator -= interval;
      }
    }
  }
}
