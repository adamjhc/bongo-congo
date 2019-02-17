package com.knightlore.client.networking.backend.responsehandlers.server;

import com.google.gson.Gson;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.client.networking.backend.responsehandlers.GenericHandler;
import com.knightlore.networking.GameRequestResponse;
import com.knightlore.networking.Sendable;

public class StartGame implements GenericHandler {

    Gson gson = new Gson();

    public void run(Sendable response){
        System.out.println("Game Start Received!");
    }
}
