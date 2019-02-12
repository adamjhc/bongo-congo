package com.knightlore.server.commandhandler;

import com.knightlore.networking.ApiKey;
import com.knightlore.networking.GameRequestResponse;
import com.knightlore.networking.Sendable;
import com.knightlore.networking.SessionKeyResponse;
import com.knightlore.server.ClientHandler;
import com.knightlore.server.database.SessionGenerator;
import com.knightlore.server.database.model.*;
import com.knightlore.server.game.GameRepository;
import com.knightlore.util.Config;
import sun.security.x509.IPAddressName;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class GameRequest extends Command{
    SessionGenerator apikey = new SessionGenerator();

    public void run(ClientHandler handler, Sendable sendable) {
        System.out.println("Game request made");

        // Create json data
        String json = sendable.getData();
        ApiKey apikey = gson.fromJson(json, ApiKey.class);

        Sendable response = sendable.makeResponse();
        GameRequestResponse gameRequestResponse;

        System.out.println("KEY" + handler.sessionKey.isPresent());
        if(handler.sessionKey.isPresent()){
            String sessionKey = handler.sessionKey.get();

            // Generate port and UUID
            int port = GameRepository.instance.getNewPort();
            UUID uuid = UUID.randomUUID();

            // Create new game
            GameRepository.instance.newServer(uuid, port);

            // Start Server
            GameRepository.instance.startServer(uuid);

            // Finally make db
            Calendar calendar = Calendar.getInstance();
            java.util.Date now = calendar.getTime();
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

            String ip = Config.authServerIp().get();

            Game game = new Game();
            game.setAttribute("uuid", uuid.toString());
            game.setAttribute("session_id", handler.sessionKey);
            game.setAttribute("created_at", currentTimestamp);
            game.setAttribute("started_at", currentTimestamp);
            game.setAttribute("ends_at", currentTimestamp);
            game.setAttribute("ip", ip);
            game.setAttribute("port", port);

            try{
                gameRequestResponse = new GameRequestResponse(uuid, InetAddress.getByName(ip), port);
                response.setData(gson.toJson(gameRequestResponse));
            }catch(UnknownHostException e){
                System.out.println("Unknown host " + ip + " Specified as regserver in config");
            }

            System.out.println("Starting game server (" + uuid.toString() + ") IP: " + ip + " Port:" + port);

        }else{
            response.success = false;

        }


//        try{
//            handler.dos.writeObject(response);
//        }catch (IOException e){
//            e.printStackTrace();
//        }
    }
}
