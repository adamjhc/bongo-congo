package com.knightlore.game.entity;

import com.knightlore.client.networking.GameConnection;
import com.knightlore.game.map.LevelMap;
import com.knightlore.game.map.Tile;
import com.knightlore.game.util.CoordinateUtils;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4f;

public class Player extends Entity {

  static final Vector3f START_POSITION = new Vector3f(0.5f, 0.5f, 0);
  static final Direction START_DIRECTION = Direction.NORTH_WEST;
  static final PlayerState START_PLAYER_STATE = PlayerState.IDLE;
  static final int START_LIVES = 3;
  static final int START_ROLL_COOLDOWN = 0;

  private int score;
  private int lives;
  private int rollCooldown;
  private float climbVal = 0.1f;
  private PlayerState playerState;
  private String associatedSession;
  private Vector4f colour;

  public Player(String sessionID, int id, Vector4f colour) {
    this.associatedSession = sessionID;
    this.id = id;
    this.colour = colour;

    speed = 7;
    score = 0;

    lives = START_LIVES;
    rollCooldown = START_ROLL_COOLDOWN;
    direction = START_DIRECTION;
    position = START_POSITION;
    playerState = START_PLAYER_STATE;
  }

  public String getAssociatedSession() {
    return associatedSession;
  }

  public PlayerState getPlayerState() {
    return playerState;
  }

  public void setPlayerState(PlayerState playerState) {
    this.playerState = playerState;
  }

  public Vector4f getColour() {
    return colour;
  }

  public int getLives() {
    return lives;
  }

  public int getScore() {
    return score;
  }

  public void addToScore(int amount) {
    score += amount;
  }

  public float getClimbVal() {
    return climbVal;
  }

  public int getCooldown() {
    return rollCooldown;
  }

  public void setCooldown(int rollCooldown) {
    this.rollCooldown = rollCooldown;
  }

  @Override
  public Vector3f getPosition() {
    return position;
  }

  public void reset() {
    playerState = START_PLAYER_STATE;
    position = START_POSITION;
    direction = START_DIRECTION;
    lives = START_LIVES;
    rollCooldown = START_ROLL_COOLDOWN;
  }

  @Override
  void update() {}

  /**
   * The main method called in the game loop. Continuously checks for collision events with specific
   * tiles such as blocking or hazards. Also allows the player to climb up layers of the levelMap,
   * and manages falling down layers.
   *
   * @param oldPos The position of the player before collision check update
   * @param newPos The potential position of the player after collision check update
   * @param levelMap The current levelMap being played
   * @author Jacqueline Henes
   */
  public void update(Vector3f oldPos, Vector3f newPos, LevelMap levelMap) {

    Vector3i coords = CoordinateUtils.getTileCoord(setPadding(newPos));

    try {
      Tile newTile = levelMap.getTile(coords);

      if (newTile.getIndex() == 0) { // Checks if tile is an air tile
        coords = CoordinateUtils.getTileCoord(new Vector3f(coords.x, coords.y, coords.z - 1));
        Tile below = levelMap.getTile(coords);
        if (below.getIndex() == 2
            || below.getIndex() == 3) { // Check if the tile you are falling onto is walkable
          setPosition(oldPos);
        } else if (below.getIndex() == 0) {
          setPlayerState(PlayerState.FALLING);
        } else {
          climbVal = -0.1f;
          setPlayerState(PlayerState.CLIMBING);
        }

      } else if (newTile.getIndex() == 2) { // Checks if tile is a blocking tile
        setPosition(oldPos);
      } else { // Sets new position
        setPosition(newPos);
      }

      if (newTile.getIndex() == 3) { // Checks for climbable tile
        coords = CoordinateUtils.getTileCoord(new Vector3f(coords.x, coords.y, coords.z + 1));
        Tile above = levelMap.getTile(coords);
        if (above.getIndex() == 1
            && playerState
                != PlayerState.ROLLING) { // Checks if the tile above climbable tile is accessible
          climbVal = 0.1f;
          setPlayerState(PlayerState.CLIMBING);
        } else {
          setPosition(oldPos);
        }
      }
      if (newTile.getIndex() == 4) {
        loseLife();
      }

      if (newTile.getIndex() == 5) { // Checks for goal
        addToScore(10000);
        setPosition(newPos);
        // TODO: Switch game state here
      }

      // TODO: Enemy collisions

      // catches SW and SE edges    catches NE and NW edges
    } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
      setPosition(oldPos);
    }
  }

  /**
   * Returns the coordinates of the player plus padding to give the look of actual collisions in
   * game.
   *
   * @param pos The position to be padded
   * @return The padded coordinates of the player
   * @author Jacqueline Henes, Adam Cox
   */
  public Vector3f setPadding(Vector3f pos) {
    Vector3f padded = new Vector3f();
    direction.getNormalisedDirection().mul(0.4f, padded);
    return padded.add(pos);
  }

  /**
   * Resets player to spawn point having lost a life
   *
   * @author Jacqueline Henes
   */
  public void loseLife() {
    if (playerState != PlayerState.ROLLING) {
      lives -= 1;
      if (lives <= 0) {
        lives = 0;
        playerState = PlayerState.DEAD;
      } else {
        setPlayerState(START_PLAYER_STATE);
        setPosition(START_POSITION);
        setDirection(START_DIRECTION);
        setCooldown(START_ROLL_COOLDOWN);
      }
    }
  }
}
