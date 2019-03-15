package com.knightlore.game.entity;

import java.util.ArrayList;
import org.joml.Vector3f;

public class EnemySet {

  private ArrayList<Enemy> set;

  public EnemySet() {
    set = new ArrayList<>();
    set.add(new Enemy());
    set.add(new Enemy());
    set.add(new Enemy());
    set.add(new Enemy());
  }

  public Enemy get(int index, Vector3f position) {
    Enemy enemy = new Enemy(set.get(index));
    enemy.setPosition(position);
    return enemy;
  }
}
