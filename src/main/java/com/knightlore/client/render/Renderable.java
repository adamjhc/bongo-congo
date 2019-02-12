package com.knightlore.client.render;

import com.knightlore.game.model.math.Matrix4f;
import com.knightlore.game.model.math.Vector3f;

public abstract class Renderable {

  protected RenderModel model;
  protected ShaderProgram shaderProgram;
  protected Texture texture;

  public void render(int x, int y, Matrix4f world, Camera camera) {
    shaderProgram.bind();
    texture.bind(0);

    Matrix4f position = new Matrix4f().translate(new Vector3f(x, y, 0));
    Matrix4f target = new Matrix4f();
    camera.getProjection().mul(world, target);
    target.mul(position);

    shaderProgram.setUniform("sampler", 0);
    shaderProgram.setUniform("projection", target);

    model.render();
  }
}
