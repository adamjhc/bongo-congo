package com.knightlore.game.server.commandhandler;

import com.google.gson.Gson;
import com.knightlore.game.Game;
import com.knightlore.game.server.ClientHandler;
import com.knightlore.game.server.GameServer;
import com.knightlore.networking.ApiKey;
import com.knightlore.networking.Sendable;

import javax.swing.text.Position;
import java.util.Optional;

public class PositionUpdate extends Command {

    public void run(ClientHandler handler, Sendable sendable) {
        System.out.println("Position changed");

        Gson gson = new Gson();
        com.knightlore.networking.PositionUpdate newPosition = gson.fromJson(sendable.getData(), com.knightlore.networking.PositionUpdate.class);

        Sendable response = new Sendable();
        response.setFunction("position_update");
        response.setData(gson.toJson(newPosition));

        handler.server().sendToRegisteredExceptSelf(response, handler.sessionKey.get());
    }
}
