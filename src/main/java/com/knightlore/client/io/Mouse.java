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

  private static long window = 0;
  private static boolean[] previousMouseButtonStates = new boolean[GLFW_MOUSE_BUTTON_LAST];

  public static void setWindow(long windowNew) {
    window = windowNew;
  }

  public static boolean isButtonDown(int button) {
    return glfwGetMouseButton(window, button) == GLFW_PRESS;
  }

  public static boolean isButtonPressed(int button) {
    return previousMouseButtonStates[button];
  }

  public static boolean isButtonReleased(int button) {
    return !previousMouseButtonStates[button] && isButtonDown(button);
  }

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

  @Override
  public void invoke(long window, int button, int action, int mods) {
    previousMouseButtonStates[button] = action != GLFW_RELEASE;
  }
}
