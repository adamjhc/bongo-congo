package com.knightlore.client.render.world;

import com.knightlore.client.render.opengl.RenderModel;
import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.opengl.Texture;
import com.knightlore.client.util.CoordinateUtils;
import com.knightlore.game.math.Matrix4f;
import com.knightlore.game.math.Vector3f;

public class TileGameObject extends Renderable {
  // 256 * 512

  public static float tileWidth = 2f;
  public static float tileHeight = 1.171875f;

  public TileGameObject(String textureFileName) {
    texture = new Texture(textureFileName);
    float tileHeight = (float) texture.getHeight() / texture.getWidth();

    float[] vertices =
        new float[] {
          -1f, 2 * tileHeight, 0, // TOP LEFT     0
          1f, 2 * tileHeight, 0, // TOP RIGHT    1
          1f, 0, 0, // BOTTOM RIGHT 2
          -1f, 0, 0, // BOTTOM LEFT  3
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

  @Override
  public void render(
      float x, float y, ShaderProgram shaderProgram, Matrix4f world, Matrix4f camera) {
    Vector3f cartesian = CoordinateUtils.toCartesian(new Vector3f(x, y, 0));
    super.render(cartesian.x, cartesian.y, shaderProgram, world, camera);
  }
}
