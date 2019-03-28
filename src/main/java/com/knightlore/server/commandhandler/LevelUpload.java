package com.knightlore.server.commandhandler;

import com.google.gson.Gson;
import com.knightlore.networking.Sendable;
import com.knightlore.server.ClientHandler;
import com.knightlore.server.database.model.*;
import org.apache.log4j.Logger;

import java.util.*;

public class LevelUpload extends Command{

    final static Logger logger = Logger.getLogger(LevelUpload.class);

    public void run(ClientHandler handler, Sendable sendable) {
        logger.info("Level upload request rec");

        Gson gson = new Gson();
        com.knightlore.networking.server.LevelUpload levelUpload =
                gson.fromJson(sendable.getData(), com.knightlore.networking.server.LevelUpload.class);



        // Get api key from session key
        SessionToken current = new SessionToken();
        current.where(new Condition("token", "=", handler.sessionKey.get()));
        Optional<Model> currentTokenModel = current.first();

        System.out.println("Name: "+ levelUpload.name);
        System.out.println("ID: "+  currentTokenModel.get().getAttribute("registration_key_id"));

        Level levelModel = new Level();
        levelModel.setAttribute("created_by", (int) currentTokenModel.get().getAttribute("registration_key_id"));
        levelModel.setAttribute("uuid", UUID.randomUUID().toString());
        levelModel.setAttribute("name", levelUpload.name);
        levelModel.setAttribute("data", gson.toJson(levelUpload.level));
        levelModel.save();
    }
}
