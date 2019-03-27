package com.knightlore.game;

import static org.joml.Math.round;

import com.knightlore.client.audio.Audio;
import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.Enemy;
import com.knightlore.game.entity.Player;
import com.knightlore.game.entity.PlayerState;
import com.knightlore.game.map.LevelMap;
import com.knightlore.game.util.CoordinateUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.knightlore.game.util.CoordinateUtils;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class GameModel {

  private static final int NUMBER_OF_LEVELS = 3;
  private static final float ROLL_SPEED = 1.5f;
  private static final int ROLL_COOLDOWN = 150;

  private String uuid;

  private GameState currentState;
  private Integer currentLevelIndex;
  private ArrayList<Level> levels;

  private Map<String, Player> players;
  private int playerIdInc;
  private List<Vector4f> playerColours;

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
    if (currentLevelIndex == NUMBER_OF_LEVELS - 1) {
      currentState = GameState.SCORE;
      return;
    }

    currentState = GameState.NEXT_LEVEL;
    players.forEach((playerUUID, player) -> player.reset());
    currentLevelIndex++;
  }

  public void clientUpdate(float delta, Direction playerInputDirection) {
    if (currentState == GameState.NEXT_LEVEL) {
      currentState = GameState.PLAYING;
    }

    if (myPlayer().getPlayerState() != PlayerState.ROLLING) {
      rollCountdown();
    }

    if (getTileIndex(myPlayer().getPosition()) == 5) { // Checks for goal
      myPlayer().addToScore(10000);
      if(GameConnection.instance != null){
        GameConnection.instance.sendLevelComplete();
      }
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
        // 'Plays' climbing animation
        if (accumulator < 9) {
          bottom.z += player.getClimbVal();
          accumulator++;
          player.setPosition(bottom);
          delay(5);
        } else {
          // Done playing animation, set position
          accumulator = 0;
          player.setPosition(player.setPadding(roundZ(bottom)));
          System.out.println(player.getPosition());
          player.setClimbFlag(false);
          player.setPlayerState(PlayerState.IDLE);
        }
        break;
      case ROLLING:
        // 'Play' animation
        if (accumulator < 20) {
          delay(5);
          movePlayerInDirection(myPlayer().getDirection(), delta * ROLL_SPEED);
          updatePlayerState(PlayerState.ROLLING);
          accumulator++;
        } else {
          // Set up cooldown and stop animation
          accumulator = 0;
          myPlayer().setCooldown(ROLL_COOLDOWN);
          updatePlayerState(PlayerState.IDLE);
          myPlayer().setPosition(myPlayer().getPosition());
        }
        break;
      case FALLING:
        player = myPlayer();
        Vector3f top = player.getPosition();
        // 'Play' animation
        if (top.z != 0) {
          top.z -= 0.1;
          if (top.z < 0) {
            top.z = 0;
          }
          player.setPosition(top);
          delay(5);
        } else {
          // Reset player
          player.setPosition(top);
          delay(200);
          player.loseLife();
        }
        break;
      case DEAD:
        break;
    }
    if (getTileIndex(myPlayer().getPosition()) == 4
            && myPlayer().getPlayerState() != PlayerState.ROLLING
            && myPlayer().getPlayerState() != PlayerState.DEAD
            && myPlayer().getPlayerState() != PlayerState.FALLING) {
      Audio.play(Audio.AudioName.SOUND_HIT);
      delay(200);
      myPlayer().loseLife();
    }

    myPlayer().setClimbFlag(false);
  }

  public void serverUpdate(float delta) {
    List<Enemy> enemies = getCurrentLevel().getEnemies();
    for (Enemy enemy : enemies) {
      enemy.update();
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

    Vector3f origPos = player.getPosition();
    Vector3f newPos = new Vector3f();
    direction.getNormalisedDirection().mul(delta, newPos).mul(player.getSpeed(), newPos);
    origPos.add(newPos, newPos);
    player.update(origPos, newPos, getCurrentLevel().getLevelMap());
  }

  /**
   * Updates the current client's Player PlayerState
   *
   * @param state
   */
  private void updatePlayerState(PlayerState state) {
    myPlayer().setPlayerState(state);
  }

  /**
   * Helper method for game physics related animations in the update loop 'Pauses' the player for a
   * set delay, allowing animations to play out
   *
   * @param target delay in milliseconds
   * @author Jacqui Henes
   */
  private void delay(long target) {
    long start = System.nanoTime() / 1000000;
    long difference = 0;
    while (difference < target) {
      long check = System.nanoTime() / 1000000;
      difference = check - start;
    }
  }

  /**
   * Decrements the cooldown for Player rolling
   *
   * @author Jacqui Henes
   */
  private void rollCountdown() {
    Player player = myPlayer();
    int playerCooldown = player.getCooldown();
    if (playerCooldown != 0) {
      player.setCooldown(playerCooldown - 1);
    }
  }

  public boolean lastLevel() {
    return this.currentLevelIndex == this.levels.size() - 1;
  }

  public void incrementLevel() {
    this.currentLevelIndex++;
  }

  public Vector3f roundZ(Vector3f pos) {
    Vector3f rounded = new Vector3f(pos);
    rounded.z = round(pos.z);
    return rounded;
  }

  public int getTileIndex(Vector3f coords) {
    return getCurrentLevel().getLevelMap().getTile(CoordinateUtils.getTileCoord(coords)).getIndex();
  }
}
