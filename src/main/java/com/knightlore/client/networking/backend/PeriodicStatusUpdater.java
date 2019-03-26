package com.knightlore.client.networking.backend;

import com.knightlore.client.networking.GameConnection;

import java.util.concurrent.TimeUnit;

public class PeriodicStatusUpdater extends Thread {

    GameConnection gameConnection;

    public PeriodicStatusUpdater(GameConnection connection){
        gameConnection = connection;

    }

    public void run(){
        while(true){
            gameConnection.updateStatus();

            try{
                TimeUnit.MILLISECONDS.sleep(20);
            }catch(InterruptedException e){

            }

            System.out.println("send");
        }
    }
}
