package com.knightlore.AI;

import javafx.geometry.Point3D;

import java.awt.*;
import java.util.LinkedList;
// THIS CODE IS NOT USED ANYMORE!!!!!!!! AS OF 24/01/2019 TEAM MEETING
/*
The AI that controls the computer player character when doing single player.

Current approach:
    - Get start and end position
    - Get map data
    - A* search path
    - Create queue of actions
    - Execute actions
    - ???
    - Win


What happens when the computer AI gets hit?
    - while executing loop, check for events
    - if hit, reset, re-pathfind
    - execute

What happens if AI dies?
    - wait for respawn
    - re-pathfind
    - execute

    TODO: Do we have some sort of tile class?
*/
public class PlayerAI {

    // init open and closed queues
    private LinkedList<Point3D> openTiles;
    private LinkedList<Point3D> closedTiles;
    // init movement queue
    private LinkedList<Point3D> movementQueue;

    private int MAP_SIZE = 5;

    private void init() {
        // get 3D map as array
        [][][] gameMap = new int[5][5][5]; // placeholder
    }


    private void run() {
        while (!movementQueue.isEmpty()) { // something in still in path queue
            // check state of character: isStunned()/isLocked() or something??
            // otherwise
            // execute next path action in queue ie. move to next tile
        }
    }



    // Pathfinding method
    private LinkedList<Point3D> getMovementQueue() {
        Point3D start = getStartPos(); // Get start position
        Point3D goal = getGoalPos();    // Get end

        movementQueue = new LinkedList<Point3D>();

        openTiles = new LinkedList<Point3D>();
        closedTiles = new LinkedList<Point3D>();

        double[][][] costs = calcCosts(start, goal);

        // start loop here

        // Add current position to closed list
        Point3D current = getCurrentPos();
        closedTiles.add(current);


        // add walkable tiles to open list
        // TODO: if a tile is a wall, check if its climbable somehow?
        // TODO: are diagonals extra cost or no
        Point3D[] neighbours = getAdjacent(current);
        for (Point3D tile : neighbours) { // 8-directional movement so 8 possible options
            if (gameMap[tile.getX()][tile.getY()][tile.getZ()].isWalkable()) { // or .isWall, or getType?
                // if tile is accessible add to open list
                openTiles.add(tile);
            } else {
                //check if tile is a wall and the tile above is Not a wall/is walkable
                if (gameMap[tile.getX()][tile.getY()][tile.getZ()]).isWall() && !(gameMap[tile.getX()][tile.getY() + 1][tile.getZ()]) {
                    openTiles.add(Tile);
                }
            }
        }



        return movementQueue;
    }


    // TODO: take into account unwalkable tiles and climbing?
    private double[][][] calcCosts(Point3D start, Point3D goal) {
        double[][][] costMap = new double[MAP_SIZE][MAP_SIZE][MAP_SIZE];

        // Calculate costs for each tile in map
        for (int x = 0; x < MAP_SIZE; x++ ){
            for (int y = 0; y < MAP_SIZE; y++) {
                for (int z = 0; z < MAP_SIZE; z++){
                    double sCost = getStartCost(start, new Point3D (x,y,z));
                    double gCost = getGoalCost(new Point3D(x,y,z), goal);

                    costMap[x][y][z] = sCost + gCost;
                }
            }
        }

        return costMap;
    }

    // TODO: find out how the map is represented and how we store coordinates
    private double getStartCost(Point3D start, Point3D current) {
        double cost = current.distance(start);
        return cost;
    }

    private double getGoalCost(Point3D current, Point3D goal) {
        double cost = goal.distance(current);
        return cost;

    }


    private Point3D[] getAdjacent(Point3D point) {
        double x = point.getX();
        double y = point.getY();
        double z = point.getZ()

        Point3D[] neighbours = new Point3D[] {
                new Point3D(x-1,y+1,z),   new Point3D(x,y+1,z),    new Point3D(x+1,y+1,z),
                new Point3D(x-1, y, z),                                  new Point3D(x+1,y,z),
                new Point3D(x-1, y-1, z), new Point3D(x, y-1, z),  new Point3D(x+1, y-1, z)
        };
        return neighbours;

    }



}
