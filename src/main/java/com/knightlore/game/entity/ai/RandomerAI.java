package com.knightlore.game.entity.ai;

import com.knightlore.game.math.Vector3f;

import java.util.List;

// Walks in a random direction for a set distance before changing direction and continuing
public class RandomerAI extends EnemyAI{
    public float distance;

    @Override
    public List<Vector3f> getPath() {
        return null;
    }
}
