package com.knightlore.client.networking.backend.responsehandlers.server;

import com.google.gson.Gson;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.ServerConnection;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.client.networking.backend.responsehandlers.GenericHandler;
import com.knightlore.networking.GameRequestResponse;
import com.knightlore.networking.Sendable;

import java.util.concurrent.TimeUnit;

public class GameRequest implements GenericHandler {

    Gson gson = new Gson();

    public void run(Sendable response){
        System.out.println("Game Request response received");
        GameRequestResponse data = gson.fromJson(response.getData(), GameRequestResponse.class);

        System.out.println("SUCCESS" + response.success);

        if(response.success){
            // Connect to server
            Client gameClient = new Client(data.ip, data.port);
            gameClient.run();

            GameConnection.instance = new GameConnection(gameClient, ServerConnection.instance.getSessionKey().get());

            // Connection established
            // Call additional listeners
            GameConnection.instance.gameConnectionMade();
        }else{
            GameConnection.instance.gameConnectionFailed();
        }
    }
}
