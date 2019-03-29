package com.knightlore.client.networking.backend.responsehandlers.server;

import com.knightlore.client.networking.backend.responsehandlers.GenericHandler;
import com.knightlore.networking.Sendable;

public class GetGame implements GenericHandler {

  public void run(Sendable response) {
    System.out.println("GET GAME" + response.getData());
  }
}
