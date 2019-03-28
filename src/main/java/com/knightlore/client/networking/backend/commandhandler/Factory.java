package com.knightlore.client.networking.backend.commandhandler;

import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.networking.Sendable;

public class Factory {

    public static void create(Client client, Sendable sendable){
        System.out.println("FACTORY" + sendable.getFunction());
        if(sendable.getFunction().equals("start_game")){
            StartGame handler = new StartGame();
            handler.run(client, sendable);
        }

        if(sendable.getFunction().equals("position_update")){
            PositionUpdate handler = new PositionUpdate();
            handler.run(client, sendable);
        }

        if(sendable.getFunction().equals("position_update_chunk")){
            PositionUpdateChunk handler = new PositionUpdateChunk();
            handler.run(client, sendable);
        }

        if(sendable.getFunction().equals("player_joined")){
            PlayerJoined handler = new PlayerJoined();
            handler.run(client, sendable);
        }

        if(sendable.getFunction().equals("enemy_location_update")){
            EnemyPositionUpdate handler = new EnemyPositionUpdate();
            handler.run(client, sendable);
        }

        if(sendable.getFunction().equals("ready")){
            Ready handler = new Ready();
            handler.run(client, sendable);
        }

        if(sendable.getFunction().equals("game_close")){
            GameClose handler = new GameClose();
            handler.run(client, sendable);
        }

        if(sendable.getFunction().equals("level_complete")){
            LevelComplete handler = new LevelComplete();
            handler.run(client, sendable);
        }

        if(sendable.getFunction().equals("game_complete")){
            GameComplete handler = new GameComplete();
            handler.run(client, sendable);
        }
        if(sendable.getFunction().equals("player_death")){
            PlayerDeath handler = new PlayerDeath();
            handler.run(client, sendable);
        }
    }
}
