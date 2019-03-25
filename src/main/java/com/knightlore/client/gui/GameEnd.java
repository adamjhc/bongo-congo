package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;
import com.knightlore.game.entity.Player;
import java.util.ArrayList;

/**
 * Screen shown at the end of a game to display winner
 * 
 * @author Joseph
 *
 */
public class GameEnd implements IGui {

	/** Position of the top separator line */
  private static final int SEPARATOR_TOP_POS = 185;
  /** Position of the bottom separator line */
  private static final int SEPARATOR_BOT_POS = 200;
  /** Gap between each line of text */
  private static final int SEPARATOR_GAP = FONT_SIZE_SMALL;

  /** Bongo text object */
  private final TextObject bongo;
  /** Congo text object */
  private final TextObject congo;
  /** Exit screen text object */
  private final TextObject exit;
  /** Top separator line text object */
  private final TextObject separatorTop;
  /** Bottom separator line text object */
  private final TextObject separatorBot;
  /** Game winner text object */
  private final TextObject winner;
  /** List of each players score */
  private ArrayList<TextObject> scores;

  /** List of all the gui objects */
  private GuiObject[] guiObjects;
  /** List of the text objects that have user interaction */
  private TextObject[] textObjects;

  /** Position of the next gui object */
  private int yPos = SEPARATOR_TOP_POS - GAP;

  /**
   * Create gui objects
   * 
   * @author Joseph
   * 
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

    guiObjects = new GuiObject[] {bongo, congo, exit, separatorTop, separatorBot, winner};
    textObjects = new TextObject[] {exit};
  }

  /**
   * Creates a text object for each player to display their score
   * 
   * @param players List of the player names
   * @author Joseph
   * 
   */
  public void displayScores(ArrayList<Player> players) {
    yPos = SEPARATOR_TOP_POS - GAP;

    this.scores = new ArrayList<>();

    for (Player player : players) {
      TextObject score =
          new TextObject(player.getId() + ": " + player.getScore(), SMALL);
      score.setColour(player.getColour());
      score.setPosition(Window.getHalfWidth() - score.getSize() / 2, Window.getHalfHeight() - yPos);
      this.scores.add(score);
      yPos -= GAP;
    }
    addScores();
  }

  /**
   * Adds the scores to the list of gui objects
   * 
   * @author Joseph
   * 
   */
  public void addScores() {
    GuiObject[] guiObjectsNew = new GuiObject[guiObjects.length + scores.size()];
    System.arraycopy(guiObjects, 0, guiObjectsNew, 0, guiObjects.length);
    for (int i = guiObjects.length; i < guiObjects.length + scores.size(); i++) {
      guiObjectsNew[i] = scores.get(i - guiObjects.length);
    }
    guiObjects = guiObjectsNew.clone();
  }

  /**
   * Updates the position of the gui objects
   * 
   * @author Joseph
   * 
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
   * Returns the winner text object
   * 
   * @return The winner text object
   * @author Joseph
   * 
   */
  public TextObject getWinner() {
    return winner;
  }

  /**
   * Returns the exit text object
   *
   * @return The exit text object
   * @author Joseph
   * 
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
