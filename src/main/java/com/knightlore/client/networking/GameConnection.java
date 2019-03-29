package com.knightlore.client.networking;

import com.google.gson.Gson;
import com.knightlore.client.ClientState;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.client.networking.backend.PeriodicStatusUpdater;
import com.knightlore.client.networking.backend.ResponseHandler;
import com.knightlore.client.networking.backend.responsehandlers.game.GameRegister;
import com.knightlore.game.GameModel;
import com.knightlore.game.GameState;
import com.knightlore.game.entity.Player;
import com.knightlore.networking.game.LevelComplete;
import com.knightlore.networking.server.ApiKey;
import com.knightlore.networking.game.PositionUpdate;
import com.knightlore.networking.Sendable;
import com.knightlore.networking.server.GameRequest;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

/**
 * Store for current game connection & related information
 *
 * @author Lewis Relph
 */
public class GameConnection {

  public static GameConnection instance;
  public static GameModel gameModel;

  public UUID uuid;
  public String sessionKey;
  public int playerIndex;
  public boolean gameCancelled;
  boolean authenticated = false;
  private Client client;
  public PeriodicStatusUpdater updater;

  /**
   * Constructor for when key is already provided
   * @param client
   * @param sessionKey
   */
  public GameConnection(Client client, String sessionKey) {
    this.client = client;
    this.sessionKey = sessionKey;
  }

  /**
   * Getter for client ready value
   * @return client is ready to accept commands
   */
  public boolean ready() {
    return this.client.ready;
  }

  /**
   * Close current thread
   */
  public void close() {
    // Check for periodic
    if(this.updater != null){
      this.updater.close();
    }

    try {
      this.client.close();
    } catch (IOException e) {
      System.out.println("Error occurred while disconnecting");
    }

    if (GameConnection.gameModel != null && GameConnection.gameModel.getState() != GameState.SCORE) {
      com.knightlore.client.Client.changeScreen(ClientState.MAIN_MENU, false);
    }
  }

  /**
   * Send start game command to server
   */
  public void startGame() {
    // Build up get session string
    Sendable sendable = new Sendable();
    sendable.setUuid();
    sendable.setFunction("game_start");

    Gson gson = new Gson();

    GameRequest request = new GameRequest();
    sendable.setData(gson.toJson(request));

    try {
      client.dos.writeObject(sendable);
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  /**
   * Update position based on current player model
   */
  public void updateStatus() {
    // Build up get session string
    Sendable sendable = new Sendable();
    sendable.setUuid();
    sendable.setFunction("position_update");

    Gson gson = new Gson();

    Player player = GameConnection.gameModel.myPlayer();

    PositionUpdate request =
        new PositionUpdate(
            player.getPosition(),
            this.sessionKey,
            player.getDirection(),
            player.getPlayerState(),
            player.getScore());

    sendable.setData(gson.toJson(request));

    try {
      client.dos.writeObject(sendable);
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  /**
   * Send register command to server
   */
  public void register() {
    // Build up get session string
    Gson gson = new Gson();

    Sendable sendable = new Sendable();
    sendable.setUuid();
    sendable.setFunction("register");
    ApiKey key = new ApiKey(this.sessionKey);
    sendable.setData(gson.toJson(key));

    // Specify handler
    ResponseHandler.waiting.put(sendable.getUuid(), new GameRegister());

    try {
      client.dos.writeObject(sendable);
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  /**
   * Send level complete command to server
   */
  public void sendLevelComplete() {
    Gson gson = new Gson();
    Sendable sendable = new Sendable();
    sendable.setFunction("level_complete");
    LevelComplete lc = new LevelComplete(GameConnection.gameModel.myPlayer().getScore());
    sendable.setData(gson.toJson(lc));

    try {
      client.dos.writeObject(sendable);
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  /**
   * Send ready command to server
   */
  public void sendReady() {
    // Build up get session string
    Sendable sendable = new Sendable();
    sendable.setUuid();
    sendable.setFunction("ready");

    try {
      client.dos.writeObject(sendable);
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  /**
   * Send death command to server
   */
  public void sendDeath() {
    Sendable sendable = new Sendable();
    sendable.setFunction("player_death");

    try {
      client.dos.writeObject(sendable);
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  /**
   * Additional listeners for when a game connection is made
   */
  public void gameConnectionMade() {}

  /**
   * Additional listeners for when a game connection fails
   */
  public void gameConnectionFailed() {}

  /**
   * Getter for client port
   * @return
   */
  public int port() {
    return this.client.port;
  }
}
