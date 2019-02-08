package com.knightlore.client.render.world;

import java.util.ArrayList;

public class TileSet {

  private ArrayList<TileGameObject> tileSet;

  public TileSet() {
    tileSet = new ArrayList<>();
    loadTiles();
  }

  public TileGameObject getTile(int index) {
    return tileSet.get(index);
  }

  private void loadTiles() {
    tileSet.add(new TileGameObject());
    tileSet.add(new TileGameObject("floor_E.png"));
    tileSet.add(new TileGameObject("slab_N.png"));
    tileSet.add(new TileGameObject("blockHuge_E.png"));
  }
}
