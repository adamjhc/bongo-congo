package com.knightlore.game.entity.ai;

import com.knightlore.game.math.Vector3f;

import java.util.LinkedList;
import java.util.List;

/* Generic Enemy AI
   Requirements:
        - Only walk on floor tiles
        - Only stay on level they were spawned
        - Reverse direction when hits a wall

   For now, default enemy doesn't move from their spawn
*/
public abstract class EnemyAI {

    public List<Vector3f> getPath() {
        return new LinkedList<Vector3f>(); // TODO: confirm what the 'coordinates' are
    };
}
