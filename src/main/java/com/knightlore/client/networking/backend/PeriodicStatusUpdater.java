package com.knightlore.client.networking.backend;

import com.knightlore.client.networking.GameConnection;

import java.util.concurrent.TimeUnit;

public class PeriodicStatusUpdater extends Thread {

  boolean running;
  private Client client;

  public PeriodicStatusUpdater(Client client) {
    this.client = client;
    running = true;
  }

  @Override
  public void run() {
    while (running) {
      if (GameConnection.gameModel != null) {
        GameConnection.instance.updateStatus();
      }

      try {
        TimeUnit.MILLISECONDS.sleep(60);
      } catch (InterruptedException e) {

      }
    }
  }

  public void close() {
    System.out.println("Closing periodic status");
    this.running = false;
    this.interrupt();
  }
}
