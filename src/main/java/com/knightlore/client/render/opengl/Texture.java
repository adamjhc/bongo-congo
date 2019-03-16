package com.knightlore.client.render.opengl;

/**
 * Texture interface used in GameObject
 *
 * @author Adam Cox
 */
public interface Texture {

  /**
   * Get the width of the texture in pixels
   *
   * @return width of the texture in pixels
   * @author Adam Cox
   */
  int getWidth();

  /**
   * Get the height of the texture in pixels
   *
   * @return height of the texture in pixels
   * @author Adam Cox
   */
  int getHeight();

  /**
   * Bind the texture to OpenGL
   *
   * @param sampler sampler used in shader
   * @author Adam Cox
   */
  void bind(int sampler);

  /**
   * Memory cleanup of texture
   *
   * @author Adam Cox
   */
  void cleanup();
}
