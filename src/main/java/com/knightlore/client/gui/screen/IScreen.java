package com.knightlore.client.gui.screen;

public interface IScreen {

  default void init(Object... args) {}

  default void input() {}

  default void update(float delta) {}

  default void render() {}
}
