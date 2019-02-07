package com.knightlore.client.render;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

import com.knightlore.client.render.opengl.ElementBufferObject;
import com.knightlore.client.render.opengl.VertexArrayObject;
import com.knightlore.client.render.opengl.VertexBufferObject;
import com.knightlore.client.util.BufferUtils;

public class RenderModel {

  private int draw_count;

  private VertexArrayObject vertexArrayObject;
  private VertexBufferObject vertexBufferObject;
  private VertexBufferObject textureBufferObject;
  private ElementBufferObject elementBufferObject;

  public RenderModel(float[] vertices, float[] textureCoords, int[] indices) {
    draw_count = indices.length;

    //    vertexArrayObject = new VertexArrayObject();
    //    vertexArrayObject.bind();

    vertexBufferObject = new VertexBufferObject();
    vertexBufferObject.bind();
    vertexBufferObject.upload(BufferUtils.createBuffer(vertices));

    textureBufferObject = new VertexBufferObject();
    textureBufferObject.bind();
    textureBufferObject.upload(BufferUtils.createBuffer(textureCoords));

    elementBufferObject = new ElementBufferObject();
    elementBufferObject.bind();
    elementBufferObject.upload(BufferUtils.createBuffer(indices));

    unbind();
  }

  public void render() {
    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);

    vertexBufferObject.bind();
    vertexBufferObject.vertexAttribPointer(0);

    textureBufferObject.bind();
    textureBufferObject.vertexAttribPointer(1);

    elementBufferObject.bind();
    elementBufferObject.draw(draw_count);

    unbind();

    glDisableVertexAttribArray(0);
    glDisableVertexAttribArray(1);
  }

  private void unbind() {
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    glBindBuffer(GL_ARRAY_BUFFER, 0);
//    glBindVertexArray(0);
  }

  protected void finalize() throws Throwable {
    vertexBufferObject.delete();
    textureBufferObject.delete();
    elementBufferObject.delete();
    super.finalize();
  }
}
