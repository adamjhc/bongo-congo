package com.knightlore.game;

import com.knightlore.client.networking.GameConnection;
import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.Player;
import com.knightlore.game.entity.PlayerState;
import com.knightlore.game.map.LevelMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joml.Vector3f;

public class Game {

  private final int noOfLevels = 3;

  private String uuid;
  private HashMap<UUID, Level> levels;
  private Map<String, Player> players;
  private UUID currentLevelUUID;
  private GameState currentState;
  private ArrayList<UUID> levelOrder;

  public Game(String uuid) {
    this.uuid = uuid;
    currentState = GameState.LOBBY;
    levels = new HashMap<>();
    players = new HashMap<>();
  }

  public GameState getState() {
    return currentState;
  }

  public void setState(GameState state) {
    currentState = state;
  }

  public Level getCurrentLevel() {
    return levels.get(currentLevelUUID);
  }

  public Map<String, Player> getPlayers() {
    return players;
  }

  public Player myPlayer() {
    if (GameConnection.instance == null) {
      return this.players.get("1");
    }
    return this.players.get(GameConnection.instance.sessionKey);
  }

  public void createNewLevel(UUID uuid, LevelMap levelMap) {
    levels.put(uuid, new Level(levelMap));

    if (currentLevelUUID == null) {
      currentLevelUUID = uuid;
    }
  }

  public void createNewLevel(LevelMap levelMap){
    createNewLevel(UUID.randomUUID(), levelMap);
  }

  public void addLevel(UUID uuid, Level level){
    this.levels.put(uuid, level);
  }

  @Deprecated
  public void nextLevel() {
//    if (currentLevelIndex == noOfLevels - 1) {
//      currentState = GameState.SCORE;
//      return;
//    }
//
//    players.forEach((playerUUID, player) -> player.nextLevel());
//    currentLevelIndex++;
  }

  public void update(float delta) {}

  public void movePlayerInDirection(Direction direction, float delta) {
    Player player = myPlayer();
    player.setDirection(direction);
    player.setPlayerState(PlayerState.MOVING);

    Vector3f origPos = player.getPosition();
    Vector3f newPos = new Vector3f();
    direction.getNormalisedDirection().mul(delta, newPos).mul(player.getSpeed(), newPos);
    origPos.add(newPos, newPos);
    player.update(origPos, newPos, getCurrentLevel().getLevelMap());
  }

  public void updatePlayerState(PlayerState state) {
    myPlayer().setPlayerState(state);
  }

  public void addPlayer(String uuid) {
    players.put(uuid, new Player(uuid));
  }
}
