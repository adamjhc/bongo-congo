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

  private final int id;

  private int size;

  public VertexBufferObject() {
    id = glGenBuffers();
  }

  public int getId() {
    return id;
  }

  public void bind() {
    glBindBuffer(GL_ARRAY_BUFFER, id);
  }

  public void upload(FloatBuffer data) {
    size = data.capacity() / 4;
    glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
  }

  public void vertexAttribPointer(int index) {
    glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
  }

  public void delete() {
    glDeleteBuffers(id);
  }
}
