package com.knightlore.client.networking.backend.ResponseHandlers;

import com.knightlore.networking.Sendable;
import org.json.JSONObject;

public class GetGame implements GenericHandler{

    public void run(Sendable response){
        System.out.println("GET GAME" + response.getData());
    }
}
