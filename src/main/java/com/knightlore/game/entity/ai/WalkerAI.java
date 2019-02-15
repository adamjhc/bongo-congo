package com.knightlore.game.entity.ai;


import com.knightlore.game.entity.Direction;
import org.joml.Vector3f;

import java.util.LinkedList;
import java.util.List;

// Walks back and forth on the same plane
public class WalkerAI extends EnemyAI {

    Object startDirection; 


    public WalkerAI(Vector3f home, Direction startDirection) {
        this.home = home;
        this.startDirection = startDirection;
    }


    @Override
    public List<Vector3f> pathfind() {

        LinkedList<Vector3f> path = new LinkedList<Vector3f>();


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
