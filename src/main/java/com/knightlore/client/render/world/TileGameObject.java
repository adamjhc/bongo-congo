package com.knightlore.client.render.world;

import com.knightlore.client.render.opengl.AnimatedTexture;
import com.knightlore.client.render.opengl.RenderModel;
import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.opengl.StaticTexture;
import com.knightlore.client.render.opengl.Texture;
import org.joml.Matrix4f;

public class TileGameObject extends GameObject {

  /** Set rendering width of the tiles */
  public static final float TILE_WIDTH = 2f;

  /** Set rendering height of the tiles */
  public static final float TILE_HEIGHT = 1f;

  /** Texture rendered on the tile */
  private Texture texture;

  private boolean isFloor = false;

  /** Empty constructor for tiles without textures (e.g. air tiles) */
  TileGameObject() {}

  /**
   * Initialise tile game object with static texture
   *
   * @param textureFileName Name of the texture file
   */
  TileGameObject(boolean isFloor, String textureFileName) {
    this.isFloor = isFloor;
    texture = new StaticTexture(textureFileName);
    setupRenderModel();
  }

  /**
   * Initialise tile game object with an animated texture
   *
   * @param textFileName Name of the texture file without _X
   * @param frames Number of frames
   * @param fps Frames to render per second
   */
  TileGameObject(boolean isFloor, String textFileName, int frames, int fps) {
    this.isFloor = isFloor;
    texture = new AnimatedTexture(textFileName, frames, fps);
    setupRenderModel();
  }

  TileGameObject(TileGameObject copy) {
    texture = copy.texture;
    model = copy.model;
    isFloor = copy.isFloor;
  }

  public boolean isFloor() {
    return isFloor;
  }

  /**
   * Render the tile
   *
   * @param shaderProgram Shader program to use
   * @param world World projection
   * @param camera Camera projection
   */
  public void render(ShaderProgram shaderProgram, Matrix4f world, Matrix4f camera) {
    if (texture != null) {
      shaderProgram.bind();

      texture.bind(0);

      Matrix4f position = new Matrix4f(camera).mul(world).translate(getIsometricPosition());

      shaderProgram.setUniform("sampler", 0);
      shaderProgram.setUniform("projection", position);

      model.render();
    }
  }

  /** Setup OpenGL render model */
  private void setupRenderModel() {
    float scaledTextureHeight = 2 * (float) texture.getHeight() / texture.getWidth();

    float[] vertices =
        new float[] {
          -1f,
          scaledTextureHeight,
          0, // TOP LEFT     0
          1f,
          scaledTextureHeight,
          0, // TOP RIGHT    1
          1f,
          0,
          0, // BOTTOM RIGHT 2
          -1f,
          0,
          0, // BOTTOM LEFT  3
        };

    float[] textureCoords =
        new float[] {
          0, 0,
          1, 0,
          1, 1,
          0, 1,
        };

    int[] indices =
        new int[] {
          0, 1, 2,
          2, 3, 0
        };

    model = new RenderModel(vertices, textureCoords, indices);
  }
}
