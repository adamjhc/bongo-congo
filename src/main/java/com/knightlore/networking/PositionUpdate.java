package com.knightlore.networking;

import org.joml.Vector3f;

public class PositionUpdate {

    public Vector3f coordinates;
    public String sessionId;

    public PositionUpdate(Vector3f c, String sessionId) {
        this.coordinates = c;
        this.sessionId = sessionId;
    }
}
