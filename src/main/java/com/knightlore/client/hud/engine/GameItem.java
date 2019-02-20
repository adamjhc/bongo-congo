package com.knightlore.client.hud.engine;

import org.joml.Vector3f;

import com.knightlore.client.hud.engine.graphics.Mesh;

public class GameItem {

    private Mesh mesh;
    
    private final Vector3f position;
    
    private float scale;

    public GameItem() {
        position = new Vector3f(0, 0, 0);
        scale = 1;
    }
    
    public GameItem(Mesh mesh) {
        this();
        this.mesh = mesh;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Mesh getMesh() {
        return mesh;
    }
    
    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }
}