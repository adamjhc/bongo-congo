package com.knightlore.client.render;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import com.knightlore.client.util.FileUtils;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;

public class Texture {

  private static final String texturePathPrefix = "./src/main/resources/textures/";

  private final int id;
  private int width, height;

  public Texture(String fileName) {
    Image image = FileUtils.loadImage(texturePathPrefix + fileName);
    width = image.getWidth();
    height = image.getHeight();
    int[] pixels = image.getPixels();

    ByteBuffer pixelBuffer = BufferUtils.createByteBuffer(width * height * 4);

    for (int i = 0; i < width * height; i++) {
      pixelBuffer.put((byte) ((pixels[i] >> 24) & 0xFF)); // ALPHA
      pixelBuffer.put((byte) ((pixels[i] >> 16) & 0xFF)); // RED
      pixelBuffer.put((byte) ((pixels[i] >> 8) & 0xFF)); // GREEN
      pixelBuffer.put((byte) (pixels[i] & 0xFF)); // BLUE
    }
    pixelBuffer.flip();

    id = glGenTextures();

    bind();

    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

    glTexImage2D(
        GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixelBuffer);

    unbind();
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public void bind(int sampler) {
    if (sampler >= 0 && sampler <= 31) {
      glActiveTexture(GL_TEXTURE0 + sampler);
      bind();
    }
  }

  protected void finalize() throws Throwable {
    glDeleteTextures(id);
    super.finalize();
  }

  private void bind() {
    glBindTexture(GL_TEXTURE_2D, id);
  }

  private void unbind() {
    glBindTexture(GL_TEXTURE_2D, 0);
  }
}
