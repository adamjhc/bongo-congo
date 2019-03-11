package com.knightlore.client.render.world;

import com.knightlore.client.render.opengl.RenderModel;
import com.knightlore.game.util.CoordinateUtils;
import org.joml.Vector3f;

public abstract class GameObject {

  protected RenderModel model;
  float[] textureCoordinates =
      new float[] {
        0, 0,
        1, 0,
        1, 1,
        0, 1,
      };
  int[] indices =
      new int[] {
        0, 1, 2,
        2, 3, 0
      };
  Vector3f isometricPosition;
  Vector3f modelPosition;

  public Vector3f getIsometricPosition() {
    return isometricPosition;
  }

  public void setIsometricPosition(Vector3f isometricPosition) {
    this.isometricPosition = isometricPosition;
  }

  public void setModelPosition(Vector3f modelPosition) {
    this.modelPosition = modelPosition;
  }

  public Vector3f getModelPosition() {
    return modelPosition;
  }

  public void setPosition(Vector3f modelPosition) {
    this.modelPosition = modelPosition;
    isometricPosition = CoordinateUtils.toIsometric(modelPosition);
  }

  public abstract void cleanup();
}
