package com.knightlore.client.render.world;

import com.knightlore.client.render.opengl.RenderModel;
import com.knightlore.game.util.CoordinateUtils;
import org.joml.Vector3f;

public abstract class GameObject {

  protected RenderModel model;
  private Vector3f isometricPosition;
  private Vector3f modelPosition;

  public Vector3f getIsometricPosition() {
    return isometricPosition;
  }

  public void setPosition(Vector3f modelPosition) {
    this.modelPosition = modelPosition;
    isometricPosition = CoordinateUtils.toIsometric(modelPosition);
  }

  public Vector3f getModelPosition() {
    return modelPosition;
  }

  public abstract void cleanup();
}
