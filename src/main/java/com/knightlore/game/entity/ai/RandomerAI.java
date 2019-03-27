package com.knightlore.game.entity.ai;

import java.util.List;
import org.joml.Vector3f;

// Walks in a random direction for a set distance before changing direction and continuing
public class RandomerAI extends EnemyAI {

  public float distance;

  public RandomerAI(Vector3f home, float distance) {
    this.home = home;
    this.distance = distance;
  }
 }