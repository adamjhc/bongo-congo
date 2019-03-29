package com.knightlore.game.util;

import com.knightlore.client.render.world.TileGameObject;
import com.knightlore.game.Level;
import com.knightlore.game.map.LevelMap;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class CoordinateUtils {

  private CoordinateUtils() {}

  /**
   * Converts Vector3f to its isometric coordinates
   *
   * @param x x coordinate
   * @param y y coordinate
   * @return Isometric coordinates
   */
  private static Vector3f toIsometric(float x, float y) {
    return new Vector3f(
        (x - y) * TileGameObject.TILE_WIDTH / 2, (y + x) * TileGameObject.TILE_HEIGHT / 2, 0);
  }

  /**
   * Converts Vector3f to its isometric coordinates
   *
   * @param pos Vector3f coordinates
   * @return Isometric coordinates
   */
  public static Vector3f toIsometric(Vector3f pos) {
    return toIsometric(pos.x + pos.z, pos.y + pos.z);
  }

  /**
   * Converts Vector3f to its isometric coordinates
   *
   * @param x x coordinate
   * @param y y coordinate
   * @param z z coordinate
   * @return Isometric coordinates
   */
  public static Vector3f toIsometric(float x, float y, float z) {
    return toIsometric(x + z, y + z);
  }

  /**
   * Converts Vector3f to its cartesian coordinates
   *
   * @param x
   * @param y
   * @return
   */
  public static Vector3f toCartesian(float x, float y) {
    return new Vector3f(
        x / TileGameObject.TILE_WIDTH + y / TileGameObject.TILE_HEIGHT,
        y / TileGameObject.TILE_HEIGHT - x / TileGameObject.TILE_WIDTH,
        0);
  }

  /**
   * Checks if a map is big enough to contain a specific coordinate
   *
   * @param mapSize
   * @param position
   * @return
   */
  public static boolean mapHasPosition(Vector3i mapSize, Vector3i position) {
    return position.x >= 0
        && position.y >= 0
        && position.z >= 0
        && position.x < mapSize.x
        && position.y < mapSize.y
        && position.z < mapSize.z;
  }

  /**
   * Converts between Vector3f and Vector 3i
   *
   * @param coord Vector3f tile coordinates
   * @return Vector3i tile coordinates
   */
  public static Vector3i getTileCoord(Vector3f coord) {
    return new Vector3i(((int) Math.floor(coord.x)), ((int) Math.floor(coord.y)), ((int) coord.z));
  }

  /**
   * Returns the tile index of a tile from a map
   *
   * @param level The level being referenced
   * @param coords Coordinates of the tile being checked
   * @return Tile type index
   */
  public static int getTileIndex(Level level, Vector3f coords) {
    return level.getLevelMap().getTile(getTileCoord(coords)).getIndex();
  }

  /**
   * Returns the tile index of a tile from a map
   *
   * @param levelMap Level map being referenced
   * @param coords Coordinates of tile being checked
   * @return Tile type index
   */
  public static int getTileIndex(LevelMap levelMap, Vector3f coords) {
    return levelMap.getTile(getTileCoord(coords)).getIndex();
  }
}
