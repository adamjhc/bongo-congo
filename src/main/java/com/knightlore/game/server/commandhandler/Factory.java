package com.knightlore.game.server.commandhandler;

import com.knightlore.networking.Sendable;
import com.knightlore.game.server.ClientHandler;

public class Factory {

    public static void create(ClientHandler clientHandler, Sendable sendable){

        if(sendable.getFunction().equals("game_start")){
            StartGame handler = new StartGame();
            handler.run(clientHandler, sendable);
        }
        if(sendable.getFunction().equals("register")){
            Register handler = new Register();
            handler.run(clientHandler, sendable);
        }
    }
}
