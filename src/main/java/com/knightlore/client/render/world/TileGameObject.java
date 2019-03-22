package com.knightlore.client.render.world;

import com.knightlore.client.render.opengl.AnimatedTexture;
import com.knightlore.client.render.opengl.RenderModel;
import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.opengl.StaticTexture;
import com.knightlore.client.render.opengl.Texture;
import com.knightlore.game.map.TileType;
import org.joml.Matrix4f;

/**
 * GameObject used for map tiles
 *
 * @author Adam Cox
 */
public class TileGameObject extends GameObject {

  /** Set rendering width of the tiles */
  public static final float TILE_WIDTH = 2f;

  /** Set rendering height of the tiles */
  public static final float TILE_HEIGHT = 1f;

  /** Texture rendered on the tile */
  private Texture texture;

  /** Type of tile */
  private TileType tileType;

  /**
   * Initialise tile game object with static texture
   *
   * @param tileType Type of tile
   * @param textureFileName Name of the texture file
   * @author Adam Cox
   */
  TileGameObject(TileType tileType, String textureFileName) {
    this.tileType = tileType;
    texture = new StaticTexture(textureFileName);
    setupRenderModel();
  }

  /**
   * Initialise tile game object with an animated texture
   *
   * @param textFileName Name of the texture file without _X
   * @param frames Number of frames
   * @param fps Frames to render per second
   * @author Adam Cox
   */
  TileGameObject(TileType tileType, String textFileName, int frames, int fps) {
    this.tileType = tileType;
    texture = new AnimatedTexture(textFileName, frames, fps);
    setupRenderModel();
  }

  /**
   * Copy constructor
   *
   * @param copy Copy to copy fields from
   * @author Adam Cox
   */
  TileGameObject(TileGameObject copy) {
    texture = copy.texture;
    model = copy.model;
    tileType = copy.tileType;
  }

  /**
   * Setup OpenGL render model
   *
   * @author Adam Cox
   */
  private void setupRenderModel() {
    float scaledTextureHeight = 2 * (float) texture.getHeight() / texture.getWidth();

    float[] vertices =
        new float[] {
          -1f, scaledTextureHeight, 0, // TOP LEFT     0
          1f, scaledTextureHeight, 0, // TOP RIGHT    1
          1f, 0, 0, // BOTTOM RIGHT 2
          -1f, 0, 0, // BOTTOM LEFT  3
        };

    model = new RenderModel(vertices, textureCoordinates, indices);
  }

  /**
   * Get whether the tile is a floor
   *
   * @return whether the tile is a floor
   * @author Adam Cox
   */
  public boolean isFloor() {
    return  tileType == TileType.FLOOR ||
    		tileType == TileType.SPAWN_WALKER ||
    		tileType == TileType.SPAWN_RANDOMER ||
    		tileType == TileType.SPAWN_CIRCLER ||
    		tileType == TileType.SPAWN_CHARGER;
  }

  /**
   * Render the tile - unhighlighted
   *
   * @param shaderProgram Shader program to use
   * @param world World projection
   * @param camera Camera projection
   * @author Adam Cox
   */
  public void render(ShaderProgram shaderProgram, Matrix4f world, Matrix4f camera) {
    render(shaderProgram, world, camera, 0);
  }

  /**
   * Render the TileGameObject
   *
   * @param shaderProgram Shader program to use
   * @param world world projection
   * @param camera camera projection
   * @param highlight whether to highlight the tile
   * @author Adam Cox
   */
  public void render(ShaderProgram shaderProgram, Matrix4f world, Matrix4f camera, int highlight) {
    if (tileType != TileType.AIR || highlight == 1) {
      shaderProgram.bind();

      texture.bind(0);

      Matrix4f position = new Matrix4f(camera).mul(world).translate(isometricPosition);

      shaderProgram.setUniform("sampler", 0);
      shaderProgram.setUniform("projection", position);
      shaderProgram.setUniform("highlight", highlight);

      model.render();
    }
  }

  /**
   * Memory cleanup of textures and render model
   *
   * @author Adam Cox
   */
  public void cleanup() {
    if (texture != null) {
      texture.cleanup();
      model.cleanup();
    }
  }
}
