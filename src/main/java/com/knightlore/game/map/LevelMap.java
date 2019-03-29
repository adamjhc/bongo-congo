package com.knightlore.game.map;

import com.knightlore.game.util.CoordinateUtils;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class LevelMap {
  private Tile[][][] tiles;

  private int time;

  private Vector3i size;

  private int rotation;

  public LevelMap(int[][][] tiles) {
    this.tiles = new Tile[tiles.length][tiles[0].length][tiles[0][0].length];
    this.rotation = 0;
    this.time = 60;

    TileSet tileSet = new TileSet();

    for (int z = 0; z < tiles.length; z++) {
      for (int y = 0; y < tiles[z].length; y++) {
        for (int x = 0; x < tiles[z][y].length; x++) {
          this.tiles[z][y][x] = tileSet.getTile(tiles[z][y][x], new Vector3f(x, y, z));
        }
      }
    }

    this.size = new Vector3i(tiles[0][0].length, tiles[0].length, tiles.length);
  }

  public Tile[][][] getTiles() {
    return tiles;
  }

  public int getTime() {
    return time;
  }

  public void setTime(int t) {
    time = t;
  }

  public Tile getTile(Vector3i location) {
    if (CoordinateUtils.mapHasPosition(size, location)) {
      return this.tiles[location.z][location.y][location.x];
    }

    return null;
  }

  public Vector3i getSize() {
    return size;
  }

  public void rotate(boolean right) {
    Tile[][][] newMap = new Tile[tiles.length][tiles[0].length][tiles[0][0].length];

    if (right) {
      for (int i = 0; i < tiles.length; i++) {
        newMap[i] = rotateLayerLeft(tiles[i]);
      }

      if (rotation == 0) {
        rotation = 3;
      } else {
        rotation -= 1;
      }
    } else {
      for (int i = 0; i < tiles.length; i++) {
        newMap[i] = rotateLayerRight(tiles[i]);
      }
      if (rotation == 3) {
        rotation = 0;
      } else {
        rotation += 1;
      }
    }

    tiles = newMap;
    int temp = size.x;
    size.x = size.y;
    size.y = temp;
  }

  public void resetRotation() {
    if (rotation != 0) {
      if (rotation == 1) {
        rotate(true);
      } else if (rotation == 3) {
        rotate(false);
      } else {
        rotate(true);
        rotate(true);
      }
    }
  }

  private Tile[][] rotateLayerLeft(Tile[][] layer) {
    Tile[][] rotatedLayer = new Tile[size.x][size.y];

    for (int i = 0; i < size.x; i++) {
      for (int j = 0; j < size.y; j++) {
        rotatedLayer[i][j] = layer[size.y - j - 1][i];
      }
    }

    return rotatedLayer;
  }

  private Tile[][] rotateLayerRight(Tile[][] layer) {
    Tile[][] rotatedLayer = new Tile[size.x][size.y];

    for (int i = 0; i < size.x; i++) {
      for (int j = 0; j < size.y; j++) {
        rotatedLayer[i][j] = layer[j][size.x - i - 1];
      }
    }

    return rotatedLayer;
  }
}
