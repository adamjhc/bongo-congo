package com.knightlore.client.networking;

import com.knightlore.networking.ListGameObject;
import com.knightlore.networking.ListGameResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class LobbyCache {

    public static LobbyCache instance = new LobbyCache();

    HashMap<UUID, ListGameObject> games;


    public LobbyCache(){
        this.games = new HashMap<>();
    }

    public void setGames(ListGameResponse response){
        this.games = response.getGames();
    }

    public Collection<ListGameObject> getGames(){
        return this.games.values();
    }

}
