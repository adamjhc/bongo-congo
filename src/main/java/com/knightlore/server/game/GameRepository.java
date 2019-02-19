package com.knightlore.server.game;

import com.knightlore.game.Game;
import com.knightlore.game.map.MapSet;
import com.knightlore.game.map.TileSet;
import com.knightlore.game.server.GameServer;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.UUID;
import java.util.Random;

public class GameRepository {

    final static Logger logger = Logger.getLogger(GameRepository.class);

    public static GameRepository instance = new GameRepository();
    final Random rand = new Random();

    HashMap<UUID, GameServer> servers;

    public GameRepository() {
        servers = new HashMap<>();
    }

    /**
     * Create new game server instance with default game model
     * Store in repository
     * @param uuid
     * @param port
     * @param sessionOwner
     */
    public void newServer(UUID uuid, int port, String sessionOwner){
        Game game = new Game("1", new MapSet(new TileSet()));
        this.newServer(uuid, port, sessionOwner, game);
    }

    /**
     * Create new game server instance with predefined model
     * Store in repository
     * @param uuid
     * @param port
     * @param sessionOwner
     * @param game
     */
    public void newServer(UUID uuid, int port, String sessionOwner, Game game){
        GameServer server = new GameServer(uuid, port, sessionOwner, game);
        servers.put(uuid, server);
    }

    public void startServer(UUID uuid){
        logger.info("Starting server: " + uuid.toString());
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

    public HashMap<UUID, GameServer> getServers(){
        return this.servers;
    }
}
