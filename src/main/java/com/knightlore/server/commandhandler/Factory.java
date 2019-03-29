package com.knightlore.server.commandhandler;

import com.knightlore.networking.Sendable;
import com.knightlore.server.ClientHandler;

/**
 * Routing of incoming commands to main server
 *
 * @author Lewis Relph
 */
public class Factory {

  /**
   * Route command to correct command handler
   *
   * @param clientHandler
   * @param sendable
   */
  public static void create(ClientHandler clientHandler, Sendable sendable) {
    Command handler;

    if (sendable.getFunction().equals("get_session_token")) {
      handler = new GetSessionToken();
      handler.run(clientHandler, sendable);
    }
    if (sendable.getFunction().equals("request_game")) {
      handler = new GameRequest();
      handler.run(clientHandler, sendable);
    }
    if (sendable.getFunction().equals("list_games")) {
      handler = new ListGames();
      handler.run(clientHandler, sendable);
    }
    if (sendable.getFunction().equals("list_levels")) {
      handler = new ListLevels();
      handler.run(clientHandler, sendable);
    }
    if (sendable.getFunction().equals("level_upload")) {
      handler = new LevelUpload();
      handler.run(clientHandler, sendable);
    }
    if (sendable.getFunction().equals("list_highscores")) {
      handler = new ListHighscores();
      handler.run(clientHandler, sendable);
    }
  }
}
