package com.knightlore.networking.game;

import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.PlayerState;
import org.joml.Vector3f;

import java.util.ArrayList;

/**
 * Player position update, update location, direction, state and score
 *
 * @author Lewis Relph
 */
public class PositionUpdateChunk {

    ArrayList<PositionUpdate> queue;

    public PositionUpdateChunk() {
        this.queue = new ArrayList<>();
    }

    public void add(PositionUpdate u){
        queue.add(u);
    }

    public void clear(){
        this.queue.clear();
    }

    public ArrayList<PositionUpdate> getQueue(){
        return queue;
    }
}
