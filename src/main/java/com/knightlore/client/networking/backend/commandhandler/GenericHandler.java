package com.knightlore.client.networking.backend.commandhandler;

import com.knightlore.networking.Sendable;

public interface GenericHandler {

    void run(Sendable response);
}
