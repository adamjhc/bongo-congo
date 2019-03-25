package com.knightlore.client.networking.backend.commandhandler;

import com.knightlore.client.networking.ServerConnection;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.networking.Sendable;

public class PlayerJoined implements GenericHandler {

  public void run(Client client, Sendable sendable) {
    // Decode
    System.out.println("PLAYER JOINED REC");

    // Refresh
    ServerConnection.instance.listGames();
  }
}
