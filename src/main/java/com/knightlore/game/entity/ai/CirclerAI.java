package com.knightlore.game.entity.ai;


import com.knightlore.game.entity.Direction;
import org.joml.Vector3f;



import static com.knightlore.game.entity.Direction.*;

// Walks around in a circle of a set radius
public class CirclerAI {
  public static float radius = 70f;
  public static int angle; // in degrees
  private Vector3f home;

  public CirclerAI() {
    angle = 0;
  }

    public Vector3f pathfind(Vector3f pos, float delta) {
      Vector3f newPos = new Vector3f();
      if (angle < 360) {
        newPos.x = home.x + (float)((Math.cos(Math.toRadians(angle)) * radius * delta));
        newPos.y = home.y + (float)((Math.sin(Math.toRadians(angle)) * radius * delta));
        angle += 2;
      } else {
        angle = 0;
      }
      return newPos;}

      public Direction getDirection() {
        if (angle >= 0 && angle < 45) {
          return SOUTH_WEST;
        } else if (angle >= 45 && angle < 90) {
          return SOUTH;
        } else if (angle >= 90 && angle < 135) {
          return SOUTH_EAST;
        } else if (angle >= 135 && angle < 180) {
          return EAST;
        } else if (angle >= 180 && angle < 225) {
          return NORTH_EAST;
        } else if (angle >= 225 && angle < 270) {
          return NORTH;
        } else if (angle >= 270 && angle < 315) {
          return NORTH_WEST;
        } else { return WEST; }


        }
        public void setHome(Vector3f home) {this.home = home;}
  }