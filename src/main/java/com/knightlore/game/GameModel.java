package com.knightlore.game;

import com.knightlore.client.gui.Colour;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.Player;
import com.knightlore.game.entity.PlayerState;
import com.knightlore.game.map.LevelMap;
import java.lang.Math.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.knightlore.game.map.Tile;
import com.knightlore.game.util.CoordinateUtils;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static com.knightlore.game.entity.PlayerState.DEAD;

public class GameModel {

  private final int noOfLevels = 3;

  private String uuid;


  private ArrayList<Level> levels;

  private Map<String, Player> players;
  private int playerIdInc;
  private List<Vector4f> playerColours;
  private Integer currentLevelIndex;
  private GameState currentState;
  private int accumulator = 0;

  public GameModel(String uuid) {
    this.uuid = uuid;
    currentState = GameState.LOBBY;

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
    levels.add(new Level(levelMap));

    if (currentLevelIndex == null) {
      currentLevelIndex = 0;
    }
  }

  public void overwriteCurrentLevel(LevelMap levelMap) {
    levels.set(0, new Level(levelMap));
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
          Player player = myPlayer();
          Vector3f bottom = player.getPosition();
          if (!(accumulator > 10)) {
              bottom.z += player.getClimbVal();
              accumulator ++;
              player.setPosition(bottom);
              delay(5);
          } else {
              accumulator = 0;
              player.setPosition(player.setPadding(player.getPosition()));
              player.setPlayerState(PlayerState.IDLE);
          }
        break;
      case ROLLING:
          break;
      case FALLING:
          player = myPlayer();
          Vector3f top = player.getPosition();
          Tile tile = getCurrentLevel().getLevelMap().getTile(CoordinateUtils.getTileCoord(top));
          // TODO: change the direction so that the falling looks better
           if (top.z != 0) {
              top.z -= 0.1;
              if (top.z < 0) { top.z = 0;}
              player.setPosition(top);
              delay(5);
           } else {
               delay(500);
               player.setPosition(player.setPadding(player.getPosition()));
               player.loseLife();
           }
          break;
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

  private void delay(long target) { // target delay in milliseconds
    long start = System.nanoTime() / 1000000;
    long difference = 0;
    while (difference < target) {
      long check = System.nanoTime() / 1000000;
      difference = check - start;
    }
        }
}
