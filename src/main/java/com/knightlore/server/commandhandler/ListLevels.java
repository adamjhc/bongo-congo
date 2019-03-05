package com.knightlore.server.commandhandler;

import com.knightlore.game.Level;
import com.knightlore.game.server.GameServer;
import com.knightlore.networking.ListGameResponse;
import com.knightlore.networking.ListLevelsResponse;
import com.knightlore.networking.Sendable;
import com.knightlore.server.ClientHandler;
import com.knightlore.server.database.SessionGenerator;
import com.knightlore.server.database.model.Model;
import com.knightlore.server.game.GameRepository;
import com.knightlore.util.Config;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ListLevels extends Command{

    final static Logger logger = Logger.getLogger(ListLevels.class);

    public void run(ClientHandler handler, Sendable sendable) {
        logger.info("List levels recieved");

        // Build response
        Sendable response = sendable.makeResponse();
        ListLevelsResponse listLevelResponse = new ListLevelsResponse();

        // Get levels
        com.knightlore.server.database.model.Level levelModel = new com.knightlore.server.database.model.Level();
        ArrayList<String> columns = new ArrayList<>();
        columns.add("created_by");
        columns.add("uuid");
        columns.add("name");

        for(Model level : levelModel.get(columns)){
            com.knightlore.server.database.model.Level levelCast =
                    (com.knightlore.server.database.model.Level) level;

            listLevelResponse.addLevel(UUID.fromString((String) level.getAttribute("uuid")), (String) level.getAttribute("name"), "Jack");
        }

        response.setData(gson.toJson(listLevelResponse));

        try{
            handler.dos.writeObject(response);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
