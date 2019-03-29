package com.knightlore.client.networking.backend.commandhandler;

import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.networking.Sendable;

public class LevelComplete implements GenericHandler {

  public void run(Client client, Sendable sendable) {
    System.out.println("level complete!");

    // Increment
    GameConnection.gameModel.nextLevel();
  }
}
