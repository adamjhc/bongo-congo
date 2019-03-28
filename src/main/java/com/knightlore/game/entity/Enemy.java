package com.knightlore.game.entity;

import com.knightlore.game.map.LevelMap;
import org.joml.Vector3f;

import static com.knightlore.game.util.CoordinateUtils.getTileIndex;

public class Enemy extends Entity {

  private EnemyType enemyType;
  private EnemyState currentState;
  private Vector3f home;
  private int angle;

  public Enemy(EnemyType enemyType) {
    super();
    speed = 2;
    this.enemyType = enemyType;
    currentState = EnemyState.IDLE;
    angle = 0;
  }

  public Enemy(Enemy copy) {
    id = copy.id;
    speed = copy.speed;
    position = copy.position;
    direction = copy.direction;
    enemyType = copy.enemyType;
    currentState = copy.currentState;
    home = copy.home;
    angle = 0;
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

  public void setHome(Vector3f home) {this.home = home;}

  public void update(float delta, LevelMap levelMap) {
    Vector3f newPos = new Vector3f();
    switch(enemyType) {
      case WALKER:
        newPos = enemyType.getWalk().pathfind(position, delta, speed, direction);
        move(newPos, levelMap);
        break;
      case CIRCLER:
        newPos = enemyType.getCircle().pathfind(home, delta, angle);
        if (angle < 360) {
          angle += 2;
        } else {
          angle = 0;
        }

        move(newPos, levelMap);
        break;
      case RANDOMER:
        Direction newDir = enemyType.getRandom().pathfind(direction);
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
      if (enemyType == EnemyType.CIRCLER) { setDirection(enemyType.getCircle().getDirection(angle)); }
      if (tileIndex >= 6 || tileIndex == 1) {
        setCurrentState(EnemyState.MOVING);
        setPosition(newPos);
      } else {
        if (enemyType == EnemyType.CIRCLER) {return;}
        setDirection(direction.getReverse(direction));
      }
    } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
      if (enemyType == EnemyType.CIRCLER) {return;}
      setDirection(direction.getReverse(direction));
    }
  }

  @Override
  void update() {}
}



