package com.knightlore.client;

import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.io.Window;
import com.knightlore.client.render.Renderer;
import com.knightlore.game.Game;
import com.knightlore.game.entity.Direction;

import static org.lwjgl.glfw.GLFW.*;

public class Client {

  private Window window;
  private Renderer renderer;
  private Game gameModel;
  //  private PlayerRenderer player;

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
    Mouse.setWindow(window.getWindow());

    renderer = new Renderer(window);
    gameModel = new Game();
  }

  private void loop() {
    boolean canRender = false;
    float targetFPS = 60;
    float interval = 1 / targetFPS;
    float delta = 0.0f;
    double previousTime = glfwGetTime();

    while (!window.shouldClose()) {
      double currentTime = glfwGetTime();
      delta += currentTime - previousTime;
      previousTime = currentTime;

      while (delta >= interval) {
        delta -= interval;

        canRender = true;

        input(delta);
        gameModel.update(delta);
      }

      if (canRender) {
        renderer.render(gameModel);
      }
    }
  }

  private void input(float delta) {
    window.update();

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      glfwSetWindowShouldClose(window.getWindow(), true);
    }

    if (Keyboard.isKeyPressed(GLFW_KEY_W) && !Keyboard.isKeyDown(GLFW_KEY_A) && !Keyboard.isKeyDown(GLFW_KEY_S) && !Keyboard.isKeyDown(GLFW_KEY_D)) {
      gameModel.movePlayerInDirection(Direction.NORTH_EAST, delta);
    }

    if ((Keyboard.isKeyPressed(GLFW_KEY_W)
            && Keyboard.isKeyDown(GLFW_KEY_A))
            || Keyboard.isKeyDown(GLFW_KEY_W)
            && Keyboard.isKeyPressed(GLFW_KEY_A)) {
      gameModel.movePlayerInDirection(Direction.NORTH, delta);
    }

    if ((Keyboard.isKeyPressed(GLFW_KEY_S)
            && Keyboard.isKeyDown(GLFW_KEY_D))
            || Keyboard.isKeyDown(GLFW_KEY_S)
            && Keyboard.isKeyPressed(GLFW_KEY_D)) {
      gameModel.movePlayerInDirection(Direction.SOUTH, delta);
    }

    if ((Keyboard.isKeyPressed(GLFW_KEY_W)
            && Keyboard.isKeyDown(GLFW_KEY_D))
            || Keyboard.isKeyDown(GLFW_KEY_W)
            && Keyboard.isKeyPressed(GLFW_KEY_D)) {
      gameModel.movePlayerInDirection(Direction.EAST, delta);
    }

    if ((Keyboard.isKeyPressed(GLFW_KEY_S)
            && Keyboard.isKeyDown(GLFW_KEY_A))
            || (Keyboard.isKeyDown(GLFW_KEY_W)
            && Keyboard.isKeyPressed(GLFW_KEY_S))) {
      gameModel.movePlayerInDirection(Direction.WEST, delta);
    }

    if (!Keyboard.isKeyPressed(GLFW_KEY_W) && !Keyboard.isKeyPressed(GLFW_KEY_A) && !Keyboard.isKeyPressed(GLFW_KEY_S) && Keyboard.isKeyPressed(GLFW_KEY_D)) {
      gameModel.movePlayerInDirection(Direction.NORTH_WEST, delta);
    }

    if (!Keyboard.isKeyPressed(GLFW_KEY_W) && !Keyboard.isKeyPressed(GLFW_KEY_A) && Keyboard.isKeyPressed(GLFW_KEY_S) && !Keyboard.isKeyPressed(GLFW_KEY_D)) {
      gameModel.movePlayerInDirection(Direction.SOUTH_WEST, delta);
    }

    if (!Keyboard.isKeyPressed(GLFW_KEY_W) && Keyboard.isKeyPressed(GLFW_KEY_A) && !Keyboard.isKeyPressed(GLFW_KEY_S) && !Keyboard.isKeyPressed(GLFW_KEY_D)) {
      gameModel.movePlayerInDirection(Direction.SOUTH_EAST, delta);
    }
  }

  private void dispose() {
    window.freeCallbacks();
    window.destroyWindow();

    glfwTerminate();
  }
}
