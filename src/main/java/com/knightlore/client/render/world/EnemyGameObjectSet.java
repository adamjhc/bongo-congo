package com.knightlore.client.render.world;

import com.knightlore.game.entity.Enemy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EnemyGameObjectSet {

  private static final List<EnemyGameObject> enemySet;

  static {
    enemySet = new ArrayList<>();
  }

  private EnemyGameObjectSet() {}

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
