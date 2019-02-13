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
        hud = new Hud();
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
    	if (window.isKeyPressed(GLFW_KEY_W)) {
    		System.out.println("W pressed");
    	}
    }

    @Override
    public void update(float interval, float elapsedTime, MouseInput mouseInput, Window window) {
        hud.setCounter("Time: "+Integer.toString(90 - Math.round(elapsedTime)));

        if (window.isKeyPressed(GLFW_KEY_L)) {
        	hud.setP1Lives();
        }
        if (window.isKeyPressed(GLFW_KEY_P)) {
        	hud.setP1Score();
        }
    }

    @Override
    public void render(Window window) {
        hud.updateSize(window);
        renderer.render(window, hud);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        hud.cleanup();
    }

}
