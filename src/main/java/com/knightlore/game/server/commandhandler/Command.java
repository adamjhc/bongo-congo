package com.knightlore.game.server.commandhandler;

import com.google.gson.Gson;
import com.knightlore.game.server.ClientHandler;
import com.knightlore.networking.Sendable;

/**
 * Template for incoming command handling
 *
 * @author Lewis Relph
 */
public abstract class Command {

  Gson gson = new Gson();

  public abstract void run(ClientHandler clientHandler, Sendable sendable);
}
