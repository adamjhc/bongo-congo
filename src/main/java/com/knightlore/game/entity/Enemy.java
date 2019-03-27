package com.knightlore.game.entity;

import com.knightlore.game.entity.ai.EnemyAI;
import com.knightlore.game.entity.ai.WalkerAI;
import com.knightlore.game.map.LevelMap;
import com.knightlore.game.util.CoordinateUtils;
import org.joml.Vector3f;

import static com.knightlore.game.util.CoordinateUtils.getTileIndex;

//import static com.knightlore.game.util.CoordinateUtils.getTileIndex;

public class Enemy extends Entity {

  private EnemyType enemyType;
  private EnemyState currentState;

  public Enemy(EnemyType enemyType) {
    super();
    speed = 2;
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

  public EnemyType getEnemyType() {
    return enemyType;
  }

  public EnemyState getCurrentState() {
    return currentState;
  }

  public void setCurrentState(EnemyState currentState) {
    this.currentState = currentState;
  }

  public void update(float delta, LevelMap levelMap) {
    Vector3f newPos = new Vector3f();
    switch(enemyType) {
      case WALKER:
        newPos = enemyType.getWalk().pathfind(position, delta, speed, direction);
        move(newPos, levelMap);
        break;
      case CHARGER:
        newPos = enemyType.getCharge().pathfind(position, delta, speed, direction);
        move(newPos, levelMap);
        break;
      case CIRCLER:
        newPos = enemyType.getCircle().pathfind(position, delta, speed, direction);
        move(newPos, levelMap);
        break;
      case RANDOMER:
        Direction newDir = enemyType.getRandom().pathfind(position, delta, speed, direction);
        setDirection(newDir);
        direction.getNormalisedDirection().mul(speed * delta, newPos);
        newPos.add(position);
        move(newPos, levelMap);
        break;
    }
  }

  private void move(Vector3f newPos, LevelMap levelMap) {
    try {
      int tileIndex = getTileIndex(levelMap, newPos);
      System.out.println(newPos);
      if (tileIndex >= 6 || tileIndex == 1) {
        setCurrentState(EnemyState.MOVING);
        setPosition(newPos);
      } else {
        setDirection(direction.getReverse(direction));
      }
    } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
      setDirection(direction.getReverse(direction));
    }
  }


  @Override
  void update() {}
}



