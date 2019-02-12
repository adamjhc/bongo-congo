package com.knightlore.server.game;

import com.knightlore.game.server.GameServer;

import java.util.HashMap;
import java.util.UUID;
import java.util.Random;

public class GameRepository {

    public static GameRepository instance = new GameRepository();
    final Random rand = new Random();

    HashMap<UUID, GameServer> servers;

    public GameRepository() {
        servers = new HashMap<>();
    }

    public void newServer(UUID uuid, int port){
        GameServer server = new GameServer(uuid, port);
        servers.put(uuid, server);
    }

    public void startServer(UUID uuid){
        System.out.println("Starting");
        servers.get(uuid).start();
    }

    public int getNewPort(){
        int port;

        while(true){
            port = rand.nextInt(65530) + 1025;

            for(GameServer current: this.servers.values()){
                if(current.socket == port){
                    continue;
                }
            }

            break;
        }

        return port;
    }
}
