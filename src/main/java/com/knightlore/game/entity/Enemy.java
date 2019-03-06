package com.knightlore.game.entity;

import com.knightlore.game.entity.ai.EnemyAI;

public class Enemy extends Entity {

  public EnemyAI AI;
  private EnemyState currentState;

  @Override
  void update() {}

  public EnemyState getCurrentState() {
    return currentState;
  }

  public void setCurrentState(EnemyState currentState) {
    this.currentState = currentState;
  }
}
