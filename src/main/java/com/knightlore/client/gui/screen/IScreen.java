package com.knightlore.client.gui.screen;

import com.knightlore.client.ClientState;

/**
 * Interface for screens
 *
 * @author Adam Cox
 */
public interface IScreen {

  /**
   * initialise values when the screen starts
   *
   * @param args List of objects passed in on start up
   * @author Adam Cox
   */
  default void startup(Object... args) {}

  /**
   * Check for user input
   *
   * @author Adam Cox
   */
  default void input() {}

  /**
   * Update the screen
   *
   * @param delta Delta time
   * @author Adam Cox
   */
  default void update(float delta) {}

  /**
   * Render the screen
   *
   * @author Adam Cox
   */
  default void render() {}

  /**
   * Reset values when screen shutdown
   *
   * @param nextScreen The next screen to display once shutdown
   * @author Adam Cox
   */
  default void shutdown(ClientState nextScreen) {}

  /**
   * Cleanup gui
   *
   * @author Adam Cox
   */
  void cleanUp();
}
