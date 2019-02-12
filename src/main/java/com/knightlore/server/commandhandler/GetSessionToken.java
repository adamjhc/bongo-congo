package com.knightlore.server.commandhandler;

import com.knightlore.client.networking.backend.ResponseHandlers.SessionKey;
import com.knightlore.networking.ApiKey;
import com.knightlore.networking.Sendable;
import com.knightlore.networking.SessionKeyResponse;
import com.knightlore.server.ClientHandler;
import com.knightlore.server.database.SessionGenerator;
import com.knightlore.server.database.model.Condition;
import com.knightlore.server.database.model.Model;
import com.knightlore.server.database.model.RegistrationKey;
import com.knightlore.server.database.model.SessionToken;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

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
            // Check against existing API key
            RegistrationKey reg = new RegistrationKey();
            System.out.println(apikey.key);
            reg.where(new Condition("key", "=", apikey.key));

            ArrayList<Model> tokens = reg.get();

            if(tokens.size() == 1){
                Integer keyID = (int) tokens.get(0).getAttribute("id");

                String sessionKey = SessionGenerator.generateActivationKey();

                // Add to session
                handler.sessionKey = Optional.of(sessionKey);


                SessionToken token = new SessionToken();
                token.setAttribute("id", keyID);
                token.setAttribute("token", sessionKey);
                token.setAttribute("ip", handler.getSocketIP().toString());
                token.setAttribute("registration_key_id", keyID);


                Calendar calendar = Calendar.getInstance();
                java.util.Date now = calendar.getTime();
                java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());


                token.setAttribute("last_used_at", currentTimestamp);
                token.setAttribute("created_at", currentTimestamp);
                token.setAttribute("expires_at", currentTimestamp);


//                Date currentDatePlusOne = c.getTime();
//
//                c.add(Calendar.DATE, 1);
//
//                java.sql.Date date = new java.sql.Date(currentDatePlusOne.getTime());


                token.save();

                // Respond
                sessionKeyResponse = new SessionKeyResponse(true, sessionKey);
            }else{
                sessionKeyResponse = new SessionKeyResponse(false);
            }

        }

        response.setData(gson.toJson(sessionKeyResponse));

        try{
            handler.dos.writeObject(response);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
