package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.audio.Audio;
import com.knightlore.client.audio.Audio.AudioName;
import com.knightlore.client.gui.PreLevelEditor;
import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.game.map.LevelMap;
import org.joml.Vector4f;

/**
 * Screen to choose the inital paramaters for a new level to edit
 * @author Adam W
 *
 */
public class LevelEditorSetupScreen implements IScreen {

  /** The sound that plays when the users selects a menu item */
  private static final AudioName SELECT = AudioName.SOUND_MENUSELECT;

  /** The renderer to render to menu */
  private GuiRenderer guiRenderer;

  /** The menu object */
  private PreLevelEditor preLevelEditor;

  /**
   * Initialise LevelEditorSetupScreen
   * @param guiRenderer renderer used to render gui elements
   * @author Adam W
   */
  public LevelEditorSetupScreen(GuiRenderer guiRenderer) {
    this.guiRenderer = guiRenderer;
    preLevelEditor = new PreLevelEditor();
  }

  /** Method to process the user clicking on menu items
   * @author Adam W
   */
  @Override
  public void input() {
    if (checkPosition(preLevelEditor, preLevelEditor.getWLeft().getId())) {
      preLevelEditor.getWLeft().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        preLevelEditor.decWidth();
      }
    } else preLevelEditor.getWLeft().setColour(Colour.YELLOW);

    if (checkPosition(preLevelEditor, preLevelEditor.getWRight().getId())) {
      preLevelEditor.getWRight().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        preLevelEditor.incWidth();
      }
    } else preLevelEditor.getWRight().setColour(Colour.YELLOW);

    if (checkPosition(preLevelEditor, preLevelEditor.getLLeft().getId())) {
      preLevelEditor.getLLeft().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        preLevelEditor.decLength();
      }
    } else preLevelEditor.getLLeft().setColour(Colour.YELLOW);

    if (checkPosition(preLevelEditor, preLevelEditor.getLRight().getId())) {
      preLevelEditor.getLRight().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        preLevelEditor.incLength();
      }
    } else preLevelEditor.getLRight().setColour(Colour.YELLOW);

    if (checkPosition(preLevelEditor, preLevelEditor.getHLeft().getId())) {
      preLevelEditor.getHLeft().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        preLevelEditor.decHeight();
      }
    } else preLevelEditor.getHLeft().setColour(Colour.YELLOW);

    if (checkPosition(preLevelEditor, preLevelEditor.getHRight().getId())) {
      preLevelEditor.getHRight().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        preLevelEditor.incHeight();
      }
    } else preLevelEditor.getHRight().setColour(Colour.YELLOW);

    if (checkPosition(preLevelEditor, preLevelEditor.getCreateLevel().getId())) {
      preLevelEditor.getCreateLevel().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        LevelMap editorMap =
            initialiseMap(
                preLevelEditor.getWidthNum(),
                preLevelEditor.getLengthNum(),
                preLevelEditor.getHeightNum());
        Client.changeScreen(ClientState.LEVEL_EDITOR, false, editorMap);
        return;
      }
    } else preLevelEditor.getCreateLevel().setColour(Colour.YELLOW);

    if (checkPosition(preLevelEditor, preLevelEditor.getExit().getId())) {
      preLevelEditor.getExit().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        Client.changeScreen(ClientState.MAIN_MENU, false);
        return;
      }
    } else preLevelEditor.getExit().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(preLevelEditor, preLevelEditor.getLoadLevel().getId(), "")) {
      preLevelEditor.getLoadLevel().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        Client.changeScreen(ClientState.LOADING_LEVEL, false, new Object());
        return;
      }
    } else preLevelEditor.getLoadLevel().setColour(new Vector4f(1, 1, 0, 1));

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(ClientState.MAIN_MENU, false);
    }
  }

  /** Method to render the menu */
  @Override
  public void render() {
    preLevelEditor.updateSize();

    guiRenderer.render(preLevelEditor);
  }

  /** Method to cleanup the GUI 
   * @author Adam W
   */
  @Override
  public void cleanUp() {
    preLevelEditor.cleanup();
  }

  /**
   * Method to set up an empty map of the specified dimensions
   *
   * @param width The width of the level
   * @param length The length of the level
   * @param height The height of the level
   * @return A new LevelMap with only floor tiles on the bottom layer and empty tiles on all others
   * @author Adam W
   */
  private LevelMap initialiseMap(int width, int length, int height) {
    int[][][] emptyMap = new int[height][length][width];
    for (int i = 0; i < emptyMap.length; i++) {
      for (int j = 0; j < emptyMap[i].length; j++) {
        for (int k = 0; k < emptyMap[i][j].length; k++) {
          if (i == 0) {
            emptyMap[i][j][k] = 1;
          } else {
            emptyMap[i][j][k] = 0;
          }
        }
      }
    }
    return (new LevelMap(emptyMap));
  }
}
