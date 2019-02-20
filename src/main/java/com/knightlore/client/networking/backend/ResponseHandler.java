package com.knightlore.client.networking.backend;

import com.knightlore.client.networking.backend.responsehandlers.GenericHandler;
import com.knightlore.networking.Sendable;

import java.util.HashMap;

public class ResponseHandler {

    public static HashMap<String, GenericHandler> waiting = new HashMap<>();

    public static void handle(String uuid, Sendable response){
        if(waiting.containsKey(uuid)){
            GenericHandler handler = waiting.get(uuid);
            handler.run(response);
        }
    }

    public static boolean isWaiting(String uuid){
        return waiting.containsKey(uuid);
    }
}
