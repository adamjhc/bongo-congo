package com.knightlore.client.gui.screen;

import com.knightlore.client.ClientState;

public interface IScreen {

  /**
   * Initialisation of screen
   *
   * @param args Variable arguments passed from previous screen
   * @author Adam Cox
   */
  default void startup(Object... args) {}

  /**
   * Input handling of screen
   *
   * @author Adam Cox
   */
  default void input() {}

  /**
   * Update screen
   *
   * @param delta Amount of time passed since last update
   * @author Adam Cox
   */
  default void update(float delta) {}

  /**
   * Render screen
   *
   * @author Adam Cox
   */
  default void render() {}

  /**
   * De-constructor of screen
   *
   * @param nextScreen Screen next showing
   * @author Adam Cox
   */
  default void shutdown(ClientState nextScreen) {}

  /**
   * Clean up of memory allocated objects
   *
   * @author Adam Cox
   */
  void cleanUp();
}
