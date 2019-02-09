package com.knightlore.client.render.opengl;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class VertexArrayObject {

  private final int id;

  public VertexArrayObject() {
    id = glGenVertexArrays();
  }

  public int getId() {
    return id;
  }

  public void bind() {
    glBindVertexArray(id);
  }
}
