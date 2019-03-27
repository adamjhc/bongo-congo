package com.knightlore.client.io;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;

public class Keyboard {

  /** Last keyCode */
  private static int keyCode;

  /** Private constructor so class cannot be instantiated */
  private Keyboard() {}

  /** Initialise Keyboard */
  public static void init() {
    // Setup a key callback. It will be called every time a key is pressed, repeated or released.
    glfwSetKeyCallback(
        Window.getWindowHandle(),
        (window, key, scanCode, action, mods) -> {
          if (action == GLFW_RELEASE) {
            keyCode = key;
          }
        });
  }

  /**
   * Get whether a key has been released
   *
   * @param newKeyCode GLFW key code of key to be released
   * @return boolean on whether key has been released
   */
  public static boolean isKeyReleased(int newKeyCode) {
    if (keyCode == newKeyCode) {
      keyCode = -1;
      return true;
    }
    return false;
  }

  /**
   * Gets the last key code used
   *
   * @return GLFW key code for last key used
   */
  public static int getKeyCode() {
    int x = keyCode;
    keyCode = -1;
    return x;
  }

  /**
   * Get whether a key has been pressed
   *
   * @param newKeyCode GLFW key code of key to be pressed
   * @return boolean on whether key has been pressed
   */
  public static boolean isKeyPressed(int newKeyCode) {
    return glfwGetKey(Window.getWindowHandle(), newKeyCode) == GLFW_PRESS;
  }
}
