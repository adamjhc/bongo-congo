package com.knightlore.game.entity;

import org.joml.Vector3f;

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

    float root2over2 = 0.7071f;
    NORTH_EAST.normalisedDirection = new Vector3f(root2over2, -root2over2, 0);
    SOUTH_EAST.normalisedDirection = new Vector3f(-root2over2, -root2over2, 0);
    SOUTH_WEST.normalisedDirection = new Vector3f(-root2over2, root2over2, 0);
    NORTH_WEST.normalisedDirection = new Vector3f(root2over2, root2over2, 0);
  }

  private final String abbreviation;
  private Vector3f normalisedDirection;

  Direction(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  public String getAbbreviation() {
    return abbreviation;
  }

  public Vector3f getNormalisedDirection() {
    return normalisedDirection;
  }
}
