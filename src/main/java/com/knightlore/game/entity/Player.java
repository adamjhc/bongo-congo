package com.knightlore.game.entity;

import com.knightlore.client.audio.Audio;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.game.map.LevelMap;
import com.knightlore.game.map.Tile;
import com.knightlore.game.util.CoordinateUtils;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4f;

/** The entity that the user controls! Handles collisions, player movement, life and death. */
public class Player extends Entity {

  static final Vector3f START_POSITION = new Vector3f(0.1f, 0.1f, 0);
  static final Direction START_DIRECTION = Direction.NORTH_WEST;
  static final PlayerState START_PLAYER_STATE = PlayerState.IDLE;
  static final int START_LIVES = 3;
  static final int START_ROLL_COOLDOWN = 0;

  private int score;
  private int lives;
  private int rollCooldown;
  private float climbVal = 0.1f;
  private boolean climbFlag = false;
  private PlayerState playerState;
  private String associatedSession;
  private Vector4f colour;

  /**
   * Constructor for a Player object
   *
   * @param sessionID ID for the session where the player is instantiated
   * @param id Used to differentiate between multiple Players in one session
   * @param colour Colour of the player character
   */
  public Player(String sessionID, int id, Vector4f colour) {
    this.associatedSession = sessionID;
    this.id = id;
    this.colour = colour;

    speed = 4;
    score = 0;

    lives = START_LIVES;
    rollCooldown = START_ROLL_COOLDOWN;
    direction = START_DIRECTION;
    position = START_POSITION;
    playerState = START_PLAYER_STATE;
  }

  /**
   * Getter for property 'associatedSession'.
   *
   * @return Value for property 'associatedSession'.
   */
  public String getAssociatedSession() {
    return associatedSession;
  }

  /**
   * Getter for property 'playerState'.
   *
   * @return Value for property 'playerState'.
   */
  public PlayerState getPlayerState() {
    return playerState;
  }

  /**
   * Setter for property 'playerState'.
   *
   * @param playerState Value to set for property 'playerState'.
   */
  public void setPlayerState(PlayerState playerState) {
    this.playerState = playerState;
  }

  /**
   * Getter for property 'colour'.
   *
   * @return Value for property 'colour'.
   */
  public Vector4f getColour() {
    return colour;
  }

  /**
   * Getter for property 'lives'.
   *
   * @return Value for property 'lives'.
   */
  public int getLives() {
    return lives;
  }

  /**
   * Getter for property 'score'.
   *
   * @return Value for property 'score'.
   */
  public int getScore() {
    return score;
  }

  /**
   * Setter for property 'score'.
   *
   * @param score Value to set for property 'score'.
   */
  public void setScore(int score) {
    this.score = score;
  }

  public void addToScore(int amount) {
    score += amount;
  }

  /**
   * Getter for property 'climbVal'.
   *
   * @return Value for property 'climbVal'.
   */
  public float getClimbVal() {
    return climbVal;
  }

  /**
   * Getter for property 'cooldown'.
   *
   * @return Value for property 'cooldown'.
   */
  public int getCooldown() {
    return rollCooldown;
  }

  /**
   * Setter for property 'cooldown'.
   *
   * @param rollCooldown Value to set for property 'cooldown'.
   */
  public void setCooldown(int rollCooldown) {
    this.rollCooldown = rollCooldown;
  }

  /**
   * Setter for property 'climbFlag'.
   *
   * @param climbFlag Value to set for property 'climbFlag'.
   */
  public void setClimbFlag(boolean climbFlag) {
    this.climbFlag = climbFlag;
  }

  /** {@inheritDoc} */
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

  /** {@inheritDoc} */
  @Override
  void update() {}

  /**
   * The main method called in the client game loop. Continuously checks for collision events with
   * specific tiles such as air tiles and wall tiles. Also allows the player to climb up and down
   * layers of the levelMap.
   *
   * @param oldPos The position of the player before collision check update
   * @param newPos The potential position of the player after collision check update
   * @param levelMap The current levelMap being played
   * @author Jacqui Henes
   */
  public void update(Vector3f oldPos, Vector3f newPos, LevelMap levelMap) {

    Vector3i coords = CoordinateUtils.getTileCoord(setPadding(newPos));

    try {
      Tile newTile = levelMap.getTile(coords);

      if (newTile.getIndex() == 0) { // Air tile collision
        coords = CoordinateUtils.getTileCoord(new Vector3f(coords.x, coords.y, coords.z - 1));
        Tile below = levelMap.getTile(coords);
        if (below.getIndex() == 2
            || below.getIndex() == 3) { // Check if the tile you are falling onto is walkable
          setPosition(oldPos);
        } else if (below.getIndex() == 0) {
          setPosition(setPadding(position, 0.3f));
          setPlayerState(PlayerState.FALLING);
        } else if (climbFlag) {
          climbVal = -0.1f;
          setPlayerState(PlayerState.CLIMBING);
        }

      } else if (newTile.getIndex() == 2) { // Wall tile collision
        setPosition(oldPos);
      } else {
        setPosition(newPos);
      }

      if (newTile.getIndex() == 3) { // Climbing
        coords = CoordinateUtils.getTileCoord(new Vector3f(coords.x, coords.y, coords.z + 1));
        Tile above = levelMap.getTile(coords);
        if ((above.getIndex() == 1 || above.getIndex() >= 5)
            && playerState != PlayerState.ROLLING
            && climbFlag) { // Checks if the tile above climbable tile is accessible
          climbVal = 0.1f;
          Audio.play(Audio.AudioName.SOUND_CLIMB);
          setPlayerState(PlayerState.CLIMBING);
        } else {
          setPosition(oldPos);
        }
      }

      // catches SW and SE edges    catches NE and NW edges
    } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
      climbFlag = false;
      setPosition(oldPos);
    }
  }

  /**
   * Returns the coordinates of the player plus padding to give the look of actual collisions in
   * game.
   *
   * @param pos The position to be padded
   * @return The padded coordinates of the player
   * @author Jacqui Henes, Adam Cox
   */
  public Vector3f setPadding(Vector3f pos) {
    Vector3f padded = new Vector3f();
    direction.getNormalisedDirection().mul(0.2f, padded);
    return padded.add(pos);
  }

  public Vector3f setPadding(Vector3f pos, float scalar) {
    Vector3f padded = new Vector3f();
    direction.getNormalisedDirection().mul(scalar, padded);
    return padded.add(pos);
  }

  /**
   * Resets player to spawn point having lost a life, or sets their PlayerState to dead, triggering
   * the level's end.
   *
   * @author Jacqui Henes
   */
  public void loseLife() {
    if (GameConnection.instance != null) {
      GameConnection.instance.sendDeath();
    }
    decrementLives();
    if (lives <= 0) {
      Audio.play(Audio.AudioName.JINGLE_GAMEOVER);
      lives = 0;
      playerState = PlayerState.DEAD;
    } else {
      setPlayerState(START_PLAYER_STATE);
      setPosition(START_POSITION);
      setDirection(START_DIRECTION);
      setCooldown(START_ROLL_COOLDOWN);
    }
  }

  /** Reduces life count by 1 */
  public void decrementLives() {
    this.lives--;
  }
}
