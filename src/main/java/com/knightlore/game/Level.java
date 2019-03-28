package com.knightlore.game;

import com.knightlore.game.entity.Enemy;
import com.knightlore.game.entity.EnemySet;
import com.knightlore.game.map.LevelMap;
import com.knightlore.game.map.Tile;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joml.Vector3f;

public class Level {

  private int duration;
  Date startedAt;
  private LevelMap levelMap;

  private EnemySet enemySet;
  private ArrayList<Enemy> enemies;
  private int enemyIdInc;

  public Level(LevelMap levelMap) {
    this.levelMap = levelMap;

    enemySet = new EnemySet();
    enemies = new ArrayList<>();
    enemyIdInc = 0;
    duration = 60;

    Tile[][][] tiles = levelMap.getTiles();
    for (int z = 0; z < tiles.length; z++) {
      for (int y = 0; y < tiles[z].length; y++) {
        for (int x = 0; x < tiles[z][y].length; x++) {
          Tile tile = tiles[z][y][x];
          if (tile.getType().ordinal() >= 6) {
            addEnemy(tile.getType().ordinal() - 6, new Vector3f(x + 0.5f, y + 0.5f, z));
          }
        }
      }
    }
  }

  public int getDuration() {
    return duration;
  }

  public LevelMap getLevelMap() {
    return levelMap;
  }

  public void setLevelMap(LevelMap levelMap) {
    this.levelMap = levelMap;
  }

  public List<Enemy> getEnemies() {
    return enemies;
  }

  private void addEnemy(int enemySetIndex, Vector3f position) {
    Enemy enemy = enemySet.get(enemySetIndex, position);
    enemy.setHome(position);
    enemy.setId(enemyIdInc);
    enemyIdInc++;
    enemies.add(enemy);
  }
}
