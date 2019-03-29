package com.knightlore.client.networking.backend.commandhandler;

import com.google.gson.Gson;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.networking.Sendable;
import com.knightlore.networking.game.PositionUpdate;

public class PositionUpdateChunk implements GenericHandler {

  public void run(Client client, Sendable sendable) {
    // Decode
    Gson gson = new Gson();
    com.knightlore.networking.game.PositionUpdateChunk chunk =
        gson.fromJson(sendable.getData(), com.knightlore.networking.game.PositionUpdateChunk.class);

    for (PositionUpdate update : chunk.getQueue()) {
      if (!update.sessionId.equals(GameConnection.instance.sessionKey)) {
        // Update location
        GameConnection.gameModel.getPlayers().get(update.sessionId).setPosition(update.coordinates);
        GameConnection.gameModel.getPlayers().get(update.sessionId).setDirection(update.direction);
        GameConnection.gameModel.getPlayers().get(update.sessionId).setPlayerState(update.state);

        // Update score
        GameConnection.gameModel.getPlayers().get(update.sessionId).setScore(update.score);
      }
    }
  }
}
