package com.knightlore.hud.engine;

import com.knightlore.client.render.Renderer;
import com.knightlore.game.Game;

public interface IGameLogic {

    void init(Window window) throws Exception;
    
    void input(Window window, MouseInput mouseInput);

    void update(float interval, float elapsedTime);
    
    void render(Window window, Renderer renderer, Game gameModel);
    
    void cleanup();
}