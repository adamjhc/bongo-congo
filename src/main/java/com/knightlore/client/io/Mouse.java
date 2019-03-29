package com.knightlore.client.io;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {

  /** X position of mouse in screen */
  private static double xPos = 0;

  /** Y position of mouse in screen */
  private static double yPos = 0;

  /** Boolean whether mouse can be found within the screen */
  private static boolean isInScreen = false;

  /** Boolean whether left mouse button is pressed */
  private static boolean leftButtonPressed = false;

  /** Boolean whether right mouse button is pressed */
  private static boolean rightButtonPressed = false;

  /** Boolean whether mouse is scrolling up */
  private static boolean scrollUp = false;

  /** Boolean whether mouse is scrolling down */
  private static boolean scrollDown = false;

  /** Private constructor so Mouse cannot be instantiated */
  private Mouse() {}

  /** Initialised Mouse and set callbacks */
  public static void init() {
    glfwSetCursorPosCallback(
        Window.getWindowHandle(),
        (windowHandle, xPosNew, yPosNew) -> {
          xPos = xPosNew;
          yPos = yPosNew;
        });

    glfwSetMouseButtonCallback(
        Window.getWindowHandle(),
        (windowHandle, button, action, mode) -> {
          leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
          rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });

    glfwSetScrollCallback(
        Window.getWindowHandle(),
        (windowHandle, xOffset, yOffset) -> {
          scrollUp = yOffset > 0;
          scrollDown = yOffset < 0;
        });

    glfwSetCursorEnterCallback(
        Window.getWindowHandle(),
        (windowHandle, entered) -> {
          isInScreen = entered;
        });
  }

  /** Hide cursor while mouse is on screen */
  public static void hideCursor() {
    glfwSetInputMode(Window.getWindowHandle(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
  }

  /** Unhides cursor */
  public static void showCursor() {
    glfwSetInputMode(Window.getWindowHandle(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
  }

  /**
   * Get the x position of the mouse
   *
   * @return x position of the mouse
   */
  public static double getXPos() {
    return xPos;
  }

  /**
   * Get the y position of the mouse
   *
   * @return y position of the mouse
   */
  public static double getYPos() {
    return yPos;
  }

  /**
   * Get whether the mouse is inside the screen
   *
   * @return whether the mouse is inside the screen
   */
  public static boolean isInScreen() {
    return isInScreen;
  }

  /**
   * Get whether the mouse is scrolling up
   *
   * @return whether the mouse is scrolling up
   */
  public static boolean scrolledUp() {
    if (scrollUp) {
      scrollUp = false;
      return true;
    }
    return false;
  }

  /**
   * Get whether the mouse is scrolling down
   *
   * @return whether the mouse is scrolling down
   */
  public static boolean scrolledDown() {
    if (scrollDown) {
      scrollDown = false;
      return true;
    }
    return false;
  }

  /**
   * Get whether the left mouse button is pressed
   *
   * @return whether the left mouse button is pressed
   */
  public static boolean isLeftButtonPressed() {
    if (leftButtonPressed) {
      leftButtonPressed = false;
      return true;
    }
    return false;
  }

  /**
   * Get whether the left mouse button is held
   *
   * @return whether the left mouse button is held
   */
  public static boolean isLeftButtonHeld() {
    return leftButtonPressed;
  }

  /**
   * Get whether the right mouse button is pressed
   *
   * @return whether the right mouse button is pressed
   */
  public static boolean isRightButtonPressed() {
    if (rightButtonPressed) {
      rightButtonPressed = false;
      return true;
    }
    return false;
  }
}
