package com.knightlore.client.networking.backend;

import com.knightlore.client.networking.backend.commandhandler.Factory;
import com.knightlore.networking.Sendable;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientReceiver extends Thread {

  ObjectInputStream dis;
  Client client;
  boolean running;
  int yeetCount = 0;

  public ClientReceiver(Client client, ObjectInputStream dis) {
    this.client = client;
    this.dis = dis;
    this.running = true;
  }

  @Override
  public void run() {

    Sendable received;

    while (running) {
      try {
        received = (Sendable) dis.readObject();
        System.out.println(received.getFunction());

        if (received.hasUUID() && ResponseHandler.isWaiting(received.getUuid())) {
          // Request is a command response, pass to handler
          System.out.println("UUID " + received.getUuid());
          ResponseHandler.handle(received.getUuid(), received);
          continue;
        }

        // Pass to handlers
        Factory.create(this.client, received);

        System.out.println("CLIENT RECEIVED: " + received);
      } catch (IOException e) {
        if (yeetCount == 0) {
          System.out.println("ERR");
          yeetCount++;
        }
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }

    System.out.println("Connection lost");
  }

  public void close() {
    this.running = false;
    this.interrupt();
  }
}
