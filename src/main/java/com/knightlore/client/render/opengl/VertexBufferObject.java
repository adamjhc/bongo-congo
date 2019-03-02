package com.knightlore.client.render.opengl;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;

public class VertexBufferObject {

  /** The OpenGL id of the VBO */
  private final int id;

  /** Size of the buffer */
  private int size;

  /** Initialise the VBO */
  public VertexBufferObject() {
    id = glGenBuffers();
  }

  /**
   * Get the OpenGL id of the VBO
   *
   * @return OpenGL id of the VBO
   */
  public int getId() {
    return id;
  }

  /** Bind the VBO */
  public void bind() {
    glBindBuffer(GL_ARRAY_BUFFER, id);
  }

  /**
   * Send the data off to the graphics card
   *
   * @param data Data to send
   */
  public void upload(FloatBuffer data) {
    size = data.capacity() / 4;
    glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
  }

  /**
   * Define an array of generic vertex attribute data
   *
   * @param index Index to define the array at
   */
  public void vertexAttribPointer(int index) {
    vertexAttribPointer(index, size);
  }

  public void vertexAttribPointer(int index, int size) {
    glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
  }

  /** Delete the buffer */
  public void delete() {
    glDeleteBuffers(id);
  }
}
