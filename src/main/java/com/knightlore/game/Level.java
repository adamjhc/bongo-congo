package com.knightlore.game;

import com.knightlore.client.networking.GameConnection;
import com.knightlore.game.entity.Enemy;
import com.knightlore.game.entity.Player;
import com.knightlore.game.map.LevelMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Level {

  private LevelMap levelMap;
  private ArrayList<Enemy> enemies;

  Date startedAt;
  public int duration;

  public Level() {
    this.enemies = new ArrayList<>();
  }

  public Level(LevelMap levelMap) {
    this.levelMap = levelMap;

    enemies = new ArrayList<>();
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
