package com.knightlore.game.entity;
import com.knightlore.game.entity.ai.*;

public enum EnemyType {
  WALKER,
  RANDOMER,
  CIRCLER,
  CHARGER;

  private static WalkerAI walk;
  private static RandomerAI random;
  private static CirclerAI circle;
  private static ChargerAI charge;

  static {
    walk = new WalkerAI();
    random = new RandomerAI();
    circle = new CirclerAI();
    charge = new ChargerAI();
  }

  public ChargerAI getCharge() {
    return charge;
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



