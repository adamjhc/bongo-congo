package com.knightlore.server.game;

import com.knightlore.game.GameModel;
import com.knightlore.game.Level;
import com.knightlore.game.map.LevelMapSet;
import com.knightlore.game.server.GameServer;
import com.knightlore.server.GameServerSupervisor;
import com.knightlore.server.database.model.Model;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Repository for storing current games running on the machine
 *
 * @author Lewis Relph
 */
public class GameRepository {

  private static final Logger logger = Logger.getLogger(GameRepository.class);
  public static GameRepository instance = new GameRepository();
  private final Random rand = new Random();

  private HashMap<UUID, GameServer> servers;
  private HashMap<UUID, GameServerSupervisor> supervisors;

  /** Default constructor */
  public GameRepository() {
    servers = new HashMap<>();
    supervisors = new HashMap<>();
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

    // Build new mapset and game model
    LevelMapSet ms = new LevelMapSet();
    GameModel gameModel = new GameModel(uuid.toString());

    // Check if levels are provided
    if (!levels.isEmpty()) {
      // Add provided levels
      for (com.knightlore.game.Level currentLevel : levels) {
        gameModel.addLevel(currentLevel);
      }
    } else {
      // No levels provided, fallback on random 3 from database from database
      // TODO finish
      Optional<Model> optLevel = com.knightlore.server.database.model.Level.instance.first();

      if (!optLevel.isPresent()) {
        // No db levels found, default to first from mapset
        logger.warn("No levels could be found! Defaulting");
        gameModel.createNewLevel(ms.getMap(0));
      } else {
        // Levels from db found, add to model
        com.knightlore.server.database.model.Level level =
            (com.knightlore.server.database.model.Level) optLevel.get();
        Level gameLevel = level.getModelLevel();
        gameModel.addLevel(gameLevel);
      }
    }

    // Create with model
    this.newServer(uuid, port, sessionOwner, gameModel, username);
  }

  /**
   * Create new game server instance with predefined model Store in repository
   *
   * @param uuid
   * @param port
   * @param sessionOwner
   * @param gameModel
   */
  public void newServer(
      UUID uuid, int port, String sessionOwner, GameModel gameModel, String username) {
    // Setup name
    String name = username + "'s game";

    // Setup gameserver & add to repository
    GameServer server = new GameServer(uuid, port, sessionOwner, gameModel, name);
    servers.put(uuid, server);

    // Create supervisor
    GameServerSupervisor supervisor = new GameServerSupervisor(server);
    supervisors.put(uuid, supervisor);
  }

  /**
   * Start server thread
   *
   * @param uuid
   */
  public void startServer(UUID uuid) {
    logger.info("Starting server: " + uuid.toString());
    servers.get(uuid).start();
  }

  /**
   * Dynamically generate new port unused port
   *
   * @return unique port
   */
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

  /**
   * Getter for server hashmap
   *
   * @return
   */
  public HashMap<UUID, GameServer> getServers() {
    return this.servers;
  }

  /**
   * Remove server based on uuid
   *
   * @param uuid
   */
  public void removeServer(UUID uuid) {
    // stop supervisor
    // supervisors.get(uuid).close();

    this.servers.get(uuid).close();
    this.servers.remove(uuid);
  }
}
