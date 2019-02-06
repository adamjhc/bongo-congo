package com.knightlore.client;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;

import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Window;
import com.knightlore.client.render.Camera;
import com.knightlore.client.world.FloorTile;
import com.knightlore.game.math.Matrix4f;
import com.knightlore.game.math.Vector3f;
import org.lwjgl.opengl.GL;

public class Client {

  private Window window;

  public static void main(String[] args) {
    new Client().run();
  }

  public void run() {
    init();
    loop();
    dispose();
  }

  private void init() {
    if (!glfwInit()) {
      throw new IllegalStateException("Unable to initialise GLFW");
    }

    window = new Window();
    window.createWindow("Bongo Congo Client");
    window.setCallbacks();
    Keyboard.setWindow(window.getWindow());

    GL.createCapabilities();

    glEnable(GL_TEXTURE_2D);
  }

  private void loop() {
    Camera camera = new Camera(window.getWidth(), window.getHeight());
    FloorTile tile = new FloorTile();

    Matrix4f world = new Matrix4f().setTranslation(new Vector3f(0));
    world.scale(128);

    while (!window.shouldClose()) {
      glfwPollEvents();

      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

      tile.render(0, 0, world, camera.getProjection());

      window.swapBuffers();
    }
  }

  private void dispose() {
    window.freeCallbacks();
    window.destroyWindow();

    glfwTerminate();
  }
}
