package com.knightlore.client.io;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

import org.lwjgl.glfw.GLFWKeyCallback;

public class Keyboard extends GLFWKeyCallback {

  private static long window = 0;
  private static boolean[] previousKeyStates = new boolean[GLFW_KEY_LAST];

  public static void setWindow(long windowNew) {
    window = windowNew;
  }

  public static boolean isKeyDown(int keyCode) {
    return glfwGetKey(window, keyCode) == GLFW_PRESS;
  }

  public static boolean isKeyPressed(int keyCode) {
    return isKeyDown(keyCode) && !previousKeyStates[keyCode];
  }

  public static boolean isKeyReleased(int keyCode) {
    return !isKeyDown(keyCode) && previousKeyStates[keyCode];
  }

  @Override
  public void invoke(long window, int key, int scancode, int action, int mods) {
    if (key != -1) {
      previousKeyStates[key] = action != GLFW_RELEASE;
    }
  }
}
