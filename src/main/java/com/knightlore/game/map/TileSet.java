package com.knightlore.game.map;

import java.util.ArrayList;

public class TileSet {

  private static Tile airTile = new Tile(true, false, false, false);
  private static Tile floorTile = new Tile(true, false, false, false);
  private static Tile wallTile = new Tile(false, false, false, false);
  private static Tile climbableWallTile = new Tile(false, true, false, false);
  private static Tile hazardTile = new Tile(true, false, true, false);
  private static Tile goalTile = new Tile(true, false, false, true);

  private ArrayList<Tile> tileSet;

  public TileSet() {
    tileSet = new ArrayList<>();
    tileSet.add(0, airTile);
    tileSet.add(1, floorTile);
    tileSet.add(2, wallTile);
    tileSet.add(3, climbableWallTile);
    tileSet.add(4, hazardTile);
    tileSet.add(5, goalTile);
  }

  Tile getTile(int index) {
    return tileSet.get(index);
  }
}
