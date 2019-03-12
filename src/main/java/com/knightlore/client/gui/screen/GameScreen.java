package com.knightlore.client.gui.screen;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_J;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import java.lang.Thread.State;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.audio.Audio;
import com.knightlore.client.gui.Hud;
import com.knightlore.client.gui.engine.Timer;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.render.GameRenderer;
import com.knightlore.game.GameModel;
import com.knightlore.game.GameState;
import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.Player;
import com.knightlore.game.entity.PlayerState;
import com.knightlore.game.map.LevelMapSet;
import com.knightlore.game.map.TileSet;

public class GameScreen implements IScreen {

  GameModel gameModel;

  Timer timer;
  Timer countDown;
  Hud hud;
  Direction playerInputDirection;

  private GameRenderer gameRenderer;

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
      gameModel.addPlayer("1");
    } else {
      gameModel = (GameModel) args[0];
    }

    hud.renderScores(gameModel);
    
    Audio.restart();
    Mouse.hideCursor();

    gameRenderer.init(gameModel); 
    
    timer.setStartTime(0);
    hud.getCountDown().setRender(true);
    countDown = new Timer();
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
    	hud.getCountDown().setRender(true);
    	timer.setStartTime(0);
      gameModel.nextLevel();
      countDown.setStartTime();
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
  	float countDownTime = countDown.getGameTime();
  	int countDownLeft = 5 - Math.round(countDownTime);
  	if (countDownLeft <= 0) countDownLeft = 0;
  	
  	int timeLeft = 90;
  	if (countDownLeft == 0) {
  		hud.getCountDown().setRender(false);
  		if (timer.getStartTime() == 0) {
  			timer.setStartTime();
  		}
  		else {
        float gameTime = timer.getGameTime();
        timeLeft = 90 - Math.round(gameTime);
        if (timeLeft < 0) {
          timeLeft = 0;
        }
  		}
  	} 
  	
  	String text = String.format("%01d", countDownLeft);
  	hud.setCountDown(text);
  	
    text = String.format("%02d", timeLeft);
    hud.setCounter(text);
    
    Map<String, Player> players = gameModel.getPlayers();
    //GET ALL PLAYERS THAT AREN'T YOU?
    //UPDATE THEIR SCORE, LIVES AND COLOUR

    int lives = gameModel.myPlayer().getLives();
    hud.setLives(0, lives);

    int score = gameModel.myPlayer().getScore();
    hud.setScore(0, score);

    hud.getScore(0).setColour(gameModel.myPlayer().getColour());

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
