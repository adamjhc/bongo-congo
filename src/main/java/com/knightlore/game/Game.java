package com.knightlore.game;

import com.knightlore.client.networking.GameConnection;
import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.Player;
import com.knightlore.game.entity.PlayerState;
import com.knightlore.game.map.LevelMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.joml.Vector3f;

public class Game {

  private final int noOfLevels = 3;

  private String uuid;
  private ArrayList<Level> levels;
  private Map<String, Player> players;
  private Integer currentLevelIndex;
  private GameState currentState;

  public Game(String uuid) {
    this.uuid = uuid;
    levels = new ArrayList<>();
    currentState = GameState.LOBBY;
    levels = new ArrayList<>();
    players = new HashMap<>();
    currentLevelIndex = 0;
  }

  public GameState getState() {
    return currentState;
  }

  public void setState(GameState state) {
    currentState = state;
  }

  public Level getCurrentLevel() {
    return levels.get(currentLevelIndex);
  }

  public Integer getCurrentLevelIndex() {
    return currentLevelIndex;
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

  public void createNewLevel(LevelMap levelMap) {
    levels.add(new Level(levelMap));

    if (currentLevelIndex == null) {
      currentLevelIndex = 0;
    }
  }
  
  public void overwriteCurrentLevel(LevelMap levelMap) {
	  levels.set(0, new Level(levelMap));
  }

  public void addLevel(Level level){
    this.levels.add(level);
  }

  public void nextLevel() {
    if (currentLevelIndex == noOfLevels - 1) {
      currentState = GameState.SCORE;
      return;
    }

    players.forEach((playerUUID, player) -> player.nextLevel());
    currentLevelIndex++;
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
  
  public void addPlayer(String uuid, int id) {
	  players.put(uuid, new Player(uuid, id));
  }
  
  public void removePlayer(String uuid) {
	  players.remove(uuid, players.get(uuid));
  }
}
