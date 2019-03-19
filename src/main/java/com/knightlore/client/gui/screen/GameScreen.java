package com.knightlore.client.gui.screen;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_J;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.audio.Audio;
import com.knightlore.client.gui.Hud;
import com.knightlore.client.gui.engine.Timer;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.render.GameRenderer;
import com.knightlore.game.GameModel;
import com.knightlore.game.GameState;
import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.Player;
import com.knightlore.game.map.LevelMapSet;
import com.knightlore.game.map.TileSet;
import java.util.HashMap;
import java.util.Map;

public class GameScreen implements IScreen {

  GameModel gameModel;

  Timer timer;
  Timer countDown;
  Hud hud;
  Direction playerInputDirection;

  private GameRenderer gameRenderer;

  private int levelTime = 90;
  private int countDownTime = 5;

  public GameScreen(GameRenderer gameRenderer, Timer timer) {
    this.gameRenderer = gameRenderer;
    this.timer = timer;
    hud = new Hud();
  }

  @Override
  public void startup(Object... args) {
    if (args.length == 0) {
      LevelMapSet mapSet = new LevelMapSet(new TileSet());
      gameModel = new GameModel("");
      gameModel.createNewLevel(mapSet.getMap(0));
      gameModel.createNewLevel(mapSet.getMap(1));
      gameModel.createNewLevel(mapSet.getMap(2));
      gameModel.addPlayer("1");
    } else {
      gameModel = (GameModel) args[0];
    }

    hud.renderScores(gameModel);

    Audio.restart();
    Mouse.hideCursor();

    countDown = new Timer();

    gameRenderer.init(gameModel);

    timer.resetStartTime();
    countDown.setStartTime();
  }

  @Override
  public void input() {
    if (Integer.parseInt(hud.getCountDown().getText()) == 0) {
      playerInputDirection = getPlayerInputDirection();
    } else {
      playerInputDirection = null;
    }

    if (Keyboard.isKeyReleased(GLFW_KEY_J)) {
      gameModel.nextLevel();
      
      if (gameModel.getState() == GameState.NEXT_LEVEL) {
      	hud.setLevel(gameModel.getCurrentLevelIndex());
        timer.resetStartTime();
        countDown.setStartTime();
      }
      else if (gameModel.getState() == GameState.SCORE) {
    		Client.changeScreen(ClientState.END, gameModel);
    	}
    }

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(ClientState.MAIN_MENU);
    }

    if (Keyboard.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
      hud.moveScore(35, hud.getScoreSideGap());
    } else {
      hud.moveScore(-10, hud.getScoreHide());
    }
  }

  @Override
  public void update(float delta) {
    float countDown = this.countDown.getGameTime();
    int countDownLeft = this.countDownTime + 1 - Math.round(countDown);
    if (countDownLeft <= 0) countDownLeft = 0;
    if (countDownLeft == this.countDownTime) hud.getCountDown().setRender(true);

    int timeLeft = levelTime;
    if (countDownLeft == 0) {
      hud.getCountDown().setRender(false);
      if (timer.getStartTime() == 0) {
        timer.setStartTime();
      } else {
        float gameTime = timer.getGameTime();
        timeLeft = levelTime - Math.round(gameTime);
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
    hud.setScore(playerIndex, score);

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
      hud.setScore(playerIndex, score);

      hud.getScore(playerIndex).setColour(player.getColour());
    }

    if (gameModel.getState() == GameState.NEXT_LEVEL) {
      gameRenderer.init(gameModel);
    }

    gameModel.update(delta, playerInputDirection);
  }

  @Override
  public void render() {
    hud.updateSize();

    gameRenderer.render(gameModel, hud);
  }

  @Override
  public void shutdown(ClientState nextScreen) {
    Mouse.showCursor();
    Audio.restart();
    
    hud.getCountDown().setRender(false);
  }

  @Override
  public void cleanUp() {
    hud.cleanup();
  }

  Direction getPlayerInputDirection() {
    if (Keyboard.isKeyPressed(GLFW_KEY_W) // Player presses W
        && !Keyboard.isKeyPressed(GLFW_KEY_A)
        && !Keyboard.isKeyPressed(GLFW_KEY_S)
        && !Keyboard.isKeyPressed(GLFW_KEY_D)) {
      return Direction.NORTH_WEST;
    } else if (Keyboard.isKeyPressed(GLFW_KEY_W) // Player presses W and D
        && Keyboard.isKeyPressed(GLFW_KEY_D)) {
      return Direction.NORTH;
    } else if (!Keyboard.isKeyPressed(GLFW_KEY_W) // Player presses D
        && !Keyboard.isKeyPressed(GLFW_KEY_A)
        && !Keyboard.isKeyPressed(GLFW_KEY_S)
        && Keyboard.isKeyPressed(GLFW_KEY_D)) {
      return Direction.NORTH_EAST;
    } else if (Keyboard.isKeyPressed(GLFW_KEY_S) // Player presses S and D
        && Keyboard.isKeyPressed(GLFW_KEY_D)) {
      return Direction.EAST;
    } else if (!Keyboard.isKeyPressed(GLFW_KEY_W) // Player presses S
        && !Keyboard.isKeyPressed(GLFW_KEY_A)
        && Keyboard.isKeyPressed(GLFW_KEY_S)
        && !Keyboard.isKeyPressed(GLFW_KEY_D)) {
      return Direction.SOUTH_EAST;
    } else if (Keyboard.isKeyPressed(GLFW_KEY_S) // Player presses S and A
        && Keyboard.isKeyPressed(GLFW_KEY_A)) {
      return Direction.SOUTH;
    } else if (!Keyboard.isKeyPressed(GLFW_KEY_W) // Player presses A
        && Keyboard.isKeyPressed(GLFW_KEY_A)
        && !Keyboard.isKeyPressed(GLFW_KEY_S)
        && !Keyboard.isKeyPressed(GLFW_KEY_D)) {
      return Direction.SOUTH_WEST;
    } else if (Keyboard.isKeyPressed(GLFW_KEY_W) // Player presses W and A
        && Keyboard.isKeyPressed(GLFW_KEY_A)) {
      return Direction.WEST;
    } else {
      return null;
    }
  }
}
