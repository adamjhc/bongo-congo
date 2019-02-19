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
import org.apache.log4j.Logger;
import sun.security.x509.IPAddressName;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GameRequest extends Command{

    final static Logger logger = Logger.getLogger(GameRequest.class);

    public void run(ClientHandler handler, Sendable sendable) {
        logger.info("Game request made");

        // Create json data
        String json = sendable.getData();
        ApiKey apikey = gson.fromJson(json, ApiKey.class);

        Sendable response = sendable.makeResponse();
        GameRequestResponse gameRequestResponse;

        if(handler.sessionKey.isPresent()){
            String sessionKey = handler.sessionKey.get();

            // Find session id
            SessionToken token = new SessionToken();
            token.where(new Condition("token", "=", handler.sessionKey.get()));
            Optional<Model> sessionToken = token.first();

            // Assume session token exists
            int sessionID = (int) sessionToken.get().getAttribute("id");

            // Generate port and UUID
            int port = GameRepository.instance.getNewPort();
            UUID uuid = UUID.randomUUID();

            // Create new game server
            GameRepository.instance.newServer(uuid, port, handler.sessionKey.get());

            // Start server
            GameRepository.instance.startServer(uuid);

            // Finally make db
            Calendar calendar = Calendar.getInstance();
            java.util.Date now = calendar.getTime();
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

            String ip = Config.authServerIp().get();

            Game game = new Game();
            game.setAttribute("uuid", uuid.toString());
            game.setAttribute("session_id", sessionID);
            game.setAttribute("created_at", currentTimestamp);
            game.setAttribute("started_at", currentTimestamp);
            game.setAttribute("ends_at", currentTimestamp);
            game.setAttribute("ip", ip);
            game.setAttribute("port", port);
            game.save();

            // Build response
            try{
                gameRequestResponse = new GameRequestResponse(uuid, InetAddress.getByName(ip), port);
                response.setData(gson.toJson(gameRequestResponse));
                response.success = true;

            }catch(UnknownHostException e){
                logger.warn("Unknown host " + ip + " Specified as regserver in config");
            }

            logger.info("Starting game server (" + uuid.toString() + ") IP: " + ip + " Port:" + port);

            // Finally wait until server ready (default 5 seconds)
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException e){
                // Shouldn't be interrupted
            }
        }else{
            response.success = false;
        }


        try{
            handler.dos.writeObject(response);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
