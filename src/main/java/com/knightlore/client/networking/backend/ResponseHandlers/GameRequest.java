package com.knightlore.client.networking.backend.ResponseHandlers;

import com.google.gson.Gson;
import com.knightlore.client.networking.ServerConnection;
import com.knightlore.networking.Sendable;
import com.knightlore.networking.SessionKeyResponse;

public class GameRequest implements GenericHandler{

    Gson gson = new Gson();

    public void run(Sendable response){
        System.out.println("Game Request response received");
        SessionKeyResponse data = gson.fromJson(response.getData(), SessionKeyResponse.class);


        if(data.success){
            ServerConnection.instance.authSuccess(data.key);
        }else{
            ServerConnection.instance.authFail();
        }

    }
}
