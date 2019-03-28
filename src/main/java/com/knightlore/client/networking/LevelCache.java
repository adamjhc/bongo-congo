package com.knightlore.client.networking;

import com.knightlore.networking.server.ListLevelObject;
import com.knightlore.networking.server.ListLevelsResponse;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class LevelCache {

    public static LevelCache instance = new LevelCache();

    HashMap<UUID, ListLevelObject> levels;

    public LevelCache(){
        this.levels = new HashMap<>();
    }

    public void setLevels(ListLevelsResponse response){
        this.levels = response.getLevels();
    }

    public Collection<ListLevelObject> getLevels(){
        return this.levels.values();
    }

}
