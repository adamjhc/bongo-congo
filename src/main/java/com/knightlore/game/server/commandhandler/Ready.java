package com.knightlore.game.server.commandhandler;

import com.google.gson.Gson;
import com.knightlore.client.Client;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.game.server.ClientHandler;
import com.knightlore.game.server.GameManager;
import com.knightlore.networking.ApiKey;
import com.knightlore.networking.GameRegisterResponse;
import com.knightlore.networking.PlayerJoined;
import com.knightlore.networking.Sendable;

import java.util.Optional;

public class Ready extends Command {

  public void run(ClientHandler handler, Sendable sendable) {
    System.out.println("Game ready");

    handler.ready = true;

    if(handler.server().allReady()){
      // Start supervisor
      GameManager manager = new GameManager(handler.model(), handler.server());
      manager.start();

      // Send
      Sendable ready = new Sendable();
      ready.setFunction("ready");
      handler.server().sendToRegistered(ready);
    }
  }
}
