package com.knightlore.client.render.world;

import com.knightlore.game.map.Tile;
import java.util.ArrayList;
import java.util.List;

public class TileGameObjectSet {

  private static final List<TileGameObject> tileSet;

  static {
    tileSet = new ArrayList<>();
    tileSet.add(new TileGameObject());
    tileSet.add(new TileGameObject(true, "floor", 4, 2));
    tileSet.add(new TileGameObject(false, "slab"));
    tileSet.add(new TileGameObject(false, "block"));
  }

  private TileGameObjectSet() {}

  public static TileGameObject getTile(int index) {
    return tileSet.get(index);
  }

  public static List<TileGameObject> fromGameModel(Tile[][][] tiles) {
    List<TileGameObject> tileGameObjects = new ArrayList<>();
    for (Tile[][] layer : tiles) {
      for (Tile[] columns : layer) {
        for (Tile tile : columns) {
          TileGameObject tileGameObject = new TileGameObject(tileSet.get(tile.getId()));
          tileGameObject.setPosition(tile.getPosition());
          tileGameObjects.add(tileGameObject);
        }
      }
    }

    return tileGameObjects;
  }
}
