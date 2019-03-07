package com.knightlore.networking;


import java.net.InetAddress;
import java.util.HashMap;
import java.util.UUID;

public class ListLevelsResponse {

    HashMap<UUID, ListLevelObject> levels;

    public ListLevelsResponse() {
        levels = new HashMap<>();
    }

    public void addLevel(UUID uuid, String name, String created_by){
        this.levels.put(uuid, new ListLevelObject(uuid, name, created_by));
    }

    public HashMap<UUID, ListLevelObject> getLevels() {
        return levels;
    }

}
