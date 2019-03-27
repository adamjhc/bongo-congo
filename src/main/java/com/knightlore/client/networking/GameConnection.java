package com.knightlore.client.networking;

import com.google.gson.Gson;
import com.knightlore.client.ClientState;
import com.knightlore.client.networking.backend.Client;
import com.knightlore.client.networking.backend.ResponseHandler;
import com.knightlore.client.networking.backend.responsehandlers.game.GameRegister;
import com.knightlore.game.GameModel;
import com.knightlore.game.GameState;
import com.knightlore.game.entity.Player;
import com.knightlore.game.server.GameServer;
import com.knightlore.networking.ApiKey;
import com.knightlore.networking.PositionUpdate;
import com.knightlore.networking.Sendable;
import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

public class GameConnection {

  public static GameConnection instance;
  public static GameModel gameModel;

  public UUID uuid;
  public String sessionKey;
  public int playerIndex;
  public boolean gameCancelled;
  boolean authenticated = false;
  private Client client;

  // Key already validated
  public GameConnection(Client client, String sessionKey) {
    this.client = client;
    this.sessionKey = sessionKey;
  }

  public boolean ready() {
    return this.client.ready;
  }

  public void close() {
    try {
      this.client.close();
    } catch (IOException e) {
      System.out.println("Error occurred while disconnecting");
    }

    // TODO game cancelled for 5 seconds
    if(GameConnection.gameModel.getState() != GameState.SCORE){
      com.knightlore.client.Client.changeScreen(ClientState.MAIN_MENU, false);
    }

  }

  public void startGame() {
    // Build up get session string
    Sendable sendable = new Sendable();
    sendable.setUuid();
    sendable.setFunction("game_start");

    Gson gson = new Gson();

    com.knightlore.networking.GameRequest request = new com.knightlore.networking.GameRequest();
    sendable.setData(gson.toJson(request));

    // Specify handler
    System.out.println("SENDING " + sendable.getData());

    try {
      client.dos.writeObject(sendable);
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public void updateStatus() {
    // Build up get session string
    Sendable sendable = new Sendable();
    sendable.setUuid();
    sendable.setFunction("position_update");

    Gson gson = new Gson();

    Player player = GameConnection.gameModel.myPlayer();

    PositionUpdate request =
        new com.knightlore.networking.PositionUpdate(
            player.getPosition(),
            this.sessionKey,
            player.getDirection(),
            player.getPlayerState(),
            player.getScore());

    sendable.setData(gson.toJson(request));

    // Specify handler
    System.out.println("SENDING " + sendable.getData());

    try {
      client.dos.writeObject(sendable);
    } catch (IOException e) {
      System.out.println(e);
    }
  }

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

  public void sendLevelComplete() {
    Sendable sendable = new Sendable();
    sendable.setFunction("level_complete");

    try {
      client.dos.writeObject(sendable);
    } catch (IOException e) {
      System.out.println(e);
    }
  }

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

  public void sendDeath() {
    Sendable sendable = new Sendable();
    sendable.setFunction("player_death");

    try {
      client.dos.writeObject(sendable);
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  // Run code after a connection to the game server has been successfully made
  public void gameConnectionMade() {}

  // Game connection was unable to be established
  public void gameConnectionFailed() {}

  public InetAddress getIP() {
    return this.client.ip;
  }

  public int port() {
    return this.client.port;
  }
}
