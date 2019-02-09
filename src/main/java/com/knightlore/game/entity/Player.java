package com.knightlore.game.entity;

import com.knightlore.game.math.Vector3f;

public class Player extends Entity {

  private static int inc = 0;

  public int lives;
  public int score;

  public Player() {
    id = inc;
    inc++;

    direction = Direction.SOUTH;
    position = new Vector3f(1, 1, 0);

    lives = 3;
    score = 0;
  }
}
