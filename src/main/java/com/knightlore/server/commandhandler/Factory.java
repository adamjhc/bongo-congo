package com.knightlore.server.commandhandler;

import com.knightlore.server.ClientHandler;
import com.knightlore.networking.Sendable;

public class Factory {


    public static void create(ClientHandler clientHandler, Sendable sendable){

        if(sendable.getFunction().equals("get_session_token")){
            GetSessionToken handler = new GetSessionToken();
            handler.run(clientHandler, sendable);
        }
    }
}
