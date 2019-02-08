package com.knightlore.client;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.io.Window;
import com.knightlore.client.render.Renderer;
import com.knightlore.game.Game;

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
    while (!window.shouldClose()) {
      input();
      update();
      renderer.render(gameModel);
    }
  }

  private void input() {
    window.update();

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      glfwSetWindowShouldClose(window.getWindow(), true);
    }
  }

  private void update() {}

  private void dispose() {
    window.freeCallbacks();
    window.destroyWindow();

    glfwTerminate();
  }
}
