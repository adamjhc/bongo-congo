package com.knightlore.game.server;

import com.google.gson.Gson;
import com.knightlore.networking.Sendable;
import com.knightlore.networking.game.PositionUpdate;
import com.knightlore.networking.game.PositionUpdateChunk;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PositionUpdateQueueHandler extends Thread{

    PositionUpdateQueue queue;
    GameServer server;

    public PositionUpdateQueueHandler(PositionUpdateQueue queue, GameServer server){
        this.queue = queue;
        this.server = server;
    }

    @Override
    public void run(){
        Gson gson = new Gson();
        while(true){
            System.out.println("Relaying");

            Sendable sendable = new Sendable();
            sendable.setFunction("position_update_chunk");
            PositionUpdateChunk chunk = queue.getQueue();
            queue.clear();

            sendable.setData(gson.toJson(chunk));

            this.server.sendToRegistered(sendable);


            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){

            }

        }
    }
}
