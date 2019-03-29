package com.knightlore.client.networking.backend.responsehandlers.server;

import com.google.gson.Gson;
import com.knightlore.client.networking.LobbyCache;
import com.knightlore.client.networking.backend.responsehandlers.GenericHandler;
import com.knightlore.networking.server.ListGameResponse;
import com.knightlore.networking.Sendable;

public class GameList implements GenericHandler {

    Gson gson = new Gson();

    public void run(Sendable response){
        System.out.println("List game response");
        ListGameResponse data = gson.fromJson(response.getData(), ListGameResponse.class);

        // Set games based on response data
        if(LobbyCache.instance == null){
            LobbyCache.instance = new LobbyCache();
        }

        LobbyCache.instance.setGames(data);

    }
}
