package com.knightlore.client.io;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;

public class Mouse {

  private static double xPos = 0;
  private static double yPos = 0;

  private static boolean leftButtonPressed = false;
  private static boolean rightButtonPressed = false;

  private static boolean scrollUp = false;
  private static boolean scrollDown = false;

  private Mouse() {}

  public static void init() {
    glfwSetCursorPosCallback(
        Window.getWindowHandle(),
        (WindowHandle, xPosNew, yPosNew) -> {
          xPos = xPosNew;
          yPos = yPosNew;
        });

    glfwSetMouseButtonCallback(
        Window.getWindowHandle(),
        (WindowHandle, button, action, mode) -> {
          leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
          rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });

    glfwSetScrollCallback(
        Window.getWindowHandle(),
        (WindowHandle, xOffset, yOffset) -> {
          scrollUp = yOffset > 0;
          scrollDown = yOffset < 0;
        });
  }

  public static double getXPos() {
    return xPos;
  }

  public static double getYPos() {
    return yPos;
  }

  public static boolean scrolledUp() {
    if (scrollUp) {
      scrollUp = false;
      return true;
    }
    return false;
  }

  public static boolean scrolledDown() {
    if (scrollDown) {
      scrollDown = false;
      return true;
    }
    return false;
  }

  public static boolean isLeftButtonPressed() {
    if (leftButtonPressed) {
      leftButtonPressed = false;
      return true;
    }
    return false;
  }

  public static boolean isLeftButtonHeld() {
    return leftButtonPressed;
  }

  public static boolean isRightButtonPressed() {
    if (rightButtonPressed) {
      rightButtonPressed = false;
      return true;
    }
    return false;
  }
}
