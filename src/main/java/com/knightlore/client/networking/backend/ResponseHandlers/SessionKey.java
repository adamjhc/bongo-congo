package com.knightlore.client.networking.backend.ResponseHandlers;

import com.knightlore.networking.Sendable;
import org.json.JSONObject;

public class SessionKey implements GenericHandler{

    public void run(Sendable response){
        System.out.println("GET session" + response.getData());
    }
}
