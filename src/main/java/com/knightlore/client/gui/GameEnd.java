package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;
import com.knightlore.game.entity.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * Screen shown at the end of a game to display winner
 *
 * @author Joseph
 */
public class GameEnd implements IGui {

  /** Position of the top separator line */
  private static final int SEPARATOR_TOP_POS = 185;

  /** Position of the bottom separator line */
  private static final int SEPARATOR_BOT_POS = 200;

  /** Gap between each line of text */
  private static final int SEPARATOR_GAP = FONT_SIZE_SMALL;

  /** Bongo text */
  private final TextObject bongo;

  /** Congo text */
  private final TextObject congo;

  /** Exit screen text */
  private final TextObject exit;

  /** Top separator line text */
  private final TextObject separatorTop;

  /** Bottom separator line text */
  private final TextObject separatorBot;

  /** Game winner text */
  private final TextObject winner;

  /** List of base gui objects */
  private GuiObject[] baseGuiObjects;

  /** List of all the gui objects */
  private GuiObject[] guiObjects;

  /** List of the text objects that have user interaction */
  private TextObject[] textObjects;

  /**
   * Create gui objects
   *
   * @author Joseph
   */
  public GameEnd() {
    this.bongo = new TextObject("Bongo", TITLE);
    this.bongo.setColour(Colour.LIGHT_BLUE);

    this.congo = new TextObject("Congo", TITLE);
    this.congo.setColour(Colour.RED);

    this.exit = new TextObject("Exit", SMALL);
    this.exit.setColour(Colour.YELLOW);

    this.separatorTop = new TextObject("------------------------------", SMALL);
    this.separatorTop.setColour(Colour.YELLOW);

    this.separatorBot = new TextObject("------------------------------", SMALL);
    this.separatorBot.setColour(Colour.YELLOW);

    this.winner = new TextObject("Winner", SMALL);
    this.winner.setColour(Colour.YELLOW);

    baseGuiObjects = new GuiObject[] {bongo, congo, exit, separatorTop, separatorBot, winner};

    guiObjects = new GuiObject[0];
    textObjects = new TextObject[] {exit};
  }

  /**
   * Creates a text object for each player to display their score
   *
   * @param players List of the player names
   * @author Joseph
   */
  public void displayScores(List<Player> players) {
    int yPos = SEPARATOR_TOP_POS - GAP;

    List<TextObject> scores = new ArrayList<>();

    for (Player player : players) {
      TextObject score = new TextObject(player.getId() + ": " + player.getScore(), SMALL);
      score.setColour(player.getColour());
      score.setPosition(Window.getHalfWidth() - score.getSize() / 2, Window.getHalfHeight() - yPos);
      scores.add(score);
      yPos -= GAP;
    }

    addScores(scores);
  }

  /**
   * Adds the scores to the list of gui objects
   *
   * @author Joseph
   */
  private void addScores(List<TextObject> scores) {
    guiObjects = new GuiObject[baseGuiObjects.length + scores.size()];

    System.arraycopy(baseGuiObjects, 0, guiObjects, 0, baseGuiObjects.length);
    for (int i = baseGuiObjects.length; i < baseGuiObjects.length + scores.size(); i++) {
      guiObjects[i] = scores.get(i - baseGuiObjects.length);
    }
  }

  /**
   * Updates the position of the gui objects
   *
   * @author Joseph
   */
  public void updateSize() {
    this.bongo.setPosition(
        Window.getHalfWidth() - bongo.getSize(), Window.getHalfHeight() - TITLE_POS);
    this.congo.setPosition(Window.getHalfWidth(), Window.getHalfHeight() - TITLE_POS);
    this.separatorTop.setPosition(
        Window.getHalfWidth() - separatorTop.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS);
    this.separatorBot.setPosition(
        Window.getHalfWidth() - separatorBot.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS);
    this.exit.setPosition(
        Window.getHalfWidth() - exit.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP);
    this.winner.setPosition(
        Window.getHalfWidth() - winner.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS - SEPARATOR_GAP);
  }

  /**
   * Returns winner
   *
   * @return Winner
   * @author Joseph
   */
  public TextObject getWinner() {
    return winner;
  }

  /**
   * Returns Exit
   *
   * @return Exit
   * @author Joseph
   */
  public TextObject getExit() {
    return exit;
  }

  @Override
  public TextObject[] getTextObjects() {
    return textObjects;
  }

  @Override
  public GuiObject[] getGuiObjects() {
    return guiObjects;
  }
}
