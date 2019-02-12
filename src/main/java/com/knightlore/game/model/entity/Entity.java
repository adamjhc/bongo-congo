package com.knightlore.game.model.entity;

import com.knightlore.game.model.math.Vector3f;

public abstract class Entity {

  private Vector3f position;

  public Vector3f getPosition() {
    return position;
  }

  public void setPosition(Vector3f position) {
    this.position = position;
  }
}
