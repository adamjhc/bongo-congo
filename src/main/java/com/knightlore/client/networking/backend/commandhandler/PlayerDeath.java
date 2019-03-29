package com.knightlore.client.networking.backend.commandhandler;

import com.google.gson.Gson;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.networking.Sendable;

public class PlayerDeath implements GenericHandler {

  public void run(Client client, Sendable sendable) {
    // Set state
    System.out.println("Player death detected");

    Gson gson = new Gson();
    com.knightlore.networking.game.PlayerDeath death =
        gson.fromJson(sendable.getData(), com.knightlore.networking.game.PlayerDeath.class);

    GameConnection.gameModel.getPlayers().get(death.playerID).decrementLives();
  }
}
