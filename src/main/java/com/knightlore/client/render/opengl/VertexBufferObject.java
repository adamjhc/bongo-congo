package com.knightlore.client.render.opengl;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

/**
 * Wrapper class for OpenGL VBOs
 *
 * @author Adam Cox
 */
public class VertexBufferObject {

  /** The OpenGL id of the VBO */
  private final int id;

  /** Size of the buffer */
  private int size;

  /** Initialise the VBO */
  VertexBufferObject() {
    id = glGenBuffers();
  }

  /**
   * Get the OpenGL id of the VBO
   *
   * @return OpenGL id of the VBO
   * @author Adam Cox
   */
  public int getId() {
    return id;
  }

  /**
   * Bind the VBO
   *
   * @author Adam Cox
   */
  public void bind() {
    glBindBuffer(GL_ARRAY_BUFFER, id);
  }

  /**
   * Send the data off to the graphics card
   *
   * @param data Data to send
   * @author Adam Cox
   */
  void upload(FloatBuffer data) {
    size = data.capacity() / 4;
    glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
  }

  /**
   * Define an array of generic vertex attribute data
   *
   * @param index Index to define the array at
   * @author Adam Cox
   */
  void vertexAttribPointer(int index) {
    vertexAttribPointer(index, size);
  }

  /**
   * Define an array of generic vertex attribute data using a given size
   *
   * @param index Index to define the array at
   * @param size Size of the array
   * @author Adam Cox
   */
  private void vertexAttribPointer(int index, int size) {
    glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
  }

  /**
   * Delete the buffer
   *
   * @author Adam Cox
   */
  void delete() {
    glDeleteBuffers(id);
  }
}
