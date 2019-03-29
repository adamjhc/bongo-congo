package com.knightlore.game.server.commandhandler;

import com.google.gson.Gson;
import com.knightlore.game.server.ClientHandler;
import com.knightlore.networking.server.ApiKey;
import com.knightlore.networking.game.GameRegisterResponse;
import com.knightlore.networking.server.PlayerJoined;
import com.knightlore.networking.Sendable;
import java.util.Optional;

/**
 * Initial register command handler
 *
 * @author Lewis Relph
 */
public class Register extends Command {

  public void run(ClientHandler handler, Sendable sendable) {
    // Create json data
    String json = sendable.getData();
    ApiKey apikey = gson.fromJson(json, ApiKey.class);

    // Save session key
    handler.sessionKey = Optional.of(apikey.key);

    // Set username
    handler.username = Optional.of("Geoff");

    // Update model to reflect new player
    handler.server().getModel().addPlayer(handler.sessionKey.get());

    // Build register response
    Gson gson = new Gson();
    Sendable response = sendable.makeResponse();
    GameRegisterResponse gameRegisterResponse = new GameRegisterResponse(handler.server().getUUID());
    response.setData(gson.toJson(gameRegisterResponse));

    // Send
    handler.send(response);

    // Send player updates to other players
    Sendable playerJoinedSendable = new Sendable();
    playerJoinedSendable.setFunction("player_joined");
    playerJoinedSendable.setData(gson.toJson(new PlayerJoined(handler.sessionKey.get())));
    handler.server().sendToRegisteredExceptSelf(playerJoinedSendable, handler.sessionKey.get());
  }
}
