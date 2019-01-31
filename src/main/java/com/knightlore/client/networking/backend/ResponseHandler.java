package com.knightlore.client.networking.backend;

import com.knightlore.client.networking.backend.ResponseHandlers.GenericHandler;
import com.knightlore.networking.Sendable;
import org.json.JSONObject;

import java.util.HashMap;

public class ResponseHandler {

    public static HashMap<String, GenericHandler> waiting = new HashMap<>();

    public static void handle(String uuid, Sendable response){
        if(waiting.containsKey(uuid)){
            GenericHandler handler = waiting.get(uuid);
            handler.run(response);
        }
    }
}
