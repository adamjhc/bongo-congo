package com.knightlore.game.entity;

import com.knightlore.game.math.Vector3f;

public abstract class Entity {

  private Vector3f position;

  public Vector3f getPosition() {
    return position;
  }

  public void setPosition(Vector3f position) {
    this.position = position;
  }

  abstract void update();
}
