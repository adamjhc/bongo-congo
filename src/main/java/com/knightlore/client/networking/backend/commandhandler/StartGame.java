package com.knightlore.client.networking.backend.commandhandler;

import com.knightlore.client.Main;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.game.GameState;
import com.knightlore.networking.Sendable;

public class StartGame implements GenericHandler{

    public void run(Client client, Sendable sendable){
        // Set state
        Main.model.setState(GameState.PLAYING);
    }
}
