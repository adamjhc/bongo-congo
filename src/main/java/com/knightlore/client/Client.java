package com.knightlore.client;

import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static com.knightlore.client.util.GuiUtils.registerFont;

import com.knightlore.client.audio.Audio;
import com.knightlore.client.gui.engine.Timer;
import com.knightlore.client.gui.screen.GameScreen;
import com.knightlore.client.gui.screen.IScreen;
import com.knightlore.client.gui.screen.LevelEditorScreen;
import com.knightlore.client.gui.screen.LevelEditorSetupScreen;
import com.knightlore.client.gui.screen.LoadLevelScreen;
import com.knightlore.client.gui.screen.LobbyScreen;
import com.knightlore.client.gui.screen.MainScreen;
import com.knightlore.client.gui.screen.NameLevelScreen;
import com.knightlore.client.gui.screen.OptionsScreen;
import com.knightlore.client.gui.screen.LobbySelectScreen;
import com.knightlore.client.gui.screen.TestingLevelScreen;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.io.Window;
import com.knightlore.client.render.GameRenderer;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.client.render.LevelEditorRenderer;
import java.util.EnumMap;
import java.util.Map;

public class Client extends Thread {

  private static final int TARGET_UPS = 60;

  private static Map<ClientState, IScreen> screens;
  private static IScreen currentScreen;

  private Timer timer;

  private GameRenderer gameRenderer;

  public static void main(String[] args) {
    new Client().run();
  }

  public static void changeScreen(ClientState newScreen, Object... args) {
    currentScreen.shutdown(newScreen);
    currentScreen = screens.get(newScreen);
    currentScreen.startup(args);
  }

  @Override
  public void run() {
    init();
    loop();
    dispose();
  }

  private void init() {
    // Setting up GLFW
    Window.init();

    // Setup peripherals
    Audio.init();
    Mouse.init();
    Keyboard.init();

    timer = new Timer();
    
    registerFont();

    gameRenderer = new GameRenderer();
    GuiRenderer guiRenderer = new GuiRenderer();
    LevelEditorRenderer levelEditorRenderer = new LevelEditorRenderer();

    screens = new EnumMap<>(ClientState.class);
    screens.put(ClientState.MAIN_MENU, new MainScreen(guiRenderer));
    screens.put(ClientState.LOBBY_MENU, new LobbySelectScreen(guiRenderer));
    screens.put(ClientState.PRE_EDITOR, new LevelEditorSetupScreen(guiRenderer));
    screens.put(ClientState.LEVEL_EDITOR, new LevelEditorScreen(levelEditorRenderer));
    screens.put(ClientState.TESTING_LEVEL, new TestingLevelScreen(gameRenderer, timer));
    screens.put(ClientState.OPTIONS_MENU, new OptionsScreen(guiRenderer));
    screens.put(ClientState.GAME, new GameScreen(gameRenderer, timer));
    screens.put(ClientState.NAMING_LEVEL, new NameLevelScreen(guiRenderer));
    screens.put(ClientState.LOBBY, new LobbyScreen(guiRenderer));
    screens.put(ClientState.LOADING_LEVEL, new LoadLevelScreen(guiRenderer));


    currentScreen = screens.get(ClientState.MAIN_MENU);
    currentScreen.startup();
  }

  private void loop() {
    float elapsedTime;
    float accumulator = 0f;
    float interval = 1f / TARGET_UPS;

    while (!Window.shouldClose()) {
      elapsedTime = timer.getElapsedTime();

      accumulator += elapsedTime;

      Window.update();
      currentScreen.input();

      while (accumulator >= interval) {
        currentScreen.update(interval);

        accumulator -= interval;
      }

      currentScreen.render();
    }
  }

  private void dispose() {
    gameRenderer.cleanup();
    screens.forEach((state, screen) -> screen.cleanUp());

    Window.freeCallbacks();
    Window.destroyWindow();

    glfwTerminate();
  }
}
