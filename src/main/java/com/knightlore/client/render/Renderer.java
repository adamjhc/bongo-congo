package com.knightlore.client.render;

import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
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
