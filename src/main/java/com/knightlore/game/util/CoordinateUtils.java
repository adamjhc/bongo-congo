package com.knightlore.game.util;

import com.knightlore.client.render.world.TileGameObject;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class CoordinateUtils {

  public static Vector3f toCartesian(float x, float y) {
    return new Vector3f(
        (x * TileGameObject.tileWidth / 2) - (y * TileGameObject.tileWidth / 2),
        (y * TileGameObject.tileHeight / 2) + (x * TileGameObject.tileHeight / 2),
        0);
  }

  public static boolean mapHasPosition(Vector3i map, Vector3i position) {
    return position.x >= 0
        && position.y >= 0
        && position.z >= 0
        && position.x <= map.x
        && position.y <= map.y
        && position.z <= map.z;
  }

  public static Vector3i getTileCoord(Vector3f coord) {
    return new Vector3i(
            ((int) Math.floor(coord.x)),
            ((int) Math.floor(coord.y)),
            ((int) coord.z)
    );
  }

}