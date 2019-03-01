package com.knightlore.game.map;

import java.util.ArrayList;
import org.joml.Vector3f;

public class TileSet {

  private static Tile airTile = new Tile(false, false, false, false);
  private static Tile floorTile = new Tile(true, false, false, false);
  private static Tile wallTile = new Tile(false, false, false, false);
  private static Tile climbableWallTile = new Tile(false, true, false, false);
  private static Tile hazardTile = new Tile(true, false, true, false);
  private static Tile goalTile = new Tile(true, false, false, true);

  private ArrayList<Tile> set;

  public TileSet() {
    set = new ArrayList<>();
    set.add(0, airTile);
    set.add(1, floorTile);
    set.add(2, wallTile);
    set.add(3, climbableWallTile);
    set.add(4, hazardTile);
    set.add(5, goalTile);
  }

  Tile getTile(int index, Vector3f position) {
    Tile tile = new Tile(set.get(index));
    tile.setPosition(position);
    return tile;
  }
}
