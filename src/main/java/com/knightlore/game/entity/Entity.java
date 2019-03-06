package com.knightlore.game.entity;

import org.joml.Vector3f;

public abstract class Entity {

  protected int id;
  protected int speed;
  protected Vector3f position;
  protected Direction direction;

  public Entity() {
    position = new Vector3f();
    direction = Direction.SOUTH;
  }

  abstract void update();

  public int getId() {
    return id;
  }

  public int getSpeed() {
    return speed;
  }

  public Vector3f getPosition() {
    return position;
  }

  public void setPosition(Vector3f position) {
    this.position = position;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }
}
