package com.knightlore.client.render.opengl;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.IntBuffer;

public class ElementBufferObject {

  /** OpenGL id of the EBO */
  private final int id;

  /** Initialise EBO */
  ElementBufferObject() {
    id = glGenBuffers();
  }

  /**
   * Get the OpenGL id
   *
   * @return OpenGL id
   */
  public int getId() {
    return id;
  }

  /** Sets the buffer object as the current working object */
  void bind() {
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
  }

  /**
   * Send the data off to the graphics card
   *
   * @param data Data to send
   */
  void upload(IntBuffer data) {
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, GL_STATIC_DRAW);
  }

  /**
   * Draw the triangles according to the bound indices
   *
   * @param count The number of indices
   */
  void draw(int count) {
    glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
  }

  /** Delete the buffer object */
  void delete() {
    glDeleteBuffers(id);
  }
}
