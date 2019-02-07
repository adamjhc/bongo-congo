package com.knightlore.client;

import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL.createCapabilities;
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
    // Setting up GLFW
    window = new Window();
    window.createWindow("Bongo Congo");
    window.setCallbacks();
    Keyboard.setWindow(window.getWindow());

    // Setting up OpenGL
    createCapabilities();
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

    int[][][] map = Map.getSetMap();

    while (!window.shouldClose()) {
      window.update();

      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

      for (int z = 0; z < map.length; z++) {
        for (int y = map[z].length - 1; y >= 0; y--) {
          for (int x = map[z][y].length - 1; x >= 0; x--) {
            int tileId = map[z][y][x];
            if (tileId != -1) {
              tileSet
                  .getTile(tileId)
                  .render(x + z, y + z, shaderProgram, world, camera.getProjection());
            }
          }
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
