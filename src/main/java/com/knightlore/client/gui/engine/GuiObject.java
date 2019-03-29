package com.knightlore.client.gui.engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import com.knightlore.client.gui.engine.graphics.Mesh;

/**
 * Object to render on the screen
 * 
 * @author Joseph
 *
 */
public class GuiObject {

	/** Object mesh */
	private Mesh mesh;
	/** Object position */
	private final Vector3f position;
	/** Object scale */
	private float scale;
	/** Object rotation */
	private final Vector3f rotation;
	/** Should object be rendered */
	private Boolean render;

	/**
	 * Initiates values to default
	 * 
	 * @author Joseph
	 * 
	 */
	public GuiObject() {
		position = new Vector3f(0, 0, 0);
		scale = 1;
		rotation = new Vector3f(0, 0, 0);
		render = true;
	}

	/**
	 * Initiates values to default
	 * Sets the mesh
	 * 
	 * @param mesh
	 * @author Joseph
	 * 	 
	 */
	public GuiObject(Mesh mesh) {
		this();
		this.mesh = mesh;
	}

	/**
	 * Returns if should render
	 * 
	 * @return Render
	 * @author Joseph
	 * 
	 */
	public boolean getRender() {
		return render;
	}

	/**
	 * Switches render boolean
	 * 
	 * @author Joseph
	 * 
	 */
	public void setRender() {
		this.render = !this.render;
	}

	/**
	 * Sets render
	 * 
	 * @param state New render state
	 * @author Joseph
	 * 
	 */
	public void setRender(boolean state) {
		this.render = state;
	}

	/**
	 * Returns the position
	 * 
	 * @return Position
	 * @author Joseph
	 * 
	 */
	public Vector3f getPosition() {
		return position;
	}

	/**
	 * Returns the x position
	 * 
	 * @return Xposition
	 * @author Joseph
	 * 
	 */
	public float getPositionX() {
		return position.x;
	}

	/**
	 * Returns the y position
	 * 
	 * @return Yposition
	 * @author Joseph
	 * 
	 */
	public float getPositionY() {
		return position.y;
	}

	/**
	 * Sets the x position
	 * 
	 * @param x The new x position
	 * @author Joseph
	 * 
	 */
	public void setPositionX(float x) {
		this.position.x = x;
	}

	/**
	 * Sets the y position
	 * 
	 * @param y The new y position
	 * @author Joseph
	 * 
	 */
	public void setPositionY(float y) {
		this.position.y = y;
	}

	/**
	 * Sets the position
	 * 
	 * @param x The new x position
	 * @param y The new y position
	 */
	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = 0f;
	}

	/**
	 * Returns the scale
	 * 
	 * @return Scale
	 * @author Joseph
	 * 
	 */
	public float getScale() {
		return scale;
	}

	/**
	 * Sets the scale
	 * 
	 * @param scale The new scale
	 * @author Joseph
	 * 
	 */
	public void setScale(float scale) {
		this.scale = scale;
	}

	/**
	 * Returns the rotation
	 * 
	 * @return Rotation
	 * @author Joseph
	 * 
	 */
	public Vector3f getRotation() {
		return rotation;
	}

	/**
	 * Sets the rotation
	 * 
	 * @param x The x rotation
	 * @param y The y rotation
	 * @param z The z rotation
	 * @author Joseph
	 * 
	 */
	public void setRotation(float x, float y, float z) {
		this.rotation.x = x;
		this.rotation.y = y;
		this.rotation.z = z;
	}

	/**
	 * Returns the mesh
	 * 
	 * @return Mesh
	 * @author Joseph
	 * 
	 */
	public Mesh getMesh() {
		return mesh;
	}

	/**
	 * Sets the mesh
	 * 
	 * @param mesh The new mesh
	 * @author Joseph
	 * 
	 */
	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}

	/**
	 * Returns the colour
	 * 
	 * @return Colour
	 * @author Joseph
	 * 
	 */
	public Vector4f getColour() {
		return mesh.getMaterial().getColour();
	}

	/**
	 * Sets the colour
	 * 
	 * @param colour The new colour
	 * @author Joseph
	 * 
	 */
	public void setColour(Vector4f colour) {
		mesh.getMaterial().setColour(colour);
	}

	/**
	 * Sets the colour to default
	 * 
	 * @author Joseph
	 * 
	 */
	public void setColour() {
		mesh.getMaterial().setColour();
	}
}