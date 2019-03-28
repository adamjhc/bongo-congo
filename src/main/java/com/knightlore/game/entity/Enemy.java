package com.knightlore.game.entity;

import com.knightlore.game.map.LevelMap;
import org.joml.Vector3f;

import static com.knightlore.game.util.CoordinateUtils.getTileIndex;

/**
 * Class that handles collisions and behaviour of Enemy entities
 */
public class Enemy extends Entity {

  private EnemyType enemyType;
  private EnemyState currentState;
  private Vector3f home; // Spawn point of enemy
  private int angle; // Angle of circle for Circler enemy

  /**
   * Constructor for an Enemy
   * @param enemyType Type of behaviour for the enemy
   */
  public Enemy(EnemyType enemyType) {
    super();
    speed = 2;
    this.enemyType = enemyType;
    currentState = EnemyState.IDLE;
    angle = 0;
  }

  /**
   * Makes a copy of an Enemy object
   * @param copy Enemy to be copied
   */
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

  /**
   * Getter for property 'enemyType'.
   *
   * @return Value for property 'enemyType'.
   */
  public EnemyType getEnemyType() {
    return enemyType;
  }

  /**
   * Getter for property 'currentState'.
   *
   * @return Value for property 'currentState'.
   */
  public EnemyState getCurrentState() {
    return currentState;
  }

  /**
   * Setter for property 'currentState'.
   *
   * @param currentState Value to set for property 'currentState'.
   */
  public void setCurrentState(EnemyState currentState) {
    this.currentState = currentState;
  }

  /**
   * Setter for property 'home'.
   *
   * @param home Value to set for property 'home'.
   */
  public void setHome(Vector3f home) {this.home = home;}

  /**
   * Main method called in the server loop. Updates and moves the enemies depending on their
   * enemy type. Also updates their states, which in turn allows animations to play out/
   * @param delta Time elapsed since last server update
   * @param levelMap Current map being played
   * @author Jacqui Henes
   */
  public void update(float delta, LevelMap levelMap) {
    Vector3f newPos = new Vector3f();

    // Determine what AI behaviour to use
    switch(enemyType) {
      case WALKER:
        newPos = enemyType.getWalk().pathfind(position, delta, speed, direction);
        move(newPos, levelMap);
        break;
      case CIRCLER:
        newPos = enemyType.getCircle().pathfind(home, delta, angle);
        // Iterate through the circle
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

  /**
   * Moves and does collision detection for enemies. If an enemy walks onto a
   * tile they aren't supposed to, they just reverse direction (or in the Circler
   * case just run into the block without updating their position)
   * @param newPos Position enemy is trying to move to
   * @param levelMap Current map being played
   * @author Jacqui Henes
   */
  private void move(Vector3f newPos, LevelMap levelMap) {
    try {
      int tileIndex = getTileIndex(levelMap, newPos);
      if (enemyType == EnemyType.CIRCLER) { setDirection(enemyType.getCircle().getDirection(angle)); }
      if (tileIndex >= 6 || tileIndex == 1) { // Collision check
        setCurrentState(EnemyState.MOVING);
        setPosition(newPos);
      } else { // If you bump into unwalkable tile reverse direction
        if (enemyType == EnemyType.CIRCLER) {return;}
        setDirection(direction.getReverse(direction));
      }
    } catch (NullPointerException | ArrayIndexOutOfBoundsException e) { // Doesn't allow enemies to move beyond the boundaries of the map
      if (enemyType == EnemyType.CIRCLER) {return;}
      setDirection(direction.getReverse(direction));
    }
  }

  /** {@inheritDoc} */
  @Override
  void update() {}
}



