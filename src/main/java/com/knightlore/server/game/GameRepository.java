package com.knightlore.server.game;

import com.knightlore.game.GameModel;
import com.knightlore.game.Level;
import com.knightlore.game.map.LevelMapSet;
import com.knightlore.game.server.GameServer;
import com.knightlore.server.GameServerSupervisor;
import com.knightlore.server.database.model.Model;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import org.apache.log4j.Logger;

public class GameRepository {

  private static final Logger logger = Logger.getLogger(GameRepository.class);
  public static GameRepository instance = new GameRepository();

  private final Random rand = new Random();
  private HashMap<UUID, GameServer> servers;

  public GameRepository() {
    servers = new HashMap<>();
  }

  /**
   * Create new game server instance with default game model Store in repository
   *
   * @param uuid
   * @param port
   * @param sessionOwner
   */
  public void newServer(
      UUID uuid, int port, String sessionOwner, List<Level> levels, String username) {
    LevelMapSet ms = new LevelMapSet();
    GameModel gameModel = new GameModel(uuid.toString());

    // Default to provided levels
    if (!levels.isEmpty()) {
      for (com.knightlore.game.Level currentLevel : levels) {
        gameModel.addLevel(currentLevel);
      }
    } else {
      // No levels provided, fallback on first from database
      Optional<Model> optLevel = com.knightlore.server.database.model.Level.instance.first();

            if(!optLevel.isPresent()){
                // No db found, fallback on server generated
                logger.warn("No levels could be found! Defaulting");
                gameModel.createNewLevel(ms.getMap(0));
            }else{
                com.knightlore.server.database.model.Level level = (com.knightlore.server.database.model.Level) optLevel.get();
                Level gameLevel = level.getModelLevel();
                gameModel.addLevel(gameLevel);
            }
        }

        this.newServer(uuid, port, sessionOwner, gameModel, username);
    }

    /**
     * Create new game server instance with predefined model
     * Store in repository
     * @param uuid
     * @param port
     * @param sessionOwner
     * @param gameModel
     */
    public void newServer(UUID uuid, int port, String sessionOwner, GameModel gameModel, String username){
        String name = username + "'s game";
        GameServer server = new GameServer(uuid, port, sessionOwner, gameModel, name);
        servers.put(uuid, server);

        // Create supervisor
        GameServerSupervisor currentManager = new GameServerSupervisor(server);
        currentManager.start();

    }

  public void startServer(UUID uuid) {
    logger.info("Starting server: " + uuid.toString());
    servers.get(uuid).start();
  }

  public int getNewPort() {
    int port;

    while (true) {
      port = rand.nextInt(65530) + 1025;

      for (GameServer current : this.servers.values()) {
        if (current.socket == port) {
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

    public void removeServer(UUID uuid){
        this.servers.remove(uuid);
    }
}
