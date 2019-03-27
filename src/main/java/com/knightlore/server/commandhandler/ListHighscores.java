package com.knightlore.server.commandhandler;

import com.knightlore.networking.HighScoreResponse;
import com.knightlore.networking.Sendable;
import com.knightlore.server.ClientHandler;
import com.knightlore.server.database.model.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class ListHighscores extends Command{

    final static Logger logger = Logger.getLogger(ListHighscores.class);

    public void run(ClientHandler handler, Sendable sendable) {
        logger.info("List Highscores requested");

        GamePlayer model = new GamePlayer();
        ArrayList<Model> models = model.get();


        // Build response
        Sendable response = sendable.makeResponse();
        HighScoreResponse hsResponse = new HighScoreResponse();

        int max = 20;
        for(Model currentGamePlayer: models){
            if(max != 0){
                // Retrieve username for this
                SessionToken stm = new SessionToken();
                stm.where(new Condition("id", "=", (int) currentGamePlayer.getAttribute("session_id")));
                Optional<Model> st = stm.first();

                // Retrieve Username
                RegistrationKey rkm = new RegistrationKey();
                rkm.where(new Condition("id", "=", (int)st.get().getAttribute("registration_key_id")));
                Optional<Model> rk = rkm.first();

                hsResponse.addScore((String) rk.get().getAttribute("username"),
                        (int) currentGamePlayer.getAttribute("score"));
            }else{
                break;
            }
            max --;
        }

        System.out.println("size: " + hsResponse.scores.size());
        response.setData(gson.toJson(hsResponse));
        try{
            handler.dos.writeObject(response);
        }catch(IOException e){

        }
    }
}
