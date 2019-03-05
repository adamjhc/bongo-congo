package com.knightlore.client.render.opengl;

public interface Texture {

  int getWidth();

  int getHeight();

  void bind(int sampler);

  void cleanup();
}
