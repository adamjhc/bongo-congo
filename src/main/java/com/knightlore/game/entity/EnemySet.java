package com.knightlore.game.entity;

import org.joml.Vector3f;

import java.util.ArrayList;

public class EnemySet {

  private ArrayList<Enemy> set;

  public EnemySet() {
    set = new ArrayList<>();
    set.add(new Enemy(EnemyType.WALKER));
    set.add(new Enemy(EnemyType.RANDOMER));
    set.add(new Enemy(EnemyType.CIRCLER));
  }

  public Enemy get(int index, Vector3f position) {
    Enemy enemy = new Enemy(set.get(index));
    enemy.setPosition(position);
    return enemy;
  }
}
