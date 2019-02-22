package com.knightlore.game.map;

import com.knightlore.game.util.CoordinateUtils;
import org.joml.Vector3i;

public class Map {
  private Tile[][][] tiles;

  private Vector3i size;

  public Map(int[][][] tiles, TileSet tileSet) {
    this.tiles = new Tile[tiles.length][tiles[0].length][tiles[0][0].length];

    for (int z = 0; z < tiles.length; z++) {
      for (int y = 0; y < tiles[z].length; y++) {
        for (int x = 0; x < tiles[z][y].length; x++) {
          this.tiles[z][y][x] = tileSet.getTile(tiles[z][y][x]);
        }
      }
    }
    this.size = new Vector3i(tiles[0][0].length, tiles[0].length, tiles.length);
  }

  public Tile[][][] getTiles() {
    return tiles;
  }

  public void setTiles(Tile[][][] tiles) {
    this.tiles = tiles;
  }

  public Tile getTile(Vector3i location) {
    if (CoordinateUtils.mapHasPosition(size, location)) {
        return this.tiles[location.z][location.y][location.x];
    }

    return null;
  }

  public void setTile(Vector3i location, Tile tile) {
    if (CoordinateUtils.mapHasPosition(size, location)) {
      this.tiles[location.z -1][location.y -1][location.x -1] = tile;
    }
  }
}
