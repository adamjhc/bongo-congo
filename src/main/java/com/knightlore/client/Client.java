package com.knightlore.client;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.knightlore.client.audio.AudioHandler;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.io.Window;
import com.knightlore.client.render.Renderer;
import com.knightlore.game.Game;
import com.knightlore.game.entity.Direction;

public class Client {

  private Window window;
  private Renderer renderer;
  private Game gameModel;
  private AudioHandler audio;
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
    
    try {
    	audio = new AudioHandler();
    	audio.toggle();
    } catch (Exception e) {
    	e.printStackTrace();
    }

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

    if (Keyboard.isKeyPressed(GLFW_KEY_W)) {
      gameModel.movePlayerInDirection(Direction.NORTH_WEST, delta);
    }
    
    if (Keyboard.isKeyReleased(GLFW_KEY_X)) {
    	try {
			audio.toggle();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
  }

  private void dispose() {
    window.freeCallbacks();
    window.destroyWindow();

    glfwTerminate();
  }
}
