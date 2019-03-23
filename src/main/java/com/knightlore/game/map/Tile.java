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

  public TileType getType() {
    return type;
  }

  public void setType(TileType type) {
    this.type = type;
  }

  public Vector3f getPosition() {
    return position;
  }

  public void setPosition(Vector3f position) {
    this.position = position;
  }

  public int getIndex() {
    return type.ordinal();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Tile)) {
      return false;
    }

    Tile tile = (Tile) obj;

    return this.type == tile.type && this.position.equals(tile.position, 0.01f);
  }
}
