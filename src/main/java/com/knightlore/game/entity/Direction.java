package com.knightlore.game.entity;

import com.knightlore.client.render.world.TileGameObject;
import org.joml.Vector3f;

import java.util.List;
import java.util.Random;

public enum Direction {
  NORTH("n"),
  NORTH_EAST("ne"),
  EAST("e"),
  SOUTH_EAST("se"),
  SOUTH("s"),
  SOUTH_WEST("sw"),
  WEST("w"),
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

    NORTH.opposite = SOUTH;
    NORTH_EAST.opposite = SOUTH_WEST;
    EAST.opposite = WEST;
    SOUTH_EAST.opposite = NORTH_WEST;
    SOUTH.opposite = NORTH;
    SOUTH_WEST.opposite = NORTH_EAST;
    WEST.opposite = EAST;
    NORTH_WEST.opposite = SOUTH_EAST;
  }

  private final String abbreviation;
  private Vector3f normalisedDirection;
  private Direction opposite;
  private static Random random = new Random();
  private static Direction dirs[] = Direction.values();

  Direction(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  public String getAbbreviation() {
    return abbreviation;
  }

  public Vector3f getNormalisedDirection() {
    return normalisedDirection;
  }

  public Direction getReverse(Direction dir) { return opposite; }

  public Direction getRandom(){
    return dirs[random.nextInt(dirs.length)];
  }

}
