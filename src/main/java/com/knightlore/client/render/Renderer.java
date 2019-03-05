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

import com.knightlore.client.gui.engine.Window;

public abstract class Renderer {

  private static boolean hasSetupOpenGL = false;

  protected Window window;

  Renderer(Window window) {
    this.window = window;

    if (!hasSetupOpenGL) {
      setupOpenGL();
      hasSetupOpenGL = true;
    }
  }

  private void setupOpenGL() {
    createCapabilities();
    glEnable(GL_TEXTURE_2D);
    glEnable(GL_MULTISAMPLE);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
  }

  void clearBuffers() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
  }

  void swapBuffers() {
    window.swapBuffers();
  }

  protected abstract void cleanup();
}
