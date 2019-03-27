package com.knightlore.networking;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;

public class EnemyLocationUpdate {

    public HashMap<Integer, EnemyLocationUpdateObject> updates;

    public EnemyLocationUpdate() {
        this.updates = new HashMap<>();
    }

    public void addEnemy(Integer id, EnemyLocationUpdateObject loc){
        updates.put(id, loc);
    }
}
