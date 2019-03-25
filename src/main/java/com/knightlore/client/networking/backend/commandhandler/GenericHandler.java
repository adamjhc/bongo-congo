package com.knightlore.client.networking.backend.commandhandler;

import com.knightlore.client.networking.backend.Client;
import com.knightlore.networking.Sendable;

public interface GenericHandler {

  void run(Client client, Sendable response);
}
