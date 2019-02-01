package com.knightlore.client.networking.backend.ResponseHandlers;

import com.knightlore.networking.Sendable;
import org.json.JSONObject;

public interface GenericHandler {

    void run(Sendable response);
}
