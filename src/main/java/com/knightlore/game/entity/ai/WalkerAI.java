package com.knightlore.game.entity.ai;

import com.knightlore.game.math.Vector3f;
import org.lwjgl.system.CallbackI;

import java.util.LinkedList;
import java.util.List;

// Walks back and forth on the same plane
public class WalkerAI extends EnemyAI {

    Object startDirection; // TODO: need to decide how we implement this


    public WalkerAI(Vector3f home, Object startDirection) {
        this.home = home;
        this.startDirection = startDirection;
    }


    @Override
    public List<Vector3f> pathfind() {

        LinkedList<Vector3f> path = new LinkedList<Vector3f>();

        // some maths to get direction i guess
        //-------------------------------------------------------------------------
        // do (add tile to list if not a wall else get the other direction ??)
        // while (list.peek(!home))
        //      get next tile up/down
        //      if (tile not wall)
        //          add to list
        //      else
        //          reverse direction, continue
        //-------------------------------------------------------------------------
        // ^ do this twice to get the whole path (?)
        // ???
        // profit

        return path;
    }
}
