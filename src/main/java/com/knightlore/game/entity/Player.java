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
    void update() {

    }

    public void update(Vector3f oldPos, Vector3f newPos, Map map) {
        Vector3i coords = CoordinateUtils.getTileCoord(newPos);

        try {
            Tile newTile = map.getTile(coords);
            if (!newTile.isWalkable()) {
              System.out.println("BLOCK Iso:" +  newPos + ", 2D:" + coords);
              setPosition(oldPos);
            } else {
              setPosition(newPos);
            }
        } catch (NullPointerException e) {
            System.out.println("Null: Iso: " + newPos + "2D: " + coords);
            setPosition(oldPos);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array: " + coords + e.getMessage());
            setPosition(oldPos);
        }
    }
}
