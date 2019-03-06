package com.knightlore.client;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_J;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import com.knightlore.client.audio.AudioHandler;
import com.knightlore.client.gui.Hud;
import com.knightlore.client.gui.MainMenu;
import com.knightlore.client.gui.OptionsMenu;
import com.knightlore.client.gui.ServerMenu;
import com.knightlore.client.gui.engine.MouseInput;
import com.knightlore.client.gui.engine.Timer;
import com.knightlore.client.gui.engine.Window;
import com.knightlore.client.render.GameRenderer;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.client.render.LevelEditorRenderer;
import com.knightlore.game.Game;
import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.PlayerState;
import com.knightlore.game.map.LevelMapSet;
import com.knightlore.game.map.TileSet;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Client extends Thread {

  private static final int TARGET_UPS = 60;
  public static Game model;
  private static State gameState = State.MAINMENU;
  private Window window;

  private GameRenderer gameRenderer;

  private GuiRenderer menuRenderer;

  private LevelEditorRenderer levelEditorRenderer;

  private Game gameModel;

  private AudioHandler audio;

  private MouseInput mouseInput;

  private Timer timer;

  private Hud hud;

  private MainMenu menu;

  private ServerMenu serverMenu;

  private OptionsMenu optionsMenu;

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
    menu = new MainMenu(window);
    serverMenu = new ServerMenu(window);
    optionsMenu = new OptionsMenu(window);

    menuRenderer = new GuiRenderer(window);
    gameRenderer = new GameRenderer(window);
    levelEditorRenderer = new LevelEditorRenderer(window);

    if (model == null) {
      LevelMapSet mapSet = new LevelMapSet(new TileSet());
      gameModel = new Game("");

      gameModel.createNewLevel(mapSet.getMap(0));
      gameModel.createNewLevel(mapSet.getMap(1));
      gameModel.addPlayer("1");
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

    switch (gameState) {
      case MAINMENU:

        // SINGEPLAYER BUTTON
        if (mouseInput.getXPos() > menu.getSingleplayer().getPositionX()
            && mouseInput.getXPos() < menu.getSingleplayer().getPositionX()+menu.getSingleplayer().getSize()
            && mouseInput.getYPos() > menu.getSingleplayer().getPositionY()
            && mouseInput.getYPos() < menu.getSingleplayer().getPositionY()+menu.getSingleplayer().getHeight()) {
          menu.getSingleplayer().getMesh().getMaterial().setColour();;
          if (mouseInput.isLeftButtonPressed()) {
            audio.toggle();
            audio.toggle();

            timer.setStartTime();
            gameModel.myPlayer().nextLevel();
            gameState = State.SINGLEPLAYER;
          }
        } else menu.getSingleplayer().getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));

        // MULTIPLAYER BUTTON
        if (mouseInput.getXPos() > menu.getMultiplayer().getPositionX()
            && mouseInput.getXPos() < menu.getMultiplayer().getPositionX()+menu.getMultiplayer().getSize()
            && mouseInput.getYPos() > menu.getMultiplayer().getPositionY()
            && mouseInput.getYPos() < menu.getMultiplayer().getPositionY()+menu.getMultiplayer().getHeight()) {
          menu.getMultiplayer().getMesh().getMaterial().getColour();
          if (mouseInput.isLeftButtonPressed()) {
            gameState = State.SERVERMENU;
          }
        } else menu.getMultiplayer().getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));

        // OPTIONS BUTTON
        if (mouseInput.getXPos() > menu.getOptions().getPositionX()
            && mouseInput.getXPos() < menu.getOptions().getPositionX()+menu.getOptions().getSize()
            && mouseInput.getYPos() > menu.getOptions().getPositionY()
            && mouseInput.getYPos() < menu.getOptions().getPositionY()+menu.getOptions().getHeight()) {
        	menu.getOptions().getMesh().getMaterial().getColour();
          if (mouseInput.isLeftButtonPressed()) {
            gameState = State.OPTIONSMENU;
          }
        } else menu.getOptions().getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));

        // QUIT BUTTON
        if (mouseInput.getXPos() > menu.getQuit().getPositionX()
            && mouseInput.getXPos() < menu.getQuit().getPositionX()+menu.getQuit().getSize()
            && mouseInput.getYPos() > menu.getQuit().getPositionY()
            && mouseInput.getYPos() < menu.getQuit().getPositionY()+menu.getQuit().getHeight()) {
        	menu.getQuit().getMesh().getMaterial().getColour();
          if (mouseInput.isLeftButtonPressed()) {
            glfwSetWindowShouldClose(window.getWindowHandle(), true);
          }
        } else menu.getQuit().getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));

        audio();

        // ESC TO EXIT
        if (window.isKeyReleased(GLFW_KEY_ESCAPE)) {
          glfwSetWindowShouldClose(window.getWindowHandle(), true);
        }

        break;

      case SERVERMENU:
        if (mouseInput.getXPos() > window.getWidth() / 2 - 225
            && mouseInput.getXPos() < window.getWidth() / 2 + 255
            && mouseInput.getYPos() > window.getHeight() / 2 - 185
            && mouseInput.getYPos() < window.getHeight() / 2 + 200) {
          if (mouseInput.scrolledDown()) {
            serverMenu.moveDown();
          }
          if (mouseInput.scrolledUp()) {
            serverMenu.moveUp();
          }
          if (mouseInput.isLeftButtonPressed()) {
            serverMenu.highlight(window, mouseInput.getYPos());
          }
        }

        if (mouseInput.getXPos() > window.getWidth() / 2 - 82.5f
            && mouseInput.getXPos() < window.getWidth() / 2 + 82.5f
            && mouseInput.getYPos() > window.getHeight() / 2 + 215
            && mouseInput.getYPos() < window.getHeight() / 2 + 230) {
          serverMenu.setCreate();
          if (mouseInput.isLeftButtonPressed()) {
            serverMenu.createServer(window);
          }
        } else serverMenu.setRestoreCreate();

        if (mouseInput.getXPos() > window.getWidth() / 2 - 30
            && mouseInput.getXPos() < window.getWidth() / 2 + 30
            && mouseInput.getYPos() < window.getHeight() / 2 + 270
            && mouseInput.getYPos() > window.getHeight() / 2 + 255) {
          serverMenu.setExit();
          if (mouseInput.isLeftButtonPressed()) {
            gameState = State.MAINMENU;
          }
        } else serverMenu.setRestoreExit();

        leaveMenu();

        break;

      case OPTIONSMENU:
        if (mouseInput.getXPos() > window.getWidth() / 2 + 85
            && mouseInput.getXPos() < window.getWidth() / 2 + 115
            && mouseInput.getYPos() > window.getHeight() / 2 - 145
            && mouseInput.getYPos() < window.getHeight() / 2 - 115) {
          optionsMenu.setIncVol();
          if (mouseInput.isLeftButtonHeld()) {
            optionsMenu.incVolume();
            audio.incVolume();
          }
        } else optionsMenu.setRestoreIncVol();

        if (mouseInput.getXPos() > window.getWidth() / 2 - 115
            && mouseInput.getXPos() < window.getWidth() / 2 - 85
            && mouseInput.getYPos() > window.getHeight() / 2 - 145
            && mouseInput.getYPos() < window.getHeight() / 2 - 115) {
          optionsMenu.setDecVol();
          if (mouseInput.isLeftButtonHeld()) {
            optionsMenu.decVolume();
            audio.decVolume();
          }
        } else optionsMenu.setRestoreDecVol();

        if (mouseInput.getXPos() > window.getWidth() / 2 - 30
            && mouseInput.getXPos() < window.getWidth() / 2 + 30
            && mouseInput.getYPos() < window.getHeight() / 2 + 270
            && mouseInput.getYPos() > window.getHeight() / 2 + 255) {
          optionsMenu.setExit();
          if (mouseInput.isLeftButtonPressed()) {
            gameState = State.MAINMENU;
          }
        } else optionsMenu.setRestoreExit();

        leaveMenu();

        break;

      case SINGLEPLAYER:
        movement(delta);

        if (window.isKeyReleased(GLFW_KEY_J)) {
          gameModel.nextLevel();
        }

        leaveGame();

        audio();

