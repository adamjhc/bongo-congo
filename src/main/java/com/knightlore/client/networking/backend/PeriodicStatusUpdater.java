package com.knightlore.client.networking.backend;

import com.knightlore.client.networking.GameConnection;
import java.util.concurrent.TimeUnit;

public class PeriodicStatusUpdater extends Thread {

  private Client client;

  public PeriodicStatusUpdater(Client client) {
    this.client = client;
  }

  @Override
  public void run() {
    while (client.isRunning()) {
      if (GameConnection.gameModel != null) {
        GameConnection.instance.updateStatus();
      }

      try {
        TimeUnit.MILLISECONDS.sleep(60);
      } catch (InterruptedException e) {

      }

      System.out.println("send");
    }
  }
}
