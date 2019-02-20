package com.knightlore.client.hud.engine.graphics;

import org.joml.Vector4f;

public class Material {

    private static final Vector4f DEFAULT_COLOUR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

    private Vector4f colour;

    private Texture texture;

    public Material() {
        this.colour = DEFAULT_COLOUR;
        this.texture = null;
    }

    public Material(Vector4f colour) {
        this(colour, null);
    }

    public Material(Texture texture) {
        this(DEFAULT_COLOUR, texture);
    }

    public Material(Vector4f colour, Texture texture) {
        this.colour = colour;
        this.texture = texture;
    }

    public Vector4f getColour() {
        return colour;
    }

    public void setColour(Vector4f ambientColour) {
        this.colour = ambientColour;
    }

    public boolean isTextured() {
        return this.texture != null;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

}