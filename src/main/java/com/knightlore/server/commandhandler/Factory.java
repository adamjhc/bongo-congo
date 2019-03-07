package com.knightlore.server.commandhandler;

import com.knightlore.server.ClientHandler;
import com.knightlore.networking.Sendable;

public class Factory {


    public static void create(ClientHandler clientHandler, Sendable sendable){
        Command handler;

        if(sendable.getFunction().equals("get_session_token")){
            handler = new GetSessionToken();
            handler.run(clientHandler, sendable);
        }
        if(sendable.getFunction().equals("request_game")){
            handler = new GameRequest();
            handler.run(clientHandler, sendable);
        }
        if(sendable.getFunction().equals("list_games")){
            handler = new ListGames();
            handler.run(clientHandler, sendable);
        }
        if(sendable.getFunction().equals("list_levels")){
            handler = new ListLevels();
            handler.run(clientHandler, sendable);
        }
    }
}
