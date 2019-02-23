package com.knightlore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.knightlore.game.Game;
import com.knightlore.game.map.MapSet;
import com.knightlore.game.map.TileSet;
import com.knightlore.game.server.GameServer;
import com.knightlore.server.database.model.Model;



public class Test {

    public static void main(String[] args){

        Game game = new Game("1", new MapSet(new TileSet()));
        game.createNewLevel(new MapSet(new TileSet()).getMap(0));

        Gson gson = new Gson();

        String str2send = gson.toJson(game);

        Game msg_recv = gson.fromJson(str2send, Game.class);

        System.out.println(str2send);
    }
}
