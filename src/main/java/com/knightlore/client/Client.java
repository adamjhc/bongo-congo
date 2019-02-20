package com.knightlore.client;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_L;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import com.knightlore.client.audio.AudioHandler;
import com.knightlore.client.gui.Hud;
import com.knightlore.client.gui.engine.MouseInput;
import com.knightlore.client.gui.engine.Timer;
import com.knightlore.client.gui.engine.Window;
import com.knightlore.client.render.Renderer;
import com.knightlore.game.Game;
import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.PlayerState;
import com.knightlore.game.map.MapSet;
import com.knightlore.game.map.TileSet;

public class Client extends Thread {

  public static Game model;

  private static final int TARGET_UPS = 60;

  private Window window;

  private Renderer renderer;

  private Game gameModel;

  private AudioHandler audio;

  private MouseInput mouseInput;

  private Timer timer;

  private Hud hud;

  public static void main(String[] args) {
    new Client().run();
  }

  public void run() {
    try {
      init();
      loop();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dispose();
    }
  }

  private void init() throws Exception {
    // Setting up GLFW
    window = new Window("Bongo Congo", 1280, 720);
    mouseInput = new MouseInput();

    timer = new Timer();
    audio = new AudioHandler();

    window.init();
    timer.init();
    mouseInput.init(window);

    hud = new Hud(window);

    renderer = new Renderer(window);
    if (model == null) {
      MapSet mapSet = new MapSet(new TileSet());
      gameModel = new Game("", mapSet);
      gameModel.createNewLevel(mapSet.getMap(0));
    } else {
      gameModel = model;
    }

    audio.toggle();
  }

  private void loop() {
    float elapsedTime;
    float accumulator = 0f;
    float interval = 1f / TARGET_UPS;

    while (!window.windowShouldClose()) {
      elapsedTime = timer.getElapsedTime();

      accumulator += elapsedTime;

      input(interval);

      while (accumulator >= interval) {
        update(interval);

        accumulator -= interval;
      }

      render(gameModel);
    }
  }

  private void input(float delta) {
    window.update();

    if (window.isKeyPressed(GLFW_KEY_W) // Player presses W
        && !window.isKeyPressed(GLFW_KEY_A)
        && !window.isKeyPressed(GLFW_KEY_S)
        && !window.isKeyPressed(GLFW_KEY_D)) {
      gameModel.movePlayerInDirection(Direction.NORTH_WEST, delta);
    } else if (window.isKeyPressed(GLFW_KEY_W) // Player presses W and D
        && window.isKeyPressed(GLFW_KEY_D)) {
      gameModel.movePlayerInDirection(Direction.NORTH, delta);
    } else if (!window.isKeyPressed(GLFW_KEY_W) // Player presses D
        && !window.isKeyPressed(GLFW_KEY_A)
        && !window.isKeyPressed(GLFW_KEY_S)
        && window.isKeyPressed(GLFW_KEY_D)) {
      gameModel.movePlayerInDirection(Direction.NORTH_EAST, delta);
    } else if (window.isKeyPressed(GLFW_KEY_S) // Player presses S and D
        && window.isKeyPressed(GLFW_KEY_D)) {
      gameModel.movePlayerInDirection(Direction.EAST, delta);
    } else if (!window.isKeyPressed(GLFW_KEY_W) // Player presses S
        && !window.isKeyPressed(GLFW_KEY_A)
        && window.isKeyPressed(GLFW_KEY_S)
        && !window.isKeyPressed(GLFW_KEY_D)) {
      gameModel.movePlayerInDirection(Direction.SOUTH_EAST, delta);
    } else if (window.isKeyPressed(GLFW_KEY_S) // Player presses S and A
        && window.isKeyPressed(GLFW_KEY_A)) {
      gameModel.movePlayerInDirection(Direction.SOUTH, delta);
    } else if (!window.isKeyPressed(GLFW_KEY_W) // Player presses A
        && window.isKeyPressed(GLFW_KEY_A)
        && !window.isKeyPressed(GLFW_KEY_S)
        && !window.isKeyPressed(GLFW_KEY_D)) {
      gameModel.movePlayerInDirection(Direction.SOUTH_WEST, delta);
    } else if (window.isKeyPressed(GLFW_KEY_W) // Player presses W and A
        && window.isKeyPressed(GLFW_KEY_A)) {
      gameModel.movePlayerInDirection(Direction.WEST, delta);
    } else {
      gameModel.updatePlayerState(PlayerState.IDLE);
    }

    if (window.isKeyReleased(GLFW_KEY_L)) {
      hud.setP1Lives();
    }

    if (window.isKeyReleased(GLFW_KEY_P)) {
      hud.setP1Score();
    }

    if (mouseInput.getXPos() < 65 && mouseInput.getYPos() > window.getHeight() - 25) {
      hud.setExit();
      if (mouseInput.isLeftButtonPressed()) {
        glfwSetWindowShouldClose(window.getWindowHandle(), true);
      }
    } else hud.setRestoreExit();

    if (mouseInput.getXPos() > window.getWidth() - 35
        && mouseInput.getYPos() > window.getHeight() - 35) {
      // hud.setSound();
      if (mouseInput.isLeftButtonPressed()) {
        hud.setSoundOff();
        audio.toggle();
      }
    } else hud.setRestoreSound();

    if (mouseInput.getXPos() > window.getWidth() / 2 - 90
        && mouseInput.getXPos() < window.getWidth() / 2 + 90
        && mouseInput.getYPos() > window.getHeight() / 2 + 90
        && mouseInput.getYPos() < window.getHeight() / 2 + 115) {
      hud.setSingleplayer();
      if (mouseInput.isLeftButtonPressed()) {
        // System.out.println("Singleplayer button pressed");
        // hud.deleteGameItem();
      }
    } else hud.setRestoreSingleplayer();

    if (mouseInput.isLeftButtonPressed()) {
      // System.out.println(mouseInput.getXPos()+" "+mouseInput.getYPos());
    }
    if (mouseInput.isRightButtonPressed()) {
      // System.out.println(mouseInput.getXPos()+" "+mouseInput.getYPos());
    }
  }

  private void update(float delta) {
    float gameTime = timer.getGameTime();

    int timeLeft = 90 - Math.round(gameTime);
    if (timeLeft < 0) {
      timeLeft = 0;
    }

    hud.setCounter("Time: " + timeLeft);
    gameModel.update(delta);
  }

  private void render(Game gameModel) {
    renderer.render(gameModel, window, hud);
  }

  private void dispose() {
    hud.cleanup();
    renderer.cleanup();

    window.freeCallbacks();
    window.destroyWindow();

    glfwTerminate();
  }
}
