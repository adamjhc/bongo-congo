package com.knightlore.networking;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;

public class EnemyLocationUpdate {

    public HashMap<Integer, Vector3f> updates;

    public EnemyLocationUpdate() {
        this.updates = new HashMap<>();
    }

    public void addEnemy(Integer id, Vector3f loc){
        updates.put(id, loc);
    }
}
