package com.knightlore.client.networking.backend.responsehandlers.server;

import com.google.gson.Gson;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.client.networking.backend.responsehandlers.GenericHandler;
import com.knightlore.networking.GameRequestResponse;
import com.knightlore.networking.ListGameObject;
import com.knightlore.networking.ListGameResponse;
import com.knightlore.networking.Sendable;

public class GameList implements GenericHandler {

    Gson gson = new Gson();

    public void run(Sendable response){
        System.out.println("List game response");
        ListGameResponse data = gson.fromJson(response.getData(), ListGameResponse.class);

        // TODO replace
        for(ListGameObject game : data.getGames().values()){
            System.out.println("UUID: " + game.getUuid() + " IP: " + game.getIp() + " Port: " + game.getPort());
            System.out.println("---------------------");
        }
    }
}
