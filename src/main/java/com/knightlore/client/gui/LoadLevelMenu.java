package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.Gui;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Screen that allows you to load different levels in the level editor
 *
 * @author Adam W, Joseph
 */
public class LoadLevelMenu extends Gui {

  /** Position of the top separator line */
  private static final int SEPARATOR_TOP_POS = 185;

  /** Position of the bottom separator line */
  private static final int SEPARATOR_BOT_POS = 200;

  /** Gap between each line of text */
  private static final int SEPARATOR_GAP = FONT_SIZE_SMALL;

  /** Maximum number of levels */
  private static final int MAX_LEVEL_COUNT = 12;

  /** List of all the set gui objects */
  private final GuiObject[] setGuiObjects;

  /** List of the set text objects that have user interaction */
  private final TextObject[] setTextObjects;

  /** Offset from centre to make space for map preview */
  private int menuOffset;

  /** Total number of pages */
  private int pageCount;

  /** level index */
  private int levelIndex;

  /** Current page number */
  private int currentPageNum;

  /** The page counter */
  private TextObject pageCounter;

  /** Bongo text object */
  private TextObject bongo;

  /** Congo text object */
  private TextObject congo;

  /** Top separator line text object */
  private TextObject separatorTop;

  /** Bottom separator line text object */
  private TextObject separatorBottom;

  /** List of the levels */
  private TextObject[] levels;

  /** Load level (title) text object */
  private TextObject loadLevel;

  /** Load level text object */
  private TextObject load;

  /** Exit screen text object */
  private TextObject exit;

  /** Next page text object */
  private TextObject nextPage;

  /** Last page text object */
  private TextObject lastPage;

  /**
   * Create gui objects
   *
   * @author Adam W
   */
  public LoadLevelMenu() {
    this.bongo = new TextObject("Bongo", TITLE);
    this.bongo.setColour(Colour.LIGHT_BLUE);

    this.congo = new TextObject("Congo", TITLE);
    this.congo.setColour(Colour.RED);

    this.loadLevel = new TextObject("Load Level", SMALL);
    this.loadLevel.setColour(Colour.YELLOW);

    this.separatorTop = new TextObject("---------------------", SMALL);
    this.separatorTop.setColour(Colour.YELLOW);

    this.separatorBottom = new TextObject("---------------------", SMALL);
    this.separatorBottom.setColour(Colour.YELLOW);

    this.load = new TextObject("Load", SMALL);
    this.load.setColour(Colour.YELLOW);

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
    menuOffset = 0;

    setGuiObjects =
        new GuiObject[] {
          bongo,
          congo,
          loadLevel,
          separatorTop,
          separatorBottom,
          load,
          exit,
          nextPage,
          lastPage,
          pageCounter
        };
    setTextObjects = new TextObject[] {load, exit, nextPage, lastPage};

    guiObjects = setGuiObjects;
    textObjects = setTextObjects;
  }

  /**
   * Returns load
   *
   * @return Load
   * @author Adam W
   */
  public TextObject getLoad() {
    return load;
  }

  /**
   * Returns exit
   *
   * @return exit
   * @author Joseph
   */
  public TextObject getExit() {
    return exit;
  }

  /**
   * Initialise levels
   *
   * @param l Level list
   * @author Adam W
   */
  public void setLevels(TextObject[] l) {
    levels = l;
    if (levels.length % 12 == 0) {
      pageCount = levels.length / 12;
    } else {
      pageCount = levels.length / 12 + 1;
    }
  }

  /**
   * Get level
   *
   * @param i Level index
   * @return Level
   * @author Adam W
   */
  public TextObject getLevel(int i) {
    return levels[levelIndex + i];
  }

  /**
   * Increase the page
   *
   * @author Adam W
   */
  public void incPage() {
    if (levelIndex + 12 < levels.length) {
      levelIndex += 12;
      currentPageNum += 1;
    }
  }

  /**
   * Decrease the page
   *
   * @author Adam W
   */
  public void decPage() {
    if (levelIndex >= 12) {
      levelIndex -= 12;
      currentPageNum -= 1;
    }
  }

  /**
   * Gets the next page
   *
   * @return NextPage
   * @author Adam W
   */
  public TextObject getNextPage() {
    return nextPage;
  }

  /**
   * Gets the last page
   *
   * @return LastPage
   * @author Adam W
   */
  public TextObject getLastPage() {
    return lastPage;
  }

  /**
   * Returns the number of levels on screen
   *
   * @return Number of levels on screen
   * @author Adam W
   */
  public int numOnScreenLevels() {
    return Math.min(MAX_LEVEL_COUNT, levels.length - levelIndex);
  }

  /**
   * Offsets the menu to the left by an amount
   *
   * @param amount amount to offset by
   * @author Adam Cox
   */
  public void offsetMenu(float amount) {
    if (menuOffset <= 250) {
      menuOffset += amount;
    }
  }

  /**
   * Updates the position of the gui objects
   *
   * @author Adam W
   */
  public void updateSize() {
    this.bongo.setPosition(
        Window.getHalfWidth() - bongo.getSize(), Window.getHalfHeight() - TITLE_POS);
    this.congo.setPosition(Window.getHalfWidth(), Window.getHalfHeight() - TITLE_POS);

    this.separatorTop.setPosition(
        Window.getHalfWidth() - separatorTop.getSize() / 2 - menuOffset,
        Window.getHalfHeight() - SEPARATOR_TOP_POS);
    this.separatorBottom.setPosition(
        Window.getHalfWidth() - separatorBottom.getSize() / 2 - menuOffset,
        Window.getHalfHeight() + SEPARATOR_BOT_POS);
    this.loadLevel.setPosition(
        Window.getHalfWidth() - loadLevel.getSize() / 2 - menuOffset,
        Window.getHalfHeight() - SEPARATOR_TOP_POS - SEPARATOR_GAP);
    this.load.setPosition(
        Window.getHalfWidth() - load.getSize() / 2 - menuOffset,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP);
    this.exit.setPosition(
        Window.getHalfWidth() - exit.getSize() / 2 - menuOffset,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * 2);
    this.nextPage.setPosition(
        Window.getHalfWidth() - separatorBottom.getSize() / 2 + nextPage.getSize() - menuOffset,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP + (float) nextPage.getHeight() / 2);
    this.lastPage.setPosition(
        Window.getHalfWidth() - separatorBottom.getSize() / 2 - menuOffset,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP + (float) lastPage.getHeight() / 2);

    this.pageCounter.setText(currentPageNum + "/" + pageCount);
    this.pageCounter.setPosition(
        Window.getHalfWidth() + separatorBottom.getSize() / 2 - pageCounter.getSize() - menuOffset,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP + pageCounter.getHeight());

    List<GuiObject> tempG = new ArrayList<>(Arrays.asList(setGuiObjects));
    List<TextObject> tempT = new ArrayList<>(Arrays.asList(setTextObjects));

    for (int i = 0; i < Math.min(MAX_LEVEL_COUNT, levels.length - levelIndex); i++) {
      tempG.add(levels[levelIndex + i]);
      tempT.add(levels[levelIndex + i]);
      levels[levelIndex + i].setPosition(
          Window.getHalfWidth() - levels[levelIndex + i].getSize() / 2 - menuOffset,
          Window.getHalfHeight()
              - SEPARATOR_TOP_POS
              + SEPARATOR_GAP
              + (levels[levelIndex + i].getHeight() * i));
    }

    guiObjects = tempG.toArray(setGuiObjects);
    textObjects = tempT.toArray(setTextObjects);
  }
}
