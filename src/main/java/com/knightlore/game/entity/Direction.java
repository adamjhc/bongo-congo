package com.knightlore.game.entity;

import com.knightlore.client.render.world.TileGameObject;
import java.util.Random;
import org.joml.Vector3f;

public enum Direction {
  /** Positive in x */
  NORTH("n"),

  /** Positive in x, negative in y */
  NORTH_EAST("ne"),

  /** Negative in y */
  EAST("e"),

  /** Negative in x, negative in y */
  SOUTH_EAST("se"),

  /** Negative in x */
  SOUTH("s"),

  /** Negative in x, positive in y */
  SOUTH_WEST("sw"),

  /** Positive in y */
  WEST("w"),

  /** Positive in x, positive in y */
  NORTH_WEST("nw");

  static {
    NORTH.normalisedDirection = new Vector3f(1, 0, 0);
    EAST.normalisedDirection = new Vector3f(0, -1, 0);
    SOUTH.normalisedDirection = new Vector3f(-1, 0, 0);
    WEST.normalisedDirection = new Vector3f(0, 1, 0);

    // The ratio between vertical movement speed and horizontal movement speed should be the same as
    // the ratio between tile height and tile width. 1.4142 is pythagoras of the above vectors
    float normalised = 1.4142f * TileGameObject.TILE_HEIGHT / TileGameObject.TILE_WIDTH;
    NORTH_EAST.normalisedDirection = new Vector3f(normalised, -normalised, 0);
    SOUTH_EAST.normalisedDirection = new Vector3f(-normalised, -normalised, 0);
    SOUTH_WEST.normalisedDirection = new Vector3f(-normalised, normalised, 0);
    NORTH_WEST.normalisedDirection = new Vector3f(normalised, normalised, 0);

    // Each directions has a opposite
    NORTH.opposite = SOUTH;
    NORTH_EAST.opposite = SOUTH_WEST;
    EAST.opposite = WEST;
    SOUTH_EAST.opposite = NORTH_WEST;
    SOUTH.opposite = NORTH;
    SOUTH_WEST.opposite = NORTH_EAST;
    WEST.opposite = EAST;
    NORTH_WEST.opposite = SOUTH_EAST;
  }

  /** Abbreviation used in texture file names */
  private final String abbreviation;

  /** Normalised direction vector for isometric movement */
  private Vector3f normalisedDirection;

  /** Polar opposite direction */
  private Direction opposite;

  /**
   * Initialisation of Direction enum
   *
   * @param abbreviation Abbreviation used in texture file names
   * @author Adam Cox
   */
  Direction(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  /**
   * Get a random direction
   *
   * @return a random direction
   * @author Jacqui Henes
   */
  public static Direction getRandom() {
    return values()[new Random().nextInt(values().length)];
  }

  /**
   * Get the abbreviation
   *
   * @return abbreviation
   * @author Adam Cox
   */
  public String getAbbreviation() {
    return abbreviation;
  }

  /**
   * Get the normalised direction
   *
   * @return normalised direction
   * @author Adam Cox
   */
  public Vector3f getNormalisedDirection() {
    return normalisedDirection;
  }

  /**
   * Get the reverse direction
   *
   * @return reverse direction
   * @author Adam Cox
   */
  public Direction getReverse() {
    return opposite;
  }
}
