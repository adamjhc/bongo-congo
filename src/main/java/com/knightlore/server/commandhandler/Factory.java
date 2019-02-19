package com.knightlore.server.commandhandler;

import com.knightlore.server.ClientHandler;
import com.knightlore.networking.Sendable;

public class Factory {


    public static void create(ClientHandler clientHandler, Sendable sendable){

        if(sendable.getFunction().equals("get_session_token")){
            GetSessionToken handler = new GetSessionToken();
            handler.run(clientHandler, sendable);
        }
        if(sendable.getFunction().equals("request_game")){
            GameRequest handler = new GameRequest();
            handler.run(clientHandler, sendable);
        }
        if(sendable.getFunction().equals("list_games")){
            ListGames handler = new ListGames();
            handler.run(clientHandler, sendable);
        }
    }
}
