package com.knightlore.game.server.commandhandler;

import com.knightlore.game.server.ClientHandler;
import com.knightlore.networking.ApiKey;
import com.knightlore.networking.Sendable;
import com.knightlore.networking.SessionKeyResponse;

public class StartGame extends Command {

    public void run(ClientHandler handler, Sendable sendable) {
        System.out.println("START GAME CALLED");

        // Create json data
        String json = sendable.getData();
        ApiKey apikey = gson.fromJson(json, ApiKey.class);

        Sendable response = sendable.makeResponse();
        SessionKeyResponse sessionKeyResponse;

        // Check if session key is equal to game owner
        if(handler.isOwner()){
            // Start Game
            handler.server().startGame();
        }else{
            System.out.println("NOT OWNER");
        }
    }
}
