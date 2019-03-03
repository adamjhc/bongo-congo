package com.knightlore.client.render.world;

import com.knightlore.game.entity.Enemy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EnemyGameObjectSet {

  private final List<EnemyGameObject> enemySet;

  public EnemyGameObjectSet() {
    enemySet = new ArrayList<>();
  }

  public List<EnemyGameObject> fromGameModel(Collection<Enemy> enemies) {
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
