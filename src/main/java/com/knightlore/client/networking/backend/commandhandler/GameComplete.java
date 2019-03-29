package com.knightlore.client.networking.backend.commandhandler;

import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.game.GameState;
import com.knightlore.networking.Sendable;

public class GameComplete implements GenericHandler {

  public void run(Client client, Sendable sendable) {
    System.out.println("Game complete!");

    // Set state to score
    GameConnection.gameModel.setState(GameState.SCORE);

    GameConnection.instance.close();
  }
}
