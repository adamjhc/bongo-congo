package com.knightlore.client.render.opengl;

public class Image {

  /** Width of the image */
  private int width;

  /** Height of the image */
  private int height;

  /** Raw pixel array */
  private int[] pixels;

  /**
   * Initialise Image
   *
   * @param width Width of the image
   * @param height Height of the image
   * @param pixels Raw pixel array
   */
  public Image(int width, int height, int[] pixels) {
    this.width = width;
    this.height = height;
    this.pixels = pixels;
  }

  /**
   * Get the width of the image
   *
   * @return Width of the image
   */
  public int getWidth() {
    return width;
  }

  /**
   * Get the height of the image
   *
   * @return height of the image
   */
  public int getHeight() {
    return height;
  }

  /**
   * Get the raw pixel array
   *
   * @return Raw pixel array
   */
  public int[] getPixels() {
    return pixels;
  }
}
