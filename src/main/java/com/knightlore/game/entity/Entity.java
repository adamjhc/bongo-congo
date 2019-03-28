package com.knightlore.game.entity;

import org.joml.Vector3f;

public abstract class Entity {

  protected int id;
  protected int speed;
  protected Vector3f position;
  protected Direction direction;

  /** Constructs a new Entity. */
  public Entity() {
    position = new Vector3f();
    direction = Direction.SOUTH;
  }

  abstract void update();

  /**
   * Getter for property 'id'.
   *
   * @return Value for property 'id'.
   */
  public int getId() {
    return id;
  }

  /**
   * Setter for property 'id'.
   *
   * @param id Value to set for property 'id'.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Getter for property 'speed'.
   *
   * @return Value for property 'speed'.
   */
  public int getSpeed() {
    return speed;
  }

  /**
   * Getter for property 'position'.
   *
   * @return Value for property 'position'.
   */
  public Vector3f getPosition() {
    return position;
  }

  /**
   * Setter for property 'position'.
   *
   * @param position Value to set for property 'position'.
   */
  public void setPosition(Vector3f position) {
    this.position = position;
  }

  /**
   * Getter for property 'direction'.
   *
   * @return Value for property 'direction'.
   */
  public Direction getDirection() {
    return direction;
  }

  /**
   * Setter for property 'direction'.
   *
   * @param direction Value to set for property 'direction'.
   */
  public void setDirection(Direction direction) {
    this.direction = direction;
  }
}
