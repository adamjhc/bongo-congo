package com.knightlore.client.render.opengl;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

import java.util.ArrayList;
import java.util.List;

public class AnimatedTexture implements Texture {

  /** Frames of the animation */
  private List<StaticTexture> frames;

  /** The index of the current texture to render */
  private int currentTextureIndex;

  /** Amount of time passed since the texture was changed last */
  private double delta;

  /** The last system time when the texture was updated */
  private double previousTime;

  /** The target amount of time between each frame update */
  private double interval;

  private boolean loop;

  public AnimatedTexture(String textureFileName, int noOfFrames, int fps) {
    this(textureFileName, noOfFrames, fps, true);
  }

  /**
   * Initialise the Animated texture
   *
   * @param textureFileName Name of the texture prefix (with _X)
   * @param noOfFrames Number of frames in the animation
   * @param fps Number of frames to render every second
   */
  public AnimatedTexture(String textureFileName, int noOfFrames, int fps, boolean loop) {
    frames = new ArrayList<>(noOfFrames);
    for (int i = 0; i < noOfFrames; i++) {
      frames.add(i, new StaticTexture(textureFileName + "_" + i));
    }

    currentTextureIndex = 0;
    delta = 0;
    previousTime = glfwGetTime();
    interval = 1.0 / fps;

    this.loop = loop;
  }

  /**
   * Get the width of the first texture in pixels
   *
   * @return Width of the first texture in pixels
   */
  @Override
  public int getWidth() {
    return frames.get(0).getWidth();
  }

  /**
   * Get the height of the first texture in pixels
   *
   * @return Height of the first texture in pixels
   */
  @Override
  public int getHeight() {
    return frames.get(0).getHeight();
  }

  /**
   * Calculate the current frame to bind and binds it
   *
   * @param sampler Sampler of the texture
   */
  @Override
  public void bind(int sampler) {
    if (currentTextureIndex < frames.size() - 1) {
      double currentTime = glfwGetTime();
      delta += currentTime - previousTime;
      previousTime = currentTime;

      if (delta >= interval) {
        delta = 0;
        currentTextureIndex++;
      }

      if (currentTextureIndex == frames.size() - 1 && loop) {
        currentTextureIndex = 0;
      }
    }

    frames.get(currentTextureIndex).bind(sampler);
  }

  public void reset() {
    currentTextureIndex = 0;
  }

  public void cleanup() {
    frames.forEach(StaticTexture::cleanup);
  }
}
