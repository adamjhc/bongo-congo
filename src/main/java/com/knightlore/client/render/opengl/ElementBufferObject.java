package com.knightlore.client.render.opengl;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.*;

/**
 * EBO used to store vertex indices for OpenGL
 *
 * @author Adam Cox
 */
public class ElementBufferObject {

  /** OpenGL id of the EBO */
  private final int id;

  /**
   * Initialise EBO
   *
   * @author Adam Cox
   */
  public ElementBufferObject() {
    id = glGenBuffers();
  }

  /**
   * Get the OpenGL id
   *
   * @return OpenGL id
   * @author Adam Cox
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the buffer object as the current working object
   *
   * @author Adam Cox
   */
  public void bind() {
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
  }

  /**
   * Send the data off to the graphics card
   *
   * @param data Data to send
   * @author Adam Cox
   */
  public void upload(IntBuffer data) {
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, GL_STATIC_DRAW);
  }

  /**
   * Draw the triangles according to the bound indices
   *
   * @param count The number of indices
   * @author Adam Cox
   */
  public void draw(int count) {
    glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
  }

  /**
   * Delete the buffer object
   *
   * @author Adam Cox
   */
  public void delete() {
    glDeleteBuffers(id);
  }
}
