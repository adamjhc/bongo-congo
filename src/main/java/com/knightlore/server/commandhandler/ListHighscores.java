package com.knightlore.server.commandhandler;

import com.google.gson.Gson;
import com.knightlore.networking.HighscoreResponse;
import com.knightlore.networking.Sendable;
import com.knightlore.server.ClientHandler;
import com.knightlore.server.database.model.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class ListHighscores extends Command{

    final static Logger logger = Logger.getLogger(ListHighscores.class);

    public void run(ClientHandler handler, Sendable sendable) {
        logger.info("List Highscores requested");

        GamePlayer model = new GamePlayer();
        ArrayList<Model> models = model.get();


        // Build response
        Sendable response = new Sendable();
        HighscoreResponse hsResponse = new HighscoreResponse();

        int max = 3;
        for(Model currentGamePlayer: models){
            if(max == 0){
                // Retrieve username for this

            }else{
                break;
            }
            max --;
        }

    }
}
