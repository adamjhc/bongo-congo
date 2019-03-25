package com.knightlore.game.util;

import com.knightlore.client.render.world.TileGameObject;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class CoordinateUtils {

  private CoordinateUtils() {}

  private static Vector3f toIsometric(float x, float y) {
    return new Vector3f(
        (x - y) * TileGameObject.TILE_WIDTH / 2, (y + x) * TileGameObject.TILE_HEIGHT / 2, 0);
  }

  public static Vector3f toIsometric(Vector3f pos) {
    return toIsometric(pos.x + pos.z, pos.y + pos.z);
  }

  public static Vector3f toIsometric(float x, float y, float z) {
    return toIsometric(x + z, y + z);
  }

  public static Vector3f toCartesian(float x, float y) {
    return new Vector3f(
        x / TileGameObject.TILE_WIDTH + y / TileGameObject.TILE_HEIGHT,
        y / TileGameObject.TILE_HEIGHT - x / TileGameObject.TILE_WIDTH,
        0);
  }

  public static boolean mapHasPosition(Vector3i mapSize, Vector3i position) {
    return position.x >= 0
        && position.y >= 0
        && position.z >= 0
        && position.x < mapSize.x
        && position.y < mapSize.y
        && position.z < mapSize.z;
  }

  public static Vector3i getTileCoord(Vector3f coord) {
    return new Vector3i(((int) Math.floor(coord.x)), ((int) Math.floor(coord.y)), ((int) coord.z));
  }
}
