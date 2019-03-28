package com.knightlore.client.io;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_DONT_CARE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
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
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowIcon;
import static org.lwjgl.glfw.GLFW.glfwSetWindowMonitor;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.glViewport;
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

  /** Set window width while windowed */
  public static final int WINDOWED_WIDTH = 1280;

  /** Set window height while windowed */
  public static final int WINDOWED_HEIGHT = 720;

  /** Title of window */
  private static final String TITLE = "Bongo Congo";

  /** Current width of window */
  private static int width = WINDOWED_WIDTH;

  /** Current height of window */
  private static int height = WINDOWED_HEIGHT;

  /** Half of the current window width */
  private static float widthHalf = width / 2f;

  /** Half of the current window height */
  private static float heightHalf = height / 2f;

  /** GLFW window handle */
  private static long windowHandle;

  /** Whether window is fullscreen */
  private static boolean fullScreen = false;

  /** Private constructor so window can't be instantiated */
  private Window() {}

  /** Initialise Window */
  public static void init() {
    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));

    if (!glfwInit()) {
      throw new IllegalStateException("Unable to initialize GLFW");
    }

    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
    glfwWindowHint(GLFW_SAMPLES, 4);

    // Create the window, use (WIDTH, HEIGHT, title, glfwGetPrimaryMonitor(), 0) for full-screen
    windowHandle = glfwCreateWindow(width, height, TITLE, NULL, NULL);
    if (windowHandle == NULL) {
      throw new IllegalStateException("Failed to create the GLFW window");
    }

    glfwSetFramebufferSizeCallback(
        windowHandle,
        (window, newWidth, newHeight) -> {
          width = newWidth;
          height = newHeight;
        });

    // Get the resolution of the primary monitor
    GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

    // Centre window
    glfwSetWindowPos(windowHandle, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);

    // Set window icon
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

  /** Set window to fullscreen */
  public static void setFullscreen() {
    fullScreen = !fullScreen;
    GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

    if (fullScreen) {
      width = vidMode.width();
      height = vidMode.height();
      widthHalf = width / 2f;
      heightHalf = height / 2f;
    } else {
      width = WINDOWED_WIDTH;
      height = WINDOWED_HEIGHT;
      widthHalf = WINDOWED_WIDTH / 2f;
      heightHalf = WINDOWED_HEIGHT / 2f;
    }
    glfwSetWindowMonitor(
        windowHandle,
        fullScreen ? glfwGetPrimaryMonitor() : NULL,
        (vidMode.width() - width) / 2,
        (vidMode.height() - height) / 2,
        width,
        height,
        GLFW_DONT_CARE);
    glfwMakeContextCurrent(windowHandle);
    glfwSwapInterval(1);
    glfwShowWindow(windowHandle);
    GL.createCapabilities();
    glViewport(0, 0, Window.getWidth(), Window.getHeight());
  }

  /**
   * Get the GLFW window handle
   *
   * @return GLFW window handle
   */
  static long getWindowHandle() {
    return windowHandle;
  }

  /**
   * Get current width of window
   *
   * @return current width of window
   */
  public static int getWidth() {
    return width;
  }

  /**
   * Get half the current window width
   *
   * @return half the current window width
   */
  public static float getHalfWidth() {
    return widthHalf;
  }

  /**
   * Get the current height of the window
   *
   * @return current height of the window
   */
  public static int getHeight() {
    return height;
  }

  /**
   * Get half the current window height
   *
   * @return half the current window height
   */
  public static float getHalfHeight() {
    return heightHalf;
  }

  /**
   * Gets whether the window should close
   *
   * @return whether the window should close
   */
  public static boolean shouldClose() {
    return glfwWindowShouldClose(windowHandle);
  }

  /** Sets should close to true */
  public static void setShouldClose() {
    glfwSetWindowShouldClose(windowHandle, true);
  }

  /** Swap the buffers of the window */
  public static void swapBuffers() {
    glfwSwapBuffers(windowHandle);
  }

  /** Cycle callbacks */
  public static void update() {
    glfwPollEvents();
  }

  /** Free set callbacks */
  public static void freeCallbacks() {
    glfwFreeCallbacks(windowHandle);
    glfwSetErrorCallback(null).free();
  }

  /** Destroy GLFW window */
  public static void destroyWindow() {
    glfwDestroyWindow(windowHandle);
  }
}
