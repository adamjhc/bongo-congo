package com.knightlore.client.networking.backend.commandhandler;


import com.google.gson.Gson;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.networking.game.EnemyLocationUpdate;
import com.knightlore.networking.game.EnemyLocationUpdateObject;
import com.knightlore.networking.Sendable;

import java.util.Map;

public class EnemyPositionUpdate implements GenericHandler{

    public void run(Client client, Sendable sendable){
        System.out.println("Enemy position update rec");
        Gson gson = new Gson();
        EnemyLocationUpdate enemyLocationUpdate = gson.fromJson(sendable.getData(), EnemyLocationUpdate.class);

        for(Map.Entry<Integer, EnemyLocationUpdateObject> entity : enemyLocationUpdate.updates.entrySet()){
            GameConnection.gameModel.getCurrentLevel().getEnemies().get(entity.getKey()).setPosition(entity.getValue().position);
            GameConnection.gameModel.getCurrentLevel().getEnemies().get(entity.getKey()).setDirection(entity.getValue().direction);
            GameConnection.gameModel.getCurrentLevel().getEnemies().get(entity.getKey()).setCurrentState(entity.getValue().state);
        }
    }

}
