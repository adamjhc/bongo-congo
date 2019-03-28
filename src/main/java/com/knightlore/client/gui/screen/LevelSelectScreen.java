package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.audio.Audio;
import com.knightlore.client.audio.Audio.AudioName;
import com.knightlore.client.gui.LevelSelectMenu;
import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.io.Window;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.client.render.LevelSelectRenderer;
import com.knightlore.game.Level;
import com.knightlore.game.map.LevelMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.joml.Vector3f;
import org.joml.Vector3i;

/**
 * Screen for selecting 3 levels to use in Single Player
 * @author Adam Cox
 *
 */
public class LevelSelectScreen implements IScreen {

  /** Audio clip name used for selection */
  private static final AudioName SELECT = AudioName.SOUND_MENUSELECT;

  /** File path to maps */
  private static final String MAP_FILE_PATH = "customMaps/playable";

  /** Renderer used for rendering gui elements */
  private GuiRenderer guiRenderer;

  /** Renderer used for rendering map preview */
  private LevelSelectRenderer levelSelectRenderer;

  /** Collection of selected levels */
  private Collection<LevelDisplay> selectedLevels;

  /** Selected map for preview */
  private LevelMap selectedMap;

  /** GUI elements for screen */
  private LevelSelectMenu levelSelectMenu;

  /**
   * Initialise LevelSelectScreen
   *
   * @param levelSelectRenderer renderer used for screen
   * @author Adam Cox
   */
  public LevelSelectScreen(GuiRenderer guiRenderer, LevelSelectRenderer levelSelectRenderer) {
    this.guiRenderer = guiRenderer;
    this.levelSelectRenderer = levelSelectRenderer;

    levelSelectMenu = new LevelSelectMenu();
  }

  @Override
  public void startup(Object... args) {
    selectedLevels = new ArrayList<>();

    File[] fLevels = (new File(MAP_FILE_PATH)).listFiles();
    if (fLevels != null) {
      int fCount = fLevels.length;
      TextObject[] allLevels = new TextObject[fCount];
      for (int i = 0; i < fCount; i++) {
        String fileName = fLevels[i].getName();
        if (fileName.endsWith(".fmap")) {
          allLevels[i] = new TextObject(fileName.substring(0, fileName.length() - 5), IGui.SMALL);
          allLevels[i].setId(fileName);
          allLevels[i].setColour(Colour.YELLOW);
        }
      }

      levelSelectMenu.setLevels(allLevels);
    }
  }

  @Override
  public void input() {
    if (checkPosition(levelSelectMenu, levelSelectMenu.getStart().getId())) {
      levelSelectMenu.getStart().setColour();
      if (Mouse.isLeftButtonPressed() && selectedLevels.size() == 3) {
        Audio.play(SELECT);
        List<Level> levelList = getSelectedLevelsFromFile();

        Gson gson = new Gson();
        System.out.println(gson.toJson(levelList.get(0)));
        Client.changeScreen(ClientState.GAME, true, levelList);
        return;
      }
    } else levelSelectMenu.getStart().setColour(Colour.YELLOW);

    if (checkPosition(levelSelectMenu, levelSelectMenu.getBack().getId())) {
      levelSelectMenu.getBack().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        Client.changeScreen(ClientState.MAIN_MENU, false);
        return;
      }
    } else levelSelectMenu.getBack().setColour(Colour.YELLOW);

