package com.knightlore.client.networking.backend.commandhandler;

import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.networking.Sendable;

public class GameClose implements GenericHandler {

  public void run(Client client, Sendable sendable) {
    // Set state
    System.out.println("Game close");

    GameConnection.instance.close();
  }
}
