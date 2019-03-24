package com.knightlore.client;

import static com.knightlore.client.util.GuiUtils.registerFont;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import com.knightlore.client.audio.Audio;
import com.knightlore.client.gui.Loading;
import com.knightlore.client.gui.engine.Timer;
import com.knightlore.client.gui.screen.GameEndScreen;
import com.knightlore.client.gui.screen.GameScreen;
import com.knightlore.client.gui.screen.IScreen;
import com.knightlore.client.gui.screen.LevelEditorScreen;
import com.knightlore.client.gui.screen.LevelEditorSetupScreen;
import com.knightlore.client.gui.screen.LoadLevelScreen;
import com.knightlore.client.gui.screen.LobbyScreen;
import com.knightlore.client.gui.screen.LobbySelectScreen;
import com.knightlore.client.gui.screen.MainScreen;
import com.knightlore.client.gui.screen.NameLevelScreen;
import com.knightlore.client.gui.screen.OptionsScreen;
import com.knightlore.client.gui.screen.TestingLevelScreen;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.io.Window;
import com.knightlore.client.render.GameRenderer;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.client.render.LevelEditorRenderer;
import java.util.EnumMap;
import java.util.Map;

public class Client {

  private static final int TARGET_UPS = 60;

  private static Map<ClientState, IScreen> screens;
  private static IScreen currentScreen;
  private static Loading loadingScreen;

  private static GameRenderer gameRenderer;
  private static GuiRenderer guiRenderer;
  private static LevelEditorRenderer levelEditorRenderer;

  private static Timer timer;

  public static void main(String[] args) {
    Client.run();
  }

  public static void changeScreen(
      ClientState newScreen, boolean showLoadingScreen, Object... args) {
    if (showLoadingScreen) {
      loadingScreen.updateSize();
      showLoadingScreen();
    }

    currentScreen.shutdown(newScreen);
    currentScreen = screens.get(newScreen);
    currentScreen.startup(args);
  }

  public static void showLoadingScreen() {
    guiRenderer.render(loadingScreen);
  }

  public static void run() {
    init();
    loop();
    dispose();
  }

  private static void init() {
    // Setting up GLFW
    Window.init();

    // Setup peripherals
    Audio.init();
    Mouse.init();
    Keyboard.init();

    timer = new Timer();

    registerFont();

    gameRenderer = new GameRenderer();
    guiRenderer = new GuiRenderer();
    levelEditorRenderer = new LevelEditorRenderer();

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
    screens.put(ClientState.LOADING_LEVEL_TO_EDIT, new LoadLevelScreen(guiRenderer, false));
    screens.put(ClientState.END, new GameEndScreen(guiRenderer));

    loadingScreen = new Loading();

    currentScreen = screens.get(ClientState.MAIN_MENU);
    currentScreen.startup();
  }

  private static void loop() {
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

  private static void dispose() {
    gameRenderer.cleanup();
    guiRenderer.cleanup();
    levelEditorRenderer.cleanup();
    screens.forEach((state, screen) -> screen.cleanUp());

    Window.freeCallbacks();
    Window.destroyWindow();

    glfwTerminate();
  }
}
