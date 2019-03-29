package com.knightlore.game.server;

import com.google.gson.Gson;
import com.knightlore.game.GameModel;
import com.knightlore.game.entity.Enemy;
import com.knightlore.networking.Sendable;
import com.knightlore.networking.game.EnemyLocationUpdate;
import com.knightlore.networking.game.EnemyLocationUpdateObject;
import com.knightlore.server.database.model.Model;

import java.util.concurrent.TimeUnit;

public class EnemyPositionUpdateManager extends Thread{

    GameModel model;
    GameServer server;

    public EnemyPositionUpdateManager(GameModel model, GameServer server){
        this.model = model;
        this.server = server;
    }

    public void run(){
        /*Gson gson = new Gson();
        while(true){
            Sendable sendable = new Sendable();
            sendable.setFunction("enemy_location_update");
            EnemyLocationUpdate update = new EnemyLocationUpdate();

            for(Enemy current : model.getCurrentLevel().getEnemies()){
                update.addEnemy(current.getId(), new EnemyLocationUpdateObject(current.getPosition(), current.getDirection(), current.getCurrentState()));
            }

            sendable.setData(gson.toJson(update));

            server.sendToRegistered(sendable);

            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
            
            }

        }
*/
    }
}
