package com.knightlore.client;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_J;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_3;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_9;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_7;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_8;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import com.knightlore.client.audio.AudioHandler;
import com.knightlore.client.gui.Hud;
import com.knightlore.client.gui.MainMenu;
import com.knightlore.client.gui.OptionsMenu;
import com.knightlore.client.gui.PreLevelEditor;
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
import com.knightlore.game.map.LevelMap;
import com.knightlore.game.map.LevelMapSet;
import com.knightlore.game.map.TileSet;
import com.knightlore.game.map.TileType;

import org.joml.Vector3f;

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
  
  private PreLevelEditor preLevelEditor;
  
  private Vector3f cameraPosition;
  
  private LevelMap editorMap;
  
  private int currentTileX, currentTileY, currentTileZ;

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
    preLevelEditor = new PreLevelEditor(window);

    menuRenderer = new GuiRenderer(window);
    gameRenderer = new GameRenderer(window);
    levelEditorRenderer = new LevelEditorRenderer(window);

    if (model == null) {
      //LevelMapSet mapSet = new LevelMapSet(new TileSet());
      gameModel = new Game("");

      //gameModel.createNewLevel(mapSet.getMap(0));
      //gameModel.createNewLevel(mapSet.getMap(1));
      //gameModel.addPlayer("1");
    } else {
      gameModel = model;
    }

    audio.toggle();
    cameraPosition = new Vector3f(0, 0, 0);
    currentTileX = 0;
    currentTileY = 0;
    currentTileZ = 0;
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
        if (mouseInput.getXPos() > window.getWidth() / 2 - 90
            && mouseInput.getXPos() < window.getWidth() / 2 + 90
            && mouseInput.getYPos() > window.getHeight() / 2 + 95
            && mouseInput.getYPos() < window.getHeight() / 2 + 115) {
          menu.setSingleplayer();
          if (mouseInput.isLeftButtonPressed()) {
        	LevelMapSet mapSet = new LevelMapSet(new TileSet());
        	gameModel.createNewLevel(mapSet.getMap(0));
            gameModel.createNewLevel(mapSet.getMap(1));
            gameModel.addPlayer("1");
        	  
            audio.toggle();
            audio.toggle(); // eventually change this so switches between menu and game music

            timer.setStartTime();
            hud.resetP1Lives();
            gameState = State.SINGLEPLAYER;
          }
        } else menu.setRestoreSingleplayer();

        // MULTIPLAYER BUTTON
        if (mouseInput.getXPos() > window.getWidth() / 2 - 84
            && mouseInput.getXPos() < window.getWidth() / 2 + 84
            && mouseInput.getYPos() > window.getHeight() / 2 + 117
            && mouseInput.getYPos() < window.getHeight() / 2 + 135) {
          menu.setMultiplayer();
          if (mouseInput.isLeftButtonPressed()) {
            gameState = State.SERVERMENU;
          }
        } else menu.setRestoreMultiplayer();
        
        // LEVEL EDITOR BUTTON
        if (mouseInput.getXPos() > window.getWidth() / 2 - 84
        	&& mouseInput.getXPos() < window.getWidth() / 2 + 84
    		&& mouseInput.getYPos() > window.getHeight() / 2 + 138
    		&& mouseInput.getYPos() < window.getHeight() / 2 + 155) {
    			menu.setLevelEditor();
    			if (mouseInput.isLeftButtonPressed()) {
    				gameState = State.PRE_EDITOR;
    			}
    	} else menu.setRestoreLevelEditor();

        // OPTIONS BUTTON
        if (mouseInput.getXPos() > window.getWidth() / 2 - 52
            && mouseInput.getXPos() < window.getWidth() / 2 + 52
            && mouseInput.getYPos() > window.getHeight() / 2 + 158
            && mouseInput.getYPos() < window.getHeight() / 2 + 175) {
          menu.setOptions();
          if (mouseInput.isLeftButtonPressed()) {
            gameState = State.OPTIONSMENU;
          }
        } else menu.setRestoreOptions();

        // QUIT BUTTON
        if (mouseInput.getXPos() > window.getWidth() / 2 - 30
            && mouseInput.getXPos() < window.getWidth() / 2 + 30
            && mouseInput.getYPos() > window.getHeight() / 2 + 180
            && mouseInput.getYPos() < window.getHeight() / 2 + 196) {
          menu.setQuit();
          if (mouseInput.isLeftButtonPressed()) {
            glfwSetWindowShouldClose(window.getWindowHandle(), true);
          }
        } else menu.setRestoreQuit();

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
        
      case PRE_EDITOR:
    	  if (mouseInput.getXPos() > window.getWidth()/2 - 52
    		  && mouseInput.getXPos() < window.getWidth()/2 - 37
    		  && mouseInput.getYPos() > window.getHeight()/2 - 90
    		  && mouseInput.getYPos() < window.getHeight()/2 - 75) {
    		  preLevelEditor.setWLeft();
    		  if (mouseInput.isLeftButtonPressed()) {
    			  preLevelEditor.decWidth();
    		  }
    	  } else preLevelEditor.setRestoreWLeft();
    	  
    	  if (mouseInput.getXPos() > window.getWidth()/2 + 22
    		  && mouseInput.getXPos() < window.getWidth()/2 + 37
    		  && mouseInput.getYPos() > window.getHeight()/2 - 90
    		  && mouseInput.getYPos() < window.getHeight()/2 - 75) {
    		  preLevelEditor.setWRight();
    		  if (mouseInput.isLeftButtonPressed()) {
    			  preLevelEditor.incWidth();
    		  }
    	  } else preLevelEditor.setRestoreWRight();
    	  
    	  if (mouseInput.getXPos() > window.getWidth()/2 - 52
        		  && mouseInput.getXPos() < window.getWidth()/2 - 37
        		  && mouseInput.getYPos() > window.getHeight()/2 + 20
        		  && mouseInput.getYPos() < window.getHeight()/2 + 35) {
        		  preLevelEditor.setLLeft();
        		  if (mouseInput.isLeftButtonPressed()) {
        			  preLevelEditor.decLength();
        		  }
        	  } else preLevelEditor.setRestoreLLeft();
        	  
          if (mouseInput.getXPos() > window.getWidth()/2 + 22
        	  && mouseInput.getXPos() < window.getWidth()/2 + 37
        	  && mouseInput.getYPos() > window.getHeight()/2 + 20
        	  && mouseInput.getYPos() < window.getHeight()/2 + 35) {
        	  preLevelEditor.setLRight();
        	  if (mouseInput.isLeftButtonPressed()) {
        		  preLevelEditor.incLength();
        	  }
          } else preLevelEditor.setRestoreLRight();
        	  
          if (mouseInput.getXPos() > window.getWidth()/2 - 52
        	  && mouseInput.getXPos() < window.getWidth()/2 - 37
        	  && mouseInput.getYPos() > window.getHeight()/2 + 130
        	  && mouseInput.getYPos() < window.getHeight()/2 + 145) {
              preLevelEditor.setHLeft();
              if (mouseInput.isLeftButtonPressed()) {
            	  preLevelEditor.decHeight();
              }
          } else preLevelEditor.setRestoreHLeft();
            	  
          if (mouseInput.getXPos() > window.getWidth()/2 + 22
              && mouseInput.getXPos() < window.getWidth()/2 + 37
              && mouseInput.getYPos() > window.getHeight()/2 + 130
              && mouseInput.getYPos() < window.getHeight()/2 + 145) {
              preLevelEditor.setHRight();
              if (mouseInput.isLeftButtonPressed()) {
            	  preLevelEditor.incHeight();
              }
          } else preLevelEditor.setRestoreHRight();
          
          if (mouseInput.getXPos() > window.getWidth()/2 - 175
        	  && mouseInput.getXPos() < window.getWidth()/2 + 180
        	  && mouseInput.getYPos() > window.getHeight()/2 + 210
        	  && mouseInput.getYPos() < window.getHeight()/2 + 240) {
        	  preLevelEditor.setCreateLevel();
        	  if (mouseInput.isLeftButtonPressed()) {
        		  LevelMapSet mapSet = new LevelMapSet(new TileSet());
        		  editorMap = initialiseMap(preLevelEditor.getWidth(), preLevelEditor.getLength(), preLevelEditor.getHeight());
        		  gameState = State.LEVEL_EDITOR;
        	  }
          } else preLevelEditor.setRestoreCreateLevel();
          
          if (mouseInput.getXPos() > window.getWidth()/2 - 30
        	  && mouseInput.getXPos() < window.getWidth()/2 + 30
        	  && mouseInput.getYPos() > window.getHeight()/2 + 260
        	  && mouseInput.getYPos() < window.getHeight()/2 + 275) {
        	  preLevelEditor.setBack();
        	  if (mouseInput.isLeftButtonPressed()) {
        		  gameState = State.MAINMENU;
        	  }
          } else preLevelEditor.setRestoreBack();
    	
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

        // SCORE
        if (window.isKeyReleased(GLFW_KEY_P)) {
          hud.setP1Score();
        }

        if (window.isKeyReleased(GLFW_KEY_J)) {
          gameModel.nextLevel();
        }

        leaveGame();

        audio();

        // CONTROL TO SHOW OTHER PLAYERS SCORES
        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
          hud.moveScore(20, 5);
        } else {
          hud.moveScore(-10, -230);
        }

        break;

      case DEAD:
        leaveGame();

        audio();

        break;
        
      case LEVEL_EDITOR:
    	
    	cameraControl();
    	levelEditorInput();
    	break;
    	
      case TESTING_LEVEL:
    	movement(delta);
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

        break;
        
      case TESTING_LEVEL:
    	float testTime = timer.getGameTime();

        int testTimeLeft = 90 - Math.round(testTime);
        if (testTimeLeft < 0) {
          testTimeLeft = 0;
        }
        String testText = String.format("%02d", testTimeLeft);

        hud.setCounter(testText);
        gameModel.update(delta);

        break;

      case DEAD:
        gameModel.update(delta);

		  break;
	  
	  case LEVEL_EDITOR:
		  
		  gameModel.update(delta);
		  leaveGame();
		  break;
		  
	  case PRE_EDITOR:
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
    	
    case PRE_EDITOR:
    	menuRenderer.render(preLevelEditor);
    	break;

    case LEVEL_EDITOR:
      levelEditorRenderer.render(editorMap, cameraPosition);
      break;
      
    case TESTING_LEVEL:
    	gameRenderer.render(gameModel, hud);
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
  
  private void cameraControl() {
	  if (mouseInput.getXPos() <= 5) {
		  cameraPosition.add(-0.1f,0.1f, 0);
	  } else if (mouseInput.getXPos() >= window.getWidth() - 5) {
		  cameraPosition.add(0.1f, -0.1f, 0);
	  }
	  
	  if (mouseInput.getYPos() <= 5) {
		  cameraPosition.add(0.1f, 0.1f, 0);
	  } else if (mouseInput.getYPos() >= window.getHeight() - 5) {
		  cameraPosition.add(-0.1f, -0.1f, 0);
	  }
  }
  
  private void levelEditorInput() {
	  if (window.isKeyReleased(GLFW_KEY_KP_9)) {
		  if (currentTileX != editorMap.getTiles()[currentTileZ][currentTileY].length - 1) {
			  currentTileX += 1;
		  }
	  } else if (window.isKeyReleased(GLFW_KEY_KP_1)) {
		  if (currentTileX != 0) {
			  currentTileX -= 1;
		  }
	  } else if (window.isKeyReleased(GLFW_KEY_KP_7)) {
		  if (currentTileY != editorMap.getTiles()[currentTileZ].length - 1) {
			  currentTileY += 1;
		  }
	  } else if (window.isKeyReleased(GLFW_KEY_KP_3)) {
		  if (currentTileY != 0) {
			  currentTileY -= 1;
		  }
	  } else if (window.isKeyReleased(GLFW_KEY_KP_8)) {
		  if (currentTileZ != editorMap.getTiles().length - 1) {
			  currentTileZ += 1;
		  }
	  } else if (window.isKeyReleased(GLFW_KEY_KP_2)) {
		  if (currentTileZ != 0) {
			  currentTileZ -= 1;
		  }
	  } else if (window.isKeyReleased(GLFW_KEY_SPACE)) {
		  int id = editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].getType().ordinal();
		  if (id == 5) {
			  editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(TileType.values()[0]);
		  } else {
			  editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(TileType.values()[id + 1]);
		  }
	  } else if (window.isKeyReleased(GLFW_KEY_ENTER)) {
		  	  try {
		  		gameModel.overwriteCurrentLevel(editorMap);
		  	  } catch (Exception e) {
		  		gameModel.createNewLevel(editorMap);
		  	  } finally {
		  		gameModel.addPlayer("1", 0);
		  		gameState = State.TESTING_LEVEL;
		  	  }

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
      if (gameState == State.SINGLEPLAYER || gameState == State.LEVEL_EDITOR) {
    	  gameState = State.MAINMENU;
      } else if (gameState == State.TESTING_LEVEL) {
    	  gameModel.removePlayer("1");
    	  gameState = State.LEVEL_EDITOR;
      }
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
  
  private LevelMap initialiseMap(int width, int length, int height) {
		int[][][] emptyMap = new int[height][length][width];
		for (int i = 0; i < emptyMap.length; i++) {
			for (int j = 0; j < emptyMap[i].length; j++) {
				for (int k = 0; k < emptyMap[i][j].length; k++) {
					if (i == 0) {
						emptyMap[i][j][k] = 1;
					} else {
						emptyMap[i][j][k] = 0;
					}
				}
			}
		}
		return (new LevelMap(emptyMap, (new TileSet())));
  }

  private enum State {
    MAINMENU,
    SERVERMENU,
    PRE_EDITOR,
    LEVEL_EDITOR,
    TESTING_LEVEL,
    OPTIONSMENU,
    LOBBY,
    SINGLEPLAYER,
    DEAD,
    END,
  }
}
