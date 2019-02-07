package com.knightlore.client.util;

import com.knightlore.client.world.BaseTile;
import com.knightlore.game.math.Vector3f;

public class CoordinateUtils {

  public static Vector3f toIsometric(Vector3f cartesian) {
    // TODO do we even need this?
    return null;
  }

  public static Vector3f toCartesian(Vector3f iso) {
    return new Vector3f(
        (iso.x * BaseTile.tileWidth / 2) - (iso.y * BaseTile.tileWidth / 2),
        (iso.y * BaseTile.tileHeight / 2) + (iso.x * BaseTile.tileHeight / 2),
        iso.z);
  }
}
