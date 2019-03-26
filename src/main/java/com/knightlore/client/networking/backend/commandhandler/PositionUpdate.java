package com.knightlore.client.networking.backend.commandhandler;

import com.google.gson.Gson;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.networking.Sendable;

public class PositionUpdate implements GenericHandler {

  public void run(Client client, Sendable sendable) {
    // Decode
    Gson gson = new Gson();
    com.knightlore.networking.PositionUpdate location =
        gson.fromJson(sendable.getData(), com.knightlore.networking.PositionUpdate.class);

    // Update location
    GameConnection.gameModel.getPlayers().get(location.sessionId).setPosition(location.coordinates);

    // Update score
    GameConnection.gameModel.getPlayers().get(location.sessionId).setScore(location.score);
  }
}
