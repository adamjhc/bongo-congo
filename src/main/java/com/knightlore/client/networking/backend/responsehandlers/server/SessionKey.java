package com.knightlore.client.networking.backend.responsehandlers.server;

import com.knightlore.client.networking.ServerConnection;
import com.knightlore.client.networking.backend.responsehandlers.GenericHandler;
import com.knightlore.networking.Sendable;
import com.knightlore.networking.SessionKeyResponse;
import com.google.gson.Gson;

public class SessionKey implements GenericHandler {


    Gson gson = new Gson();

    public void run(Sendable response){
        System.out.println("Session key received");
        SessionKeyResponse data = gson.fromJson(response.getData(), SessionKeyResponse.class);

        if(data.success){
            ServerConnection.instance.authSuccess(data.key, data.username);
        }else{
            ServerConnection.instance.authFail();
        }

    }
}
