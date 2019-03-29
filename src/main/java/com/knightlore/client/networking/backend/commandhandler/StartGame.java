package com.knightlore.client.networking.backend.commandhandler;

import com.google.gson.Gson;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.game.GameState;
import com.knightlore.networking.Sendable;
import com.knightlore.networking.game.GameStart;

public class StartGame implements GenericHandler {

  public void run(Client client, Sendable sendable) {
    Gson gson = new Gson();
    GameStart gameStart = gson.fromJson(sendable.getData(), GameStart.class);
    System.out.println("Setting model");

    // Load model
    GameConnection.gameModel = gameStart.model;

    // Set state
    GameConnection.gameModel.setState(GameState.WAITING_READY);
  }
}
