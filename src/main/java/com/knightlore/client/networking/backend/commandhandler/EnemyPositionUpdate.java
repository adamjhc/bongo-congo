package com.knightlore.client.networking.backend.commandhandler;


import com.google.gson.Gson;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.networking.EnemyLocationUpdate;
import com.knightlore.networking.Sendable;
import org.joml.Vector3f;

import java.util.Map;

public class EnemyPositionUpdate implements GenericHandler{

    public void run(Client client, Sendable sendable){
        System.out.println("Enemy position update rec");
        Gson gson = new Gson();
        EnemyLocationUpdate enemyLocationUpdate = gson.fromJson(sendable.getData(), EnemyLocationUpdate.class);

        for(Map.Entry<Integer, Vector3f> entity : enemyLocationUpdate.updates.entrySet()){
            GameConnection.gameModel.getCurrentLevel().getEnemies().get(entity.getKey()).setPosition(entity.getValue());
        }
    }

}
