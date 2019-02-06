package com.knightlore.client.render;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;

class VertexBufferObject {

  private final int id;

  private int size;

  VertexBufferObject() {
    id = glGenBuffers();
  }

  int getId() {
    return id;
  }

  void bind() {
    glBindBuffer(GL_ARRAY_BUFFER, id);
  }

  void upload(FloatBuffer data) {
    size = data.capacity();
    glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
  }

  void vertexAttribPointer(int index) {
    glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
  }

  void delete() {
    glDeleteBuffers(id);
  }
}