//        // CONTROL TO SHOW OTHER PLAYERS SCORES
//        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
//          hud.moveScore(20, 5);
//        } else {
//          hud.moveScore(-10, -230);
//        }

        break;

      case DEAD:
    	//gameModel.updatePlayerState(PlayerState.IDLE);
    	
        if (window.isKeyReleased(GLFW_KEY_J)) {
          gameState = State.SINGLEPLAYER;
          gameModel.nextLevel();
        }
        
        leaveGame();

        audio();

        break;
    }
  }

  private void update(float delta) {

    switch (gameState) {
      case MAINMENU:
        break;

      case SERVERMENU:
        break;

      case OPTIONSMENU:
        break;

      case SINGLEPLAYER:
        float gameTime = timer.getGameTime();
        
        int timeLeft = 90 - Math.round(gameTime);
        if (timeLeft < 0) {
          timeLeft = 0;
        }
        String text = String.format("%02d", timeLeft);

		hud.setCounter(text);
		gameModel.update(delta);
		  
		int lives = gameModel.myPlayer().getLives();
		hud.setP1Lives(lives);
		  
		int score = gameModel.myPlayer().getScore();
		hud.setP1Score(score);
		  
		Vector3f colour = gameModel.myPlayer().getColour();
		hud.setP1ScoreColour(colour);
		
		if (gameModel.myPlayer().getPlayerState() == PlayerState.DEAD) {
			gameState = State.DEAD;
		}

        break;

      case DEAD:
        gameModel.update(delta);

        break;
    }
  }

  private void render(Game gameModel) {
    switch (gameState) {
      case MAINMENU:
        menuRenderer.render(menu);

        break;

      case SERVERMENU:
        menuRenderer.render(serverMenu);

        break;

      case LEVEL_EDITOR:
        levelEditorRenderer.render(new LevelMapSet(new TileSet()).getMap(0), new Vector3f(0, 0, 0));

        break;

      case OPTIONSMENU:
        menuRenderer.render(optionsMenu);

        break;

      case SINGLEPLAYER:
        gameRenderer.render(gameModel, hud);

        break;

      case DEAD:
        gameRenderer.render(gameModel, hud);

        break;
    }
  }

  private void movement(float delta) {
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
  }

  private void audio() {
    if (mouseInput.getXPos() > window.getWidth() - 35
        && mouseInput.getYPos() > window.getHeight() - 35) {
      if (mouseInput.isLeftButtonPressed()) {
        menu.toggleSound();
        hud.toggleSound();
        audio.toggle();
      }
    }
  }

  private void leaveGame() {
    if (window.isKeyReleased(GLFW_KEY_ESCAPE)) {
      audio.toggle();
      audio.toggle();
      gameState = State.MAINMENU;
    }
  }

  private void leaveMenu() {
    if (window.isKeyReleased(GLFW_KEY_ESCAPE)) {
      gameState = State.MAINMENU;
    }
  }

  private void dispose() {
    hud.cleanup();
    gameRenderer.cleanup();

    window.freeCallbacks();
    window.destroyWindow();

    glfwTerminate();
  }

  private enum State {
    MAINMENU,
    SERVERMENU,
    LEVEL_EDITOR,
    OPTIONSMENU,
    LOBBY,
    SINGLEPLAYER,
    DEAD,
    END
  }
}
