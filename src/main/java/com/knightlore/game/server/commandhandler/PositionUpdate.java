package com.knightlore.game.server.commandhandler;

import com.google.gson.Gson;
import com.knightlore.game.server.ClientHandler;
import com.knightlore.game.server.PositionUpdateQueue;
import com.knightlore.networking.Sendable;

/**
 * Handler for new position update from client
 *
 * @author Lewis Relph
 */
public class PositionUpdate extends Command {

  public void run(ClientHandler handler, Sendable sendable) {

    // Get position
    Gson gson = new Gson();
    com.knightlore.networking.game.PositionUpdate newPosition =
        gson.fromJson(sendable.getData(), com.knightlore.networking.game.PositionUpdate.class);

    // Update our model to reflect position change
    handler.model().getPlayers().get(newPosition.sessionId).setPosition(newPosition.coordinates);
    handler.model().getPlayers().get(newPosition.sessionId).setScore(newPosition.score);
    handler.model().getPlayers().get(newPosition.sessionId).setDirection(newPosition.direction);
    handler.model().getPlayers().get(newPosition.sessionId).setPlayerState(newPosition.state);

    // Queue in position update queue
    PositionUpdateQueue.instance.add(newPosition);
  }
}
