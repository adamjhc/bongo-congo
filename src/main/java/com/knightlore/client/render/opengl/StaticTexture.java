package com.knightlore.client.render.opengl;

import com.knightlore.client.util.FileUtils;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

/**
 * Single framed texture used in GameObject
 *
 * @author Adam Cox
 */
public class StaticTexture implements Texture {

  /** Path to the textures directory */
  private static final String TEXTURE_PATH_PREFIX = "./src/main/resources/textures/";

  /** File extension of images */
  private static final String FILE_EXTENSION = ".png";

  /** The OpenGL id of the texture */
  private final int id;

  /** Width in pixels of the texture */
  private int width;

  /** Height in pixels of the texture */
  private int height;

  /**
   * Initialise the texture object
   *
   * @param fileName Name of the texture file
   * @author Adam Cox
   */
  public StaticTexture(String fileName) {
    Image image = FileUtils.loadTexture(TEXTURE_PATH_PREFIX + fileName + FILE_EXTENSION);
    width = image.getWidth();
    height = image.getHeight();

    id = glGenTextures();

    bind();

    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

    glTexImage2D(
        GL_TEXTURE_2D,
        0,
        GL_RGBA,
        width,
        height,
        0,
        GL_RGBA,
        GL_UNSIGNED_BYTE,
        createPixelBuffer(image.getPixels()));

    unbind();
  }

  /**
   * Get the width of the texture in pixels
   *
   * @return Width of the texture in pixels
   * @author Adam Cox
   */
  @Override
  public int getWidth() {
    return width;
  }

  /**
   * Get the height of the texture in pixels
   *
   * @return Height of the texture in pixels
   * @author Adam Cox
   */
  @Override
  public int getHeight() {
    return height;
  }

  /**
   * Binds the texture to the current object and activates
   *
   * @param sampler Sampler of the texture
   * @author Adam Cox
   */
  @Override
  public void bind(int sampler) {
    if (sampler >= 0 && sampler <= 31) {
      glActiveTexture(GL_TEXTURE0 + sampler);
      bind();
    }
  }

  /**
   * Cleans up memory
   *
   * @author Adam Cox
   */
  public void cleanup() {
    glDeleteTextures(id);
  }

  /**
   * Binds the texture to the current object
   *
   * @author Adam Cox
   */
  private void bind() {
    glBindTexture(GL_TEXTURE_2D, id);
  }

  /**
   * Separates the individual colour streams and populates a buffer with them
   *
   * @param pixels Raw pixel array
   * @return ByteBuffer
   * @author Adam Cox
   */
  private ByteBuffer createPixelBuffer(int[] pixels) {
    ByteBuffer pixelBuffer = BufferUtils.createByteBuffer(width * height * 4);
    for (int i = 0; i < width * height; i++) {
      pixelBuffer.put((byte) ((pixels[i] >> 16) & 0xFF)); // RED
      pixelBuffer.put((byte) ((pixels[i] >> 8) & 0xFF)); // GREEN
      pixelBuffer.put((byte) (pixels[i] & 0xFF)); // BLUE
      pixelBuffer.put((byte) ((pixels[i] >> 24) & 0xFF)); // ALPHA
    }
    pixelBuffer.flip();

    return pixelBuffer;
  }

  /**
   * Unbinds the textures
   *
   * @author Adam Cox
   */
  private void unbind() {
    glBindTexture(GL_TEXTURE_2D, 0);
  }
}
