package com.knightlore.client.gui.screen;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_J;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.audio.Audio;
import com.knightlore.client.gui.Hud;
import com.knightlore.client.gui.engine.Timer;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.render.GameRenderer;
import com.knightlore.game.GameModel;
import com.knightlore.game.entity.Direction;
import com.knightlore.game.map.LevelMapSet;
import com.knightlore.game.map.TileSet;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class GameScreen implements IScreen {

  public static GameModel gameModel;

  Timer timer;
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
      gameModel.myPlayer().reset();
    }

    Audio.restart();
  }

  @Override
  public void input() {
    playerInputDirection = getPlayerInputDirection();

    if (Keyboard.isKeyReleased(GLFW_KEY_J)) {
      timer.setStartTime();
      gameModel.nextLevel();
    }

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(ClientState.MAIN_MENU);
    }

    if (Mouse.getXPos() > hud.getSound().getPositionX()
        && Mouse.getYPos() > hud.getSound().getPositionY()) {
      if (Mouse.isLeftButtonPressed()) {
        Audio.toggle();
      }
    }

    //        // CONTROL TO SHOW OTHER PLAYERS SCORES
    //        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
    //          hud.moveScore(20, 5);
    //        } else {
    //          hud.moveScore(-10, -230);
    //        }
  }

  @Override
  public void update(float delta) {
    float gameTime = timer.getGameTime();

    int timeLeft = 90 - Math.round(gameTime);
    if (timeLeft < 0) {
      timeLeft = 0;
    }

    String text = String.format("%02d", timeLeft);
    hud.setCounter(text);

    int lives = gameModel.myPlayer().getLives();
    hud.setP1Lives(lives);

    int score = gameModel.myPlayer().getScore();
    hud.setP1Score(score);

    Vector3f colour = gameModel.myPlayer().getColour();
    hud.getP1Score().setColour(new Vector4f(colour.x, colour.y, colour.z, 1));

    gameModel.update(delta, playerInputDirection);
  }

  @Override
  public void render() {
    hud.getSoundMute().setRender(!Audio.isOn());

    gameRenderer.render(gameModel, hud);
  }

  @Override
  public void shutdown(ClientState nextScreen) {
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
