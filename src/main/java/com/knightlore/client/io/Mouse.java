package com.knightlore.client.io;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

public class Mouse {

  private static long window = 0;
  private static boolean[] previousMouseButtonStates = new boolean[GLFW_MOUSE_BUTTON_LAST];

  public static void setWindow(long windowNew) {
    window = windowNew;
  }

  public static boolean isMouseButtonDown(int button) {
    return glfwGetMouseButton(window, button) == GLFW_PRESS;
  }

  public static boolean isMouseButtonPressed(int button) {
    return isMouseButtonDown(button) && !previousMouseButtonStates[button];
  }

  public static boolean isMouseButtonReleased(int button) {
    return !isMouseButtonDown(button) && previousMouseButtonStates[button];
  }

  public static void update() {
    for (int i = 0; i < previousMouseButtonStates.length; i++) {
      previousMouseButtonStates[i] = isMouseButtonDown(i);
    }
  }
}
