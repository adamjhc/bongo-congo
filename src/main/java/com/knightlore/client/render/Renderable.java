package com.knightlore.client.render;

import com.knightlore.game.math.Matrix4f;

public abstract class Renderable {

  protected RenderModel model;
  protected ShaderProgram shaderProgram;
  protected Texture texture;

  public abstract void render(int x, int y, Matrix4f world, Camera camera);
}
