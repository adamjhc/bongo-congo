package com.knightlore.server.game;

import com.knightlore.game.Game;
import com.knightlore.game.Level;
import com.knightlore.game.map.LevelMapSet;
import com.knightlore.game.map.TileSet;
import com.knightlore.game.server.GameServer;
import com.knightlore.server.database.model.Model;
import org.apache.log4j.Logger;

import java.util.*;

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
    public void newServer(UUID uuid, int port, String sessionOwner, ArrayList<Level> levels){
        LevelMapSet ms = new LevelMapSet(new TileSet());
        Game game = new Game(uuid.toString());

        game.createNewLevel(ms.getMap(0));
        // Default to provided levels
        if(levels.size() > 0){
            for(com.knightlore.game.Level currentLevel : levels){
                game.addLevel(currentLevel);
            }
        }else{
            // No levels provided, fallback on first from database
            Optional<Model> optLevel = com.knightlore.server.database.model.Level.instance.first();

            if(!optLevel.isPresent()){
                // No db found, fallback on server generated
                logger.warn("No levels could be found! Defaulting");
                game.createNewLevel(ms.getMap(0));
            }else{
                com.knightlore.server.database.model.Level level = (com.knightlore.server.database.model.Level) optLevel.get();
                game.addLevel(level.getModelLevel());
            }
        }



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
