package com.knightlore.client.render;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

class VertexArrayObject {

  private final int id;

  VertexArrayObject() {
    id = glGenVertexArrays();
  }

  int getId() {
    return id;
  }

  void bind() {
    glBindVertexArray(id);
  }
}
