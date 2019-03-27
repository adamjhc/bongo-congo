package com.knightlore.client.gui.engine.graphics;

import org.joml.Vector4f;

/**
 * Holds the texture and colour for an object
 * 
 * @author Joseph
 *
 */
public class Material {

	/** The default colour (white) */
	private static final Vector4f DEFAULT_COLOUR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
	/** The material colour */
	private Vector4f colour;
	/** The texture associated with the material */
	private Texture texture;

	/**
	 * Initialise values to default
	 * 
	 * @author Joseph
	 * 
	 */
	public Material() {
		this.colour = DEFAULT_COLOUR;
		this.texture = null;
	}

	/**
	 * Initialise values given colour
	 * 
	 * @param colour
	 * @author Joseph
	 * 
	 */
	public Material(Vector4f colour) {
		this(colour, null);
	}

	/**
	 * Initialise values given texture
	 * 
	 * @param colour
	 * @author Joseph
	 * 
	 */
	public Material(Texture texture) {
		this(DEFAULT_COLOUR, texture);
	}

	/**
	 * Initialise values given texture and colour
	 * 
	 * @param colour
	 * @author Joseph
	 * 
	 */
	public Material(Vector4f colour, Texture texture) {
		this.colour = colour;
		this.texture = texture;
	}

	/**
	 * Return colour
	 * 
	 * @return Colour
	 * @author Joseph
	 * 
	 */
	public Vector4f getColour() {
		return colour;
	}

	/**
	 * Sets the colour
	 * 
	 * @param colour The new colour
	 * @author Joseph
	 * 
	 */
	public void setColour(Vector4f colour) {
		this.colour = colour;
	}

	/**
	 * Sets the colour to the default value
	 * 
	 * @author Joseph
	 * 
	 */
	public void setColour() {
		this.colour = DEFAULT_COLOUR;
	}

	/**
	 * Returns if the material is textured or not
	 * 
	 * @return Is textured
	 * @author Joseph
	 * 
	 */
	public boolean isTextured() {
		return this.texture != null;
	}

	/**
	 * Returns the texture
	 * 
	 * @return Texture
	 * @author Joseph
	 * 
	 */
	public Texture getTexture() {
		return texture;
	}

	/**
	 * Sets the texture
	 * 
	 * @param texture The new texture
	 * @author Joseph
	 * 
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
	}

}