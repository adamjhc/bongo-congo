package com.knightlore.game.server;

import com.google.gson.Gson;
import com.knightlore.networking.Sendable;
import com.knightlore.networking.game.PositionUpdateChunk;

import java.util.concurrent.TimeUnit;

/**
 * Thread for handling sending updated positions to clients
 *
 * @author Lewis Relph
 */
public class PositionUpdateQueueHandler extends Thread {

  PositionUpdateQueue queue;
  GameServer server;
  boolean running;

  public PositionUpdateQueueHandler(PositionUpdateQueue queue, GameServer server) {
    this.server = server;
    this.queue = queue;
    this.running = true;
  }

  @Override
  public void run() {
    Gson gson = new Gson();
    while (running) {
      // Retrieve latest chunk
      Sendable sendable = new Sendable();
      sendable.setFunction("position_update_chunk");
      PositionUpdateChunk chunk = queue.getQueue();

      sendable.setData(gson.toJson(chunk));

      // Send to all clients
      this.server.sendToRegistered(sendable);

      // Reset queue
      queue.clear();

      try {
        TimeUnit.MILLISECONDS.sleep(30);
      } catch (InterruptedException e) {

      }
    }
  }

  public void close() {
    this.running = false;
    this.interrupt();
  }
}
