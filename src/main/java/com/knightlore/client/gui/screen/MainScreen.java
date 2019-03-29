package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.audio.Audio;
import com.knightlore.client.audio.Audio.AudioName;
import com.knightlore.client.exceptions.ClientAlreadyAuthenticatedException;
import com.knightlore.client.exceptions.ConfigItemNotFoundException;
import com.knightlore.client.gui.MainMenu;
import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.io.Window;
import com.knightlore.client.networking.HighScoreCache;
import com.knightlore.client.networking.LobbyCache;
import com.knightlore.client.networking.ServerConnection;
import com.knightlore.client.render.GuiRenderer;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Handles the main screen startup, input, updates, rendering and cleanup
 *
 * @author Joseph, Adam Cox
 */
public class MainScreen implements IScreen {

  /** Audio clip name for selection */
  private static final AudioName SELECT = AudioName.SOUND_MENUSELECT;

  /** Renderer used for rendering gui elements */
  private GuiRenderer renderer;

  /** Gui elements to render */
  private MainMenu menu;

  /**
   * Initialise MainScreen
   *
   * @param guiRenderer renderer used to render gui elements
   */
  public MainScreen(GuiRenderer guiRenderer) {
    this.renderer = guiRenderer;
    menu = new MainMenu();

    Audio.restart();
  }

  @Override
  public void startup(Object... args) {
    if (Audio.getCurrentMusic() != AudioName.MUSIC_MENU) {
      Audio.stop(Audio.getCurrentMusic());
    }
    Audio.play(Audio.AudioName.MUSIC_MENU);
  }

  @Override
  public void input() {

    // SINGEPLAYER BUTTON
    if (checkPosition(menu, menu.getSingleplayer().getId())) {
      menu.getSingleplayer().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        Client.changeScreen(ClientState.LEVEL_SELECT, false);
        return;
      }
    } else menu.getSingleplayer().setColour(Colour.YELLOW);

    // MULTIPLAYER BUTTON
    if (checkPosition(menu, menu.getMultiplayer().getId())) {
      menu.getMultiplayer().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);

        if (!connectToServer()) {
          Client.changeScreen(ClientState.SHOW_ERROR, false, "Error connecting to server");
          return;
        }

        // Retrieve Games
        ServerConnection.instance.listGames();

        // Wait for game recieve response
        while (LobbyCache.instance == null) {
          try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Waiting");
          } catch (InterruptedException e) {

          }
        }

        Client.changeScreen(ClientState.LOBBY_MENU, false);
        return;
      }
    } else menu.getMultiplayer().setColour(Colour.YELLOW);

    // LEVEL EDITOR BUTTON
    if (checkPosition(menu, menu.getLevelEditor().getId())) {
      menu.getLevelEditor().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        Client.changeScreen(ClientState.PRE_EDITOR, false);
        return;
      }
    } else menu.getLevelEditor().setColour(Colour.YELLOW);

    // HIGHSCORE BUTTON
    if (checkPosition(menu, menu.getHighScore().getId())) {
      menu.getHighScore().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);

        Client.showLoadingScreen();
        if (!connectToServer()) {
          Client.changeScreen(ClientState.SHOW_ERROR, false, "Error connecting to server");
          return;
        }

        ServerConnection.instance.getHighScores();

        int currentCache = HighScoreCache.instance.cache;

        while (currentCache == HighScoreCache.instance.cache) {
          try {
            TimeUnit.MILLISECONDS.sleep(50);
          } catch (InterruptedException e) {

          }
        }

        Client.changeScreen(ClientState.HIGHSCORE, false);
        return;
      }
    } else menu.getHighScore().setColour(Colour.YELLOW);

    // OPTIONS BUTTON
    if (checkPosition(menu, menu.getOptions().getId())) {
      menu.getOptions().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        Client.changeScreen(ClientState.OPTIONS_MENU, false);
        return;
      }
    } else menu.getOptions().setColour(Colour.YELLOW);
    
    // HELP BUTTON
    if (checkPosition(menu, menu.getHelp().getId())) {
    	menu.getHelp().setColour();
    	if (Mouse.isLeftButtonPressed()) {
    		Audio.play(SELECT);
    		Client.changeScreen(ClientState.HELP, false);
    		return;
    	}
    } else menu.getHelp().setColour(Colour.YELLOW);

    // QUIT BUTTON
    if (checkPosition(menu, menu.getQuit().getId())) {
      menu.getQuit().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        Window.setShouldClose();
      }
    } else menu.getQuit().setColour(Colour.YELLOW);

    if (Mouse.getXPos() > menu.getSound().getPositionX()
        && Mouse.getYPos() > menu.getSound().getPositionY()) {
      if (Mouse.isLeftButtonPressed()) {
        Audio.toggle();
        Audio.play(SELECT);
      }
    }

    //    if (Keyboard.isKeyReleased(GLFW_KEY_EQUAL)) {
    //    	menu.incFont();
    //    }
    //
    //    if (Keyboard.isKeyReleased(GLFW_KEY_MINUS)) {
    //    	menu.decFont();
    //    }

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Window.setShouldClose();
    }
  }

  @Override
  public void render() {
    menu.getSoundMute().setRender(!Audio.isOn());
    menu.updateSize();

    renderer.render(menu);
  }

  @Override
  public void cleanUp() {
    menu.cleanup();
  }

  /**
   * Attempt to connect to server
   *
   * @return boolean whether connection was successful
   */
  private boolean connectToServer() {
    Client.showLoadingScreen();

    // Check for multiplayer connection
    if (ServerConnection.instance == null) {
      try {
        // Make connection
        if (!ServerConnection.makeConnection()) {
          return false;
        }

        // Authenticate
        try {
          ServerConnection.instance.auth();
        } catch (IOException e) {
          return false;
        } catch (ClientAlreadyAuthenticatedException e) {
          return true;
        }

        // Wait for auth
        int timeout = 5;
        int wait = 0;
        while (!ServerConnection.instance.isAuthenticated()) {
          // Wait
          try {
            TimeUnit.SECONDS.sleep(1);
          } catch (InterruptedException ignored) {
          }

          wait++;

          if (wait == timeout) {
            return false;
          }
        }
      } catch (ConfigItemNotFoundException ignored) {
      }
    }

    return true;
  }
}
