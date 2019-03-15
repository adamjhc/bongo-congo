package com.knightlore.game;

import com.knightlore.game.entity.Enemy;
import com.knightlore.game.entity.EnemySet;
import com.knightlore.game.map.LevelMap;
import com.knightlore.game.map.Tile;
import java.util.ArrayList;
import java.util.Date;
import org.joml.Vector3f;

public class Level {

  public int duration;
  Date startedAt;
  private LevelMap levelMap;
  private ArrayList<Enemy> enemies;

  public Level() {
    this.enemies = new ArrayList<>();
  }

  public Level(LevelMap levelMap, EnemySet enemySet) {
    this.levelMap = levelMap;

    enemies = new ArrayList<>();
    Tile[][][] tiles = levelMap.getTiles();
    for (int z = 0; z < tiles.length; z++) {
      for (int y = 0; y < tiles[z].length; y++) {
        for (int x = 0; x < tiles[z][y].length; x++) {
          Tile tile = tiles[z][y][x];
          switch (tile.getType()) {
            case SPAWN_WALKER:
              enemies.add(enemySet.get(0, new Vector3f(x + 0.5f, y + 0.5f, z)));
              break;
            case SPAWN_RANDOMER:
              enemies.add(enemySet.get(1, new Vector3f(x + 0.5f, y + 0.5f, z)));
              break;
            case SPAWN_CIRCLER:
              enemies.add(enemySet.get(2, new Vector3f(x + 0.5f, y + 0.5f, z)));
              break;
            case SPAWN_CHARGER:
              enemies.add(enemySet.get(3, new Vector3f(x + 0.5f, y + 0.5f, z)));
              break;
            default:
              break;
          }
        }
      }
    }
  }

  public void addEnemy(Enemy enemy) {
    this.enemies.add(enemy);
  }

  public LevelMap getLevelMap() {
    return levelMap;
  }

  public void setLevelMap(LevelMap levelMap) {
    this.levelMap = levelMap;
  }

  public ArrayList<Enemy> getEnemies() {
    return enemies;
  }
}
