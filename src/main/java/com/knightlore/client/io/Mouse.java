package com.knightlore.client.io;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

import java.nio.DoubleBuffer;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.system.MemoryStack;

public class Mouse extends GLFWMouseButtonCallback {

  /** Reference to the GLFW window */
  private static long window = 0;

  /** Stores button state at the time glfwPollEvents is called */
  private static boolean[] previousMouseButtonStates = new boolean[GLFW_MOUSE_BUTTON_LAST];

  /**
   * Sets the reference to the window
   *
   * @param windowNew The GLFW window
   */
  public static void setWindow(long windowNew) {
    window = windowNew;
  }

  /**
   * Returns whether a given mouse button is held down
   *
   * @param button The GLFW button
   * @return True when a button is held down
   */
  public static boolean isButtonDown(int button) {
    return glfwGetMouseButton(window, button) == GLFW_PRESS;
  }

  /**
   * Returns whether a given mouse button is pressed
   *
   * @param button The GLFW button
   * @return True when a button is pressed
   */
  public static boolean isButtonPressed(int button) {
    return previousMouseButtonStates[button];
  }

  /**
   * Returns whether a given mouse button is released
   *
   * @param button The GLFW button
   * @return True when a button is released
   */
  public static boolean isButtonReleased(int button) {
    return !previousMouseButtonStates[button] && isButtonDown(button);
  }

  /**
   * Returns whether a given mouse button is released within a given area of the screen
   *
   * @param button The GLFW button
   * @param bottomLeft Bottom left coordinates of the area
   * @param topRight Top right coordinates of the area
   * @return True when a button is released in the area
   */
  public static boolean isButtonReleasedInArea(int button, Vector2i bottomLeft, Vector2i topRight) {
    if (!isButtonReleased(button)) {
      return false;
    }

    double x, y;
    try (MemoryStack stack = MemoryStack.stackPush()) {
      DoubleBuffer xBuffer = stack.mallocDouble(1);
      DoubleBuffer yBuffer = stack.mallocDouble(1);
      glfwGetCursorPos(window, xBuffer, yBuffer);

      x = xBuffer.get();
      y = yBuffer.get();
    }

    return bottomLeft.x <= x && x <= topRight.x && bottomLeft.y <= y && y <= topRight.y;
  }

  /**
   * Runs when glfwPollEvents is called
   *
   * @param window The GLFW window
   * @param button The mouse button that caused the invoke
   * @param action The action of the key
   * @param mods The modifiers
   */
  @Override
  public void invoke(long window, int button, int action, int mods) {
    previousMouseButtonStates[button] = action != GLFW_RELEASE;
  }
}
