package com.knightlore.client.gui.engine.graphics;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * Loads a png and creates the texture
 *
 * @author Joseph
 */
public class Texture {

  /** Texture id */
  private final int id;
  /** Texture width */
  private final int width;
  /** Texture height */
  private final int height;

  /**
   * Create texture
   *
   * @param is Byte array
   * @throws IOException Exception
   * @author Joseph
   */
  public Texture(InputStream is) throws IOException {
    // Load Texture file
    PNGDecoder decoder = new PNGDecoder(is);

    this.width = decoder.getWidth();
    this.height = decoder.getHeight();

    // Load texture contents into a byte buffer
    ByteBuffer buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
    decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
    buf.flip();

    // Create a new OpenGL texture
    this.id = glGenTextures();

    // Bind the texture
    glBindTexture(GL_TEXTURE_2D, this.id);

    // Tell OpenGL how to unpack the RGBA bytes. Each component is 1 byte size
    glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

    // Upload the texture data
    glTexImage2D(
        GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);

    // Generate Mip Map
    glGenerateMipmap(GL_TEXTURE_2D);
  }

  /**
   * Return texture width
   *
   * @return Width
   * @author Joseph
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Return the texture height
   *
   * @return Height
   * @author Joseph
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Bind the texture
   *
   * @author Joseph
   */
  public void bind() {
    glBindTexture(GL_TEXTURE_2D, id);
  }

  /**
   * Return texture id
   *
   * @return Id
   * @author Joseph
   */
  public int getId() {
    return id;
  }

  /**
   * Delete the textures
   *
   * @author Joseph
   */
  public void cleanup() {
    glDeleteTextures(id);
  }
}
