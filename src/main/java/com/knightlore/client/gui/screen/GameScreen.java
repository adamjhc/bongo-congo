package com.knightlore.client.gui.screen;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
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
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.game.GameModel;
import com.knightlore.game.GameState;
import com.knightlore.game.Level;
import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.Player;
import com.knightlore.game.entity.PlayerState;
import com.knightlore.game.server.GameServer;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Handles the game screen startup, input, updates, rendering and cleanup
 *
 * @author Joseph, Adam C, Lewis
 */
public class GameScreen implements IScreen {

  /** Input direction */
  Direction playerInputDirection;

  /** Head up display */
  Hud hud;
  /** Level start counter */
  Timer countDown;
  /** level duration counter */
  Timer timer;
  /** Game renderer */
  GameRenderer gameRenderer;
  /** Gui renderer */
  private GuiRenderer guiRenderer;
  /** Game server */
  private GameServer gameServer;
  /** Client */
  private com.knightlore.client.networking.backend.Client gameClient;

  /**
   * Initialise renderers, timer and gui
   *
   * @param guiRenderer The gui renderer
   * @param gameRenderer The game renderer
   * @param timer The game timer
   * @author Joseph
   */
  public GameScreen(GuiRenderer guiRenderer, GameRenderer gameRenderer, Timer timer) {
    this.guiRenderer = guiRenderer;
    this.gameRenderer = gameRenderer;
    this.timer = timer;
    hud = new Hud();
  }

  @Override
  public void startup(Object... args) {
    Audio.stop(Audio.getCurrentMusic());
    Audio.play(Audio.AudioName.MUSIC_GAME);
    // Singleplayer sends levels to start game server
    if (args.length != 0) {
      GameModel gameModel = new GameModel("1");
      List<Level> levelList = (List<Level>) args[0];
      for (Level level : levelList) {
        gameModel.addLevel(level);
      }

      String playerSessionId = "1";

      int port = new Random().nextInt(65535);
      gameServer = new GameServer(UUID.randomUUID(), port, playerSessionId, gameModel, "Player 1");
      gameServer.start();

      try {
        gameClient =
            new com.knightlore.client.networking.backend.Client(InetAddress.getLocalHost(), port);
      } catch (UnknownHostException e) {
        e.printStackTrace();
      }
      gameClient.run();

      GameConnection.instance = new GameConnection(gameClient, playerSessionId);

      // Wait for GameServer to instantiate
      while (!GameConnection.instance.ready()) {
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          // Shouldn't happen
        }
      }

      GameConnection.instance.register();
      GameConnection.instance.startGame();
    }

    while (GameConnection.gameModel == null) {
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException ex) {
      }
    }

    GameModel gameModel = GameConnection.gameModel;

    hud.renderScores(gameModel);
    hud.setLevel(gameModel.getCurrentLevelIndex());

    Audio.restart();
    Mouse.hideCursor();

    countDown = new Timer();

    gameRenderer.init(gameModel);

    timer.resetStartTime();

    // Send ready
    GameConnection.instance.sendReady();

    while (GameConnection.gameModel.getState() != GameState.PLAYING) {
      try {
        TimeUnit.MILLISECONDS.sleep(50);
      } catch (InterruptedException e) {
      }
    }

    countDown.setStartTime();
  }

  @Override
  public void input() {
    GameModel gameModel = GameConnection.gameModel;

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(ClientState.MAIN_MENU, false);
      return;
    }

    if (Integer.parseInt(hud.getCountDown().getText()) == 0
        && gameModel.getState() == GameState.PLAYING) {
      playerInputDirection = getPlayerInputDirection();
    } else {
      playerInputDirection = null;
      return;
    }

    if (Keyboard.isKeyReleased(GLFW_KEY_RIGHT_SHIFT)
        && (gameModel.myPlayer().getCooldown() == 0)
        && (gameModel.myPlayer().getPlayerState() == PlayerState.IDLE
            || gameModel.myPlayer().getPlayerState() == PlayerState.MOVING)) {
      Audio.play(Audio.AudioName.SOUND_ROLL);
      gameModel.myPlayer().setPlayerState(PlayerState.ROLLING);
    }

    if (Keyboard.isKeyReleased(GLFW_KEY_SPACE)) {
      gameModel.myPlayer().setClimbFlag(true);
    }

    if (Keyboard.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
      hud.moveScore(35, hud.getScoreSideGap());
    } else {
      hud.moveScore(-10, hud.getScoreHide());
    }
  }

  @Override
  public void update(float delta) {
    GameModel gameModel = GameConnection.gameModel;

    float countDown = this.countDown.getGameTime();
    int countDownTime = 5;
    int countDownLeft = countDownTime + 1 - Math.round(countDown);
    if (gameModel.getCurrentLevelIndex() > 0) {
      countDownLeft += 0;
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
    System.err.println(gameModel.myPlayer().getColour());

    Map<String, Player> players = new LinkedHashMap<>(gameModel.getPlayers());
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
      System.out.println("NEXT LEVEL DETECTED");
      gameRenderer.init(gameModel);
      hud.setLevel(gameModel.getCurrentLevelIndex());
      timer.resetStartTime();
      this.countDown.setStartTime();
    }

    gameModel.clientUpdate(delta, playerInputDirection, timeLeft);

    // Check for complete
    if (gameModel.getState() == GameState.SCORE) {
      System.out.println("DETECT END");
      Client.changeScreen(ClientState.END, false, gameModel.getPlayers().values());
      return;
    }
  }

  @Override
  public void render() {
    hud.updateSize();

    gameRenderer.render(GameConnection.gameModel);
    guiRenderer.render(hud);
  }

  @Override
  public void shutdown(ClientState nextScreen) {
    Mouse.showCursor();
    Audio.stop(Audio.getCurrentMusic());

    hud.getCountDown().setRender(false);
    if (gameServer != null) {
      GameConnection.gameModel = null;
      gameServer.close();
      gameServer.interrupt();
      try {
        gameClient.close();
      } catch (IOException ignored) {
      }
    }
  }

  @Override
  public void cleanUp() {
    hud.cleanup();
  }

  /**
   * Return the current input direction
   *
   * @return Direction
   * @author Adam Cox
   */
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
