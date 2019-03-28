package com.knightlore.game.map;

import org.joml.Vector3f;

public class Tile {
  private TileType type;
  private Vector3f position;

  public Tile(TileType type) {
    this.type = type;
  }

  public Tile(Tile copy) {
    type = copy.type;
    position = copy.position;
  }

  /**
   * Getter for property 'type'.
   *
   * @return Value for property 'type'.
   */
  public TileType getType() {
    return type;
  }

  /**
   * Setter for property 'type'.
   *
   * @param type Value to set for property 'type'.
   */
  public void setType(TileType type) {
    this.type = type;
  }

  /**
   * Getter for property 'position'.
   *
   * @return Value for property 'position'.
   */
  public Vector3f getPosition() {
    return position;
  }

  /**
   * Setter for property 'position'.
   *
   * @param position Value to set for property 'position'.
   */
  public void setPosition(Vector3f position) {
    this.position = position;
  }

  /**
   * Getter for the type index of the Tile
   *
   * @return Value for property 'index'.
   */
  public int getIndex() {
    return type.ordinal();
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Tile)) {
      return false;
    }

    Tile tile = (Tile) obj;

    return this.type == tile.type && this.position.equals(tile.position, 0.01f);
  }
}
