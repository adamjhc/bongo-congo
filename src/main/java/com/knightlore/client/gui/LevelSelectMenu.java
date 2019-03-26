package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelSelectMenu implements IGui {

  private static final int SEPARATOR_TOP_POS = 185;
  private static final int SEPARATOR_BOT_POS = 200;
  private static final int SEPARATOR_GAP = FONT_SIZE_SMALL;
  private static final int MAX_LEVEL_COUNT = 12;

  private final GuiObject[] setGuiObjects;
  private final TextObject[] setTextObjects;

  private int pageCount;

  private int levelIndex;

  private int currentPageNum;

  private TextObject pageCounter;

  private TextObject bongo;

  private TextObject congo;

  private TextObject separatorTop;

  private TextObject separatorBottom;

  private TextObject[] levels;

  private TextObject loadLevel;

  private TextObject start;

  private TextObject exit;

  private TextObject nextPage;

  private TextObject lastPage;

  private TextObject[] textObjects;

  private GuiObject[] guiObjects;

  public LevelSelectMenu() {
    this.bongo = new TextObject("Bongo", TITLE);
    this.bongo.setColour(Colour.LIGHT_BLUE);

    this.congo = new TextObject("Congo", TITLE);
    this.congo.setColour(Colour.RED);

    this.loadLevel = new TextObject("Select 3 Levels", SMALL);
    this.loadLevel.setColour(Colour.YELLOW);

    this.separatorTop = new TextObject("------------------------------", SMALL);
    this.separatorTop.setColour(Colour.YELLOW);

    this.separatorBottom = new TextObject("------------------------------", SMALL);
    this.separatorBottom.setColour(Colour.YELLOW);

    this.start = new TextObject("Start", SMALL);
    this.start.setColour(Colour.YELLOW);

    this.exit = new TextObject("Exit", SMALL);
    this.exit.setColour(Colour.YELLOW);

    this.nextPage = new TextObject(">", LARGE);
    this.nextPage.setColour(Colour.YELLOW);

    this.lastPage = new TextObject("<", LARGE);
    this.lastPage.setColour(Colour.YELLOW);

    this.pageCounter = new TextObject("1/" + pageCount, SMALL);
    this.pageCounter.setColour(Colour.YELLOW);

    levelIndex = 0;
    currentPageNum = 1;

    setGuiObjects =
        new GuiObject[] {
          bongo,
          congo,
          loadLevel,
          separatorTop,
          separatorBottom,
            start,
          exit,
          nextPage,
          lastPage,
          pageCounter
        };
    setTextObjects = new TextObject[] {start, exit, nextPage, lastPage};

    guiObjects = setGuiObjects;
    textObjects = setTextObjects;
  }

  public TextObject getStart() {
    return start;
  }

  public TextObject getBack() {
    return exit;
  }

  public void setLevels(TextObject[] l) {
    levels = l;
    if (levels.length % 12 == 0) {
      pageCount = levels.length / 12;
    } else {
      pageCount = levels.length / 12 + 1;
    }
  }

  public TextObject getLevel(int i) {
    return levels[levelIndex + i];
  }

  @Override
  public TextObject[] getTextObjects() {
    return textObjects;
  }

  @Override
  public GuiObject[] getGuiObjects() {
    return guiObjects;
  }

  public void incPage() {
    if (levelIndex + 12 < levels.length) {
      levelIndex += 12;
      currentPageNum += 1;
    }
  }

  public void decPage() {
    if (levelIndex >= 12) {
      levelIndex -= 12;
      currentPageNum -= 1;
    }
  }

  public TextObject getNextPage() {
    return nextPage;
  }

  public TextObject getLastPage() {
    return lastPage;
  }

  public int numOnScreenLevels() {
    return Math.min(MAX_LEVEL_COUNT, levels.length - levelIndex);
  }

  public void updateSize() {
    this.bongo.setPosition(
        Window.getHalfWidth() - bongo.getSize(), Window.getHalfHeight() - TITLE_POS);
    this.congo.setPosition(Window.getHalfWidth(), Window.getHalfHeight() - TITLE_POS);
    this.separatorTop.setPosition(
        Window.getHalfWidth() - separatorTop.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS);
    this.separatorBottom.setPosition(
        Window.getHalfWidth() - separatorBottom.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS);
    this.loadLevel.setPosition(
        Window.getHalfWidth() - loadLevel.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS - SEPARATOR_GAP);
    this.start.setPosition(
        Window.getHalfWidth() - start.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP);
    this.exit.setPosition(
        Window.getHalfWidth() - exit.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * 2);
    this.nextPage.setPosition(
        (Window.getHalfWidth() + nextPage.getSize() * 2) / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP + (float) nextPage.getHeight() / 2);
    this.lastPage.setPosition(
        (Window.getHalfWidth() - lastPage.getSize() / 2) / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP + (float) lastPage.getHeight() / 2);
    this.pageCounter.setText(currentPageNum + "/" + pageCount);
    this.pageCounter.setPosition(
        (Window.getHalfWidth() - pageCounter.getSize() / 2) * 1.5f,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP + pageCounter.getHeight());

    List<GuiObject> tempG = new ArrayList<>(Arrays.asList(setGuiObjects));
    List<TextObject> tempT = new ArrayList<>(Arrays.asList(setTextObjects));

    for (int i = 0; i < Math.min(MAX_LEVEL_COUNT, levels.length - levelIndex); i++) {
      tempG.add(levels[levelIndex + i]);
      tempT.add(levels[levelIndex + i]);
      levels[levelIndex + i].setPosition(
          Window.getHalfWidth() - levels[levelIndex + i].getSize() / 2,
          Window.getHalfHeight()
              - SEPARATOR_TOP_POS
              + SEPARATOR_GAP
              + (levels[levelIndex + i].getHeight() * i));
    }

    guiObjects = tempG.toArray(setGuiObjects);
    textObjects = tempT.toArray(setTextObjects);
  }
}
