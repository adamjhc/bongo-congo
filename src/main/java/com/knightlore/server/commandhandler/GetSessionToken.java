package com.knightlore.server.commandhandler;

import com.knightlore.networking.ApiKey;
import com.knightlore.networking.Sendable;
import com.knightlore.networking.SessionKeyResponse;
import com.knightlore.server.ClientHandler;
import com.knightlore.server.database.SessionGenerator;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class GetSessionToken extends Command{
    SessionGenerator apikey = new SessionGenerator();

    public void run(ClientHandler handler, Sendable sendable) {
        System.out.println("GET SESSION TOKEN CALLED");

        // Create json data
        String json = sendable.getData();
        ApiKey apikey = gson.fromJson(json, ApiKey.class);

        Sendable response = sendable.makeResponse();
        SessionKeyResponse sessionKeyResponse;

        if(handler.sessionKey.isPresent()){
            String sessionKey = handler.sessionKey.get();
            sessionKeyResponse = new SessionKeyResponse(true, sessionKey);
        }else{
            sessionKeyResponse = new SessionKeyResponse(false);
        }

        response.setData(gson.toJson(sessionKeyResponse));

        try{
            handler.dos.writeObject(response);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
