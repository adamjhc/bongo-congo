package com.knightlore.game.server.commandhandler;

import com.google.gson.Gson;
import com.knightlore.game.server.ClientHandler;
import com.knightlore.networking.Sendable;

public class PositionUpdate extends Command {

  public void run(ClientHandler handler, Sendable sendable) {
    System.out.println("Position changed");

    Gson gson = new Gson();
    com.knightlore.networking.PositionUpdate newPosition =
        gson.fromJson(sendable.getData(), com.knightlore.networking.PositionUpdate.class);

    Sendable response = new Sendable();
    response.setFunction("position_update");
    response.setData(gson.toJson(newPosition));

    handler.server().sendToRegisteredExceptSelf(response, handler.sessionKey.get());
  }
}
