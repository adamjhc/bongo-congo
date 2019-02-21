package com.knightlore.game;

import com.knightlore.game.entity.Enemies;
import com.knightlore.game.entity.Player;
import com.knightlore.game.map.Map;
import java.util.ArrayList;
import java.util.Date;

public class Level {

  Map map;

  ArrayList<Player> players;
  ArrayList<Enemies> enemies;

  Date startedAt;
  int duration;

  public Level() {
    this.enemies = new ArrayList<>();
    this.players = new ArrayList<>();
  }

  public Level(Map map, ArrayList<Player> players) {
    this.map = map;
    this.players = players;
  }

  public void addEnemy(Enemies enemy) {
    this.enemies.add(enemy);
  }

  public Map getMap() {
    return map;
  }

  public void setMap(Map map) {
    this.map = map;
  }

  public ArrayList<Player> getPlayers() {
    return players;
  }

  public ArrayList<Enemies> getEnemies() {
    return enemies;
  }
}
