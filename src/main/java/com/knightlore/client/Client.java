package com.knightlore.client;

import static com.knightlore.client.util.GuiUtils.registerFont;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import com.knightlore.client.audio.Audio;
import com.knightlore.client.gui.Loading;
import com.knightlore.client.gui.engine.Timer;
import com.knightlore.client.gui.screen.GameEndScreen;
import com.knightlore.client.gui.screen.GameScreen;
import com.knightlore.client.gui.screen.HelpScreen;
import com.knightlore.client.gui.screen.HighScoreScreen;
import com.knightlore.client.gui.screen.IScreen;
import com.knightlore.client.gui.screen.LevelEditorScreen;
import com.knightlore.client.gui.screen.LevelEditorSetupScreen;
import com.knightlore.client.gui.screen.LevelSelectScreen;
import com.knightlore.client.gui.screen.LoadLevelScreen;
import com.knightlore.client.gui.screen.LobbyScreen;
import com.knightlore.client.gui.screen.LobbySelectScreen;
import com.knightlore.client.gui.screen.MainScreen;
import com.knightlore.client.gui.screen.NameLevelScreen;
import com.knightlore.client.gui.screen.OptionsScreen;
import com.knightlore.client.gui.screen.ShowErrorScreen;
import com.knightlore.client.gui.screen.TestingLevelScreen;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.io.Window;
import com.knightlore.client.render.GameRenderer;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.client.render.LevelEditorRenderer;
import com.knightlore.client.render.LevelSelectRenderer;
import java.util.EnumMap;
import java.util.Map;

public class Client {

  /** Target number of times game should update per second */
  private static final int TARGET_UPS = 60;

  /** Map between client state and screen to show */
  private static Map<ClientState, IScreen> screens;

  /** Current screen showing */
  private static IScreen currentScreen;

  /** Gui elements for loading screen */
  private static Loading loadingScreen;

  /** Renderer used for rendering the game */
  private static GameRenderer gameRenderer;

  /** Renderer used for rendering gui elements */
  private static GuiRenderer guiRenderer;

  /** Renderer used for rendering level editing */
  private static LevelEditorRenderer levelEditorRenderer;

  /** Renderer used for rendering map preview in level select */
  private static LevelSelectRenderer levelSelectRenderer;

  /** Timer used in game */
  private static Timer timer;

  /**
   * Main method for client
   *
   * @param args unused
   */
  public static void main(String[] args) {
    Client.run();
  }

  /**
   * Change current screen being shown
   *
   * @param newScreen New screen to show
   * @param showLoadingScreen whether to show the loading screen while switching screens
   * @param args Variable arguments to pass onto new screen
   * @author Adam Cox
   */
  public static void changeScreen(
      ClientState newScreen, boolean showLoadingScreen, Object... args) {
    if (showLoadingScreen) {
      showLoadingScreen();
    }

    currentScreen.shutdown(newScreen);
    currentScreen = screens.get(newScreen);
    currentScreen.startup(args);
  }

  /**
   * Display loading screen
   *
   * @author Adam Cox
   */
  public static void showLoadingScreen() {
    loadingScreen.updateSize();
    clearBuffers();
    guiRenderer.render(loadingScreen);
    Window.swapBuffers();
  }

  /**
   * Run client
   *
   * @author Adam Cox
   */
  public static void run() {
    init();
    loop();
    dispose();
  }

  /**
   * Initialise client
   *
   * @author Adam Cox
   */
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
    levelSelectRenderer = new LevelSelectRenderer();

    screens = new EnumMap<>(ClientState.class);
    screens.put(ClientState.MAIN_MENU, new MainScreen(guiRenderer));
    screens.put(ClientState.LOBBY_MENU, new LobbySelectScreen(guiRenderer));
    screens.put(ClientState.PRE_EDITOR, new LevelEditorSetupScreen(guiRenderer));
    screens.put(ClientState.LEVEL_EDITOR, new LevelEditorScreen(guiRenderer, levelEditorRenderer));
    screens.put(
        ClientState.TESTING_LEVEL, new TestingLevelScreen(guiRenderer, gameRenderer, timer));
    screens.put(ClientState.OPTIONS_MENU, new OptionsScreen(guiRenderer));
    screens.put(ClientState.GAME, new GameScreen(guiRenderer, gameRenderer, timer));
    screens.put(ClientState.NAMING_LEVEL, new NameLevelScreen(guiRenderer));
    screens.put(ClientState.LOBBY, new LobbyScreen(guiRenderer));
    screens.put(ClientState.LOADING_LEVEL, new LoadLevelScreen(guiRenderer));
    screens.put(ClientState.END, new GameEndScreen(guiRenderer));
    screens.put(ClientState.LEVEL_SELECT, new LevelSelectScreen(guiRenderer, levelSelectRenderer));
    screens.put(ClientState.SHOW_ERROR, new ShowErrorScreen(guiRenderer));
    screens.put(ClientState.HIGHSCORE, new HighScoreScreen(guiRenderer));
    screens.put(ClientState.HELP, new HelpScreen(guiRenderer));

    loadingScreen = new Loading();

    currentScreen = screens.get(ClientState.MAIN_MENU);
    currentScreen.startup();
  }

  /**
   * Game loop
   *
   * @author Adam Cox, Joseph Tuffin
   */
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

      clearBuffers();
      currentScreen.render();
      Window.swapBuffers();

      Audio.closeInactiveClips();
    }
  }

  /**
   * Dispose of memory allocated objects
   *
   * @author Adam Cox
   */
  private static void dispose() {
    gameRenderer.cleanup();
    guiRenderer.cleanup();
    levelEditorRenderer.cleanup();
    levelSelectRenderer.cleanup();
    screens.forEach((state, screen) -> screen.cleanUp());

    Window.freeCallbacks();
    Window.destroyWindow();

    glfwTerminate();
  }

  /**
   * Clear buffer
   *
   * @author Adam Cox
   */
  private static void clearBuffers() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
  }
}
