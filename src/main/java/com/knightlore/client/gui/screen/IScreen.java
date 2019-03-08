package com.knightlore.client.gui.screen;

import org.joml.Vector4f;

import com.knightlore.client.ClientState;

public interface IScreen {
	
  Vector4f YELLOW = new Vector4f(1, 1, 0, 1);

  default void startup(Object... args) {}

  default void input() {}

  default void update(float delta) {}

  default void render() {}

  default void shutdown(ClientState nextScreen) {}

  void cleanUp();
}
