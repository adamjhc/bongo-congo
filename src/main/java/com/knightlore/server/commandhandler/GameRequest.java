package com.knightlore.server.commandhandler;

import com.knightlore.networking.Sendable;
import com.knightlore.networking.server.GameRequestResponse;
import com.knightlore.server.ClientHandler;
import com.knightlore.server.database.model.*;
import com.knightlore.server.game.GameRepository;
import com.knightlore.util.Config;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Command handler for requesting a new game
 *
 * @author Lewis Relph
 */
public class GameRequest extends Command {

  static final Logger logger = Logger.getLogger(GameRequest.class);

  public void run(ClientHandler handler, Sendable sendable) {
    logger.info("Game request made");

    // Create json data
    String json = sendable.getData();
    com.knightlore.networking.server.GameRequest data =
        gson.fromJson(json, com.knightlore.networking.server.GameRequest.class);

    Sendable response = sendable.makeResponse();
    GameRequestResponse gameRequestResponse;

    if (handler.sessionKey.isPresent()) {
      String sessionKey = handler.sessionKey.get();
      String username = handler.username.get();

      // Find session id
      SessionToken token = new SessionToken();
      token.where(new Condition("token", "=", handler.sessionKey.get()));
      Optional<Model> sessionToken = token.first();

      // Assume session token exists
      int sessionID = (int) sessionToken.get().getAttribute("id");

      // Generate port and UUID
      int port = GameRepository.instance.getNewPort();
      UUID uuid = UUID.randomUUID();

      // Convert to level objects
      ArrayList<com.knightlore.game.Level> levels = new ArrayList<>();

      for (UUID levelID : data.getLevels()) {
        // Retrieve level from database
        Level model = new Level();
        model.where(new Condition("uuid", "=", levelID.toString()));
        Optional<Model> level = model.first();

        if (level.isPresent()) {
          Level levelCast = (Level) level.get();
          levels.add(levelCast.getModelLevel());
        } else {
          logger.warn("User sent incorrect level id: " + levelID.toString());
        }
      }

      // Create new game server
      GameRepository.instance.newServer(uuid, port, handler.sessionKey.get(), levels, username);

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
      game.setAttribute("name", username + "'s game");
      game.save();

      // Build response
      try {
        gameRequestResponse = new GameRequestResponse(uuid, InetAddress.getByName(ip), port);
        response.setData(gson.toJson(gameRequestResponse));
        response.success = true;

      } catch (UnknownHostException e) {
        logger.warn("Unknown host " + ip + " Specified as regserver in config");
      }

      logger.info("Starting game server (" + uuid.toString() + ") IP: " + ip + " Port:" + port);

      // Finally wait until server ready (default 5 seconds)
      try {
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {
        // Shouldn't be interrupted
      }
    } else {
      response.success = false;
    }

    try {
      handler.dos.writeObject(response);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
