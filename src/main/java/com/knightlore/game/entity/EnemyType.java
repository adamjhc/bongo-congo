package com.knightlore.game.entity;
import com.knightlore.game.entity.ai.*;
import org.joml.Vector3f;

public enum EnemyType {
  WALKER,
  RANDOMER,
  CIRCLER,
  CHARGER;

  private static WalkerAI walk;
  private static RandomerAI random;
  private static CirclerAI circle;

  static {
    walk = new WalkerAI();
    random = new RandomerAI();
    circle = new CirclerAI();
  }

  public CirclerAI getCircle() {
    return circle;
  }

  public RandomerAI getRandom() {
    return random;
  }

  public WalkerAI getWalk() {
    return walk;
  }
}



