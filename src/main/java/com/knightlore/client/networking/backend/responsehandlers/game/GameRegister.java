package com.knightlore.client.networking.backend.responsehandlers.game;

import com.google.gson.Gson;
import com.knightlore.client.Main;
import com.knightlore.client.networking.backend.responsehandlers.GenericHandler;
import com.knightlore.game.model.Game;
import com.knightlore.networking.Sendable;

public class GameRegister implements GenericHandler {

    public void run(Sendable sendable){
        Gson gson = new Gson();

        Game model = gson.fromJson(sendable.getData(), Game.class);

        System.out.println(model.getState());
        Main.model = model;

    }
}
