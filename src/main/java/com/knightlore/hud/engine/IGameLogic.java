package com.knightlore.hud.engine;

public interface IGameLogic {

    void init(Window window) throws Exception;
    
    void input(Window window, MouseInput mouseInput);

    void update(float interval, float elapsedTime, MouseInput mouseInput, Window window);
    
    void render(Window window);
    
    void cleanup();
}