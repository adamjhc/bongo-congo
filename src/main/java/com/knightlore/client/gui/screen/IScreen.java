package com.knightlore.client.gui.screen;

import com.knightlore.client.ClientState;

public interface IScreen {

  default void startup(Object... args) {}

  default void input() {}

  default void update(float delta) {}

  default void render() {}

  default void shutdown(ClientState nextScreen) {}

  void cleanUp();
}
