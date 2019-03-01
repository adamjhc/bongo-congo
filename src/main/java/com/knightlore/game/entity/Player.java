package com.knightlore.game.entity;

import com.knightlore.client.networking.GameConnection;
import com.knightlore.game.map.Map;
import com.knightlore.game.map.Tile;
import com.knightlore.game.util.CoordinateUtils;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Player extends Entity {

  private static int inc = 0;
  private int lives;
  private int score;
  private PlayerState playerState;
  private String associatedSession;

  public Player() {
    id = inc;
    inc++;

    direction = Direction.SOUTH;
    position = new Vector3f(1, 1, 0);
    playerState = PlayerState.IDLE;

    lives = 3;
    score = 0;
  }

  public Player(String sessionID) {
    this();
    this.associatedSession = sessionID;
  }

  @Override
  public Vector3f getPosition() {
    return position;
  }

  @Override
  void update() {}

  public void update(Vector3f oldPos, Vector3f newPos, Map map) {
    Vector3i coords = CoordinateUtils.getTileCoord(setPadding(newPos));

    try {
      Tile newTile = map.getTile(coords);
      if (!newTile.isWalkable()) {
        setPosition(oldPos);
      } else {
        if (GameConnection.instance != null) {
          GameConnection.instance.updatePosition(position);
        }
        setPosition(newPos);
      }
      // catches SW and SE edges    catches NE and NW edges
    } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
      setPosition(oldPos);
    }
  }

  private Vector3f setPadding(Vector3f pos) {
    Vector3f padded = new Vector3f();
    direction.getNormalisedDirection().mul(0.4f, padded);
    pos.add(padded, padded);
    return padded;
  }

  public PlayerState getPlayerState() {
    return playerState;
  }

  public void setPlayerState(PlayerState playerState) {
    this.playerState = playerState;
  }
}
