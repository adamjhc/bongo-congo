package com.knightlore.networking;

import java.util.ArrayList;
import java.util.UUID;

public class GameRequest {

    ArrayList<UUID> levels;

    public GameRequest() {
        levels = new ArrayList<>();
    }

    public GameRequest(ArrayList<UUID> levelUUIDS) {
        levels = levelUUIDS;
    }

    public void addLevel(UUID levelUUID){
        this.levels.add(levelUUID);
    }

    public void setLevels(ArrayList<UUID> levelUUIDS){
        this.levels = levelUUIDS;
    }

    public ArrayList<UUID> getLevels(){
        return this.levels;
    }


}
