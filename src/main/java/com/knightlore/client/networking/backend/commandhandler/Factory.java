package com.knightlore.client.networking.backend.commandhandler;

import com.knightlore.client.networking.backend.Client;
import com.knightlore.networking.Sendable;

public class Factory {

    public static void create(Client client, Sendable sendable){
        System.out.println("FACTORY" + sendable.getFunction());
        if(sendable.getFunction().equals("start_game")){
            StartGame handler = new StartGame();
            handler.run(client, sendable);
        }
    }
}
