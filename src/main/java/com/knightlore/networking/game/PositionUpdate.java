package com.knightlore.networking.game;

import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.PlayerState;
import org.joml.Vector3f;

/**
 * Player position update, update location, direction, state and score
 *
 * @author Lewis Relph
 */
public class PositionUpdate {

    public Vector3f coordinates;
    public String sessionId;
    public Direction direction;
    public PlayerState state;
    public int score;

    public PositionUpdate(Vector3f c, String sessionId, Direction direction, PlayerState state, int score) {
        this.coordinates = c;
        this.sessionId = sessionId;
        this.direction = direction;
        this.state = state;
        this.score = score;
    }
}
