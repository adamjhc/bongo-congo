package com.knightlore.client.render.opengl;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
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

  public void delete() {
    glBindVertexArray(0);
    glDeleteVertexArrays(id);
  }
}
