package com.knightlore.game.entity;

public class Player extends Entity {

  private static int inc = 0;

  public int lives;
  public int score;

  public Player() {
    super();
    id = inc;
    inc++;
  }
}
