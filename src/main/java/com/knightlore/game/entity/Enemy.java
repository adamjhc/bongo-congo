package com.knightlore.game.entity;

import com.knightlore.game.entity.ai.EnemyAI;

public class Enemy extends Entity {

  public EnemyAI AI;
  private EnemyType enemyType;
  private EnemyState currentState;

  public Enemy(EnemyType enemyType) {
    super();

    this.enemyType = enemyType;

    currentState = EnemyState.IDLE;
  }

  public Enemy(Enemy copy) {
    id = copy.id;
    speed = copy.speed;
    position = copy.position;
    direction = copy.direction;
    enemyType = copy.enemyType;
    currentState = copy.currentState;
  }

  @Override
  public void update() {
    setPosition(AI.pathfind(position));
    System.out.println("AI move");
  }

  public EnemyType getEnemyType() {
    return enemyType;
  }

  public EnemyState getCurrentState() {
    return currentState;
  }

  public void setCurrentState(EnemyState currentState) {
    this.currentState = currentState;
  }
}
