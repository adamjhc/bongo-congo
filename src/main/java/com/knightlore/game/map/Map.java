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
          Tile currentTile = new Tile();
          currentTile.tileX = x;
          currentTile.tileY = y;
          currentTile.tileZ = z;
          currentZ[z] = currentTile;
        }

        currentY[y] = currentZ;
      }

      currentX[x] = currentY;
    }

    this.tiles = currentX;
  }

  public void setTiles(Tile[][][] tiles) {
    this.tiles = tiles;
  }

  public void setTile(Vector3i location, Tile tile) {
    if (size.hasPosition(location)) {
      this.tiles[location.x - 1][location.y - 1][location.z - 1] = tile;
    }
  }

  public Tile getTile(Vector3i location) {
    if (size.hasPosition(location)) {
      return this.tiles[location.x + 1][location.y + 1][location.z + 1];
    }

    return null;
  }
}
