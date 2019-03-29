package com.knightlore.server.commandhandler;

import com.knightlore.game.server.GameServer;
import com.knightlore.networking.*;
import com.knightlore.networking.server.ListGameObject;
import com.knightlore.networking.server.ListGameResponse;
import com.knightlore.server.ClientHandler;
import com.knightlore.server.database.SessionGenerator;
import com.knightlore.server.game.GameRepository;
import com.knightlore.util.Config;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * List game response handler
 *
 * @author Lewis Relph
 */
public class ListGames extends Command{

    final static Logger logger = Logger.getLogger(ListGames.class);


    public void run(ClientHandler handler, Sendable sendable) {
        logger.info("List games received");

        // Create json data
        String json = sendable.getData();

        Sendable response = sendable.makeResponse();
        ListGameResponse sessionKeyResponse = new ListGameResponse();

        // Grab from db
        HashMap<UUID, GameServer> servers = GameRepository.instance.getServers();

        for(GameServer server : servers.values()){
            try{
                ListGameObject game = new ListGameObject(server.getUUID(),
                        InetAddress.getByName(Config.authServerIp().get()),
                        server.getPort(), server.getGameName());

                // TODO add connected players
                for(com.knightlore.game.server.ClientHandler client : server.registeredClients()){
                    game.addUser(client.username.get());
                }

                sessionKeyResponse.addGame(game);
                
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
