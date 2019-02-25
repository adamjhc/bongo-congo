package com.knightlore.client;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_L;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import com.knightlore.client.audio.AudioHandler;
import com.knightlore.client.gui.Hud;
import com.knightlore.client.gui.MainMenu;
import com.knightlore.client.gui.engine.MouseInput;
import com.knightlore.client.gui.engine.Timer;
import com.knightlore.client.gui.engine.Window;
import com.knightlore.client.render.Renderer;
import com.knightlore.game.Game;
import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.PlayerState;
import com.knightlore.game.map.MapSet;
import com.knightlore.game.map.TileSet;

public class Client extends Thread {
	
  private static State gameState = State.MAINMENU;

  private static final int TARGET_UPS = 60;
  
  public static Game model;
  
  private Window window;
  
  private Renderer renderer;
  
  private Game gameModel;
  
  private AudioHandler audio;
  
  private MouseInput mouseInput;
  
  private Timer timer;
  
  private Hud hud;
  
  private MainMenu menu;

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

    renderer = new Renderer(window);

    if (model == null) {
      MapSet mapSet = new MapSet(new TileSet());
      gameModel = new Game("");

      gameModel.createNewLevel(mapSet.getMap(0));
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
    
    switch(gameState) {
    
    case MAINMENU:
    	
    	// SINGEPLAYER BUTTON
        if (mouseInput.getXPos() > window.getWidth() / 2 - 90
            && mouseInput.getXPos() < window.getWidth() / 2 + 90
            && mouseInput.getYPos() > window.getHeight() / 2 + 95
            && mouseInput.getYPos() < window.getHeight() / 2 + 115) {
          menu.setSingleplayer();
          if (mouseInput.isLeftButtonPressed()) {
            audio.toggle();
            audio.toggle(); // eventually change this so switches between menu and game music

            timer.setStartTime();
            hud.resetP1Lives();
            gameState = State.PLAY;
          }
        } else menu.setRestoreSingleplayer();
        
        // AUDIO BUTTON
        if (mouseInput.getXPos() > window.getWidth() - 35
                && mouseInput.getYPos() > window.getHeight()-35) {
        	if (mouseInput.isLeftButtonPressed()) {
        		menu.toggleSound();
                hud.toggleSound();
                audio.toggle();
            }
        }
        
        // MULTIPLAYER BUTTON
        if (mouseInput.getXPos() > window.getWidth() / 2 - 84
        		&& mouseInput.getXPos() < window.getWidth() / 2 + 84
        		&& mouseInput.getYPos() > window.getHeight() / 2 + 117
        		&& mouseInput.getYPos() < window.getHeight() / 2 + 135) {
        	menu.setMultiplayer();
        	if (mouseInput.isLeftButtonPressed()) {
        		
        	}
        } else menu.setRestoreMultiplayer();
        
        // OPTIONS BUTTON
        if (mouseInput.getXPos() > window.getWidth() / 2 - 52
        		&& mouseInput.getXPos() < window.getWidth() / 2 + 52
        		&& mouseInput.getYPos() > window.getHeight() / 2 + 138
        		&& mouseInput.getYPos() < window.getHeight() / 2 + 155) {
        	menu.setOptions();
        	if (mouseInput.isLeftButtonPressed()) {
        		
        	} 
        } else menu.setRestoreOptions();
        
        // QUIT BUTTON
        if (mouseInput.getXPos() > window.getWidth() / 2 - 30
        		&& mouseInput.getXPos() < window.getWidth() / 2 + 30
        		&& mouseInput.getYPos() > window.getHeight() / 2 + 160
        		&& mouseInput.getYPos() < window.getHeight() / 2 + 176) {
        	menu.setQuit();
            if (mouseInput.isLeftButtonPressed()) {
            	glfwSetWindowShouldClose(window.getWindowHandle(), true);
            }
        } else menu.setRestoreQuit();
        
        // ESC TO EXIT
        if (window.isKeyReleased(GLFW_KEY_ESCAPE)) {
        	glfwSetWindowShouldClose(window.getWindowHandle(), true);
        }
        
    	break;
    	
    case PLAY:
    	
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

        // LIVES
        if (window.isKeyReleased(GLFW_KEY_L)) {
          hud.setP1Lives();
          gameModel.resetPlayer();

          if (hud.isP1Dead()) {
            gameModel.updatePlayerState(PlayerState.IDLE);
            gameState = State.DEAD;
          }
        }

        // SCORE 
        if (window.isKeyReleased(GLFW_KEY_P)) {
          hud.setP1Score();
        }
        
        // ESC TO EXIT TO MAIN MENU
        if (window.isKeyReleased(GLFW_KEY_ESCAPE)) {
    		audio.toggle();
    		audio.toggle();
    		
        	gameModel.resetPlayer();
        	gameState = State.MAINMENU;
        }

//        if (mouseInput.getXPos() < 65 && mouseInput.getYPos() > window.getHeight() - 25) {
//        	hud.setExit();
//            if (mouseInput.isLeftButtonPressed()) {
//        		audio.toggle();
//        		audio.toggle();
//        		
//            	gameModel.resetPlayer();
//            	gameState = State.MAINMENU;
//            }
//        } else hud.setRestoreExit();

        // AUDIO BUTTON
        if (mouseInput.getXPos() > window.getWidth() - 35
                && mouseInput.getYPos() > window.getHeight()-35) {
        	if (mouseInput.isLeftButtonPressed()) {
        		menu.toggleSound();
                hud.toggleSound();
                audio.toggle();
            }
        }
        
        // CONTROL TO SHOW OTHER PLAYERS SCORES
        if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
        	hud.moveScore(20, 5);
        	//hud.toggleScore(true);
        } else {
        	hud.moveScore(-10, -230);
        	//hud.toggleScore(false);
        }
        
    	break;
    	
    case DEAD:
    	
    	// ESC TO EXIT TO MAIN MENU
        if (window.isKeyReleased(GLFW_KEY_ESCAPE)) {
    		audio.toggle();
    		audio.toggle();
    		
        	gameModel.resetPlayer();
        	gameState = State.MAINMENU;
        }
    	
//        if (mouseInput.getXPos() < 65 && mouseInput.getYPos() > window.getHeight() - 25) {
//        	hud.setExit();
//            if (mouseInput.isLeftButtonPressed()) {
//        		audio.toggle();
//        		audio.toggle();
//        		
//            	gameModel.resetPlayer();
//            	gameState = State.MAINMENU;
//            }
//        } else hud.setRestoreExit();

        // AUDIO BUTTON
        if (mouseInput.getXPos() > window.getWidth() - 35
                && mouseInput.getYPos() > window.getHeight()-35) {
        	if (mouseInput.isLeftButtonPressed()) {
        		menu.toggleSound();
                hud.toggleSound();
                audio.toggle();
            }
        }

        break;
    }
  }

  private void update(float delta) {
	  
	  switch(gameState) {
	  
	  case MAINMENU:
		  
		  break;	
		  
	  case PLAY:
		  
		  float gameTime = timer.getGameTime();

		  int timeLeft = 90 - Math.round(gameTime);
		  if (timeLeft < 0) {
			  timeLeft = 0;
		  }
		  String text = String.format("%02d", timeLeft);
		  
		  hud.setCounter(text);
		  gameModel.update(delta); 
		  
		  break;	  
		  
	  case DEAD:
		  
		  gameModel.update(delta); 
		  
		  break;
	  }
  }

  private void render(Game gameModel) {
    switch (gameState) {
    
    case MAINMENU:
    	
        renderer.render(window, menu);
        
        break;
        
    case PLAY:
    	
        renderer.render(gameModel, window, hud);
        
        break;
        
    case DEAD:
    	
        renderer.render(gameModel, window, hud);
        
        break;    
    }
  }

  private void dispose() {
    hud.cleanup();
    renderer.cleanup();

    window.freeCallbacks();
    window.destroyWindow();

    glfwTerminate();
  }

  private enum State {
    MAINMENU,
    SERVERMENU,
    OPTIONSMENU,
    LOBBY,
    PLAY,
    DEAD,
    END
  }
}
