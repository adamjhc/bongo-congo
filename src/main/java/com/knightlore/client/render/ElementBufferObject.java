package com.knightlore.client.render;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.IntBuffer;

class ElementBufferObject {

  private final int id;

  ElementBufferObject() {
    id = glGenBuffers();
  }

  int getId() {
    return id;
  }

  void bind() {
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
  }

  void upload(IntBuffer data) {
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, GL_STATIC_DRAW);
  }

  void draw(int count) {
    glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
  }
}
