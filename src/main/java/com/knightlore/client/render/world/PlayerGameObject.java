package com.knightlore.client.render.world;

import com.knightlore.client.render.Transform;
import com.knightlore.client.render.opengl.RenderModel;
import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.opengl.StaticTexture;
import com.knightlore.client.util.CoordinateUtils;
import com.knightlore.game.math.Matrix4f;
import com.knightlore.game.math.Vector3f;

public class PlayerGameObject extends Renderable {

  private Transform transform;

  public PlayerGameObject(String textureFileName) {
    float[] vertices =
        new float[] {
          -1f, 1, 0, // TOP LEFT     0
          1f, 1, 0, // TOP RIGHT    1
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

    texture = new StaticTexture(textureFileName);
    model = new RenderModel(vertices, textureCoords, indices);
    transform = new Transform(48);
  }

  public Transform getTransform() {
    return transform;
  }

  @Override
  public void render(
      float x, float y, ShaderProgram shaderProgram, Matrix4f world, Matrix4f camera) {
    transform.setPosition(CoordinateUtils.toCartesian(x, y));

    shaderProgram.bind();
    shaderProgram.setUniform("sampler", 0);
    shaderProgram.setUniform("projection", transform.getProjection(camera));

    texture.bind(0);

    model.render();
  }
}
