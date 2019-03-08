package com.knightlore.game.entity;

import com.knightlore.client.networking.GameConnection;
import com.knightlore.game.map.LevelMap;
import com.knightlore.game.map.Tile;
import com.knightlore.game.util.CoordinateUtils;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Player extends Entity {

  private static final Vector3f START_POSITION = new Vector3f(0.5f, 0.5f, 0);

  private int lives;
  private int score;
  private boolean fallFlag = false;
  private PlayerState playerState;
  private String associatedSession;
  private Vector3f colour;

  public Player(String sessionID, int id, Vector3f colour) {
    this.associatedSession = sessionID;
    this.id = id;
    this.colour = colour;

    speed = 7;
    direction = Direction.SOUTH;
    position = START_POSITION;
    playerState = PlayerState.IDLE;

    lives = 3;
    score = 0;
  }

  public void addToScore(int amount) {
    score += amount;
  }

  public PlayerState getPlayerState() {
    return playerState;
  }

  public void setPlayerState(PlayerState playerState) {
    this.playerState = playerState;
  }

  public Vector3f getColour() {
    return colour;
  }

  public int getLives() {
    return lives;
  }

  public int getScore() {
    return score;
  }

  @Override
  public Vector3f getPosition() {
    return position;
  }

  public void reset() {
    playerState = PlayerState.IDLE;
    position = START_POSITION;
    direction = Direction.SOUTH_EAST;
    lives = 3;
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

        if (fallFlag) {
          while (newTile.getIndex() != 0) {
            newTile.getPosition().z -= 1;
          }
          setPosition(newPos);
          System.out.println("Fall"); // debug statement
          loseLife();
        } else {
          Tile below = levelMap.getTile(new Vector3i(coords.x, coords.y, coords.z - 1));
          if (!(below.getIndex() == 0
              || below.getIndex() == 1)) { // Check if the tile you are falling onto is walkable
            setPosition(oldPos);
          } else {
            fallFlag = true;
            newPos.z -= 1;
            setPosition(newPos);
          }
        }

      } else if (newTile.getIndex() == 2) { // Checks if tile is a blocking tile
        setPosition(oldPos);

      } else { // Sets new position
        if (GameConnection.instance != null) {
          GameConnection.instance.updatePosition(position);
        }
        fallFlag = false;
        setPosition(newPos);
      }

      if (newTile.getIndex() == 3) { // Checks for climbable tile
        newPos.z += 1;
        coords = CoordinateUtils.getTileCoord(setPadding(newPos));
        newTile = levelMap.getTile(coords);
        if (newTile.getIndex() == 1) { // Checks if the tile above climbable tile is accessible
          fallFlag = false;
          setPosition(newPos);
        } else {
          setPosition(oldPos);
        }
      }
      if (newTile.getIndex() == 4) {
        System.out.println("Ow!"); // debug statement
        loseLife();
      }

      if (newTile.getIndex() == 5) { // Checks for goal
        System.out.println("Win!"); // debug statement
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
  private Vector3f setPadding(Vector3f pos) {
    Vector3f padded = new Vector3f();
    direction.getNormalisedDirection().mul(0.4f, padded);
    pos.add(padded, padded);
    return padded;
  }

  /**
   * Resets player to spawn point having lost a life
   *
   * @author Jacqueline Henes
   */
  private void loseLife() {
    lives -= 1;
    if (lives <= 0) {
      lives = 0;
      playerState = PlayerState.DEAD;
      System.out.println("Lost all lives");
    } else {
      setPosition(START_POSITION);
      setDirection(Direction.SOUTH);
    }

    fallFlag = false;
  }
}
