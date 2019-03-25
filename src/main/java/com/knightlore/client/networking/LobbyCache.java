package com.knightlore.client.networking;

import com.knightlore.networking.ListGameObject;
import com.knightlore.networking.ListGameResponse;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class LobbyCache {

    public static LobbyCache instance;
    public int cacheBuster;
    HashMap<UUID, ListGameObject> games;

    public LobbyCache(){
        cacheBuster = 0;
        this.games = new HashMap<>();
    }

    public void setGames(ListGameResponse response){
        this.games = response.getGames();
        cacheBuster += 1;
    }

    public Collection<ListGameObject> getGames(){
        return this.games.values();
    }
}
