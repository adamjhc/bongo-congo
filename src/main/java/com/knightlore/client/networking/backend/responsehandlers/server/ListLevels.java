package com.knightlore.client.networking.backend.responsehandlers.server;

import com.google.gson.Gson;
import com.knightlore.client.networking.LevelCache;
import com.knightlore.client.networking.backend.responsehandlers.GenericHandler;
import com.knightlore.networking.Sendable;
import com.knightlore.networking.server.ListLevelsResponse;

public class ListLevels implements GenericHandler {

  Gson gson = new Gson();

  public void run(Sendable response) {
    System.out.println("List level response");

    ListLevelsResponse data = gson.fromJson(response.getData(), ListLevelsResponse.class);

    // Store in level cache
    LevelCache.instance.setLevels(data);
  }
}
