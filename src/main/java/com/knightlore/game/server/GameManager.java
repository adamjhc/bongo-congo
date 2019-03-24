package com.knightlore.game.server;

import com.knightlore.game.GameModel;
import com.knightlore.game.entity.Enemy;
import com.knightlore.server.database.model.Model;
import org.joml.Vector3f;

import java.util.concurrent.TimeUnit;

public class GameManager extends Thread {

    GameModel model;

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

        }
    }
}
