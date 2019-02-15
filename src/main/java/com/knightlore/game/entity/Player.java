package com.knightlore.game.entity;

import com.knightlore.game.map.Map;
import com.knightlore.game.map.Tile;
import com.knightlore.game.util.CoordinateUtils;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Player extends Entity {

  private static int inc = 0;

  public int lives;
  public int score;

  public Player() {
    id = inc;
    inc++;

    direction = Direction.SOUTH;
    position = new Vector3f(1, 1, 0);

    lives = 3;
    score = 0;
  }

    @Override
    public Vector3f getPosition() {
      return position;
    }

    @Override
    void update() {

    }

    public void update(Vector3f oldPos, Vector3f newPos, Map map) {
        Vector3i coords = CoordinateUtils.getTileCoord(setPadding(newPos));

        try {
            Tile newTile = map.getTile(coords);
            if (!newTile.isWalkable()) {
              setPosition(oldPos);
            } else {
              setPosition(newPos);
            }
        } catch (NullPointerException e) { // catches SW and SE edges
            setPosition(oldPos);

        } catch (ArrayIndexOutOfBoundsException e) { //  catches NE and NW edges
            setPosition(oldPos);
        }
    }

    private Vector3f setPadding(Vector3f npos) {
      Vector3f pos = new Vector3f(npos);
      switch (this.direction){
          case NORTH_EAST:
              pos.set(pos.x + 0.7f,pos.y + 0.7f,pos.z);
              break;
          case EAST:
              pos.set(pos.x + 0.7f,pos.y + 0.7f ,pos.z);
              break;
          case SOUTH_EAST:
              pos.set(pos.x,pos.y + 0.7f, pos.z);
              break;
          case NORTH_WEST:
              pos.set(pos.x + 0.7f, pos.y, pos.z);
              break;
          case NORTH:
              pos.set(pos.x + 0.7f,pos.y + 0.7f, pos.z);
              break;
      }
      return pos;
    }
}
