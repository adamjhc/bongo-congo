package com.knightlore.game.server.commandhandler;

import com.knightlore.game.server.ClientHandler;
import com.knightlore.networking.server.ApiKey;
import com.knightlore.networking.Sendable;
import com.knightlore.networking.server.SessionKeyResponse;

/**
 * Handler for start game command
 *
 * @author Lewis Relph
 */
public class StartGame extends Command {

  public void run(ClientHandler handler, Sendable sendable) {
    // Create json data
    String json = sendable.getData();

    // Check if request was made by the owner
    if (handler.isOwner()) {
      // Start Game
      handler.server().startGame();
    }
  }
}
