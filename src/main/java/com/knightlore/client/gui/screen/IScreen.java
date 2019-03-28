package com.knightlore.client.gui.screen;

import com.knightlore.client.ClientState;

/**
 * Interface for screens
 * 
 * @author Adam C
 *
 */
public interface IScreen {

	/**
	 * initialise values when the screen starts
	 * 
	 * @param args List of objects passed in on start up
	 * @author Adam C
	 * 
	 */
  default void startup(Object... args) {}

  /**
   * Check for user input
   * 
   * @author Adam C
   * 
   */
  default void input() {}

  /**
   * Update the screen
   * 
   * @param delta Delta time
   * @author Adam C
   * 
   */
  default void update(float delta) {}

  /**
   * Render the screen
   * 
   * @author Adam C
   * 
   */
  default void render() {}

  /**
   * Reset values when screen shutdown 
   * 
   * @param nextScreen The next screen to display once shutdown
   * @author Adam C
   * 
   */
  default void shutdown(ClientState nextScreen) {}

  /**
   * Cleanup gui
   * 
   * @author Adam C
   * 
   */
  void cleanUp();
}
