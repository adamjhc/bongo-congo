package com.knightlore.game;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.EnemySet;
import com.knightlore.game.entity.Player;
import com.knightlore.game.entity.PlayerState;
import com.knightlore.game.map.LevelMap;
import com.knightlore.game.map.TileSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class GameModel {

  private final int noOfLevels = 3;

  private EnemySet enemySet;

  private String uuid;
  private GameState currentState;

  private ArrayList<Level> levels;
  private Integer currentLevelIndex;

  private Map<String, Player> players;
  private int playerIdInc;
  private List<Vector4f> playerColours;

  public GameModel(String uuid) {
    this.uuid = uuid;
    currentState = GameState.LOBBY;

    enemySet = new EnemySet();

    levels = new ArrayList<>();
    currentLevelIndex = 0;

    players = new HashMap<>();
    playerIdInc = 0;
    playerColours = new ArrayList<>();
    playerColours.add(Colour.BLUE);
    playerColours.add(Colour.GREEN);
    playerColours.add(Colour.CYAN);
    playerColours.add(Colour.RED);
    playerColours.add(Colour.PINK);
    playerColours.add(Colour.YELLOW);
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
    levels.add(new Level(levelMap, enemySet));

    if (currentLevelIndex == null) {
      currentLevelIndex = 0;
    }
  }

  public void overwriteCurrentLevel(LevelMap levelMap) {
    levels.set(0, new Level(levelMap, enemySet));
  }

  public void clearLevels() {
    this.levels.clear();
  }

  public void addLevel(Level level) {
    this.levels.add(level);
  }

  public void nextLevel() {
    if (currentLevelIndex == noOfLevels - 1) {
      currentState = GameState.SCORE;
      return;
    }

    currentState = GameState.NEXT_LEVEL;
    players.forEach((playerUUID, player) -> player.reset());
    currentLevelIndex++;
  }

  public void update(float delta, Direction playerInputDirection) {
    if (currentState == GameState.NEXT_LEVEL) {
      currentState = GameState.PLAYING;
    }

    // Player updates
    switch (myPlayer().getPlayerState()) {
      case IDLE:
        if (playerInputDirection != null) {
          updatePlayerState(PlayerState.MOVING);
          movePlayerInDirection(playerInputDirection, delta);
        }
        break;
      case MOVING:
        if (playerInputDirection == null) {
          updatePlayerState(PlayerState.IDLE);
        } else {
          movePlayerInDirection(playerInputDirection, delta);
        }
        break;
      case CLIMBING:
      case ROLLING:
      case FALLING:
      case DEAD:
        break;
    }
  }

  public void addPlayer(String uuid) {
    Vector4f playerColour = playerColours.get(new Random().nextInt(playerColours.size()));
    players.put(uuid, new Player(uuid, playerIdInc, playerColour));
    playerIdInc++;
    playerColours.remove(playerColour);
  }

  public void removePlayer(String uuid) {
    Player removedPlayer = players.get(uuid);
    players.remove(uuid, removedPlayer);

    // decrement other player ids
    for (Player player : players.values()) {
      int playerId = player.getId();
      if (playerId > removedPlayer.getId()) {
        player.setId(playerId - 1);
      }
    }

    playerIdInc--;
    playerColours.add(removedPlayer.getColour());
  }

  private void movePlayerInDirection(Direction direction, float delta) {
    Player player = myPlayer();
    player.setDirection(direction);
    player.setPlayerState(PlayerState.MOVING);

    Vector3f origPos = player.getPosition();
    Vector3f newPos = new Vector3f();
    direction.getNormalisedDirection().mul(delta, newPos).mul(player.getSpeed(), newPos);
    origPos.add(newPos, newPos);
    player.update(origPos, newPos, getCurrentLevel().getLevelMap());
  }

  private void updatePlayerState(PlayerState state) {
    myPlayer().setPlayerState(state);
  }
}
