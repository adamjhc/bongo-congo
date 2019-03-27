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
      GameConnection.instance.updateStatus();

      try {
        TimeUnit.MILLISECONDS.sleep(20);
      } catch (InterruptedException e) {

      }

      System.out.println("send");
    }
  }
}
