package com.knightlore.client.render;

import com.knightlore.game.math.Matrix4f;
import com.knightlore.game.math.Vector3f;

public class World {

  private final int view;
  private int scale;
  private Matrix4f projection;

  public World() {
    this.view = 32;
    this.scale = 64;

    projection = new Matrix4f().setTranslation(new Vector3f(0));
    projection.scale(scale);
  }

  public Matrix4f getProjection() {
    return projection;
  }

  public void setScale(int scale) {
    this.scale = scale;
  }
}
