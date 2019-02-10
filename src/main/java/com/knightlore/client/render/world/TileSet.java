package com.knightlore.client.render.world;

import java.util.ArrayList;

public class TileSet {

  /** List containing all tile game objects */
  private ArrayList<TileGameObject> tileSet;

  /** Initialise the Tile set, loading the tile objects */
  public TileSet() {
    tileSet = new ArrayList<>();
    loadTiles();
  }

  /**
   * Get a tile game object
   *
   * @param index Index of the tile game object in the tile set
   * @return Tile game object
   */
  public TileGameObject getTile(int index) {
    return tileSet.get(index);
  }

  /** Loads the tile game objects into the tile set. Corresponds to the Game.TileSet class */
  private void loadTiles() {
    tileSet.add(new TileGameObject());
    tileSet.add(new TileGameObject("floor", 4, 2));
    tileSet.add(new TileGameObject("slab_N"));
    tileSet.add(new TileGameObject("blockHuge_E"));
  }
}
