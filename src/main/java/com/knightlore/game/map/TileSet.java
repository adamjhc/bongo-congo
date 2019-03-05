package com.knightlore.game.map;

import java.util.ArrayList;
import org.joml.Vector3f;

public class TileSet {

  private static Tile airTile = new Tile(TileType.AIR);
  private static Tile floorTile = new Tile(TileType.FLOOR);
  private static Tile wallTile = new Tile(TileType.WALL);
  private static Tile climbableWallTile = new Tile(TileType.CLIMBABLE_WALL);
  private static Tile hazardTile = new Tile(TileType.HAZARD);
  private static Tile goalTile = new Tile(TileType.GOAL);

  private ArrayList<Tile> set;

  public TileSet() {
    set = new ArrayList<>();
    set.add(airTile.getIndex(), airTile);
    set.add(floorTile.getIndex(), floorTile);
    set.add(wallTile.getIndex(), wallTile);
    set.add(climbableWallTile.getIndex(), climbableWallTile);
    set.add(hazardTile.getIndex(), hazardTile);
    set.add(goalTile.getIndex(), goalTile);
  }

  Tile getTile(int index, Vector3f position) {
    Tile tile = new Tile(set.get(index));
    tile.setPosition(position);
    return tile;
  }
}
