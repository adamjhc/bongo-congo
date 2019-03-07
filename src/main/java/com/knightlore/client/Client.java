package com.knightlore.client;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_J;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_0;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_3;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_4;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_5;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_6;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_7;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_8;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_9;
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
import com.knightlore.client.gui.engine.IGui;
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
import com.knightlore.leveleditor.LevelEditorHud;

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
  
  private LevelEditorHud leHud;

  private MainMenu menu;

  private ServerMenu serverMenu;

  private OptionsMenu optionsMenu;
  
  private PreLevelEditor preLevelEditor;
  
  private Vector3f cameraPosition;
  
  private LevelMap editorMap;
  
  private int currentTileX, currentTileY, currentTileZ;
  
  private IGui[] guis;

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
    leHud = new LevelEditorHud(window);
    menu = new MainMenu(window);
    serverMenu = new ServerMenu(window);
    optionsMenu = new OptionsMenu(window);
    preLevelEditor = new PreLevelEditor(window);
    
    guis = new IGui[] {menu, serverMenu, optionsMenu, hud, preLevelEditor};


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
        if (checkPosition(0, "Singleplayer", "")) {
          menu.getSingleplayer().setColour();
          if (mouseInput.isLeftButtonPressed()) {
        	LevelMapSet mapSet = new LevelMapSet(new TileSet());
        	gameModel.createNewLevel(mapSet.getMap(0));
            gameModel.createNewLevel(mapSet.getMap(1));
            gameModel.addPlayer("1");
            System.out.println(gameModel.getPlayers().get("1").getId());
        	  
            audio.toggle();
            audio.toggle();

            timer.setStartTime();
            gameModel.myPlayer().nextLevel();
            gameState = State.SINGLEPLAYER;
          }
        } else menu.getSingleplayer().setColour(new Vector4f(1, 1, 0, 1));

        // MULTIPLAYER BUTTON
        if (checkPosition(0, "Multiplayer", "")) {
          menu.getMultiplayer().setColour();
          if (mouseInput.isLeftButtonPressed()) {
            gameState = State.SERVERMENU;
          }
        } else menu.getMultiplayer().setColour(new Vector4f(1, 1, 0, 1));
        
        // LEVEL EDITOR BUTTON
        if (checkPosition(0, "Level Editor", "")) {
    			menu.getLevelEditor().setColour();
    			if (mouseInput.isLeftButtonPressed()) {
    				gameState = State.PRE_EDITOR;
    			}
    	} else menu.getLevelEditor().setColour(new Vector4f(1, 1, 0, 1));

        // OPTIONS BUTTON
        if (checkPosition(0, "Options", "")) {
          menu.getOptions().setColour();
          if (mouseInput.isLeftButtonPressed()) {
            gameState = State.OPTIONSMENU;
          }
        } else menu.getOptions().setColour(new Vector4f(1, 1, 0, 1));

        // QUIT BUTTON

        if (checkPosition(0, "Quit", "")) {
          menu.getQuit().setColour();
          if (mouseInput.isLeftButtonPressed()) {
            glfwSetWindowShouldClose(window.getWindowHandle(), true);
          }
        } else menu.getQuit().setColour(new Vector4f(1, 1, 0, 1));

        audio();

        // ESC TO EXIT
        if (window.isKeyReleased(GLFW_KEY_ESCAPE)) {
          glfwSetWindowShouldClose(window.getWindowHandle(), true);
        }

        break;

      case SERVERMENU:
        if (checkPosition(1, "Separator Top", "Separator Bot")) {
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

        if (checkPosition(1, "Create game", "")) {
          serverMenu.getCreate().setColour();
          if (mouseInput.isLeftButtonPressed()) {
            serverMenu.createServer(window);
          }
        } else serverMenu.getCreate().setColour(new Vector4f(1, 1, 0, 1));

        if (checkPosition(1, "Exit", "")) {
          serverMenu.getExit().setColour();
          if (mouseInput.isLeftButtonPressed()) {
            gameState = State.MAINMENU;
          }
        } else serverMenu.getExit().setColour(new Vector4f(1, 1, 0, 1));

        leaveMenu();

        break;
        
      case PRE_EDITOR:
    	  if (checkPosition(4, "wLeft", "")) {
    		  preLevelEditor.getWLeft().setColour();
    		  if (mouseInput.isLeftButtonPressed()) {
    			  preLevelEditor.decWidth();
    		  }
    	  } else preLevelEditor.getWLeft().setColour(new Vector4f(1, 1, 0, 1));
    	  
    	  if (checkPosition(4, "wRight", "")) {
    		  preLevelEditor.getWRight().setColour();
    		  if (mouseInput.isLeftButtonPressed()) {
    			  preLevelEditor.incWidth();
    		  }
    	  } else preLevelEditor.getWRight().setColour(new Vector4f(1, 1, 0, 1));
    	  
    	  if (checkPosition(4, "lLeft", "")) {
    		  preLevelEditor.getLLeft().setColour();
    		  if (mouseInput.isLeftButtonPressed()) {
    			  preLevelEditor.decLength();
    		  }
    	  } else preLevelEditor.getLLeft().setColour(new Vector4f(1, 1, 0, 1));
        	  
          if (checkPosition(4, "lRight", "")) {
        	  preLevelEditor.getLRight().setColour();
        	  if (mouseInput.isLeftButtonPressed()) {
        		  preLevelEditor.incLength();
        	  }
          } else preLevelEditor.getLRight().setColour(new Vector4f(1, 1, 0, 1));
        	  
          if (checkPosition(4, "hLeft", "")) {
              preLevelEditor.getHLeft().setColour();
              if (mouseInput.isLeftButtonPressed()) {
            	  preLevelEditor.decHeight();
              }
          } else preLevelEditor.getHLeft().setColour(new Vector4f(1, 1, 0, 1));
            	  
          if (checkPosition(4, "hRight", "")) {
              preLevelEditor.getHRight().setColour();
              if (mouseInput.isLeftButtonPressed()) {
            	  preLevelEditor.incHeight();
              }
          } else preLevelEditor.getHRight().setColour(new Vector4f(1, 1, 0, 1));
          
          if (checkPosition(4, "Create Level", "")) {
        	  preLevelEditor.getCreateLevel().setColour();
        	  if (mouseInput.isLeftButtonPressed()) {
        		  editorMap = initialiseMap(preLevelEditor.getWidthNum(), preLevelEditor.getLengthNum(), preLevelEditor.getHeightNum());
        		  gameState = State.LEVEL_EDITOR;
        	  }
          } else preLevelEditor.getCreateLevel().setColour(new Vector4f(1, 1, 0, 1));
          
          if (checkPosition(4, "Back", "")) {
        	  preLevelEditor.getBack().setColour();
        	  if (mouseInput.isLeftButtonPressed()) {
        		  gameState = State.MAINMENU;
        	  }
          } else preLevelEditor.getBack().setColour(new Vector4f(1, 1, 0, 1));
    	
    	break;

      case OPTIONSMENU:
        if (checkPosition(2, ">", "")) {
          optionsMenu.getIncVolume().setColour();
          if (mouseInput.isLeftButtonHeld()) {
            optionsMenu.incVolume();
            audio.incVolume();
          }
        } else optionsMenu.getIncVolume().setColour(new Vector4f(1, 1, 0, 1));

        if (checkPosition(2, "<", "")) {
          optionsMenu.getDecVolume().setColour();
          if (mouseInput.isLeftButtonHeld()) {
            optionsMenu.decVolume();
            audio.decVolume();
          }
        } else optionsMenu.getDecVolume().setColour(new Vector4f(1, 1, 0, 1));
        
        if (checkPosition(2, "Exit", "")) {
          optionsMenu.getExit().setColour();
          if (mouseInput.isLeftButtonPressed()) {
            gameState = State.MAINMENU;
          }
        } else optionsMenu.getExit().setColour(new Vector4f(1, 1, 0, 1));

        leaveMenu();

        break;

      case SINGLEPLAYER:
        movement(delta);

        if (window.isKeyReleased(GLFW_KEY_J)) {
          timer.setStartTime();
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
        if (window.isKeyReleased(GLFW_KEY_J)) {
          gameState = State.SINGLEPLAYER;
          timer.setStartTime();
          gameModel.nextLevel();
        }
        
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
		  
		int lives = gameModel.myPlayer().getLives();
		hud.setP1Lives(lives);
		  
		int score = gameModel.myPlayer().getScore();
		hud.setP1Score(score);
		  
		Vector3f colour = gameModel.myPlayer().getColour();
		hud.getP1Score().setColour(new Vector4f(colour.x, colour.y, colour.z, 1));
		
		gameModel.update(delta);
		
		if (gameModel.myPlayer().getPlayerState() == PlayerState.DEAD) {
			gameState = State.DEAD;
		}

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
      levelEditorRenderer.render(editorMap, cameraPosition, leHud);
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
	  } else if (window.isKeyReleased(GLFW_KEY_KP_5)) {
		  if (currentTileZ != editorMap.getTiles().length - 1) {
			  currentTileZ += 1;
		  }
	  } else if (window.isKeyReleased(GLFW_KEY_KP_0)) {
		  if (currentTileZ != 0) {
			  currentTileZ -= 1;
		  }
	  } else if (window.isKeyReleased(GLFW_KEY_KP_8)) {
		  if (currentTileX != editorMap.getTiles()[currentTileZ][currentTileY].length - 1 
		   && currentTileY != editorMap.getTiles()[currentTileZ].length - 1) {
			  currentTileX += 1;
			  currentTileY += 1;
		  }
	  } else if (window.isKeyReleased(GLFW_KEY_KP_2)) {
		  if (currentTileX != 0
		   && currentTileY != 0) {
			  currentTileX -= 1;
			  currentTileY -= 1;
		  }
	  } else if (window.isKeyReleased(GLFW_KEY_KP_4)) {
		  if (currentTileX != 0
		   && currentTileY != editorMap.getTiles()[currentTileZ].length - 1) {
			  currentTileX -= 1;
			  currentTileY += 1;
		  }
	  } else if (window.isKeyReleased(GLFW_KEY_KP_6)) {
		  if (currentTileX != editorMap.getTiles()[currentTileZ][currentTileY].length - 1
		   && currentTileY != 0) {
			  currentTileX += 1;
			  currentTileY -= 1;
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

  private boolean checkPosition(int gui, String textObject, String textObjectLower) {
	  int objectIndex = -1;
	  int objectIndexLower = -1;
	  for (int i = 0; i < guis[gui].getTextObjects().length; i++) {
		  if (guis[gui].getTextObjects()[i].getId() == textObject) {
			  objectIndex = i;
			  break;
		  }
	  }
	  
	  if (objectIndex == -1) return false;
	  
	  if (textObjectLower != "") {
		  for (int i = 0; i < guis[gui].getTextObjects().length; i++) {
			  if (guis[gui].getTextObjects()[i].getId() == textObjectLower && i != objectIndex) {
				  objectIndexLower = i;
			  }
		  }
		  
		  if (objectIndexLower == -1) return false; 
	  } else objectIndexLower = objectIndex;
	  
	  if (mouseInput.getXPos() > guis[gui].getTextObjects()[objectIndex].getPositionX()
      && mouseInput.getXPos() < guis[gui].getTextObjects()[objectIndex].getPositionX()
      +guis[gui].getTextObjects()[objectIndex].getSize()
      && mouseInput.getYPos() > guis[gui].getTextObjects()[objectIndex].getPositionY()
      && mouseInput.getYPos() < guis[gui].getTextObjects()[objectIndexLower].getPositionY()
      +guis[gui].getTextObjects()[objectIndexLower].getHeight()) {
		return true;  
	  } return false;  
  }

  private void audio() {
    if (mouseInput.getXPos() > menu.getSound().getPositionX()
        && mouseInput.getYPos() > menu.getSound().getPositionY()) {
      if (mouseInput.isLeftButtonPressed()) {
        menu.getSoundMute().setRender();
        hud.getSoundMute().setRender();
        audio.toggle();
      }
    }
  }

  private void leaveGame() {
    if (window.isKeyReleased(GLFW_KEY_ESCAPE)) {
      audio.toggle();
      audio.toggle();
      if (gameState == State.SINGLEPLAYER || gameState == State.LEVEL_EDITOR) {
    	  gameModel.clearLevels();
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
