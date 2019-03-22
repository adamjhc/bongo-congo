package com.knightlore.networking;

import com.knightlore.game.GameModel;

import java.util.UUID;

public class GameRegisterResponse {

    public GameModel game;
    public UUID uuid;

    public GameRegisterResponse(GameModel model, UUID uuid){
        this.game = game;
        this.uuid = uuid;
    }
}
