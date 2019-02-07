package com.knightlore.client.util;

import com.knightlore.client.render.world.TileGameObject;
import com.knightlore.game.math.Vector3f;

public class CoordinateUtils {

  public static Vector3f toIsometric(Vector3f cartesian) {
    // TODO do we even need this?
    return null;
  }

  public static Vector3f toCartesian(Vector3f iso) {
    return new Vector3f(
        (iso.x * TileGameObject.tileWidth / 2) - (iso.y * TileGameObject.tileWidth / 2),
        (iso.y * TileGameObject.tileHeight / 2) + (iso.x * TileGameObject.tileHeight / 2),
        iso.z);
  }
}
