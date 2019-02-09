package com.knightlore.client.io;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

import org.lwjgl.glfw.GLFWKeyCallback;

public class Keyboard extends GLFWKeyCallback {

  /** Reference to the GLFW window */
  private static long window = 0;

  /** Stores key state at time glfwPollEvents is called */
  private static boolean[] previousKeyStates = new boolean[GLFW_KEY_LAST];

  /**
   * Set the window reference
   *
   * @param windowNew The GLFW window
   */
  public static void setWindow(long windowNew) {
    window = windowNew;
  }

  /**
   * Returns whether a given key is pressed down
   *
   * @param keyCode GLFW Key code
   * @return True for key is down/False for key is up
   */
  public static boolean isKeyDown(int keyCode) {
    return glfwGetKey(window, keyCode) == GLFW_PRESS;
  }

  /**
   * Returns whether a given key is pressed, returns true only once while the key is down
   *
   * @param keyCode GLFW Key code
   * @return True for when a key is pressed
   */
  public static boolean isKeyPressed(int keyCode) {
    return previousKeyStates[keyCode];
  }

  /**
   * Returns whether a given key is released, returns true only once while the key is up
   *
   * @param keyCode GLFW Key code
   * @return True for when a key is released
   */
  public static boolean isKeyReleased(int keyCode) {
    return !previousKeyStates[keyCode] && isKeyDown(keyCode);
  }

  /**
   * Runs when glfwPollEvents is called
   *
   * @param window The GLFW window
   * @param key The GLFW key that has triggered the invoke
   * @param scancode The scancode
   * @param action The action of the key
   * @param mods The modifiers
   */
  @Override
  public void invoke(long window, int key, int scancode, int action, int mods) {
    if (key != -1) {
      previousKeyStates[key] = action != GLFW_RELEASE;
    }
  }
}
