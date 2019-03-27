package com.knightlore.client.networking.backend.responsehandlers.server;

import com.google.gson.Gson;
import com.knightlore.client.networking.HighScoreCache;
import com.knightlore.client.networking.backend.responsehandlers.GenericHandler;
import com.knightlore.networking.HighScoreResponse;
import com.knightlore.networking.Sendable;

public class HighScores implements GenericHandler {

  public void run(Sendable response) {
    System.out.println("HighScores response");

    Gson gson = new Gson();
    HighScoreResponse highScoreResponse =
        gson.fromJson(response.getData(), HighScoreResponse.class);
    HighScoreCache.instance.setScores(highScoreResponse);
  }
}
