package com.knightlore.client.render.world;

import com.knightlore.game.map.Tile;
import com.knightlore.game.map.TileType;

import java.util.ArrayList;
import java.util.List;

/**
 * Set for defined TileGameObjects
 *
 * @author Adam Cox
 */
public class TileGameObjectSet {

  /** Set of TileGameObjects */
  private static final List<TileGameObject> tileSet;

  static {
    tileSet = new ArrayList<>();
    tileSet.add(new TileGameObject(TileType.AIR, "floor"));
    tileSet.add(new TileGameObject(TileType.FLOOR, "floor"));
    tileSet.add(new TileGameObject(TileType.WALL, "slab"));
    tileSet.add(new TileGameObject(TileType.CLIMBABLE_WALL, "block"));
    tileSet.add(new TileGameObject(TileType.HAZARD, "hazard"));
    tileSet.add(new TileGameObject(TileType.GOAL, "goal"));
    tileSet.add(new TileGameObject(TileType.SPAWN_WALKER, "floor"));
    tileSet.add(new TileGameObject(TileType.SPAWN_RANDOMER, "floor"));
    tileSet.add(new TileGameObject(TileType.SPAWN_CIRCLER, "floor"));
  }

  /**
   * Private empty constructor so no one can create an instance
   *
   * @author Adam Cox
   */
  private TileGameObjectSet() {}

  /**
   * Get the tile at the given index
   *
   * @param index Index of the tile
   * @return TileGameObject
   * @author Adam Cox
   */
  public static TileGameObject getTile(int index) {
    return tileSet.get(index);
  }

  /**
   * Generate list of TileGameObjects from tile map
   *
   * @param tiles map of tiles
   * @return list of TileGameObjects
   * @author Adam Cox
   */
  public static List<TileGameObject> fromGameModel(Tile[][][] tiles) {
    List<TileGameObject> tileGameObjects = new ArrayList<>();
    for (Tile[][] layer : tiles) {
      for (Tile[] columns : layer) {
        for (Tile tile : columns) {
          TileGameObject tileGameObject = new TileGameObject(tileSet.get(tile.getIndex()));
          tileGameObject.setPosition(tile.getPosition());
          tileGameObjects.add(tileGameObject);
        }
      }
    }

    return tileGameObjects;
  }
}
