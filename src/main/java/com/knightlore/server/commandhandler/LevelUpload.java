package com.knightlore.server.commandhandler;

import com.google.gson.Gson;
import com.knightlore.networking.GameRequestResponse;
import com.knightlore.networking.Sendable;
import com.knightlore.server.ClientHandler;
import com.knightlore.server.database.model.*;
import com.knightlore.server.game.GameRepository;
import com.knightlore.util.Config;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class LevelUpload extends Command{

    final static Logger logger = Logger.getLogger(LevelUpload.class);

    public void run(ClientHandler handler, Sendable sendable) {
        logger.info("Level upload request rec");

        Gson gson = new Gson();
        com.knightlore.networking.LevelUpload levelUpload =
                gson.fromJson(sendable.getData(), com.knightlore.networking.LevelUpload.class);



        // Get api key from session key
        SessionToken current = new SessionToken();
        current.where(new Condition("token", "=", handler.sessionKey.get()));
        Optional<Model> currentTokenModel = current.first();

        Level levelModel = new Level();
        levelModel.setAttribute("created_by", currentTokenModel.get().getAttribute("registration_key_id"));
        levelModel.setAttribute("uuid", UUID.randomUUID().toString());
        levelModel.setAttribute("name", levelUpload.name);
        levelModel.setAttribute("name", gson.toJson(levelUpload.level));
    }
}
