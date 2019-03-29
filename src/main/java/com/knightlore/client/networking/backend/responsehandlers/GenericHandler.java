package com.knightlore.client.networking.backend.responsehandlers;

import com.knightlore.networking.Sendable;

public interface GenericHandler {

  void run(Sendable response);
}
