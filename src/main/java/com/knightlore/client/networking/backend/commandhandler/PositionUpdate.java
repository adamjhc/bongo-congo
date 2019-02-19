package com.knightlore.client.networking.backend.commandhandler;

import com.google.gson.Gson;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.game.GameState;
import com.knightlore.networking.Sendable;

import javax.swing.text.Position;

public class PositionUpdate implements GenericHandler{

    public void run(Client client, Sendable sendable){
        // Decode
        Gson gson = new Gson();
        com.knightlore.networking.PositionUpdate location = gson.fromJson(sendable.getData(), com.knightlore.networking.PositionUpdate.class);

        // Set state
        com.knightlore.client.Client.model.getCurrentLevel().getPlayers().get(0).setPosition(location.coordinates);
    }
}
