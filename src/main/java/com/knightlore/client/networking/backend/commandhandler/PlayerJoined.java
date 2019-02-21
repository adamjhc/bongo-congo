package com.knightlore.client.networking.backend.commandhandler;

import com.google.gson.Gson;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.networking.Sendable;

public class PlayerJoined implements GenericHandler {

  public void run(Client client, Sendable sendable) {
    // Decode
    System.out.println("PLAYER JOINED REC");
    Gson gson = new Gson();
    com.knightlore.networking.PlayerJoined playerJoined =
        gson.fromJson(sendable.getData(), com.knightlore.networking.PlayerJoined.class);

    // Update model
    com.knightlore.client.Client.model.addPlayer(playerJoined.session);
  }
}
