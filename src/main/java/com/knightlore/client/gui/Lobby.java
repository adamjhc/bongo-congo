package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.Gui;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;
import java.util.ArrayList;
import java.util.List;

/**
 * Multiplayer lobby screen
 *
 * @author Joseph
 */
public class Lobby extends Gui {

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

  /** Lobby text */
  private final TextObject lobby;

  /** Top separator line text */
  private final TextObject separatorTop;

  /** Bottom separator line text */
  private final TextObject separatorBot;

  /** Exit screen text */
  private final TextObject exit;

  /** Start game text */
  private final TextObject start;

  private GuiObject[] baseGuiObjects;

  /**
   * Create gui objects
   *
   * @author Joseph
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

    baseGuiObjects = new GuiObject[] {bongo, congo, lobby, separatorTop, separatorBot, exit, start};

    guiObjects = new GuiObject[0];
    textObjects = new TextObject[] {exit, start};
  }

  /**
   * Sets the name of the lobby
   *
   * @param name The lobby name
   * @author Joseph
   */
  public void setLobbyName(String name) {
    this.lobby.setText(name);
  }

  /**
   * Refreshes to show the players who are currently in the lobby
   *
   * @param players
   * @author Joseph
   */
  public void refreshPlayers(List<String> players) {
    int yPos = SEPARATOR_TOP_POS - GAP;

    List<TextObject> playerTextObjects = new ArrayList<>();

    for (String player : players) {
      TextObject playerTextObject = new TextObject(player, SMALL);
      playerTextObject.setColour(Colour.YELLOW);
      playerTextObject.setPosition(
          Window.getHalfWidth() - playerTextObject.getSize() / 2, Window.getHalfHeight() - yPos);
      yPos -= GAP;
      playerTextObjects.add(playerTextObject);
    }

    addPlayers(playerTextObjects);
  }

  /**
   * Adds playerGameObjects to the list of gui objects
   *
   * @author Joseph
   */
  public void addPlayers(List<TextObject> playerTextObjects) {
    guiObjects = new GuiObject[baseGuiObjects.length + playerTextObjects.size()];

    System.arraycopy(baseGuiObjects, 0, guiObjects, 0, baseGuiObjects.length);
    for (int i = baseGuiObjects.length; i < baseGuiObjects.length + playerTextObjects.size(); i++) {
      guiObjects[i] = playerTextObjects.get(i - baseGuiObjects.length);
    }
  }

  /**
   * Updates the position of the gui objects
   *
   * @param includeStart Whether start is displayed or not
   * @author Joseph
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
          Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP);
    } else {
      this.exit.setPosition(
          Window.getHalfWidth() - exit.getSize() / 2,
          Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP);
    }
  }

  /**
   * Returns start
   *
   * @return start
   * @author Joseph
   */
  public TextObject getStart() {
    return start;
  }

  /**
   * Returns exit
   *
   * @return Exit
   * @author Joseph
   */
  public TextObject getExit() {
    return exit;
  }
}
