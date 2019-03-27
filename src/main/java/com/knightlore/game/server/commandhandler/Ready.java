package com.knightlore.game.server.commandhandler;

import com.knightlore.game.server.ClientHandler;
import com.knightlore.game.server.GameManager;
import com.knightlore.networking.Sendable;

public class Ready extends Command {

  public void run(ClientHandler handler, Sendable sendable) {
    System.out.println("Game ready");

    handler.ready = true;

    if (handler.server().allReady()) {
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
