package com.knightlore.networking.game;

import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.PlayerState;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Player position update, update location, direction, state and score
 *
 * @author Lewis Relph
 */
public class PositionUpdateChunk {

    Queue<PositionUpdate> queue = new ConcurrentLinkedQueue<>();

    public PositionUpdateChunk() {
        this.queue = new ConcurrentLinkedQueue<>();
    }

    public void add(PositionUpdate u){
        boolean used = false;
        for(PositionUpdate current : queue){
            if(current.sessionId.equals(u.sessionId)){
                current = u;
                used = true;
                break;
            }
        }

        if(!used){
            queue.add(u);
        }
    }

    public void clear(){
        this.queue.clear();
    }

    public Queue<PositionUpdate> getQueue(){
        return queue;
    }
}
