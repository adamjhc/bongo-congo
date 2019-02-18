package com.knightlore.client.io;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_STICKY_KEYS;
import static org.lwjgl.glfw.GLFW.GLFW_STICKY_MOUSE_BUTTONS;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

public class Window {
  private long window;
  private int width, height;
  private boolean fullscreen;

  /** Initialises the GLFW window */
  public Window() {
    if (!glfwInit()) {
      throw new IllegalStateException("Unable to initialise GLFW");
    }

    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
    glfwWindowHint(GLFW_SAMPLES, 4);

    setSize(1280, 720);
    setFullscreen(false);
  }

  /**
   * Gets the window handle
   *
   * @return Window handle
   */
  public long getWindow() {
    return window;
  }

  /**
   * Gets the window width
   *
   * @return The window width
   */
  public int getWidth() {
    return width;
  }

  /**
   * Gets the window height
   *
   * @return The window height
   */
  public int getHeight() {
    return height;
  }

  /**
   * Returns whether the window is fullscreen
   *
   * @return Whether the window is fullscreen
   */
  public boolean isFullscreen() {
    return fullscreen;
  }

  /**
   * Sets whether the window is fullscreen
   *
   * @param fullscreen Whether the window is fullscreen
   */
  public void setFullscreen(boolean fullscreen) {
    this.fullscreen = fullscreen;
  }

  /**
   * Sets the size of the screen
   *
   * @param width Width of the screen
   * @param height Height of the screen
   */
  public void setSize(int width, int height) {
    this.width = width;
    this.height = height;
  }

  /**
   * Creates and shows the window
   *
   * @param title Title of the window
   */
  public void createWindow(String title) {
    window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : 0, 0);

    if (window == NULL) throw new RuntimeException("Failed to create the GLFW window");

    if (!fullscreen) {
      // Get the resolution of the primary monitor
      GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
      assert vidMode != null; // TODO not sure about this

      // Center the window
      glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
    }

    // Make the OpenGL context current
    glfwMakeContextCurrent(window);

    // Enable v-sync
    glfwSwapInterval(1);

    // Make the window visible
    glfwShowWindow(window);
  }

  /** Sets the callbacks of the window */
  public void setCallbacks() {
    glfwSetErrorCallback(GLFWErrorCallback.createThrow());

    glfwSetInputMode(window, GLFW_STICKY_KEYS, GLFW_TRUE);
    glfwSetKeyCallback(window, new Keyboard());

    glfwSetInputMode(window, GLFW_STICKY_MOUSE_BUTTONS, GLFW_TRUE);
    glfwSetMouseButtonCallback(window, new Mouse());
  }

  /**
   * Returns whether the window should close
   *
   * @return Whether the window should close
   */
  public boolean shouldClose() {
    return glfwWindowShouldClose(window);
  }

  /** Updates the callbacks */
  public void update() {
    glfwPollEvents();
  }

  /** Swaps the buffers */
  public void swapBuffers() {
    glfwSwapBuffers(window);
  }

  /** Frees the callbacks */
  public void freeCallbacks() {
    glfwFreeCallbacks(window);
    glfwSetErrorCallback(null).free();
  }

  /** Destroys the window */
  public void destroyWindow() {
    glfwDestroyWindow(window);
  }
}
