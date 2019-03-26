package com.knightlore.client.networking.backend;

import com.google.gson.Gson;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.networking.PositionUpdate;
import com.knightlore.networking.Sendable;

import java.util.concurrent.TimeUnit;

public class PeriodicLocationUpdater extends Thread {

    GameConnection gameConnection;

    public PeriodicLocationUpdater(GameConnection connection){
        gameConnection = connection;

    }

    public void run(){

        while(true){
            gameConnection.updatePosition(GameConnection.gameModel.myPlayer().getPosition());

            try{
                TimeUnit.MILLISECONDS.sleep(20);
            }catch(InterruptedException e){

            }

            System.out.println("send");
        }
    }
}
