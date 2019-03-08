package com.knightlore.client.networking.backend.responsehandlers.game;

import com.google.gson.Gson;
import com.knightlore.client.gui.screen.LobbyScreen;
import com.knightlore.client.networking.backend.responsehandlers.GenericHandler;
import com.knightlore.game.GameModel;
import com.knightlore.networking.Sendable;

public class GameRegister implements GenericHandler {

  public void run(Sendable sendable) {
    Gson gson = new Gson();

    GameModel model = gson.fromJson(sendable.getData(), GameModel.class);
    LobbyScreen.gameModel = model;
  }
}
