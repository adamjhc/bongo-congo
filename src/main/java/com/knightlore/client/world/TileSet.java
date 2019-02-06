package com.knightlore.client.world;

import java.util.ArrayList;

public class TileSet {

  private ArrayList<BaseTile> tileSet;

  public TileSet() {
    tileSet = new ArrayList<>();
    loadTiles();
  }

  public BaseTile getTile(int index) {
    return tileSet.get(index);
  }

  private void loadTiles() {
    tileSet.add(new BaseTile("floor_E.png"));
    tileSet.add(new BaseTile("blockHuge_E.png"));
  }
}
