package com.knightlore.client.render.opengl;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.system.MemoryStack.stackPush;

import com.knightlore.client.util.BufferUtils;
import org.lwjgl.system.MemoryStack;

/**
 * OpenGL render model, all renderable objects must have an instance of this
 *
 * @author Adam Cox
 */
public class RenderModel {

  /** Number of indices to draw */
  private int drawCount;

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
   * @author Adam Cox
   */
  public RenderModel(float[] vertices, float[] textureCoords, int[] indices) {
    drawCount = indices.length;

    try (MemoryStack stack = stackPush()) {
      vertexBufferObject = new VertexBufferObject();
      vertexBufferObject.bind();
      vertexBufferObject.upload(BufferUtils.createBuffer(stack, vertices));

      textureBufferObject = new VertexBufferObject();
      textureBufferObject.bind();
      textureBufferObject.upload(BufferUtils.createBuffer(stack, textureCoords));

      elementBufferObject = new ElementBufferObject();
      elementBufferObject.bind();
      elementBufferObject.upload(BufferUtils.createBuffer(stack, indices));
    }

    unbind();
  }

  /**
   * Render the object using the initialised buffer objects
   *
   * @author Adam Cox
   */
  public void render() {
    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);

    vertexBufferObject.bind();
    vertexBufferObject.vertexAttribPointer(0);

    textureBufferObject.bind();
    textureBufferObject.vertexAttribPointer(1);

    elementBufferObject.bind();
    elementBufferObject.draw(drawCount);

    unbind();

    glDisableVertexAttribArray(0);
    glDisableVertexAttribArray(1);
  }

  /**
   * Unbind the OpenGL bindings
   *
   * @author Adam Cox
   */
  private void unbind() {
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    glBindBuffer(GL_ARRAY_BUFFER, 0);
  }

  /**
   * Clean up memory
   *
   * @author Adam Cox
   */
  public void cleanup() {
    vertexBufferObject.delete();
    textureBufferObject.delete();
    elementBufferObject.delete();
  }
}
