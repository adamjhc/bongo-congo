package com.knightlore.client.gui.screen;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.gui.engine.Timer;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.render.GameRenderer;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.game.GameModel;
import com.knightlore.game.GameState;
import com.knightlore.game.entity.Player;
import com.knightlore.game.entity.PlayerState;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Screen used to play through an edited level
 *
 * @author Adam W
 */
public class TestingLevelScreen extends GameScreen {

  /**
   * Initialise TestingLevelScreen
   *
   * @param guiRenderer renderer used for rendering gui elements
   * @param gameRenderer renderer used for rendering the game
   * @param timer time used for the game time
   * @author Adam W
   */
  public TestingLevelScreen(GuiRenderer guiRenderer, GameRenderer gameRenderer, Timer timer) {
    super(guiRenderer, gameRenderer, timer);
  }

  /**
   * Method to process keyboard input during the level test
   *
   * @author Adam W
   */
  @Override
  public void input() {
    GameModel gameModel = GameConnection.gameModel;
    if (Integer.parseInt(hud.getCountDown().getText()) == 0) {
      playerInputDirection = getPlayerInputDirection();
    } else {
      playerInputDirection = null;
    }

    if (Keyboard.isKeyReleased(GLFW_KEY_SPACE)) {
      gameModel.myPlayer().setClimbFlag(true);
    }

    if (Keyboard.isKeyReleased(GLFW_KEY_RIGHT_SHIFT)
        && (gameModel.myPlayer().getCooldown() == 0)
        && (gameModel.myPlayer().getPlayerState() == PlayerState.IDLE
            || gameModel.myPlayer().getPlayerState() == PlayerState.MOVING)) {
      gameModel.myPlayer().setPlayerState(PlayerState.ROLLING);
    }

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(
          ClientState.LEVEL_EDITOR, false, gameModel.getCurrentLevel().getLevelMap());
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

  /**
   * updates the game model during testing, including the timer and player lives
   *
   * @author Adam W
   */
  @Override
  public void update(float delta) {
    GameModel gameModel = GameConnection.gameModel;

    float countDown = this.countDown.getGameTime();
    int countDownTime = 5;
    int countDownLeft = countDownTime + 1 - Math.round(countDown);
    if (gameModel.getCurrentLevelIndex() > 0) {
      countDownLeft += 1;
    }
    if (countDownLeft <= 0) countDownLeft = 0;
    if (countDownLeft <= countDownTime && countDownLeft > 0) hud.getCountDown().setRender(true);

    int timeLeft = gameModel.getCurrentLevel().getDuration();
    if (countDownLeft == 0) {
      hud.getCountDown().setRender(false);
      if (timer.getStartTime() == 0) {
        timer.setStartTime();
      } else {
        float gameTime = timer.getGameTime();
        timeLeft = gameModel.getCurrentLevel().getDuration() - Math.round(gameTime);
        if (timeLeft < 0) {
          timeLeft = 0;
        }
      }
    }

    String text = String.format("%01d", countDownLeft);
    hud.setCountDown(text);

    text = String.format("%02d", timeLeft);
    hud.setCounter(text);

    int playerIndex = 0;

    int lives = gameModel.myPlayer().getLives();
    hud.setLives(playerIndex, lives);

    int score = gameModel.myPlayer().getScore();
    hud.setScore(playerIndex, score, Integer.toString(gameModel.myPlayer().getId()));

    hud.getScore(playerIndex).setColour(gameModel.myPlayer().getColour());

    Map<String, Player> players = new HashMap<>(gameModel.getPlayers());
    if (GameConnection.instance == null) {
      players.remove("1");
    } else {
      players.remove(GameConnection.instance.sessionKey);
    }
    for (Player player : players.values()) {
      playerIndex++;
      lives = player.getLives();
      hud.setLives(playerIndex, lives);

      score = player.getScore();
      hud.setScore(playerIndex, score, Integer.toString(player.getId()));

      hud.getScore(playerIndex).setColour(player.getColour());
    }

    if (gameModel.getState() == GameState.NEXT_LEVEL) {
      gameRenderer.init(gameModel);
    }

    gameModel.clientUpdate(delta, playerInputDirection, timeLeft);

    // Check for complete
    if (gameModel.getState() == GameState.SCORE
        && gameModel.myPlayer().getPlayerState() != PlayerState.DEAD) {
      Client.changeScreen(
          ClientState.NAMING_LEVEL, false, gameModel.getCurrentLevel().getLevelMap(), true);
    }

    if (gameModel.getState() == GameState.NEXT_LEVEL) {
      hud.setLevel(gameModel.getCurrentLevelIndex());
      timer.resetStartTime();
    }
  }
}
