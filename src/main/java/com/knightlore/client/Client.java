package com.knightlore.client;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import com.knightlore.client.audio.AudioHandler;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.io.Window;
import com.knightlore.client.render.Renderer;
import com.knightlore.game.Game;
import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.PlayerState;
import com.knightlore.game.map.MapSet;
import com.knightlore.game.map.TileSet;

public class Client extends Thread {

  public static Game model;

  private Window window;
  private Renderer renderer;
  private Game gameModel;
  private AudioHandler audio;

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

    audio = new AudioHandler();
    audio.toggle();

    renderer = new Renderer(window);
    if (model == null) {
      MapSet mapSet = new MapSet(new TileSet());
      gameModel = new Game("", mapSet);
      gameModel.createNewLevel(mapSet.getMap(0));
    } else {
      gameModel = model;
    }
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

    if (Keyboard.isKeyPressed(GLFW_KEY_W) // Player presses W
        && !Keyboard.isKeyDown(GLFW_KEY_A)
        && !Keyboard.isKeyDown(GLFW_KEY_S)
        && !Keyboard.isKeyDown(GLFW_KEY_D)) {
      gameModel.movePlayerInDirection(Direction.NORTH_WEST, delta);
    } else if ((Keyboard.isKeyPressed(GLFW_KEY_W) // Player presses W and D
            && Keyboard.isKeyDown(GLFW_KEY_D))
        || (Keyboard.isKeyDown(GLFW_KEY_W) && Keyboard.isKeyPressed(GLFW_KEY_D))) {
      gameModel.movePlayerInDirection(Direction.NORTH, delta);
    } else if (!Keyboard.isKeyPressed(GLFW_KEY_W) // Player presses D
        && !Keyboard.isKeyPressed(GLFW_KEY_A)
        && !Keyboard.isKeyPressed(GLFW_KEY_S)
        && Keyboard.isKeyPressed(GLFW_KEY_D)) {
      gameModel.movePlayerInDirection(Direction.NORTH_EAST, delta);
    } else if ((Keyboard.isKeyPressed(GLFW_KEY_S) // Player presses S and D
            && Keyboard.isKeyDown(GLFW_KEY_D))
        || Keyboard.isKeyDown(GLFW_KEY_S) && Keyboard.isKeyPressed(GLFW_KEY_D)) {
      gameModel.movePlayerInDirection(Direction.EAST, delta);
    } else if (!Keyboard.isKeyPressed(GLFW_KEY_W) // Player presses S
        && !Keyboard.isKeyPressed(GLFW_KEY_A)
        && Keyboard.isKeyPressed(GLFW_KEY_S)
        && !Keyboard.isKeyPressed(GLFW_KEY_D)) {
      gameModel.movePlayerInDirection(Direction.SOUTH_EAST, delta);
    } else if ((Keyboard.isKeyPressed(GLFW_KEY_S) // Player presses S and A
            && Keyboard.isKeyDown(GLFW_KEY_A))
        || Keyboard.isKeyDown(GLFW_KEY_S) && Keyboard.isKeyPressed(GLFW_KEY_A)) {
      gameModel.movePlayerInDirection(Direction.SOUTH, delta);
    } else if (!Keyboard.isKeyPressed(GLFW_KEY_W) // Player presses A
        && Keyboard.isKeyPressed(GLFW_KEY_A)
        && !Keyboard.isKeyPressed(GLFW_KEY_S)
        && !Keyboard.isKeyPressed(GLFW_KEY_D)) {
      gameModel.movePlayerInDirection(Direction.SOUTH_WEST, delta);
    } else if ((Keyboard.isKeyPressed(GLFW_KEY_W) // Player presses W and A
            && Keyboard.isKeyDown(GLFW_KEY_A))
        || Keyboard.isKeyDown(GLFW_KEY_W) && Keyboard.isKeyPressed(GLFW_KEY_A)) {
      gameModel.movePlayerInDirection(Direction.WEST, delta);
    } else {
      gameModel.updatePlayerState(PlayerState.IDLE);
    }

    if (Keyboard.isKeyReleased(GLFW_KEY_X)) {
      audio.toggle();
    }
  }

  private void dispose() {
    window.freeCallbacks();
    window.destroyWindow();

    glfwTerminate();
  }
}
