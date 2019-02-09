package com.knightlore.game.map;

import java.util.ArrayList;

public class TileSet {

  private ArrayList<Tile> tileSet;

  public TileSet() {
    tileSet = new ArrayList<>();
    loadTiles();
  }

  public Tile getTile(int index) {
    return tileSet.get(index);
  }

  private void loadTiles() {
    tileSet.add(new AirTile());
    tileSet.add(new FloorTile());
    tileSet.add(new WallTile());
    tileSet.add(new WallTile());
  }
}
