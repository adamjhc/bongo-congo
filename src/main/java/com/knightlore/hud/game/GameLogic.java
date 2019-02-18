package com.knightlore.hud.game;

import static org.lwjgl.glfw.GLFW.*;

import com.knightlore.client.render.Renderer;
import com.knightlore.game.Game;
import com.knightlore.hud.engine.IGameLogic;
import com.knightlore.hud.engine.MouseInput;
import com.knightlore.hud.engine.Window;
import com.knightlore.hud.engine.graphics.HUDRenderer;


public class GameLogic implements IGameLogic {

    private final HUDRenderer hudRenderer;
    
    private Hud hud;

    public GameLogic() {
        hudRenderer = new HUDRenderer();
    }

    @Override
    public void init(Window window) throws Exception {
        hudRenderer.init(window);
        hud = new Hud(window);
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        if (window.isKeyReleased(GLFW_KEY_L)) {
        	hud.setP1Lives();
        }
        
        if (window.isKeyReleased(GLFW_KEY_P)) {
        	hud.setP1Score();
        }
        
    	if (mouseInput.getXPos() < 65 && mouseInput.getYPos() > window.getHeight()-25) {
    		hud.setExit();
    		if (mouseInput.isLeftButtonPressed()) {
    			glfwSetWindowShouldClose(window.getWindowHandle(), true);
    		}
    	} else hud.setRestoreExit();
    	
    	if (mouseInput.getXPos() > window.getWidth()-35 && mouseInput.getYPos() > window.getHeight()-35) {
    		//hud.setSound();
    		if (mouseInput.isLeftButtonPressed()) {
    			hud.setSoundOff();
    		}
    	} else hud.setRestoreSound();
    	
        if (mouseInput.getXPos() > window.getWidth()/2-90 && mouseInput.getXPos() < window.getWidth()/2+90 &&
    	mouseInput.getYPos() > window.getHeight()/2+90 && mouseInput.getYPos() < window.getHeight()/2+115) {
    		hud.setSingleplayer();
    		if (mouseInput.isLeftButtonPressed()) {
    			System.out.println("Singleplayer button pressed");
    			//hud.deleteGameItem();
    		}
    	} else hud.setRestoreSingleplayer();
    	
    	if (mouseInput.isLeftButtonPressed()) {
    		System.out.println(mouseInput.getXPos()+" "+mouseInput.getYPos());
    	}
    	if (mouseInput.isRightButtonPressed()) {
    		System.out.println(mouseInput.getXPos()+" "+mouseInput.getYPos());
    	}
    }

    @Override
    public void update(float elapsedTime) {
        hud.setCounter("Time: "+Integer.toString(90 - Math.round(elapsedTime)));
    }

    @Override
    public void render(Window window, Renderer renderer, Game gameModel) {
    	renderer.render(gameModel, window, hud);
    }

    @Override
    public void cleanup() {
        hudRenderer.cleanup();
        hud.cleanup();
    }

}
