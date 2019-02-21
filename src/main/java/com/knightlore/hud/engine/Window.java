package com.knightlore.hud.engine;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowIcon;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

public class Window {

  private final String title;

  private int width;

  private int height;

  private long windowHandle;

  private int keyCode;

  public Window(String title, int width, int height) {
    this.title = title;
    this.width = width;
    this.height = height;
    this.keyCode = -1;
  }

  public void init() {
    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    GLFWErrorCallback.createPrint(System.err).set();

    if (!glfwInit()) {
      throw new IllegalStateException("Unable to initialize GLFW");
    }

    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
    glfwWindowHint(GLFW_SAMPLES, 4);

    // Create the window, use (width, height, title, glfwGetPrimaryMonitor(), 0) for full-screen
    windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
    if (windowHandle == NULL) {
      throw new RuntimeException("Failed to create the GLFW window");
    }

    // Setup a key callback. It will be called every time a key is pressed, repeated or released.
    glfwSetKeyCallback(
        windowHandle,
        (window, key, scancode, action, mods) -> {
          if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            glfwSetWindowShouldClose(window, true);
          } else if (action == GLFW_RELEASE) {
            this.keyCode = key;
          }
        });

    // Get the resolution of the primary monitor
    GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

    // Centre window
    glfwSetWindowPos(windowHandle, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);

    try (MemoryStack stack = stackPush()) {
      IntBuffer w = stack.mallocInt(1);
      IntBuffer h = stack.mallocInt(1);
      IntBuffer comp = stack.mallocInt(1);

      ByteBuffer image = stbi_load("./src/main/resources/textures/icon.png", w, h, comp, 4);

      GLFWImage icon = GLFWImage.malloc();
      GLFWImage.Buffer imageBuffer = GLFWImage.malloc(1);
      icon.set(w.get(), h.get(), image);
      imageBuffer.put(0, icon);

      glfwSetWindowIcon(windowHandle, imageBuffer);
    }

    // Make the OpenGL context current
    glfwMakeContextCurrent(windowHandle);

    // Enable v-sync
    glfwSwapInterval(1);

    // Make the window visible
    glfwShowWindow(windowHandle);

    GL.createCapabilities();
  }

  public long getWindowHandle() {
    return windowHandle;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public boolean isKeyReleased(int keyCode) {
    if (keyCode == this.keyCode) {
      this.keyCode = -1;
      return true;
    }
    return false;
  }

  public boolean isKeyPressed(int keyCode) {
    return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
  }

  public boolean windowShouldClose() {
    return glfwWindowShouldClose(windowHandle);
  }

  public void swapBuffers() {
    glfwSwapBuffers(windowHandle);
  }

  public void update() {
    glfwPollEvents();
  }

  public void freeCallbacks() {
    glfwFreeCallbacks(windowHandle);
    glfwSetErrorCallback(null).free();
  }

  public void destroyWindow() {
    glfwDestroyWindow(windowHandle);
  }
}
