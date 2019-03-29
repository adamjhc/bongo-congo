package com.knightlore.client.render;

import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;

/**
 * Abstract Renderer to make sure OpenGL is setup
 *
 * @author Adam Cox
 */
public abstract class Renderer {

  /** Whether OpenGL has been setup */
  private static boolean hasSetupOpenGL = false;

  /**
   * Initialise Renderer
   *
   * @author Adam Cox
   */
  Renderer() {
    if (!hasSetupOpenGL) {
      setupOpenGL();
      hasSetupOpenGL = true;
    }
  }

  /**
   * Setup OpenGL for LWJGL
   *
   * @author Adam Cox
   */
  private void setupOpenGL() {
    createCapabilities();
    glEnable(GL_TEXTURE_2D);
    glEnable(GL_MULTISAMPLE);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
  }

  /** Abstract memory cleanup */
  protected abstract void cleanup();
}
