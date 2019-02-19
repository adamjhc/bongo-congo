package com.knightlore.client.render.world;

import com.knightlore.client.render.opengl.AnimatedTexture;
import com.knightlore.client.render.opengl.RenderModel;
import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.opengl.StaticTexture;
import com.knightlore.client.render.opengl.Texture;
import com.knightlore.game.util.CoordinateUtils;
import org.joml.Matrix4f;

public class TileGameObject extends GameObject {

  /** Set rendering width of the tiles */
  public static float tileWidth = 2f;

  /** Set rendering height of the tiles */
  public static float tileHeight = 1f;

  /** Texture rendered on the tile */
  private Texture texture;

  /** Empty constructor for tiles without textures (e.g. air tiles) */
  TileGameObject() {}

  /**
   * Initialise tile game object with static texture
   *
   * @param textureFileName Name of the texture file
   */
  TileGameObject(String textureFileName) {
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
  TileGameObject(String textFileName, int frames, int fps) {
    texture = new AnimatedTexture(textFileName, frames, fps);
    setupRenderModel();
  }

  /**
   * Render the tile
   *
   * @param x Isometric x position of the tile
   * @param y Isometric y position of the tile
   * @param shaderProgram Shader program to use
   * @param world World projection
   * @param camera Camera projection
   */
  public void render(
      float x, float y, ShaderProgram shaderProgram, Matrix4f world, Matrix4f camera) {
    if (texture != null) {
      shaderProgram.bind();

      texture.bind(0);

      Matrix4f position = new Matrix4f().translate(CoordinateUtils.toIsometric(x, y));
      Matrix4f target = new Matrix4f();
      camera.mul(world, target);
      target.mul(position);

      shaderProgram.setUniform("sampler", 0);
      shaderProgram.setUniform("projection", target);

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
