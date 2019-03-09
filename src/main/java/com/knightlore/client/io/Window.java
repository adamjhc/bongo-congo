package com.knightlore.client.io;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
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

  private static int width = 1280;
  private static int height = 720;
  
  private static int oldWidth;
  private static int oldHeight;

  private static float widthHalf = width / (float) 2;
  private static float heightHalf = height / (float) 2;
  
  private static float oldWidthHalf;
  private static float oldHeightHalf;

  private static long windowHandle;
  
  private static boolean fullScreen = false;
  
  private static boolean fullScreenChanged = false;
  
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
    windowHandle = glfwCreateWindow(width, height, TITLE, NULL, NULL);
    if (windowHandle == NULL) {
      throw new IllegalStateException("Failed to create the GLFW window");
    }

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
  
//  public static void fullScreen() {
//	  fullScreen = !fullScreen;
//	  
//	  if (fullScreen) {
//		  GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
//		  
//		  oldWidth = width;
//		  oldHeight = height;
//		  oldWidthHalf = widthHalf;
//		  oldHeightHalf = heightHalf;
//		  
//		  width = vidMode.width();
//	    height = vidMode.height();
//	    widthHalf = width / (float) 2;
//	    heightHalf = height / (float) 2;
//	  } else {
//		  width = oldWidth;
//		  height = oldHeight;
//		  widthHalf = oldWidthHalf;
//		  heightHalf = oldHeightHalf;
//	  }
//	  
//	  long newWindowHandle = glfwCreateWindow(width, height, TITLE, fullScreen ? glfwGetPrimaryMonitor() : NULL, windowHandle);
//	  glfwDestroyWindow(windowHandle);
//	  windowHandle = newWindowHandle;
//	  
//	  glfwMakeContextCurrent(windowHandle);
//	  glfwSwapInterval(1);
//	  glfwShowWindow(windowHandle);
//	  GL.createCapabilities();
//  }
//  
  public static void setFullscreen() {
  }

  public static long getWindowHandle() {
    return windowHandle;
  }

  public static int getWidth() {
    return width;
  }

  public static float getHalfWidth() {
    return widthHalf;
  }

  public static int getHeight() {
    return height;
  }

  public static float getHalfHeight() {
    return heightHalf;
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
