package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.*;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.io.Window;
import com.knightlore.client.networking.LobbyCache;
import com.knightlore.networking.server.ListGameObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Screen that shows the list of all the joinable lobbies and has the option to create / refresh
 * games
 *
 * @author Joseph
 */
public class LobbyMenu extends Gui {

  /** Position of the top separator line */
  private static final int SEPARATOR_TOP_POS = 185;

  /** Position of the bottom separator line */
  private static final int SEPARATOR_BOT_POS = 200;

  /** Gap between each line of text */
  private static final int SEPARATOR_GAP = FONT_SIZE_SMALL;

  /** The maximum number of servers that can be displayed */
  private static final int MAX_SERVERS = (SEPARATOR_TOP_POS + SEPARATOR_BOT_POS - GAP) / GAP;

  /** Top separator line text */
  private final TextObject separatorTop;

  /** Bottom separator line text */
  private final TextObject separatorBot;

  /** Create game text */
  private final TextObject create;

  /** Exit screen text */
  private final TextObject exit;

  /** Bongo text */
  private final TextObject bongo;

  /** Congo text */
  private final TextObject congo;

  /** Multiplayer title text */
  private final TextObject multiplayer;

  /** Join game text */
  private final TextObject join;

  /** Refresh games text */
  private final TextObject refresh;

  /** List of lobbies */
  private List<LobbyObject> lobbies;

  /** Length of gui objects initially */
  private int length;

  /** Current lobby */
  private int current;

  /** Create gui objects */
  public LobbyMenu() {
    this.bongo = new TextObject("Bongo", TITLE);
    this.bongo.setColour(Colour.LIGHT_BLUE);

    this.congo = new TextObject("Congo", TITLE);
    this.congo.setColour(Colour.RED);

    this.multiplayer = new TextObject("Play Multiplayer", SMALL);
    this.multiplayer.setColour(Colour.YELLOW);

    this.separatorTop = new TextObject("------------------------------", SMALL);
    this.separatorTop.setColour(Colour.YELLOW);
    this.separatorTop.setId("Separator Top");

    this.separatorBot = new TextObject("------------------------------", SMALL);
    this.separatorBot.setColour(Colour.YELLOW);
    this.separatorBot.setId("Separator Bot");

    this.join = new TextObject("Join game", SMALL);
    this.join.setColour(Colour.YELLOW);

    this.create = new TextObject("Create game", SMALL);
    this.create.setColour(Colour.YELLOW);

    this.refresh = new TextObject("Refresh", SMALL);
    this.refresh.setColour(Colour.YELLOW);

    this.exit = new TextObject("Exit", SMALL);
    this.exit.setColour(Colour.YELLOW);

    guiObjects =
        new GuiObject[] {
          bongo, congo, multiplayer, separatorTop, separatorBot, join, create, exit, refresh
        };
    length = guiObjects.length;

    textObjects = new TextObject[] {join, create, exit, separatorTop, separatorBot, refresh};
  }

  public void refreshLobbies() {
    int yPos = SEPARATOR_TOP_POS - GAP;
    current = 0;

    this.lobbies = new ArrayList<>();

    if (LobbyCache.instance != null) {
      Collection<ListGameObject> games = LobbyCache.instance.getGames();
      for (ListGameObject game : games) {
        lobbies.add(
            new LobbyObject(
                game.getName() + " " + game.getUsernames().size() + "/6 players", SMALL, game));
      }

      for (LobbyObject lobby : lobbies) {
        lobby.setColour(Colour.YELLOW);
        lobby.setPosition(
            Window.getHalfWidth() - lobby.getSize() / 2, Window.getHalfHeight() - yPos);
        yPos -= GAP;
      }

      addLobbies();
    }
  }

  /**
   * Deletes a lobby
   *
   * @param game The game to delete
   */
  public void deleteLobby(ListGameObject game) {
    int i = 0;
    int deleteIndex = -1;
    for (LobbyObject lobby : lobbies) {
      if (lobby.getGame().getUuid() == game.getUuid()) {
        deleteIndex = i;
        break;
      }
      i++;
    }
    if (deleteIndex != -1) {
      lobbies.remove(deleteIndex);
      refreshLobbies();
    }
  }

  /** Adds a lobby */
  private void addLobby() {
    if (lobbies.size() <= MAX_SERVERS) {
      GuiObject[] guiObjectsNew = new GuiObject[length + lobbies.size()];
      if (length >= 0) System.arraycopy(guiObjects, 0, guiObjectsNew, 0, length);
      for (int i = length; i < length + lobbies.size(); i++) {
        guiObjectsNew[i] = lobbies.get(i - length);
      }
      guiObjects = guiObjectsNew.clone();
    }
  }

  /** Adds list of lobbies */
  private void addLobbies() {
    if (lobbies.size() <= MAX_SERVERS) {
      GuiObject[] guiObjectsNew = new GuiObject[length + lobbies.size()];
      System.arraycopy(guiObjects, 0, guiObjectsNew, 0, length);
      for (int i = length; i < length + lobbies.size(); i++) {
        guiObjectsNew[i] = lobbies.get(i - length);
      }
      guiObjects = guiObjectsNew.clone();
    } else {
      GuiObject[] guiObjectsNew = new GuiObject[length + MAX_SERVERS];
      System.arraycopy(guiObjects, 0, guiObjectsNew, 0, length);
      for (int i = length; i < length + MAX_SERVERS; i++) {
        guiObjectsNew[i] = lobbies.get(i - length);
      }
      guiObjects = guiObjectsNew.clone();
    }
  }

