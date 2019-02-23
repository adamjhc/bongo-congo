package com.knightlore.networking;


import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ListGameResponse {

    HashMap<UUID, ListGameObject> games;

    public ListGameResponse() {
        games = new HashMap<>();
    }

    public void addGame(UUID uuid, InetAddress ip, int port, ArrayList<String> connectedSessions){
        this.games.put(uuid, new ListGameObject(uuid, ip, port, connectedSessions));
    }

    public HashMap<UUID, ListGameObject> getGames() {
        return games;
    }

}
