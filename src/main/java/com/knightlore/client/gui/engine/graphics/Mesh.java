package com.knightlore.client.gui.engine.graphics;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

/**
 * Creates the VBO and VAO objects to load text
 *
 * @author Joseph
 */
public class Mesh {

  /** Vertex array object id */
  private final int vaoId;
  /** List of ids */
  private final List<Integer> vboIdList;
  /** Number of vertices */
  private final int vertexCount;
  /** Material */
  private Material material;

  /**
   * Create the VBO and VAO objects
   *
   * @param positions List of positions
   * @param textCoords List of texture coordinates
   * @param normals List of normals
   * @param indices List of indicies
   * @author Joseph
   */
  public Mesh(float[] positions, float[] textCoords, float[] normals, int[] indices) {
    FloatBuffer posBuffer = null;
    FloatBuffer textCoordsBuffer = null;
    FloatBuffer vecNormalsBuffer = null;
    IntBuffer indicesBuffer = null;
    try {
      vertexCount = indices.length;
      vboIdList = new ArrayList();

      vaoId = glGenVertexArrays();
      glBindVertexArray(vaoId);

      // Position VBO
      int vboId = glGenBuffers();
      vboIdList.add(vboId);
      posBuffer = MemoryUtil.memAllocFloat(positions.length);
      posBuffer.put(positions).flip();
      glBindBuffer(GL_ARRAY_BUFFER, vboId);
      glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
      glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

      // Texture coordinates VBO
      vboId = glGenBuffers();
      vboIdList.add(vboId);
      textCoordsBuffer = MemoryUtil.memAllocFloat(textCoords.length);
      textCoordsBuffer.put(textCoords).flip();
      glBindBuffer(GL_ARRAY_BUFFER, vboId);
      glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
      glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

      // Vertex normals VBO
      vboId = glGenBuffers();
      vboIdList.add(vboId);
      vecNormalsBuffer = MemoryUtil.memAllocFloat(normals.length);
      vecNormalsBuffer.put(normals).flip();
      glBindBuffer(GL_ARRAY_BUFFER, vboId);
      glBufferData(GL_ARRAY_BUFFER, vecNormalsBuffer, GL_STATIC_DRAW);
      glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);

      // Index VBO
      vboId = glGenBuffers();
      vboIdList.add(vboId);
      indicesBuffer = MemoryUtil.memAllocInt(indices.length);
      indicesBuffer.put(indices).flip();
      glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
      glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

      glBindBuffer(GL_ARRAY_BUFFER, 0);
      glBindVertexArray(0);
    } finally {
      if (posBuffer != null) {
        MemoryUtil.memFree(posBuffer);
      }
      if (textCoordsBuffer != null) {
        MemoryUtil.memFree(textCoordsBuffer);
      }
      if (vecNormalsBuffer != null) {
        MemoryUtil.memFree(vecNormalsBuffer);
      }
      if (indicesBuffer != null) {
        MemoryUtil.memFree(indicesBuffer);
      }
    }
  }

  /**
   * Return the associated material
   *
   * @return Material
   * @author Joseph
   */
  public Material getMaterial() {
    return material;
  }

  /**
   * Set the material
   *
   * @param material The material
   * @author Joseph
   */
  public void setMaterial(Material material) {
    this.material = material;
  }

  /**
   * Return the VAO id
   *
   * @return VaoId
   * @author Joseph
   */
  public int getVaoId() {
    return vaoId;
  }

  /**
   * Return the vertex count
   *
   * @return VertexCount
   * @author Joseph
   */
  public int getVertexCount() {
    return vertexCount;
  }

  /**
   * Render the mesh
   *
   * @author Joseph
   */
  public void render() {
    Texture texture = material.getTexture();
    if (texture != null) {
      // Activate firs texture bank
      glActiveTexture(GL_TEXTURE0);
      // Bind the texture
      glBindTexture(GL_TEXTURE_2D, texture.getId());
    }

    // Draw the mesh
    glBindVertexArray(getVaoId());
    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);
    glEnableVertexAttribArray(2);

    glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);

    // Restore state
    glDisableVertexAttribArray(0);
    glDisableVertexAttribArray(1);
    glDisableVertexAttribArray(2);
    glBindVertexArray(0);
    glBindTexture(GL_TEXTURE_2D, 0);
  }

  /**
   * Cleanup the mesh
   *
   * @author Joseph
   */
  public void cleanUp() {
    glDisableVertexAttribArray(0);

    // Delete the VBOs
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    for (int vboId : vboIdList) {
      glDeleteBuffers(vboId);
    }

    // Delete the texture
    Texture texture = material.getTexture();
    if (texture != null) {
      texture.cleanup();
    }

    // Delete the VAO
    glBindVertexArray(0);
    glDeleteVertexArrays(vaoId);
  }

  /**
   * Delete the buffers
   *
   * @author Joseph
   */
  public void deleteBuffers() {
    glDisableVertexAttribArray(0);

    // Delete the VBOs
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    for (int vboId : vboIdList) {
      glDeleteBuffers(vboId);
    }

    // Delete the VAO
    glBindVertexArray(0);
    glDeleteVertexArrays(vaoId);
  }
}
