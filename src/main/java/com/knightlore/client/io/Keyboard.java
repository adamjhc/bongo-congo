package com.knightlore.client.io;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;

public class Keyboard {

  private static int keyCode;

  private Keyboard() {}

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

  public static boolean isKeyReleased(int newKeyCode) {
    if (keyCode == newKeyCode) {
      keyCode = -1;
      return true;
    }
    return false;
  }

  public static boolean isKeyPressed(int newKeyCode) {
    return glfwGetKey(Window.getWindowHandle(), newKeyCode) == GLFW_PRESS;
  }
}