  /** Scroll down */
  public void moveDown() {
    if (lobbies.size() > MAX_SERVERS && current < lobbies.size() - MAX_SERVERS) {
      current++;
      GuiObject[] guiObjectsNew = new GuiObject[length + MAX_SERVERS];
      if (length >= 0) System.arraycopy(guiObjects, 0, guiObjectsNew, 0, length);
      for (int i = length; i < length + MAX_SERVERS; i++) {
        guiObjectsNew[i] = lobbies.get((i - length) + current);
      }
      guiObjects = guiObjectsNew.clone();

      for (LobbyObject lobby : lobbies) {
        lobby.setPositionY(lobby.getPositionY() - GAP);
      }
    }
  }

  /** Scroll up */
  public void moveUp() {
    if (current > 0) {
      current--;

      GuiObject[] guiObjectsNew = new GuiObject[length + MAX_SERVERS];
      if (length >= 0) System.arraycopy(guiObjects, 0, guiObjectsNew, 0, length);
      for (int i = length; i < length + MAX_SERVERS; i++) {
        guiObjectsNew[i] = lobbies.get((i - length) + current);
      }
      guiObjects = guiObjectsNew.clone();

      for (LobbyObject lobby : lobbies) {
        lobby.setPositionY(lobby.getPositionY() + GAP);
      }
    }
  }

  /**
   * Highlight a lobby
   *
   * @author Joseph
   */
  public void highlight() {
    double pos = (Mouse.getYPos() - (Window.getHalfHeight() - (SEPARATOR_TOP_POS - GAP * 2))) / GAP;
    int posInt = (int) Math.ceil(pos);
    if (posInt >= 0 && posInt < Math.min(MAX_SERVERS, lobbies.size())) {
      setHighlight(posInt);
    }
  }

  /** Reset all highlights */
  private void resetHighlight() {
    for (LobbyObject lobby : lobbies) {
      if (lobby.getHighlighted()) {
        lobby.setHighlighted();
        lobby.setText(lobby.getText().substring(2, lobby.getText().length() - 2));
        lobby.setPositionX(Window.getHalfWidth() - lobby.getSize() / 2);
        lobby.setColour(Colour.YELLOW);
      }
    }
  }

  /**
   * Sets the highlight for a specific lobby
   *
   * @param listPos The lobby to highlight
   */
  private void setHighlight(int listPos) {
    resetHighlight();
    lobbies.get(listPos + current).setHighlighted();
    lobbies.get(listPos + current).setColour();
    lobbies.get(listPos + current).setText("- " + lobbies.get(listPos + current).getText() + " -");
    lobbies
        .get(listPos + current)
        .setPositionX(Window.getHalfWidth() - lobbies.get(listPos + current).getSize() / 2);
  }

  /**
   * Gets the highlighted lobby
   *
   * @return The highlighted lobby
   */
  public LobbyObject getHighlighted() {
    for (LobbyObject lobby : lobbies) {
      if (lobby.getHighlighted()) {
        return lobby;
      }
    }
    return null;
  }

  /**
   * Returns create
   *
   * @return Create
   */
  public TextObject getCreate() {
    return create;
  }

  /**
   * Returns separatorTop
   *
   * @return SeparatorTop
   */
  public TextObject getSeparatorTop() {
    return separatorTop;
  }

  /**
   * Returns separatorBot
   *
   * @return SeparatorBot
   */
  public TextObject getSeparatorBot() {
    return separatorBot;
  }

  /**
   * Returns exit
   *
   * @return Exit
   */
  public TextObject getExit() {
    return exit;
  }

  /**
   * Returns refresh
   *
   * @return Refresh
   */
  public TextObject getRefresh() {
    return refresh;
  }

  /**
   * Returns join
   *
   * @return Join
   */
  public TextObject getJoin() {
    return join;
  }

  /** Updates the position of the gui objects */
  public void updateSize() {
    this.bongo.setPosition(
        Window.getHalfWidth() - bongo.getSize(), Window.getHalfHeight() - TITLE_POS);
    this.congo.setPosition(Window.getHalfWidth(), Window.getHalfHeight() - TITLE_POS);
    this.multiplayer.setPosition(
        Window.getHalfWidth() - multiplayer.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS - SEPARATOR_GAP);
    this.separatorTop.setPosition(
        Window.getHalfWidth() - separatorTop.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS);
    this.separatorBot.setPosition(
        Window.getHalfWidth() - separatorTop.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS);
    this.create.setPosition(
        Window.getHalfWidth() - create.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP);
    this.join.setPosition(
        Window.getHalfWidth() - join.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * 2);
    this.refresh.setPosition(
        Window.getHalfWidth() - refresh.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * 3);
    this.exit.setPosition(
        Window.getHalfWidth() - exit.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * 4);

    int yPos = SEPARATOR_TOP_POS - GAP;
    for (LobbyObject lobby : lobbies) {
      lobby.setPosition(
          Window.getHalfWidth() - lobby.getSize() / 2,
          Window.getHalfHeight() - yPos - current * GAP);
      yPos -= GAP;
    }
  }
}
