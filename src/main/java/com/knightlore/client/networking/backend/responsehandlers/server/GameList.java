package com.knightlore.client.networking.backend.responsehandlers.server;

import com.google.gson.Gson;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.LobbyCache;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.client.networking.backend.responsehandlers.GenericHandler;
import com.knightlore.networking.GameRequestResponse;
import com.knightlore.networking.ListGameObject;
import com.knightlore.networking.ListGameResponse;
import com.knightlore.networking.Sendable;

import java.util.ArrayList;
import java.util.Collection;

public class GameList implements GenericHandler {

    Gson gson = new Gson();

    public void run(Sendable response){
        System.out.println("List game response");
        ListGameResponse data = gson.fromJson(response.getData(), ListGameResponse.class);

        // Set games based on response data
        LobbyCache.instance.setGames(data);

        // TODO replace with listener
        for(ListGameObject game : LobbyCache.instance.getGames()){
            System.out.println("---------------");
            System.out.println("PORT: " + game.getPort());
            System.out.println("---------------");
        }
    }
}
