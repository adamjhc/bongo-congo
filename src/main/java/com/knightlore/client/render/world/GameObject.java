package com.knightlore.client.render.world;

import com.knightlore.client.render.opengl.RenderModel;
import com.knightlore.game.util.CoordinateUtils;
import org.joml.Vector3f;

/**
 * Abstract GameObject class used for all renderable game objects
 *
 * @author Adam Cox
 */
public abstract class GameObject {

  /** Coordinates to used for mapping the texture to the VBO */
  static final float[] textureCoordinates =
      new float[] {
        0, 0,
        1, 0,
        1, 1,
        0, 1,
      };

  /** Vertex indices used in the render model */
  static final int[] indices =
      new int[] {
        0, 1, 2,
        2, 3, 0
      };

  /** Render model used for rendering */
  protected RenderModel model;

  /** Isometric position of the game object in the rendered world */
  Vector3f isometricPosition;

  /** Position of the game object as it is in the game model */
  private Vector3f modelPosition;

  /**
   * Get the isometric position of the game object
   *
   * @return isometric position of the game object
   * @author Adam Cox
   */
  public Vector3f getIsometricPosition() {
    return isometricPosition;
  }

  /**
   * Set the isometric position of the game object
   *
   * @param isometricPosition New isometric position
   * @author Adam Cox
   */
  public void setIsometricPosition(Vector3f isometricPosition) {
    this.isometricPosition = isometricPosition;
  }

  /**
   * Get the model position of the game object
   *
   * @return model position of the game object
   * @author Adam Cox
   */
  public Vector3f getModelPosition() {
    return modelPosition;
  }

  /**
   * Sets the model and isometric position of the game object
   *
   * @param modelPosition New model position
   * @author Adam Cox
   */
  public void setPosition(Vector3f modelPosition) {
    this.modelPosition = modelPosition;
    isometricPosition = CoordinateUtils.toIsometric(modelPosition);
  }

  /**
   * Abstract method for memory cleanup
   *
   * @author Adam Cox
   */
  public abstract void cleanup();
}
