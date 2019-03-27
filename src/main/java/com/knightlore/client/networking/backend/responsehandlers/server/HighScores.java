package com.knightlore.client.networking.backend.responsehandlers.server;

import com.google.gson.Gson;
import com.knightlore.client.networking.HighScoreCache;
import com.knightlore.client.networking.LobbyCache;
import com.knightlore.client.networking.backend.responsehandlers.GenericHandler;
import com.knightlore.networking.HighscoreResponse;
import com.knightlore.networking.ListGameResponse;
import com.knightlore.networking.Sendable;

public class HighScores implements GenericHandler {

    Gson gson = new Gson();

    public void run(Sendable response){
        System.out.println("Highscores response");

        Gson gson = new Gson();
        HighscoreResponse highscoreResponse = gson.fromJson(response.getData(), HighscoreResponse.class);
        HighScoreCache.instance.setScores(highscoreResponse);



    }
}
