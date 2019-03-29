package com.knightlore.game.server.commandhandler;

import com.knightlore.game.server.*;
import com.knightlore.networking.Sendable;

/**
 * Handler for ready command signaling all clients have rendered the model and ready to play
 *
 * @author Lewis Relph
 */
public class Ready extends Command {

  public void run(ClientHandler handler, Sendable sendable) {
    // Notify handler is ready
    handler.ready = true;

    // Check if all connected clients are ready
    if (handler.server().allReady()) {
      // Start supervisor and enemy position updater
      GameManager manager = new GameManager(handler.model(), handler.server());
      EnemyPositionUpdateManager manager1 = new EnemyPositionUpdateManager(handler.model(), handler.server());
      manager.start();
      manager1.start();

      // Attach threads to server
      handler.server().poqhandler =
              new PositionUpdateQueueHandler(PositionUpdateQueue.instance,handler.server());
      handler.server().poqhandler.start();


      // Relay ready to clients to que game countdown
      Sendable ready = new Sendable();
      ready.setFunction("ready");
      handler.server().sendToRegistered(ready);
    }
  }
}
