package com.knightlore.game.entity;

import org.joml.Vector3f;

public abstract class Entity {

  protected int id;
  protected Vector3f position;
  protected Direction direction;
  protected EntityState currentState;

  public Entity() {
    position = new Vector3f();
    direction = Direction.SOUTH;
  }

  abstract void update();

  public int getId() {
    return id;
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

  public EntityState getCurrentState() {
    return currentState;
  }

  public void setCurrentState(EntityState currentState) {
    this.currentState = currentState;
  }
}
