package com.knightlore.client.render.world;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.game.entity.Enemy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Set of possible EnemyGameObjects in game
 *
 * @author Adam Cox
 */
public class EnemyGameObjectSet {

  /** Set of EnemyGameObjects */
  private static final List<EnemyGameObject> enemySet;

  static {
    String enemyTextureFileName = "player";

    enemySet = new ArrayList<>();
    enemySet.add(new EnemyGameObject(enemyTextureFileName, Colour.BLACK));
    enemySet.add(new EnemyGameObject(enemyTextureFileName, Colour.DARK_GREY));
    enemySet.add(new EnemyGameObject(enemyTextureFileName, Colour.LIGHT_GREY));
    enemySet.add(new EnemyGameObject(enemyTextureFileName, Colour.WHITE));
  }

  /**
   * Empty private constructor so class cannot be initialised
   *
   * @author Adam Cox
   */
  private EnemyGameObjectSet() {}

  /**
   * Generate list of EnemyGameObjects based on collection of enemy entities in game
   *
   * @param enemies Collection of enemy entities
   * @return list of EnemyGameObjects
   * @author Adam Cox
   */
  public static List<EnemyGameObject> fromGameModel(Collection<Enemy> enemies) {
    List<EnemyGameObject> enemyGameObjects = new ArrayList<>();

    enemies.forEach(
        enemy -> {
          EnemyGameObject enemyGameObject = new EnemyGameObject(enemySet.get(enemy.getId()));
          enemyGameObject.setPosition(enemy.getPosition());
          enemyGameObjects.add(enemyGameObject);
        });

    return enemyGameObjects;
  }
}
