package com.knightlore.client.networking.backend.responsehandlers.game;

import com.google.gson.Gson;
import com.knightlore.client.Client;
import com.knightlore.client.Main;
import com.knightlore.client.networking.backend.responsehandlers.GenericHandler;
import com.knightlore.game.Game;
import com.knightlore.game.map.MapSet;
import com.knightlore.game.map.TileSet;
import com.knightlore.networking.Sendable;

public class GameRegister implements GenericHandler {

    public void run(Sendable sendable){
        Gson gson = new Gson();

        Game model = gson.fromJson(sendable.getData(), Game.class);
        model.createNewLevel(new MapSet(new TileSet()).getMap(0));

        Client.model = model;

    }
}
