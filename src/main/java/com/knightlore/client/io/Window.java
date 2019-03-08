package com.knightlore.client.io;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
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

  private static final String TITLE = "Bongo Congo";

  private static final int WIDTH = 1280;
  private static final int HEIGHT = 720;

  private static final float WIDTH_HALF = WIDTH / (float) 2;
  private static final float HEIGHT_HALF = HEIGHT / (float) 2;

  private static long windowHandle;

  private Window() {}

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
    windowHandle = glfwCreateWindow(WIDTH, HEIGHT, TITLE, NULL, NULL);
    if (windowHandle == NULL) {
      throw new IllegalStateException("Failed to create the GLFW window");
    }

    // Get the resolution of the primary monitor
    GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

    // Centre window
    glfwSetWindowPos(windowHandle, (vidMode.width() - WIDTH) / 2, (vidMode.height() - HEIGHT) / 2);

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

  public static long getWindowHandle() {
    return windowHandle;
  }

  public static int getWidth() {
    return WIDTH;
  }

  public static float getHalfWidth() {
    return WIDTH_HALF;
  }

  public static int getHeight() {
    return HEIGHT;
  }

  public static float getHalfHeight() {
    return HEIGHT_HALF;
  }

  public static boolean shouldClose() {
    return glfwWindowShouldClose(windowHandle);
  }

  public static void setShouldClose() {
    glfwSetWindowShouldClose(windowHandle, true);
  }

  public static void swapBuffers() {
    glfwSwapBuffers(windowHandle);
  }

  public static void update() {
    glfwPollEvents();
  }

  public static void freeCallbacks() {
    glfwFreeCallbacks(windowHandle);
    glfwSetErrorCallback(null).free();
  }

  public static void destroyWindow() {
    glfwDestroyWindow(windowHandle);
  }
}
