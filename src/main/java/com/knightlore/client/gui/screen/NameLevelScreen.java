package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_0;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_3;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_4;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_5;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_6;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_7;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_8;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_9;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_APOSTROPHE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_B;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_C;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_G;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_H;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_I;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_J;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_K;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ADD;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_SUBTRACT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_L;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_BRACKET;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_M;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_N;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_O;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_BRACKET;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SEMICOLON;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_T;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_U;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_V;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Y;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.audio.Audio;
import com.knightlore.client.audio.Audio.AudioName;
import com.knightlore.client.exceptions.ClientAlreadyAuthenticatedException;
import com.knightlore.client.exceptions.ConfigItemNotFoundException;
import com.knightlore.client.gui.NameLevel;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.networking.ServerConnection;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.game.Level;
import com.knightlore.game.map.LevelMap;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.joml.Vector4f;

/**
 * The screen for naming and saving an edited level
 * @author Adam W
 *
 */
public class NameLevelScreen implements IScreen {

  /** Audio clip name for selection */
  private static final AudioName SELECT = AudioName.SOUND_MENUSELECT;

  /** The list of all characters that we respond to the user pressing */
  private static ArrayList<Integer> acceptableCharacters =
      new ArrayList<>(
          Arrays.asList(
              GLFW_KEY_0,
              GLFW_KEY_1,
              GLFW_KEY_2,
              GLFW_KEY_3,
              GLFW_KEY_4,
              GLFW_KEY_5,
              GLFW_KEY_6,
              GLFW_KEY_7,
              GLFW_KEY_8,
              GLFW_KEY_9,
              GLFW_KEY_Q,
              GLFW_KEY_W,
              GLFW_KEY_E,
              GLFW_KEY_R,
              GLFW_KEY_T,
              GLFW_KEY_Y,
              GLFW_KEY_U,
              GLFW_KEY_I,
              GLFW_KEY_O,
              GLFW_KEY_P,
              GLFW_KEY_LEFT_BRACKET,
              GLFW_KEY_RIGHT_BRACKET,
              GLFW_KEY_A,
              GLFW_KEY_S,
              GLFW_KEY_D,
              GLFW_KEY_F,
              GLFW_KEY_G,
              GLFW_KEY_H,
              GLFW_KEY_J,
              GLFW_KEY_K,
              GLFW_KEY_K,
              GLFW_KEY_L,
              GLFW_KEY_SEMICOLON,
              GLFW_KEY_APOSTROPHE,
              GLFW_KEY_Z,
              GLFW_KEY_X,
              GLFW_KEY_C,
              GLFW_KEY_V,
              GLFW_KEY_B,
              GLFW_KEY_N,
              GLFW_KEY_M,
              GLFW_KEY_KP_SUBTRACT,
              GLFW_KEY_KP_ADD,
              GLFW_KEY_BACKSPACE,
              GLFW_KEY_SPACE));

  /** Whether the level being saved is complete or not */
  private boolean complete;

  /** The renderer used to render the menu */
  private GuiRenderer guiRenderer;

  /** The object containing the menu GUI */
  private NameLevel nameLevelUi;

  /** The map for the level being saved */
  private LevelMap level;

  /**
   * Initialise NameLevelScreen
   * @param guiRenderer renderer used for rendering gui elements
   * @author Adam W
   */
  public NameLevelScreen(GuiRenderer guiRenderer) {
    this.guiRenderer = guiRenderer;
    this.nameLevelUi = new NameLevel();
  }

  /** Method to set up the screen with the level being saved 
   * @author Adam W
   */
  @Override
  public void startup(Object... args) {
    level = (LevelMap) args[0];
    complete = (boolean) args[1];
    if (complete) {
      nameLevelUi.showPublish();
    } else nameLevelUi.removePublish();
  }

  /** Method to process user keyboard input and clicking on menu items 
   * @author Adam W
   */
  @Override
  public void input() {
    int lastKey = Keyboard.getKeyCode();
    if (lastKey != -1 && acceptableCharacters.contains(lastKey)) {
      if (lastKey == GLFW_KEY_BACKSPACE) {
        TextObject levelName = nameLevelUi.getLevelName();
        if (levelName.getText().length() <= 1) {
          levelName.setText("");
        } else {
          levelName.setText(levelName.getText().substring(0, levelName.getText().length() - 1));
        }
      } else {
        TextObject levelName = nameLevelUi.getLevelName();
        if (levelName.getText().length() < 30)
          levelName.setText(levelName.getText() + ((char) lastKey));
      }
    }

    if (checkPosition(nameLevelUi, nameLevelUi.getSaveAndContinue().getId())) {
      nameLevelUi.getSaveAndContinue().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        try {
          save(nameLevelUi.getLevelName().getText());
        } catch (IOException e) {
          e.printStackTrace();
        }
        Client.changeScreen(ClientState.LEVEL_EDITOR, false, level);
        return;
      }
    } else nameLevelUi.getSaveAndContinue().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(nameLevelUi, nameLevelUi.getSaveAndQuit().getId())) {
      nameLevelUi.getSaveAndQuit().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        try {
          save(nameLevelUi.getLevelName().getText());
        } catch (IOException e) {
          e.printStackTrace();
        }
        Client.changeScreen(ClientState.MAIN_MENU, false);
        return;
      }
    } else nameLevelUi.getSaveAndQuit().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(nameLevelUi, nameLevelUi.getCancel().getId())) {
      nameLevelUi.getCancel().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        Client.changeScreen(ClientState.LEVEL_EDITOR, false, level);
      }
    } else nameLevelUi.getCancel().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(nameLevelUi, nameLevelUi.getPublish().getId())) {
      nameLevelUi.getPublish().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        if (!connectToServer()) {
          Client.changeScreen(ClientState.SHOW_ERROR, false, "Error connecting to server");
          return;
        }

        Level sentLevel = new Level(level);
        System.out.println("LEVEL: " + nameLevelUi.getLevelName().getText());
        ServerConnection.instance.sendLevel(sentLevel, nameLevelUi.getLevelName().getText());
        nameLevelUi.removePublish();
      }
    } else nameLevelUi.getPublish().setColour(new Vector4f(1, 1, 0, 1));
  }

  /** Method to render the GUI 
   * @author Adam W
   */
  @Override
  public void render() {
    nameLevelUi.updateSize();
    guiRenderer.render(nameLevelUi);
  }

  /** Method to clean up the GUI
   * @author Adam W
   */
  @Override
  public void cleanUp() {
    nameLevelUi.cleanup();
  }

  /**
   * Method to save the level being named to a file
   * @param name The name of the level
   * @throws IOException Thrown when the file path can't be found
   * @author Adam W
   */
  private void save(String name) throws IOException {
    String filePath = "customMaps/";
    String fileType;
    if (complete) {
      filePath = filePath + "playable/";
      fileType = ".fmap";
    } else {
      filePath = filePath + "unplayable/";
      fileType = ".umap";
    }

    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();

    String jsonString = gson.toJson(level);

    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + name + fileType));
    writer.write(jsonString);
    writer.close();
  }

  /**
   * Attempt to connect to server
   * @return boolean whether connection was successful
   * @author Adam W
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
