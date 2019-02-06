package com.knightlore.client.io;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
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

  public Window() {
    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

    setSize(1280, 720);
    setFullscreen(false);
  }

  public long getWindow() {
    return window;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public boolean isFullscreen() {
    return fullscreen;
  }

  public void setFullscreen(boolean fullscreen) {
    this.fullscreen = fullscreen;
  }

  public void setSize(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public void createWindow(String title) {
    window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : 0, 0);

    if (window == NULL) throw new RuntimeException("Failed to create the GLFW window");

    if (!fullscreen) {
      // Get the resolution of the primary monitor
      GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

      // Center the window
      assert vidMode != null; // TODO not sure about this
      glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
    }

    // Make the OpenGL context current
    glfwMakeContextCurrent(window);

    // Enable v-sync
    glfwSwapInterval(1);

    // Make the window visible
    glfwShowWindow(window);
  }

  public void setCallbacks() {
    glfwSetErrorCallback(
        new GLFWErrorCallback() {
          @Override
          public void invoke(int error, long description) {
            throw new IllegalStateException(GLFWErrorCallback.getDescription(description));
          }
        });

    glfwSetKeyCallback(window, new Keyboard());
    //    glfwSetMouseButtonCallback(window, new Mouse());
  }

  public boolean shouldClose() {
    return glfwWindowShouldClose(window);
  }

  public void update() {
    glfwPollEvents();
  }

  public void swapBuffers() {
    glfwSwapBuffers(window);
  }

  public void freeCallbacks() {
    glfwFreeCallbacks(window);
    glfwSetErrorCallback(null).free();
  }

  public void destroyWindow() {
    glfwDestroyWindow(window);
  }
}
