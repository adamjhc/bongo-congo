package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;
import java.util.ArrayList;

/**
 * Multiplayer lobby screen
 * 
 * @author Joseph
 *
 */
public class Lobby implements IGui {

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
  /** Lobby text object */
  private final TextObject lobby;
  /** Top separator line text object */
  private final TextObject separatorTop;
  /** Bottom separator line text object */
  private final TextObject separatorBot;
  /** Exit screen text object */
  private final TextObject exit;
  /** Start game text object */
  private final TextObject start;
  /** Number of gui objects initially */
  private int length;
  
  /** List of players */
  private ArrayList<TextObject> players;
  /** List of gui objects */
  private GuiObject[] guiObjects;
  /** List of text objects */
  private TextObject[] textObjects;

  /** Position of the next gui object */
  private int yPos = SEPARATOR_TOP_POS - GAP;

  /**
   * Create gui objects
   * 
   * @author Joseph
   * 
   */
  public Lobby() {
    this.bongo = new TextObject("Bongo", TITLE);
    this.bongo.setColour(Colour.LIGHT_BLUE);

    this.congo = new TextObject("Congo", TITLE);
    this.congo.setColour(Colour.RED);

    this.lobby = new TextObject("Lobby", SMALL);
    this.lobby.setColour(Colour.YELLOW);

    this.separatorTop = new TextObject("------------------------------", SMALL);
    this.separatorTop.setColour(Colour.YELLOW);
    this.separatorTop.setId("Separator Top");

    this.separatorBot = new TextObject("------------------------------", SMALL);
    this.separatorBot.setColour(Colour.YELLOW);
    this.separatorBot.setId("Separator Bot");

    this.exit = new TextObject("Exit", SMALL);
    this.exit.setColour(Colour.YELLOW);

    this.start = new TextObject("Start", SMALL);
    this.start.setColour(Colour.YELLOW);

    this.start.setRender(false);

    guiObjects = new GuiObject[] {bongo, congo, lobby, separatorTop, separatorBot, exit, start};
    length = guiObjects.length;

    textObjects = new TextObject[] {exit, start};
  }

  /**
   * Sets the name of the lobby
   * 
   * @param name The lobby name
   * @author Joseph
   * 
   */
  public void setLobbyName(String name) {
    this.lobby.setText(name);
  }

  /**
   * Refreshes to show the players who are currently in the lobby
   * 
   * @param players
   * @author Joseph
   * 
   */
  public void refreshPlayers(ArrayList<String> players) {
    yPos = SEPARATOR_TOP_POS - GAP;

    if (players != null) {
      GuiObject[] guiObjectsNew = new GuiObject[length];
      for (int i = 0; i < length; i++) {
        guiObjectsNew[i] = guiObjects[i];
      }
      guiObjects = guiObjectsNew.clone();
    }

    this.players = new ArrayList<>();

    for (String player : players) {
      this.players.add(new TextObject(player, SMALL));
    }

    for (TextObject player : this.players) {
      player.setColour(Colour.YELLOW);
      player.setPosition(
          Window.getHalfWidth() - player.getSize() / 2, Window.getHalfHeight() - yPos);
      yPos -= GAP;
    }

    addPlayers();
  }

  /**
   * Adds players to the list of gui objects 
   * 
   * @author Joseph
   * 
   */
  public void addPlayers() {
    GuiObject[] guiObjectsNew = new GuiObject[guiObjects.length + players.size()];
    System.arraycopy(guiObjects, 0, guiObjectsNew, 0, guiObjects.length);
    for (int i = guiObjects.length; i < guiObjects.length + players.size(); i++) {
      guiObjectsNew[i] = players.get(i - guiObjects.length);
    }
    guiObjects = guiObjectsNew.clone();
  }
  
  /**
   * Updates the position of the gui objects
   * 
   * @param includeStart Whether start is displayed or not
   * @author Joseph
   * 
   */
  public void updateSize(boolean includeStart) {
    this.bongo.setPosition(
        Window.getHalfWidth() - bongo.getSize(), Window.getHalfHeight() - TITLE_POS);
    this.congo.setPosition(Window.getHalfWidth(), Window.getHalfHeight() - TITLE_POS);
    this.separatorTop.setPosition(
        Window.getHalfWidth() - separatorTop.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS);
    this.separatorBot.setPosition(
        Window.getHalfWidth() - separatorTop.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS);
    this.lobby.setPosition(
        Window.getHalfWidth() - lobby.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS - SEPARATOR_GAP);
    if (includeStart) {
      this.exit.setPosition(
          Window.getHalfWidth() - exit.getSize() / 2,
          Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * 2);
      this.start.setPosition(
          Window.getHalfWidth() - start.getSize() / 2,
          Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * 1);
    } else {
      this.exit.setPosition(
          Window.getHalfWidth() - exit.getSize() / 2,
          Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * 1);
    }
  }
  
  /**
   * Returns start
   * 
   * @return start
   * @author Joseph
   * 
   */
  public TextObject getStart() {
    return start;
  }

  /**
   * Returns exit
   * 
   * @return Exit
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
