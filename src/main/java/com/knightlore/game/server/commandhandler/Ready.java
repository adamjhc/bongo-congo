package com.knightlore.game.server.commandhandler;

import com.knightlore.game.server.*;
import com.knightlore.networking.Sendable;

public class Ready extends Command {

  public void run(ClientHandler handler, Sendable sendable) {
    System.out.println("Game ready");

    handler.ready = true;

    if (handler.server().allReady()) {
      // Start supervisor
      GameManager manager = new GameManager(handler.model(), handler.server());
      manager.start();

      handler.server().poqhandler =
              new PositionUpdateQueueHandler(PositionUpdateQueue.instance,handler.server());
      handler.server().poqhandler.start();


      // Send
      Sendable ready = new Sendable();
      ready.setFunction("ready");
      handler.server().sendToRegistered(ready);
    }
  }
}
