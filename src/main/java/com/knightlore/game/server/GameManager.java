package com.knightlore.game.server;

import com.google.gson.Gson;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.game.GameModel;
import com.knightlore.game.entity.Enemy;
import com.knightlore.networking.EnemyLocationUpdate;
import com.knightlore.networking.Sendable;
import com.knightlore.server.database.model.Model;
import org.joml.Vector3f;

import java.util.concurrent.TimeUnit;

public class GameManager extends Thread {

    GameModel model;
    GameServer server;

    public GameManager(GameModel model, GameServer server){
        this.model = model;
        this.server = server;
    }

    @Override
    public void run(){
        while(true){
            Enemy james = this.model.getCurrentLevel().getEnemies().get(0);

            Vector3f loc = james.getPosition();
            loc.x += 0.05;
            james.setPosition(loc);

            try{
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(InterruptedException e){

            }

            // Update
            EnemyLocationUpdate update = new EnemyLocationUpdate();
            update.addEnemy(james.getId(), james.getPosition());

            Sendable sendable = new Sendable();
            sendable.setFunction("enemy_location_update");
            Gson gson = new Gson();
            sendable.setData(gson.toJson(update));

            server.sendToRegistered(sendable);

        }
    }
}
