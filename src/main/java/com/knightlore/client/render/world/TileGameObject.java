package com.knightlore.client.render.world;

import com.knightlore.client.render.opengl.AnimatedTexture;
import com.knightlore.client.render.opengl.RenderModel;
import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.opengl.StaticTexture;
import com.knightlore.client.util.CoordinateUtils;
import com.knightlore.game.math.Matrix4f;

public class TileGameObject extends Renderable {

  public static float tileWidth = 2f;
  public static float tileHeight = 1.171875f;

  TileGameObject() {}

  TileGameObject(String textureFileName) {
    texture = new StaticTexture(textureFileName);
    setupRenderModel();
  }

  TileGameObject(String textFileName, int frames, int fps) {
    texture = new AnimatedTexture(textFileName, frames, fps);
    setupRenderModel();
  }

  @Override
  public void render(
      float x, float y, ShaderProgram shaderProgram, Matrix4f world, Matrix4f camera) {
    if (texture != null) {
      shaderProgram.bind();

      texture.bind(0);

      Matrix4f position = new Matrix4f().translate(CoordinateUtils.toCartesian(x, y));
      Matrix4f target = new Matrix4f();
      camera.mul(world, target);
      target.mul(position);

      shaderProgram.setUniform("sampler", 0);
      shaderProgram.setUniform("projection", target);

      model.render();
    }
  }

  private void setupRenderModel() {
    float tileHeight = 2 * (float) texture.getHeight() / texture.getWidth();
    float[] vertices =
        new float[] {
          -1f,
          tileHeight,
          0, // TOP LEFT     0
          1f,
          tileHeight,
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
