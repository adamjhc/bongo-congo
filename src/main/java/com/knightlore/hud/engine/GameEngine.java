package com.knightlore.hud.engine;

public class GameEngine {

    public static final int TARGET_UPS = 30;

    private final Window window;

    private final Timer timer;

    private final IGameLogic gameLogic;

    private final MouseInput mouseInput;

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
    }

    protected void gameLoop() {
        float elapsedTime;
        float fullTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        while (!window.windowShouldClose()) {
            elapsedTime = timer.getElapsedTime();
            fullTime = timer.getFullTime();
            accumulator += elapsedTime;

            input();

            while (accumulator >= interval) {
                update(interval, fullTime);
                accumulator -= interval;
            }

            render();
        }
    }

    protected void cleanup() {
        gameLogic.cleanup();                
    }

    protected void input() {
        gameLogic.input(window, mouseInput);
    }

    protected void update(float interval, float elapsedTime) {
        gameLogic.update(interval, elapsedTime);
    }

    protected void render() {
        gameLogic.render(window);
        window.update();
    }
}
