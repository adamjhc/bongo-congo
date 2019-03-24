package com.knightlore.networking;

import com.knightlore.game.GameModel;

import java.util.UUID;

public class GameRegisterResponse {

    public UUID uuid;

    public GameRegisterResponse(UUID uuid){
        this.uuid = uuid;
    }
}
