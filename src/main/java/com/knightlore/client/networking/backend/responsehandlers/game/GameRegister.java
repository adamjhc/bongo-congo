package com.knightlore.client.networking.backend.responsehandlers.game;

import com.google.gson.Gson;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.backend.responsehandlers.GenericHandler;
import com.knightlore.game.GameModel;
import com.knightlore.networking.GameRegisterResponse;
import com.knightlore.networking.Sendable;

import java.util.UUID;

public class GameRegister implements GenericHandler {

  public void run(Sendable sendable) {
    Gson gson = new Gson();

    GameRegisterResponse gameRegisterResponse = gson.fromJson(sendable.getData(), GameRegisterResponse.class);
    UUID uuid = gameRegisterResponse.uuid;

    GameConnection.instance.uuid = uuid;
  }
}
