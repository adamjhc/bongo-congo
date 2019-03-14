package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.LobbyObject;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.io.Window;
import com.knightlore.client.networking.LobbyCache;
import com.knightlore.networking.ListGameObject;

import java.util.ArrayList;
import java.util.Collection;

public class LobbyMenu implements IGui {

  private static final int SEPARATOR_TOP_POS = 185;
  private static final int SEPARATOR_BOT_POS = 200;
  private static final int SEPARATOR_GAP = FONT_SIZE_SMALL;

  private static final int MAX_SERVERS = (SEPARATOR_TOP_POS + SEPARATOR_BOT_POS - GAP) / GAP;

  private final TextObject separatorTop;
  private final TextObject separatorBot;
  private final TextObject create;
  private final TextObject exit;
  private final TextObject bongo;
  private final TextObject congo;
  private final TextObject multiplayer;
  private final TextObject join;
  private final TextObject refresh;
  private ArrayList<LobbyObject> lobbies;
  private GuiObject[] guiObjects;
  private TextObject[] textObjects;
  private int length;

  private int current;

  private int yPos = SEPARATOR_TOP_POS - GAP;

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
        new GuiObject[] {bongo, congo, multiplayer, separatorTop, separatorBot, join, create, exit, refresh};
    length = guiObjects.length;

    textObjects = new TextObject[] {join, create, exit, separatorTop, separatorBot, refresh};
    
    refreshLobbies();
    addLobbies();
  }

  public void createLobby() {
    LobbyObject newServer = new LobbyObject("New Server " + lobbies.size(), SMALL);
    newServer.setColour(Colour.YELLOW);
    newServer.setPosition(
        Window.getHalfWidth() - newServer.getSize() / 2,
        Window.getHalfHeight() - yPos - (current * GAP));
    yPos -= GAP;

    lobbies.add(newServer);
    addLobby();
    for (int i = 0; i < lobbies.size(); i++) moveDown();
  }
  
  public void refreshLobbies() {
  	current = 0;
  	
  	if (lobbies != null) {
    	for (LobbyObject lobby : lobbies) {
    		lobby.setRender(false);
    	}
  	}

    this.lobbies = new ArrayList<>();
    
    Collection<ListGameObject> games = LobbyCache.instance.getGames();
    for (ListGameObject game : games) {
    	lobbies.add(new LobbyObject(game.getName() + "'s " + "Server", SMALL, game));
    }
    
    for (LobbyObject lobby : lobbies) {
      lobby.setColour(Colour.YELLOW);
      lobby.setPosition(
          Window.getHalfWidth() - lobby.getSize() / 2, Window.getHalfHeight() - yPos);
      yPos -= GAP;
    }
  }
  
  public void deleteLobby() {
  	//TODO add delete method
  }

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

  private void addLobbies() {
    if (lobbies.size() <= MAX_SERVERS) {
      GuiObject[] guiObjectsNew = new GuiObject[guiObjects.length + lobbies.size()];
      System.arraycopy(guiObjects, 0, guiObjectsNew, 0, guiObjects.length);
      for (int i = guiObjects.length; i < guiObjects.length + lobbies.size(); i++) {
        guiObjectsNew[i] = lobbies.get(i - guiObjects.length);
      }
      guiObjects = guiObjectsNew.clone();
    } else {
      GuiObject[] guiObjectsNew = new GuiObject[guiObjects.length + MAX_SERVERS];
      System.arraycopy(guiObjects, 0, guiObjectsNew, 0, guiObjects.length);
      for (int i = guiObjects.length; i < guiObjects.length + MAX_SERVERS; i++) {
        guiObjectsNew[i] = lobbies.get(i - guiObjects.length);
      }
      guiObjects = guiObjectsNew.clone();
    }
  }

  public void moveDown() {
    if (lobbies.size() > MAX_SERVERS && current < lobbies.size() - MAX_SERVERS) {
      current++;
      GuiObject[] guiObjectsNew = new GuiObject[length + MAX_SERVERS];
      if (length >= 0) System.arraycopy(guiObjects, 0, guiObjectsNew, 0, length);
      for (int i = length; i < length + MAX_SERVERS; i++) {
        guiObjectsNew[i] = lobbies.get((i - length) + current);
      }
      guiObjects = guiObjectsNew.clone();

      for (LobbyObject server : lobbies) {
        server.setPositionY(server.getPositionY() - GAP);
      }
    }
  }

  public void moveUp() {
    if (current > 0) {
      current--;

      GuiObject[] guiObjectsNew = new GuiObject[length + MAX_SERVERS];
      if (length >= 0) System.arraycopy(guiObjects, 0, guiObjectsNew, 0, length);
      for (int i = length; i < length + MAX_SERVERS; i++) {
        guiObjectsNew[i] = lobbies.get((i - length) + current);
      }
      guiObjects = guiObjectsNew.clone();

      for (LobbyObject server : lobbies) {
        server.setPositionY(server.getPositionY() + GAP);
      }
    }
  }

  public void highlight() {
    double pos = (Mouse.getYPos() - (Window.getHalfHeight() - (SEPARATOR_TOP_POS - GAP * 2))) / GAP;
    int posInt = (int) Math.ceil(pos);
    if (posInt >= 0 && posInt < Math.min(MAX_SERVERS, lobbies.size())) {
      setHighlight(posInt);
    }
  }

  private void resetHighlight() {
    for (LobbyObject server : lobbies) {
      if (server.getHighlighted()) {
        server.setHighlighted();
        server.setText(server.getText().substring(4, server.getText().length() - 4));
        server.setPositionX(Window.getHalfWidth() - server.getSize() / 2);
        server.setColour(Colour.YELLOW);
      }
    }
  }

  private void setHighlight(int listPos) {
    resetHighlight();
    lobbies.get(listPos + current).setHighlighted();
    lobbies.get(listPos + current).setColour();
    lobbies
        .get(listPos + current)
        .setText("=== " + lobbies.get(listPos + current).getText() + " ===");
    lobbies
        .get(listPos + current)
        .setPositionX(Window.getHalfWidth() - lobbies.get(listPos + current).getSize() / 2);
  }

  public TextObject getCreate() {
    return create;
  }

  public TextObject getSeparatorTop() {
    return separatorTop;
  }

  public TextObject getSeparatorBot() {
    return separatorBot;
  }

  public TextObject getExit() {
    return exit;
  }
  
  public TextObject getRefresh() {
  	return refresh;
  }
  
  public void updateSize() {
    this.bongo.setPosition(Window.getHalfWidth() - bongo.getSize(), Window.getHalfHeight() - TITLE_POS);
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
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * 1);
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
    	lobby.setPosition(Window.getHalfWidth() - lobby.getSize() / 2,  Window.getHalfHeight() - yPos - current*GAP);
    	yPos -= GAP;
    }
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
