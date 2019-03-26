package com.knightlore.client.gui.screen;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.gui.engine.Timer;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.render.GameRenderer;

public class TestingLevelScreen extends GameScreen {

  public TestingLevelScreen(GameRenderer gameRenderer, Timer timer) {
    super(gameRenderer, timer);
  }

  /**
   * Method to process keyboard input during the level test
   */
  @Override
  public void input() {
    playerInputDirection = getPlayerInputDirection();

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(
          ClientState.LEVEL_EDITOR,
          false,
          GameConnection.gameModel.getCurrentLevel().getLevelMap());
    }
  }
}
