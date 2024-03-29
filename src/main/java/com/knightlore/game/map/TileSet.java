package com.knightlore.game.map;

import org.joml.Vector3f;

import java.util.ArrayList;

public class TileSet {

  private static Tile airTile = new Tile(TileType.AIR);
  private static Tile floorTile = new Tile(TileType.FLOOR);
  private static Tile wallTile = new Tile(TileType.WALL);
  private static Tile climbableWallTile = new Tile(TileType.CLIMBABLE_WALL);
  private static Tile hazardTile = new Tile(TileType.HAZARD);
  private static Tile goalTile = new Tile(TileType.GOAL);
  private static Tile walkerSpawnTile = new Tile(TileType.SPAWN_WALKER);
  private static Tile randomerSpawnTile = new Tile(TileType.SPAWN_RANDOMER);
  private static Tile circlerSpawnTile = new Tile(TileType.SPAWN_CIRCLER);
  private static Tile spawnWalker = new Tile(TileType.SPAWN_WALKER);

  private ArrayList<Tile> set;

  public TileSet() {
    set = new ArrayList<>();
    set.add(airTile.getIndex(), airTile);
    set.add(floorTile.getIndex(), floorTile);
    set.add(wallTile.getIndex(), wallTile);
    set.add(climbableWallTile.getIndex(), climbableWallTile);
    set.add(hazardTile.getIndex(), hazardTile);
    set.add(goalTile.getIndex(), goalTile);
    set.add(walkerSpawnTile.getIndex(), walkerSpawnTile);
    set.add(randomerSpawnTile.getIndex(), randomerSpawnTile);
    set.add(circlerSpawnTile.getIndex(), circlerSpawnTile);
    set.add(spawnWalker.getIndex(), spawnWalker);
  }

  Tile getTile(int index, Vector3f position) {
    Tile tile = new Tile(set.get(index));
    tile.setPosition(position);
    return tile;
  }
}