    if (checkPosition(levelSelectMenu, levelSelectMenu.getNextPage().getId())) {
      levelSelectMenu.getNextPage().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        levelSelectMenu.incPage();
      }
    } else levelSelectMenu.getNextPage().setColour(Colour.YELLOW);

    if (checkPosition(levelSelectMenu, levelSelectMenu.getLastPage().getId())) {
      levelSelectMenu.getLastPage().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        levelSelectMenu.decPage();
      }
    } else levelSelectMenu.getLastPage().setColour(Colour.YELLOW);

    for (int i = 0; i < levelSelectMenu.numOnScreenLevels(); i++) {
      if (checkPosition(levelSelectMenu, levelSelectMenu.getLevel(i).getId())) {
        if (selectedLevels.contains(new LevelDisplay(i)) || selectedLevels.size() != 3) {
          levelSelectMenu.getLevel(i).setColour();
        }

        if (Mouse.isLeftButtonPressed()) {
          Audio.play(SELECT);
          if (selectedLevels.contains(new LevelDisplay(i))) {
            selectedLevels.remove(new LevelDisplay(i));
          } else if (selectedLevels.size() != 3) {
            selectedLevels.add(new LevelDisplay(i, levelSelectMenu.getLevel(i).getId()));
          }
        }

        if (Mouse.isRightButtonPressed()) {
          Audio.play(SELECT);
          selectedMap = getMap(levelSelectMenu.getLevel(i).getId());
          Vector3i mapSize = selectedMap.getSize();

          levelSelectRenderer.setWorldScale(
              Math.round((Window.getHalfWidth() - 50) / (mapSize.x + mapSize.y)));
          levelSelectRenderer.setCameraPosition(
              new Vector3f(-mapSize.y, (mapSize.x + mapSize.z) / 2f, 0));
        }
      } else {
        if (selectedLevels.contains(new LevelDisplay(i))) {
          levelSelectMenu.getLevel(i).setColour(Colour.GREEN);
        } else {
          levelSelectMenu.getLevel(i).setColour(Colour.YELLOW);
        }
      }
    }
  }

  @Override
  public void update(float delta) {
    if (selectedMap != null) {
      levelSelectMenu.offsetMenu(delta * 300);
    }
  }

  @Override
  public void render() {
    levelSelectMenu.updateSize();

    levelSelectRenderer.render(selectedMap);
    guiRenderer.render(levelSelectMenu);
  }

  @Override
  public void shutdown(ClientState nextScreen) {
    selectedMap = null;
    levelSelectMenu = new LevelSelectMenu();
  }

  @Override
  public void cleanUp() {
    levelSelectMenu.cleanup();
  }

  /**
   * Gets a list of Level objects based on the selected maps
   *
   * @return list of level objects based on the selected maps
   * @author Adam Cox
   */
  private List<Level> getSelectedLevelsFromFile() {
    List<Level> levelList = new ArrayList<>();
    selectedLevels.forEach(
        levelDisplay -> levelList.add(new Level(getMap(levelDisplay.getLevelName()))));
    return levelList;
  }

  /**
   * Gets the LevelMap from file
   *
   * @param levelName Name of the level
   * @return LevelMap object
   * @author Adam Cox
   */
  private LevelMap getMap(String levelName) {
    File levelFile = new File(MAP_FILE_PATH + "/" + levelName);
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    StringBuilder jsonString = new StringBuilder();
    try (FileReader fileReader = new FileReader(levelFile);
        BufferedReader levelReader = new BufferedReader(fileReader)) {
      String line = levelReader.readLine();
      jsonString.append(line);
      while ((line = levelReader.readLine()) != null) {
        jsonString.append(line);
      }
    } catch (Exception ignored) {
    }
    return gson.fromJson(jsonString.toString(), LevelMap.class);
  }

  /** Used to display levels in the list */
  private class LevelDisplay {

    /** Name of level */
    String levelName;

    /** Index of level on display */
    int index;

    /**
     * Initialise LevelDisplay
     *
     * @param index Index of map
     * @author Adam Cox
     */
    LevelDisplay(int index) {
      this.index = index;
    }

    /**
     * Initialise LevelDisplay with level name
     *
     * @param index Index of map
     * @param levelName Level name
     * @author Adam Cox
     */
    LevelDisplay(int index, String levelName) {
      this.levelName = levelName;
      this.index = index;
    }

    /**
     * Get Level name
     *
     * @return Level name
     * @author Adam Cox
     */
    String getLevelName() {
      return levelName;
    }

    /**
     * Get level index
     *
     * @return level index
     * @author Adam Cox
     */
    public int getIndex() {
      return index;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }

      if (obj.getClass() != this.getClass()) {
        return false;
      }

      return index == ((LevelDisplay) obj).getIndex();
    }
  }
}
