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

public class ElementBufferObject {

  private final int id;

  public ElementBufferObject() {
    id = glGenBuffers();
  }

  public int getId() {
    return id;
  }

  public void bind() {
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
  }

  public void upload(IntBuffer data) {
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, GL_STATIC_DRAW);
  }

  public void draw(int count) {
    glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
  }
}
