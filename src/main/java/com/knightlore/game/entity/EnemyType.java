package com.knightlore.game.entity;

import com.knightlore.game.entity.ai.CirclerAI;
import com.knightlore.game.entity.ai.RandomerAI;
import com.knightlore.game.entity.ai.WalkerAI;

public enum EnemyType {
  WALKER,
  RANDOMER,
  CIRCLER;

  private static WalkerAI walk;
  private static RandomerAI random;
  private static CirclerAI circle;

  static {
    // Set up AI behaviours
    walk = new WalkerAI();
    random = new RandomerAI();
    circle = new CirclerAI();
  }

  /**
   * Getter for property 'circleAI'.
   *
   * @return Value for property 'circle'.
   */
  public CirclerAI getCircle() {
    return circle;
  }

  /**
   * Getter for property 'randomAI'.
   *
   * @return Value for property 'random'.
   */
  public RandomerAI getRandom() {
    return random;
  }

  /**
   * Getter for property 'walkAI'.
   *
   * @return Value for property 'walk'.
   */
  public WalkerAI getWalk() {
    return walk;
  }
}
