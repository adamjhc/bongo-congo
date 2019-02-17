package com.knightlore.game.server.commandhandler;

import com.knightlore.game.server.ClientHandler;
import com.knightlore.networking.ApiKey;
import com.knightlore.networking.Sendable;
import com.knightlore.networking.SessionKeyResponse;

import java.util.Optional;

public class Register extends Command {

    public void run(ClientHandler handler, Sendable sendable) {
        System.out.println("Game server register called");

        // Create json data
        String json = sendable.getData();
        ApiKey apikey = gson.fromJson(json, ApiKey.class);


        // Save session key
        handler.sessionKey = Optional.of(apikey.key);
    }
}
