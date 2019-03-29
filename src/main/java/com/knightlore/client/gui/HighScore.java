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
 * Screen that displays the highscore for the game
 *
 * @author Adam Cox, Joseph
 */
public class HighScore extends Gui {

  /** The position in the window of the top --------------- */
  private static final int SEPARATOR_TOP_POS = 185;

  /** The position in the window of the bottom ---------------- */
  private static final int SEPARATOR_BOT_POS = 200;

  /** The size of of the gap between a separator and a word above or below it */
  private static final int SEPARATOR_GAP = FONT_SIZE_SMALL;

  /** The maximum number of highscore that can be displayed on screen at a given time */
  private static final int MAX_HIGHSCORE_COUNT = 12;

  /** The GuiObjects that will always be present on screen, regardless of the page number */
  private final GuiObject[] setGuiObjects;

  /** The TextObjects that will always be present on screen, regardless of the page number */
  private final TextObject[] setTextObjects;

  /** The number of pages necessary to display all highscore */
  private int pageCount;

  /** The index of the list of high scores that we start displaying high scores from */
  private int highScoreIndex;

  /** The page number we are currently on */
  private int currentPageNum;

  /** The TextObject used to display the page numbers */
  private TextObject pageCounter;

  /** The TextObject for the word "Bongo" */
  private TextObject bongo;

  /** The TextObject for the word "Congo" */
  private TextObject congo;

  /** The TextObject for the first "----------------" */
  private TextObject separatorTop;

  /** The TextObject for the second "----------------" */
  private TextObject separatorBottom;

  /** The list of highscore to be displayed on screen */
  private TextObject[] highScores;

  /** The TextObject for the words "Load Level" */
  private TextObject highScoreTitle;

  /** The TextObject for the word "exit" */
  private TextObject exit;

  /** The TextObject for the ">" icon */
  private TextObject nextPage;

  /** The TextObject for the "<" icon */
  private TextObject lastPage;

  public HighScore() {
    this.bongo = new TextObject("Bongo", TITLE);
    this.bongo.setColour(Colour.LIGHT_BLUE);

    this.congo = new TextObject("Congo", TITLE);
    this.congo.setColour(Colour.RED);

    this.highScoreTitle = new TextObject("Highscores", SMALL);
    this.highScoreTitle.setColour(Colour.YELLOW);

    this.separatorTop = new TextObject("------------------------------", SMALL);
    this.separatorTop.setColour(Colour.YELLOW);

    this.separatorBottom = new TextObject("------------------------------", SMALL);
    this.separatorBottom.setColour(Colour.YELLOW);

    this.exit = new TextObject("Exit", SMALL);
    this.exit.setColour(Colour.YELLOW);

    this.nextPage = new TextObject(">", LARGE);
    this.nextPage.setColour(Colour.YELLOW);

    this.lastPage = new TextObject("<", LARGE);
    this.lastPage.setColour(Colour.YELLOW);

    this.pageCounter = new TextObject("1/" + pageCount, SMALL);
    this.pageCounter.setColour(Colour.YELLOW);

    highScoreIndex = 0;
    currentPageNum = 1;

    setGuiObjects =
        new GuiObject[] {
          bongo,
          congo,
          separatorTop,
          separatorBottom,
          exit,
          nextPage,
          lastPage,
          pageCounter,
          highScoreTitle
        };
    setTextObjects = new TextObject[] {exit, nextPage, lastPage};

    guiObjects = setGuiObjects;
    textObjects = setTextObjects;
  }

  /**
   * Method to get the word "back" from the GUI
   *
   * @return The TextObject for the word "back"
   */
  public TextObject getBack() {
    return exit;
  }

  /**
   * Method to define the list of highScores
   *
   * @param highScores The highScores
   */
  public void setHighScores(TextObject[] highScores) {
    this.highScores = highScores;
    if (highScores.length % 12 == 0) {
      pageCount = highScores.length / 12;
    } else {
      pageCount = highScores.length / 12 + 1;
    }
  }

  /** Method to increment the start pointer of the list of highscore */
  public void incPage() {
    if (highScoreIndex + 12 < highScores.length) {
      highScoreIndex += 12;
      currentPageNum += 1;
    }
  }

  /** Method to decrement the start pointer of the list of highscore */
  public void decPage() {
    if (highScoreIndex >= 12) {
      highScoreIndex -= 12;
      currentPageNum -= 1;
    }
  }

  /**
   * Method to get the ">" icon from the GUI
   *
   * @return The TextObject for ">" from the GUI
   */
  public TextObject getNextPage() {
    return nextPage;
  }

  /**
   * Method to get the "<" icon from the GUI
   *
   * @return The TextObject for "<" from the GUI
   */
  public TextObject getLastPage() {
    return lastPage;
  }

  /**
   * Method to update the position of all objects in the GUI so that the size of the window does not
   * affect it
   */
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
    this.highScoreTitle.setPosition(
        Window.getHalfWidth() - highScoreTitle.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS - SEPARATOR_GAP);
    this.exit.setPosition(
        Window.getHalfWidth() - exit.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * 2);
    this.nextPage.setPosition(
        (Window.getHalfWidth() + nextPage.getSize() * 2) / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP + nextPage.getHeight() / 2f);
    this.lastPage.setPosition(
        (Window.getHalfWidth() - lastPage.getSize() / 2) / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP + lastPage.getHeight() / 2f);
    this.pageCounter.setText(currentPageNum + "/" + pageCount);
    this.pageCounter.setPosition(
        (Window.getHalfWidth() - pageCounter.getSize() / 2) * 1.5f,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP + pageCounter.getHeight());

    List<GuiObject> tempG = new ArrayList<>(Arrays.asList(setGuiObjects));
    List<TextObject> tempT = new ArrayList<>(Arrays.asList(setTextObjects));

    for (int i = 0; i < Math.min(MAX_HIGHSCORE_COUNT, highScores.length - highScoreIndex); i++) {
      tempG.add(highScores[highScoreIndex + i]);
      tempT.add(highScores[highScoreIndex + i]);
      highScores[highScoreIndex + i].setPosition(
          Window.getHalfWidth() - highScores[highScoreIndex + i].getSize() / 2,
          Window.getHalfHeight()
              - SEPARATOR_TOP_POS
              + SEPARATOR_GAP
              + (highScores[highScoreIndex + i].getHeight() * i));
    }

    guiObjects = tempG.toArray(setGuiObjects);
    textObjects = tempT.toArray(setTextObjects);
  }
}
