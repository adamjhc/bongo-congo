package com.knightlore.game.server.commandhandler;

import com.knightlore.game.entity.Player;
import com.knightlore.game.server.ClientHandler;
import com.knightlore.networking.Sendable;

/**
 * Factory for routing incoming game server commands
 *
 * @author Lewis Relph
 */
public class Factory {

  public static void create(ClientHandler clientHandler, Sendable sendable) {
    if (sendable.getFunction().equals("game_start")) {
      StartGame handler = new StartGame();
      handler.run(clientHandler, sendable);
    }
    if (sendable.getFunction().equals("register")) {
      Register handler = new Register();
      handler.run(clientHandler, sendable);
    }
    if (sendable.getFunction().equals("position_update")) {
      PositionUpdate handler = new PositionUpdate();
      handler.run(clientHandler, sendable);
    }
    if (sendable.getFunction().equals("ready")) {
      Ready handler = new Ready();
      handler.run(clientHandler, sendable);
    }

    if (sendable.getFunction().equals("level_complete")) {
      LevelComplete handler = new LevelComplete();
      handler.run(clientHandler, sendable);
    }

    if (sendable.getFunction().equals("player_death")) {
      PlayerDeath handler = new PlayerDeath();
      handler.run(clientHandler, sendable);
    }
  }
}
