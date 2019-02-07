package com.knightlore.client;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;

import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Window;
import com.knightlore.client.render.Camera;
import com.knightlore.client.render.ShaderProgram;
import com.knightlore.client.world.TileSet;
import com.knightlore.game.map.Map;
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
    window.createWindow("Bongo Congo");
    window.setCallbacks();
    Keyboard.setWindow(window.getWindow());

    GL.createCapabilities();

    glEnable(GL_TEXTURE_2D);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
  }

  private void loop() {
    Camera camera = new Camera(window.getWidth(), window.getHeight());
    camera.addPosition(new Vector3f(0, -250, 0));

    TileSet tileSet = new TileSet();
    ShaderProgram shaderProgram = new ShaderProgram("shader");

    Matrix4f world = new Matrix4f().setTranslation(new Vector3f(0));
    world.scale(64);

    int[][] map = Map.getSetMap();

    while (!window.shouldClose()) {
      glfwPollEvents();

      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

      for (int x = map.length - 1; x >= 0; x--) {
        for (int y = map[0].length - 1; y >= 0; y--) {
          tileSet.getTile(map[x][y]).render(x, y, shaderProgram, world, camera.getProjection());
        }
      }

      window.swapBuffers();
    }
  }

  private void dispose() {
    window.freeCallbacks();
    window.destroyWindow();

    glfwTerminate();
  }
}
