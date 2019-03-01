package com.knightlore.game.map;

import org.joml.Vector3f;

public class Tile {

  private static int inc = 0;
  private int id;

  private boolean walkable;
  private boolean climbable;
  private boolean hazard;
  private boolean goal;

  private Vector3f position;

  public Tile(boolean walkable, boolean climbable, boolean hazard, boolean goal) {
    id = inc;
    inc++;

    this.walkable = walkable;
    this.climbable = climbable;
    this.hazard = hazard;
    this.goal = goal;
  }

  public Tile(Tile copy) {
    id = copy.id;
    walkable = copy.walkable;
    climbable = copy.climbable;
    hazard = copy.hazard;
    goal = copy.goal;
    position = copy.position;
  }

  public int getId() {
    return id;
  }

  public boolean isWalkable() {
    return walkable;
  }

  public void setWalkable(boolean walkable) {
    this.walkable = walkable;
  }

  public boolean isClimbable() {
    return climbable;
  }

  public void setClimbable(boolean climbable) {
    this.climbable = climbable;
  }

  public boolean isHazard() {
    return hazard;
  }

  public void setHazard(boolean hazard) {
    this.hazard = hazard;
  }

  public boolean isGoal() {
    return goal;
  }

  public void setGoal(boolean goal) {
    this.goal = goal;
  }

  public Vector3f getPosition() {
    return position;
  }

  public void setPosition(Vector3f position) {
    this.position = position;
  }
}
