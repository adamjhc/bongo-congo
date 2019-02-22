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
    tileSet.add(0, new AirTile());
    tileSet.add(1, new FloorTile());
    tileSet.add(2, new WallTile());
    tileSet.add(3, new ClimbableWallTile());
    tileSet.add(4, new HazardTile());
    tileSet.add(5, new GoalTile());
  }

  class AirTile extends Tile {
    AirTile() {
      super(true, false, false, false);
    }
  }

  class FloorTile extends Tile {
    FloorTile() {
      super(true, false, false, false);
    }
  }

  class WallTile extends Tile {
    WallTile() {
      super(false, false, false, false);
    }
  }

  class ClimbableWallTile extends Tile {
    ClimbableWallTile() {
      super(false, true, false, false);
    }
  }

  class HazardTile extends Tile {
    HazardTile() {
      super(true, false, true, false);
    }
  }

  class GoalTile extends Tile {
    GoalTile() {
      super(true, false, false, true);
    }
  }
}
