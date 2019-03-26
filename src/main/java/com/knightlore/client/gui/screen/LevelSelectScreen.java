package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.gui.LevelSelectMenu;
import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.game.Level;
import com.knightlore.game.map.LevelMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LevelSelectScreen implements IScreen {

  private static final String MAP_FILE_PATH = "customMaps/playable";

  private Queue<LevelDisplay> selectedLevels;

  private GuiRenderer guiRenderer;
  private LevelSelectMenu levelSelectMenu;

  public LevelSelectScreen(GuiRenderer guiRenderer) {
    this.guiRenderer = guiRenderer;

    levelSelectMenu = new LevelSelectMenu();
  }

  @Override
  public void startup(Object... args) {
    selectedLevels = new LinkedList<>();

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
        List<Level> levelList = getLevelsFromFile();
        Client.changeScreen(ClientState.GAME, true, levelList);
      }
    } else levelSelectMenu.getStart().setColour(Colour.YELLOW);

    if (checkPosition(levelSelectMenu, levelSelectMenu.getBack().getId())) {
      levelSelectMenu.getBack().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Client.changeScreen(ClientState.MAIN_MENU, false);
      }
    } else levelSelectMenu.getBack().setColour(Colour.YELLOW);

    if (checkPosition(levelSelectMenu, levelSelectMenu.getNextPage().getId())) {
      levelSelectMenu.getNextPage().setColour();
      if (Mouse.isLeftButtonPressed()) {
        levelSelectMenu.incPage();
      }
    } else levelSelectMenu.getNextPage().setColour(Colour.YELLOW);

    if (checkPosition(levelSelectMenu, levelSelectMenu.getLastPage().getId())) {
      levelSelectMenu.getLastPage().setColour();
      if (Mouse.isLeftButtonPressed()) {
        levelSelectMenu.decPage();
      }
    } else levelSelectMenu.getLastPage().setColour(Colour.YELLOW);

    for (int i = 0; i < levelSelectMenu.numOnScreenLevels(); i++) {
      if (checkPosition(levelSelectMenu, levelSelectMenu.getLevel(i).getId())) {
        if (!selectedLevels.contains(new LevelDisplay(levelSelectMenu.getLevel(i).getId(), i))) {
          levelSelectMenu.getLevel(i).setColour();
        }

        if (Mouse.isLeftButtonPressed()
            && !selectedLevels.contains(new LevelDisplay(levelSelectMenu.getLevel(i).getId(), i))) {
          if (selectedLevels.size() == 3) {
            LevelDisplay firstSelect = selectedLevels.remove();
            levelSelectMenu.getLevel(firstSelect.getIndex()).setColour();
          }

          selectedLevels.add(new LevelDisplay(levelSelectMenu.getLevel(i).getId(), i));
          levelSelectMenu.getLevel(i).setColour(Colour.GREEN);
        }
      } else {
        if (!selectedLevels.contains(new LevelDisplay(levelSelectMenu.getLevel(i).getId(), i))) {
          levelSelectMenu.getLevel(i).setColour(Colour.YELLOW);
        }
      }
    }
  }

  @Override
  public void render() {
    levelSelectMenu.updateSize();
    guiRenderer.render(levelSelectMenu);
  }

  @Override
  public void cleanUp() {
    levelSelectMenu.cleanup();
  }

  private List<Level> getLevelsFromFile() {
    List<Level> levelList = new ArrayList<>();
    selectedLevels.forEach(
        levelDisplay ->
            levelList.add(new Level(getMap(MAP_FILE_PATH + "/" + levelDisplay.getLevelName()))));
    return levelList;
  }

  private LevelMap getMap(String filePath) {
    File levelFile = new File(filePath);
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
      throw new IllegalStateException("Map does not exist");
    }
    return gson.fromJson(jsonString.toString(), LevelMap.class);
  }

  private class LevelDisplay {
    String levelName;
    int index;

    LevelDisplay(String levelName, int index) {
      this.levelName = levelName;
      this.index = index;
    }

    public String getLevelName() {
      return levelName;
    }

    public int getIndex() {
      return index;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }

      if (this.getClass() != obj.getClass()) {
        return false;
      }

      return index == ((LevelDisplay) obj).getIndex();
    }
  }
}
