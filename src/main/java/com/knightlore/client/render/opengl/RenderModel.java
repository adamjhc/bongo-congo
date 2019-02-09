package com.knightlore.client.render.opengl;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

import com.knightlore.client.util.BufferUtils;

public class RenderModel {

  /** Number of indices to draw */
  private int draw_count;

  /** Buffer object to draw the quad (using triangles) */
  private VertexBufferObject vertexBufferObject;

  /** Buffer object to map the texture on top of the quad */
  private VertexBufferObject textureBufferObject;

  /** Buffer object used to index vertices for multiple uses */
  private ElementBufferObject elementBufferObject;

  /**
   * Initialise the OpenGL render model
   *
   * @param vertices Four vertices to draw the quad with
   * @param textureCoords The points of the quad to map the texture onto
   * @param indices The indices of the vertices to draw the two triangles to make up the quad with
   */
  public RenderModel(float[] vertices, float[] textureCoords, int[] indices) {
    draw_count = indices.length;

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

  /** Render the object using the initialised buffer objects */
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

  /** Unbind the OpenGL bindings */
  private void unbind() {
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    glBindBuffer(GL_ARRAY_BUFFER, 0);
  }

  /**
   * Clean up memory
   *
   * @throws Throwable Exception
   */
  protected void finalize() throws Throwable {
    vertexBufferObject.delete();
    textureBufferObject.delete();
    elementBufferObject.delete();
    super.finalize();
  }
}
