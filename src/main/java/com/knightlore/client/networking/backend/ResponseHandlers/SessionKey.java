package com.knightlore.client.networking.backend.ResponseHandlers;

import com.knightlore.client.networking.ServerConnection;
import com.knightlore.networking.Sendable;
import com.knightlore.networking.SessionKeyResponse;
import org.json.JSONObject;
import com.google.gson.Gson;

public class SessionKey implements GenericHandler{


    Gson gson = new Gson();

    public void run(Sendable response){
        System.out.println("Session key received");
        SessionKeyResponse data = gson.fromJson(response.getData(), SessionKeyResponse.class);


        if(data.success){
            ServerConnection.instance.authSuccess(data.key);
        }else{
            ServerConnection.instance.authFail();
        }

    }
}
