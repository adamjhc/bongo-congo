package com.knightlore.client.networking;

import com.knightlore.networking.server.ListLevelObject;
import com.knightlore.networking.server.ListLevelsResponse;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

/**
 * Store for retrieved levels from server
 *
 * @author Lewis Relph
 */
public class LevelCache {

    public static LevelCache instance = new LevelCache();

    HashMap<UUID, ListLevelObject> levels;

    /**
     * default constructor
     */
    public LevelCache(){
        this.levels = new HashMap<>();
    }

    /**
     * Setter for levels
     * @param response
     */
    public void setLevels(ListLevelsResponse response){
        this.levels = response.getLevels();
    }

    /**
     * Getter for levels
     * @return
     */
    public Collection<ListLevelObject> getLevels(){
        return this.levels.values();
    }

}
