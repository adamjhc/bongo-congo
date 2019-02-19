package com.knightlore.game.map;

public abstract class Tile {

  private static int inc = 0;
  private int id;

  private boolean walkable;
  private boolean climbable;
  private boolean hazard;
  private boolean goal;

  public Tile(){

  }

  public Tile(boolean walkable, boolean climbable, boolean hazard, boolean goal) {
    id = inc;
    inc++;

    this.walkable = walkable;
    this.climbable = climbable;
    this.hazard = hazard;
    this.goal = goal;
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
}
