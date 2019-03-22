package com.knightlore.game.entity;

import java.util.ArrayList;
import org.joml.Vector3f;

public class EnemySet {

  private ArrayList<Enemy> set;

  public EnemySet() {
    set = new ArrayList<>();
    set.add(new Enemy(EnemyType.WALKER));
    set.add(new Enemy(EnemyType.RANDOMER));
    set.add(new Enemy(EnemyType.CIRCLER));
    set.add(new Enemy(EnemyType.CHARGER));
  }

  public Enemy get(int index, Vector3f position) {
    Enemy enemy = new Enemy(set.get(index));
    enemy.setPosition(position);
    return enemy;
  }
}
