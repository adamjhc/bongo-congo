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
 * Screen that allows you to select which levels you wish to play in Singleplayer
 *
 * @author Adam C, Joseph
 */
public class LevelSelectMenu extends Gui {

  /** Position of the top separator line */
  private static final int SEPARATOR_TOP_POS = 185;

  /** Position of the bottom separator line */
  private static final int SEPARATOR_BOT_POS = 200;

  /** Gap between each line of text */
  private static final int SEPARATOR_GAP = FONT_SIZE_SMALL;

  /** Maximum number of levels that can be shown */
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

  /** Bongo text */
  private TextObject bongo;

  /** Congo text */
  private TextObject congo;

  /** Top separator line text */
  private TextObject separatorTop;

  /** Bottom separator line text */
  private TextObject separatorBottom;

  /** List of the levels */
  private TextObject[] levels;

  /** Load level (title) text */
  private TextObject selectLevel;

  /** Start game text */
  private TextObject start;

  /** Exit screen text */
  private TextObject exit;

  /** Next page text */
  private TextObject nextPage;

  /** Last page text */
  private TextObject lastPage;

  /**
   * Create gui objects
   *
   * @author Adam C
   */
  public LevelSelectMenu() {
    this.bongo = new TextObject("Bongo", TITLE);
    this.bongo.setColour(Colour.LIGHT_BLUE);

    this.congo = new TextObject("Congo", TITLE);
    this.congo.setColour(Colour.RED);

    this.selectLevel = new TextObject("Select 3 Levels", SMALL);
    this.selectLevel.setColour(Colour.YELLOW);

    this.separatorTop = new TextObject("-------------------", SMALL);
    this.separatorTop.setColour(Colour.YELLOW);

    this.separatorBottom = new TextObject("-------------------", SMALL);
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
    menuOffset = 0;

    setGuiObjects =
        new GuiObject[] {
          bongo,
          congo,
          selectLevel,
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

  /**
   * Returns start
   *
   * @return Start
   * @author Adam C
   */
  public TextObject getStart() {
    return start;
  }

  /**
   * Returns exit
   *
   * @return Exit
   * @author Adam C
   */
  public TextObject getBack() {
    return exit;
  }

  /**
   * Initialise levels
   *
   * @param l Level list
   * @author Adam C
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
   * @author Adam C
   */
  public TextObject getLevel(int i) {
    return levels[levelIndex + i];
  }

  /**
   * Increase the page
   *
   * @author Adam C
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
   * @author Adam C
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
   * @author Adam C
   */
  public TextObject getNextPage() {
    return nextPage;
  }

  /**
   * Gets the last page
   *
   * @return LastPage
   * @author Adam C
   */
  public TextObject getLastPage() {
    return lastPage;
  }

  /**
   * Returns the number of levels on screen
   *
   * @return Number of levels on screen
   * @author Adam C
   */
  public int numOnScreenLevels() {
    return Math.min(MAX_LEVEL_COUNT, levels.length - levelIndex);
  }

  public void offsetMenu(float amount) {
    if (menuOffset <= 250) {
      menuOffset += amount;
    }
  }

  /**
   * Updates the position of the gui objects
   *
   * @author Adam C
   */
  public void updateSize() {
    this.bongo.setPosition(
        Window.getHalfWidth() - bongo.getSize(), Window.getHalfHeight() - TITLE_POS);
    this.congo.setPosition(Window.getHalfWidth(), Window.getHalfHeight() - TITLE_POS);

    this.selectLevel.setPosition(
        Window.getHalfWidth() - selectLevel.getSize() / 2 - menuOffset,
        Window.getHalfHeight() - SEPARATOR_TOP_POS - SEPARATOR_GAP);

    this.separatorTop.setPosition(
        Window.getHalfWidth() - separatorTop.getSize() / 2 - menuOffset,
        Window.getHalfHeight() - SEPARATOR_TOP_POS);
    this.separatorBottom.setPosition(
        Window.getHalfWidth() - separatorBottom.getSize() / 2 - menuOffset,
        Window.getHalfHeight() + SEPARATOR_BOT_POS);

    this.start.setPosition(
        Window.getHalfWidth() - start.getSize() / 2 - menuOffset,
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
