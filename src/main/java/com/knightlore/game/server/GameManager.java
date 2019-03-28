package com.knightlore.game.server;

import com.google.gson.Gson;
import com.knightlore.client.gui.engine.Timer;
import com.knightlore.game.GameModel;

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
//        Sendable sendable = new Sendable();
//        sendable.setFunction("enemy_location_update");
//        EnemyLocationUpdate update = new EnemyLocationUpdate();
//
//        for(Enemy current : model.getCurrentLevel().getEnemies()){
//          update.addEnemy(current.getId(), new EnemyLocationUpdateObject(current.getPosition(), current.getDirection(), current.getCurrentState()));
//        }
//
//        sendable.setData(gson.toJson(update));
//
//        server.sendToRegistered(sendable);
      }
    }
  }
}
