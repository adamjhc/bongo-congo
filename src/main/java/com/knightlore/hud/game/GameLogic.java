package com.knightlore.hud.game;

import static org.lwjgl.glfw.GLFW.*;
import com.knightlore.hud.engine.IGameLogic;
import com.knightlore.hud.engine.MouseInput;
import com.knightlore.hud.engine.Window;
import com.knightlore.hud.engine.graphics.Renderer;


public class GameLogic implements IGameLogic {

    private final Renderer renderer;
    
    private Hud hud;

    public GameLogic() {
        renderer = new Renderer();
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);
        hud = new Hud(window);
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        if (window.isKeyPressed(GLFW_KEY_L)) {
        	hud.setP1Lives();
        }
        
        if (window.isKeyPressed(GLFW_KEY_P)) {
        	hud.setP1Score();
        }
        
    	if (mouseInput.getXPos() < 65 && mouseInput.getYPos() > window.getHeight()-25) {
    		hud.setExit();
    		if (mouseInput.isLeftButtonPressed()) {
    			glfwSetWindowShouldClose(window.getWindowHandle(), true);
    		}
    	} else hud.setRestoreExit();
    	
    	if (mouseInput.getXPos() > window.getWidth()/2-90 && mouseInput.getXPos() < window.getWidth()/2+90 &&
    	mouseInput.getYPos() > window.getHeight()/2-10 && mouseInput.getYPos() < window.getHeight()/2+15) {
    		hud.setSingleplayer();
    		if (mouseInput.isLeftButtonPressed()) {
    			System.out.println("Singleplayer button pressed");
    			//hud.deleteGameItem();
    		}
    	} else hud.setRestoreSingleplayer();
    	
    	if (mouseInput.isLeftButtonPressed()) {
    		System.out.println(mouseInput.getXPos()+" "+mouseInput.getYPos());
    	}
    	if (mouseInput.isRightButtonPressed()) {}
    }

    @Override
    public void update(float interval, float elapsedTime) {
        hud.setCounter("Time: "+Integer.toString(90 - Math.round(elapsedTime)));
    }

    @Override
    public void render(Window window) {
        renderer.render(window, hud);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        hud.cleanup();
    }

}
