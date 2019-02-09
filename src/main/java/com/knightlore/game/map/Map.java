package com.knightlore.game.map;

import com.knightlore.game.math.Vector3i;

public class Map {
  private Tile[][][] tiles;

  private Vector3i size;

  public Map(int sizeX, int sizeY, int sizeZ) {
    size = new Vector3i(sizeX, sizeY, sizeZ);

    // Build array List
    Tile[][][] currentX = new Tile[sizeX][][];

    for (int x = 0; x < sizeX; x++) {
      Tile[][] currentY = new Tile[sizeY][];

      for (int y = 0; y < sizeY; y++) {
        Tile[] currentZ = new Tile[sizeZ];

        for (int z = 0; z < sizeZ; z++) {
          Tile currentTile = new FloorTile();
          currentZ[z] = currentTile;
        }

        currentY[y] = currentZ;
      }

      currentX[x] = currentY;
    }

    this.tiles = currentX;
  }

  public Map(int[][][] tiles, TileSet tileSet) {
    this.tiles = new Tile[tiles.length][tiles[0].length][tiles[0][0].length];

    for (int z = 0; z < tiles.length; z++) {
      for (int y = 0; y < tiles[z].length; y++) {
        for (int x = 0; x < tiles[z][y].length; x++) {
          this.tiles[z][y][x] = tileSet.getTile(tiles[z][y][x]);
        }
      }
    }
  }

  public Tile[][][] getTiles() {
    return tiles;
  }

  public void setTiles(Tile[][][] tiles) {
    this.tiles = tiles;
  }

  public Tile getTile(Vector3i location) {
    if (size.hasPosition(location)) {
      return this.tiles[location.x][location.y][location.z];
    }

    return null;
  }

  public void setTile(Vector3i location, Tile tile) {
    if (size.hasPosition(location)) {
      this.tiles[location.x - 1][location.y - 1][location.z - 1] = tile;
    }
  }
}
