package com.knightlore.client.render.opengl;

public class Image {
  private int width;
  private int height;
  private int[] pixels;

  public Image(int width, int height, int[] pixels) {
    this.width = width;
    this.height = height;
    this.pixels = pixels;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int[] getPixels() {
    return pixels;
  }
}
