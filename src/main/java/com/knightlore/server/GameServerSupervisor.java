package com.knightlore.server;

import com.knightlore.game.GameState;
import com.knightlore.game.entity.Player;
import com.knightlore.game.server.ClientHandler;
import com.knightlore.game.server.GameServer;
import com.knightlore.server.database.model.*;
import com.knightlore.server.game.GameRepository;
import com.knightlore.server.supervisortasks.DetectGameComplete;
import com.knightlore.server.supervisortasks.DetectPlayers;
import com.knightlore.server.supervisortasks.SupervisorTask;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Thread for detecting changes in a game server And relaying changes to main server if required
 *
 * @author Lewis Relph
 */
public class GameServerSupervisor extends Thread {

  static final Logger logger = Logger.getLogger(GameServerSupervisor.class);

  public GameServer server;
  boolean running;

  /**
   * Default construcotr
   *
   * @param server
   */
  public GameServerSupervisor(GameServer server) {
    this.server = server;
    this.running = true;
  }

  /** Loop for periodic checks */
  @Override
  public void run() {
    SupervisorTask detectPlayers = new DetectPlayers();
    SupervisorTask detectGameComplete = new DetectGameComplete();

    while (running) {

      System.out.println("Handling");

      detectPlayers.run(server);
      detectGameComplete.run(server);

      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        //
      }
    }
  }

  public void close(){
    System.out.println("Closign supervisor");
    this.running = false;
    this.interrupt();
  }
}
