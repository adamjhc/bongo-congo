package com.knightlore.client.gui.screen;

import static com.knightlore.client.networking.GameConnection.gameModel;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.gui.engine.Timer;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.render.GameRenderer;
import com.knightlore.game.GameModel;
import com.knightlore.game.entity.PlayerState;

public class TestingLevelScreen extends GameScreen {

  public TestingLevelScreen(GameRenderer gameRenderer, Timer timer) {
    super(gameRenderer, timer);
  }

  /** Method to process keyboard input during the level test */
  @Override
  public void input() {
    playerInputDirection = getPlayerInputDirection();

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(
          ClientState.LEVEL_EDITOR,
          false,
          gameModel.getCurrentLevel().getLevelMap());
    }

    if (Keyboard.isKeyReleased(GLFW_KEY_RIGHT_SHIFT)
            && (gameModel.myPlayer().getCooldown() == 0)
            && (gameModel.myPlayer().getPlayerState() == PlayerState.IDLE
            || gameModel.myPlayer().getPlayerState() == PlayerState.MOVING)) {
      gameModel.myPlayer().setPlayerState(PlayerState.ROLLING);
    }

    if (Keyboard.isKeyReleased(GLFW_KEY_SPACE)) {
      gameModel.myPlayer().setClimbFlag(true);
    }
  }
}
