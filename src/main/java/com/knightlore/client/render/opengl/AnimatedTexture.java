package com.knightlore.client.render.opengl;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class AnimatedTexture extends Texture {
  private StaticTexture[] frames;
  private int textureIndex;

  private double delta;
  private double currentTime;
  private double previousTime;
  private double interval;

  public AnimatedTexture(String textureFileName, int noOfFrames, int fps) {
    frames = new StaticTexture[noOfFrames];
    for (int i = 0; i < noOfFrames; i++) {
      frames[i] = new StaticTexture(textureFileName + "_" + i);
    }

    textureIndex = 0;
    delta = 0;
    currentTime = 0;
    previousTime = glfwGetTime();
    interval = 1.0 / fps;
  }

  @Override
  public int getWidth() {
    return frames[0].getWidth();
  }

  @Override
  public int getHeight() {
    return frames[0].getHeight();
  }

  @Override
  public void bind(int sampler) {
    this.currentTime = glfwGetTime();
    this.delta += currentTime - previousTime;

    if (delta >= interval) {
      delta = 0;
      textureIndex++;
    }

    if (textureIndex >= frames.length) {
      textureIndex = 0;
    }

    previousTime = currentTime;

    frames[textureIndex].bind(sampler);
  }
}
