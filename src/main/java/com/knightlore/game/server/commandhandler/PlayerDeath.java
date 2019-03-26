package com.knightlore.game.server.commandhandler;

import com.google.gson.Gson;
import com.knightlore.game.entity.Player;
import com.knightlore.game.entity.PlayerState;
import com.knightlore.game.server.ClientHandler;
import com.knightlore.networking.Sendable;

import java.util.UUID;

public class PlayerDeath extends Command {

  public void run(ClientHandler handler, Sendable sendable) {
    // Update our model to reflect
    handler.model().getPlayers().get(handler.sessionKey.get()).decrementLives();


    // Send out update
    Sendable lifeLost = new Sendable();
    lifeLost.setFunction("player_death");

    com.knightlore.networking.PlayerDeath death = new com.knightlore.networking.PlayerDeath(handler.sessionKey.get());
    Gson gson = new Gson();
    lifeLost.setData(gson.toJson(death));

    handler.server().sendToRegisteredExceptSelf(lifeLost, handler.sessionKey.get());
  }
}
