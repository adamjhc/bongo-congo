package com.knightlore.client.networking.backend;

import com.google.gson.Gson;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.game.entity.Player;
import com.knightlore.networking.PositionUpdate;
import com.knightlore.networking.Sendable;
import org.joml.Vector3f;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PeriodicUpdaterThread extends Thread {

    Player player;

    public PeriodicUpdaterThread(Player player){
        this.player = player;
    }

    public void run(){

        while(true){
            try{
                TimeUnit.MILLISECONDS.sleep(16);

                // Retrieve position

                Vector3f vector = this.player.getPosition();

                GameConnection.instance.updatePosition(vector);

            }catch(InterruptedException e){

            }

        }
    }
}
