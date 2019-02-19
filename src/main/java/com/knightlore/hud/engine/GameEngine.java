package com.knightlore.hud.engine;

import com.knightlore.client.render.Renderer;
import com.knightlore.game.Game;

public class GameEngine {

    public static final int TARGET_UPS = 60;

    private final Window window;

    private final Timer timer;

    private final IGameLogic gameLogic;

    private final MouseInput mouseInput;
    
    private Renderer renderer;
    private Game gameModel;

    public GameEngine(String windowTitle, int width, int height, IGameLogic gameLogic) {
        window = new Window(windowTitle, width, height);
        mouseInput = new MouseInput();
        this.gameLogic = gameLogic;
        timer = new Timer();
    }

    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    protected void init() throws Exception {
        window.init();
        timer.init();
        mouseInput.init(window);
        gameLogic.init(window);
        
        renderer = new Renderer(window);
        gameModel = new Game();
    }

    protected void gameLoop() {
        float elapsedTime;
        float gameTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        while (!window.windowShouldClose()) {
            elapsedTime = timer.getElapsedTime();
            gameTime = timer.getGameTime();
            accumulator += elapsedTime;
            
            

            while (accumulator >= interval) {
            	accumulator -= interval;
            	
            	input();
            	
                update(gameTime);
                gameModel.update(interval);
                
            }

            render(renderer, gameModel);
        }
    }

    protected void cleanup() {
        gameLogic.cleanup();                
    }

    protected void input() {
    	window.update();
        gameLogic.input(window, mouseInput);
    }

    protected void update(float elapsedTime) {
        gameLogic.update(elapsedTime);
    }

    protected void render(Renderer renderer, Game gameModel) {
        gameLogic.render(window, renderer, gameModel);
    }
}
