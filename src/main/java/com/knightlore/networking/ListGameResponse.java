package com.knightlore.networking;


import java.net.InetAddress;
import java.util.HashMap;
import java.util.UUID;

public class ListGameResponse {

    HashMap<UUID, ListGameObject> games;

    public ListGameResponse() {
        games = new HashMap<>();
    }

    public void addGame(UUID uuid, InetAddress ip, int port){
        this.games.put(uuid, new ListGameObject(uuid, ip, port));
    }

    public HashMap<UUID, ListGameObject> getGames() {
        return games;
    }

}
