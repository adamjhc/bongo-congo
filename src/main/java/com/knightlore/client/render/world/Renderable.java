package com.knightlore.client.render.world;

import com.knightlore.client.render.opengl.RenderModel;
import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.opengl.Texture;
import org.joml.Matrix4f;

public abstract class Renderable {

  RenderModel model;
  Texture texture;

  public abstract void render(
      float x, float y, ShaderProgram shaderProgram, Matrix4f world, Matrix4f camera);
}
