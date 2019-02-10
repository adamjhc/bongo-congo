package com.knightlore.client.render.opengl;

public abstract class Texture {

  public abstract int getWidth();

  public abstract int getHeight();

  public abstract void bind(int sampler);
}
