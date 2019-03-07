package com.knightlore.server.commandhandler;

import com.knightlore.game.server.GameServer;
import com.knightlore.networking.ApiKey;
import com.knightlore.networking.ListGameResponse;
import com.knightlore.networking.Sendable;
import com.knightlore.networking.SessionKeyResponse;
import com.knightlore.server.ClientHandler;
import com.knightlore.server.database.SessionGenerator;
import com.knightlore.server.database.model.*;
import com.knightlore.server.game.GameRepository;
import com.knightlore.util.Config;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class ListGames extends Command{

    SessionGenerator apikey = new SessionGenerator();
    final static Logger logger = Logger.getLogger(ListGames.class);


    public void run(ClientHandler handler, Sendable sendable) {
        logger.info("List games received");

        // Create json data
        String json = sendable.getData();

        Sendable response = sendable.makeResponse();
        ListGameResponse sessionKeyResponse = new ListGameResponse();

        HashMap<UUID, GameServer> servers = GameRepository.instance.getServers();

        for(GameServer server : servers.values()){
            try{
                sessionKeyResponse.addGame(server.getUUID(),
                        InetAddress.getByName(Config.authServerIp().get()),
                        server.getPort());

                // TODO add connected players
                
            }catch(UnknownHostException e){

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
