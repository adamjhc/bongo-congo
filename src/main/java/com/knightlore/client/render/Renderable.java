package com.knightlore.client.render;

import com.knightlore.game.math.Matrix4f;
import com.knightlore.game.math.Vector3f;

public abstract class Renderable {

  protected RenderModel model;
  protected Texture texture;

  public void render(
      float x, float y, ShaderProgram shaderProgram, Matrix4f world, Matrix4f camera) {
    shaderProgram.bind();
    texture.bind(0);

    Matrix4f position = new Matrix4f().translate(new Vector3f(x, y, 0));
    Matrix4f target = new Matrix4f();
    camera.mul(world, target);
    target.mul(position);

    shaderProgram.setUniform("sampler", 0);
    shaderProgram.setUniform("projection", target);

    model.render();
  }
}
