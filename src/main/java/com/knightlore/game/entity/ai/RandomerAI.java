package com.knightlore.game.entity.ai;

import org.joml.Vector3f;

import java.util.List;

// Walks in a random direction for a set distance before changing direction and continuing
public class RandomerAI extends EnemyAI{

    public float distance;
    
    public RandomerAI(Vector3f home, float distance) {
        this.home = home;
        this.distance = distance;
    }

    @Override
    public List<Vector3f> pathfind() {
        return null;
    }
}
