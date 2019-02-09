package com.knightlore.game.entity;

import com.knightlore.game.math.Vector3f;

public abstract class Entity {

  protected int id;
  protected Vector3f position;
  protected Direction direction;

  public Entity() {
    position = new Vector3f();
    direction = Direction.SOUTH;
  }

  abstract void update();

  public Vector3f getPosition() {
    return position;
  }

  public int getId() {
    return id;
  }

  public void setPosition(Vector3f position) {
    this.position = position;
  }


  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public Direction getDirection() {
    return direction;
  }
}
