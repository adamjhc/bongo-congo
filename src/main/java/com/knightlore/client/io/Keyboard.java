package com.knightlore.client.io;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

public class Keyboard {

  private static long window = 0;
  private static boolean[] previousKeyStates = new boolean[GLFW_KEY_LAST];

  public static void setWindow(long windowNew) {
    window = windowNew;
  }

  public static boolean isKeyDown(int keyCode) {
    // TODO Check whether key exists (GLFW has some weird key mappings)
    return glfwGetKey(window, keyCode) == GLFW_PRESS;
  }

  public static boolean isKeyReleased(int keyCode) {
    return !isKeyReleased(keyCode) && previousKeyStates[keyCode];
  }

  public static boolean isKeyPressed(int keyCode) {
    return isKeyDown(keyCode) && !previousKeyStates[keyCode];
  }

  public static void update() {
    for (int i = 0; i < previousKeyStates.length; i++) {
      previousKeyStates[i] = isKeyDown(i);
    }
  }
}
