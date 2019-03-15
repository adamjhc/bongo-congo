package com.knightlore.client.render.world;

import com.knightlore.game.map.Tile;
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
    tileSet.add(new TileGameObject());
    tileSet.add(new TileGameObject(true, "floor", 4, 2));
    tileSet.add(new TileGameObject(false, "slab"));
    tileSet.add(new TileGameObject(false, "block"));
    tileSet.add(new TileGameObject(true, "hazard"));
    tileSet.add(new TileGameObject(true, "goal"));
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
