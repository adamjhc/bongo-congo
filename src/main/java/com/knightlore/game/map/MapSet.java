package com.knightlore.game.map;

import java.util.ArrayList;

public class MapSet {

  private ArrayList<Map> mapSet;
  private TileSet tileSet;

  public MapSet(TileSet tileSet) {
    this.tileSet = tileSet;
    mapSet = new ArrayList<>();
    loadMaps();
  }

  public Map getMap(int index) {
    return mapSet.get(index);
  }

  public void loadMaps() {
    mapSet.add(
        new Map(
            new int[][][] {
              {
                {2, 2, 2, 2, 2, 2, 3},
                {2, 1, 1, 1, 1, 1, 3},
                {2, 1, 1, 1, 1, 1, 3},
                {2, 1, 1, 1, 1, 1, 3},
                {2, 1, 1, 1, 1, 1, 3},
                {2, 2, 2, 2, 2, 2, 3},
              },
              {
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 3},
                {0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 2},
              }
            }, tileSet));
  }
}
