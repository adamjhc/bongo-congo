package com.knightlore.client.render.world;

import com.knightlore.client.gui.Colour;
import com.knightlore.game.entity.Enemy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EnemyGameObjectSet {

  private static final List<EnemyGameObject> enemySet;

  static {
    String enemyTextureFileName = "player";

    enemySet = new ArrayList<>();
    enemySet.add(new EnemyGameObject(enemyTextureFileName, Colour.BLACK));
    enemySet.add(new EnemyGameObject(enemyTextureFileName, Colour.DARK_GREY));
    enemySet.add(new EnemyGameObject(enemyTextureFileName, Colour.LIGHT_GREY));
    enemySet.add(new EnemyGameObject(enemyTextureFileName, Colour.WHITE));
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
