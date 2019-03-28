package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.audio.Audio;
import com.knightlore.client.audio.Audio.AudioName;
import com.knightlore.client.gui.LoadLevelMenu;
import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.game.map.LevelMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Screen to choose a saved level to edit
 * @author Adam W
 *
 */
public class LoadLevelScreen implements IScreen {

  /** Audio clip name for selection */
  private static final AudioName SELECT = AudioName.SOUND_MENUSELECT;

  /** The file path for all finished levels */
  private static final String finishedFilePath = "customMaps/playable";

  /** The file path for all unfinished levels */
  private static final String unfinishedFilePath = "customMaps/unplayable";

  /** The name of the currently selected level */
  private String currentLevelName;

  /** The renderer used to render the menu */
  private GuiRenderer guiRenderer;

  /** The object containing the menu */
  private LoadLevelMenu loadLevelMenu;

  /**
   * Initialise LoadLevelScreen
   * @param guiRenderer renderer used to render gui elements
   * @author Adam W
   */
  public LoadLevelScreen(GuiRenderer guiRenderer) {
    this.guiRenderer = guiRenderer;
    loadLevelMenu = new LoadLevelMenu();
    currentLevelName = "";
  }

  /** Method to initialise the menu when it is changed to
   * @author Adam W
   */
  @Override
  public void startup(Object... args) {
    File[] fLevels = (new File(finishedFilePath)).listFiles();
    File[] uLevels = (new File(unfinishedFilePath)).listFiles();

    int fCount = fLevels.length;
    TextObject[] allLevels = new TextObject[fCount + uLevels.length];
    for (int i = 0; i < fCount; i++) {
      String fileName = fLevels[i].getName();
      if (fileName.endsWith(".fmap")) {
        allLevels[i] = new TextObject(fileName.substring(0, fileName.length() - 5), IGui.SMALL);
        allLevels[i].setId(fileName);
        allLevels[i].setColour(Colour.YELLOW);
      }
    }
    for (int i = 0; i < uLevels.length; i++) {
      String fileName = uLevels[i].getName();
      if (fileName.endsWith(".umap")) {
        allLevels[i + fCount] =
            new TextObject(fileName.substring(0, fileName.length() - 5), IGui.SMALL);
        allLevels[i + fCount].setId(fileName);
        allLevels[i + fCount].setColour(Colour.YELLOW);
      }
    }

    loadLevelMenu.setLevels(allLevels);
  }

  /** Method to process users clicking on menu items 
   * @author Adam W
   */
  @Override
  public void input() {
    if (checkPosition(loadLevelMenu, loadLevelMenu.getLoad().getId())) {
      loadLevelMenu.getLoad().setColour();
      if (Mouse.isLeftButtonPressed() && !currentLevelName.equals("")) {
        Audio.play(SELECT);
        if (currentLevelName.contains(".fmap")) {
          Client.changeScreen(
              ClientState.LEVEL_EDITOR, false, getMap(finishedFilePath + "/" + currentLevelName));
        } else {
          Client.changeScreen(
              ClientState.LEVEL_EDITOR, false, getMap(unfinishedFilePath + "/" + currentLevelName));
        }
        return;
      }
    } else loadLevelMenu.getLoad().setColour(Colour.YELLOW);

    if (checkPosition(loadLevelMenu, loadLevelMenu.getExit().getId())) {
      loadLevelMenu.getExit().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        Client.changeScreen(ClientState.PRE_EDITOR, false);
        return;
      }
    } else loadLevelMenu.getExit().setColour(Colour.YELLOW);

    if (checkPosition(loadLevelMenu, loadLevelMenu.getNextPage().getId())) {
      loadLevelMenu.getNextPage().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        loadLevelMenu.incPage();
      }
    } else loadLevelMenu.getNextPage().setColour(Colour.YELLOW);

    if (checkPosition(loadLevelMenu, loadLevelMenu.getLastPage().getId())) {
      loadLevelMenu.getLastPage().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        loadLevelMenu.decPage();
      }
    } else loadLevelMenu.getLastPage().setColour(Colour.YELLOW);

    for (int i = 0; i < loadLevelMenu.numOnScreenLevels(); i++) {
      if (checkPosition(loadLevelMenu, loadLevelMenu.getLevel(i).getId())) {
        if (!currentLevelName.equals(loadLevelMenu.getLevel(i).getId()))
          loadLevelMenu.getLevel(i).setColour();
        if (Mouse.isLeftButtonPressed()) {
          Audio.play(SELECT);
          loadLevelMenu.getLevel(i).setColour(Colour.GREEN);
          currentLevelName = loadLevelMenu.getLevel(i).getId();
        }
      } else {
        if (!currentLevelName.equals(loadLevelMenu.getLevel(i).getId())) {
          loadLevelMenu.getLevel(i).setColour(Colour.YELLOW);
        }
      }
    }
  }

  /** Method to render the GUI 
   * @author Adam W
   */
  @Override
  public void render() {
    loadLevelMenu.updateSize();
    guiRenderer.render(loadLevelMenu);
  }

  /** Method to clean up the GUI
   * @author AdamW
   */
  @Override
  public void cleanUp() {
    loadLevelMenu.cleanup();
  }

  /**
   * Method to get the map object from the selected level name
   *
   * @param filePath The path to the map file being loaded
   * @return A new LevelMap built from the loaded map file
   * @author Adam W
   */
  private LevelMap getMap(String filePath) {
    File levelFile = new File(filePath);
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    String jsonString = "";
    try {
      FileReader fileReader = new FileReader(levelFile);
      BufferedReader levelReader = new BufferedReader(fileReader);
      String line = levelReader.readLine();
      jsonString = jsonString + line;
      while ((line = levelReader.readLine()) != null) {
        jsonString = jsonString + line;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return gson.fromJson(jsonString, LevelMap.class);
  }
}
