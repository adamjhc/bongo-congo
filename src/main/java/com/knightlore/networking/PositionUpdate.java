package com.knightlore.networking;

import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.PlayerState;
import org.joml.Vector3f;

public class PositionUpdate {

    public Vector3f coordinates;
    public String sessionId;
    public Direction direction;
    public PlayerState state;

    public PositionUpdate(Vector3f c, String sessionId, Direction direction, PlayerState state) {
        this.coordinates = c;
        this.sessionId = sessionId;
        this.direction = direction;
        this.state = state;
    }
}
