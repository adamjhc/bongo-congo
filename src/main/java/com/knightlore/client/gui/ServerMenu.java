package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.LobbyObject;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.gui.engine.graphics.FontTexture;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.io.Window;

import java.util.ArrayList;

public class ServerMenu implements IGui {

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
  private final ArrayList<LobbyObject> servers;
  private GuiObject[] guiObjects;
  private TextObject[] textObjects;
  private int length;

  private int current = 0;

  private int yPos = SEPARATOR_TOP_POS - GAP;

  public ServerMenu() {
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

    servers = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      servers.add(new LobbyObject(i + "'s " + "Server", SMALL));
    }

    for (LobbyObject server : servers) {
      server.setColour(Colour.YELLOW);
      server.setPosition(
          Window.getHalfWidth() - server.getSize() / 2, Window.getHalfHeight() - yPos);
      yPos -= GAP;
    }

    guiObjects =
        new GuiObject[] {bongo, congo, multiplayer, separatorTop, separatorBot, join, create, exit, refresh};
    length = guiObjects.length;

    textObjects = new TextObject[] {join, create, exit, separatorTop, separatorBot, refresh};

    addServers();
  }

  public void createServer() {
    FontTexture fontTexture = new FontTexture(FONT_SMALL, CHARSET);

    LobbyObject newServer = new LobbyObject("New Server " + servers.size(), fontTexture);
    newServer.setColour(Colour.YELLOW);
    newServer.setPosition(
        Window.getHalfWidth() - newServer.getSize() / 2,
        Window.getHalfHeight() - yPos - (current * GAP));
    yPos -= GAP;

    servers.add(newServer);
    reAddServers();
    for (int i = 0; i < servers.size(); i++) moveDown();
  }

  private void reAddServers() {
    if (servers.size() <= MAX_SERVERS) {
      GuiObject[] guiObjectsNew = new GuiObject[length + servers.size()];
      if (length >= 0) System.arraycopy(guiObjects, 0, guiObjectsNew, 0, length);
      for (int i = length; i < length + servers.size(); i++) {
        guiObjectsNew[i] = servers.get(i - length);
      }
      guiObjects = guiObjectsNew.clone();
    }
  }

  private void addServers() {
    if (servers.size() <= MAX_SERVERS) {
      GuiObject[] guiObjectsNew = new GuiObject[guiObjects.length + servers.size()];
      System.arraycopy(guiObjects, 0, guiObjectsNew, 0, guiObjects.length);
      for (int i = guiObjects.length; i < guiObjects.length + servers.size(); i++) {
        guiObjectsNew[i] = servers.get(i - guiObjects.length);
      }
      guiObjects = guiObjectsNew.clone();
    } else {
      GuiObject[] guiObjectsNew = new GuiObject[guiObjects.length + MAX_SERVERS];
      System.arraycopy(guiObjects, 0, guiObjectsNew, 0, guiObjects.length);
      for (int i = guiObjects.length; i < guiObjects.length + MAX_SERVERS; i++) {
        guiObjectsNew[i] = servers.get(i - guiObjects.length);
      }
      guiObjects = guiObjectsNew.clone();
    }
  }

  public void moveDown() {
    if (servers.size() > MAX_SERVERS && current < servers.size() - MAX_SERVERS) {
      current++;
      GuiObject[] guiObjectsNew = new GuiObject[length + MAX_SERVERS];
      if (length >= 0) System.arraycopy(guiObjects, 0, guiObjectsNew, 0, length);
      for (int i = length; i < length + MAX_SERVERS; i++) {
        guiObjectsNew[i] = servers.get((i - length) + current);
      }
      guiObjects = guiObjectsNew.clone();

      for (LobbyObject server : servers) {
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
        guiObjectsNew[i] = servers.get((i - length) + current);
      }
      guiObjects = guiObjectsNew.clone();

      for (LobbyObject server : servers) {
        server.setPositionY(server.getPositionY() + GAP);
      }
    }
  }

  public void highlight() {
    double pos = (Mouse.getYPos() - (Window.getHalfHeight() - (SEPARATOR_TOP_POS - GAP * 2))) / GAP;
    int posInt = (int) Math.ceil(pos);
    if (posInt >= 0 && posInt < Math.min(MAX_SERVERS, servers.size())) {
      setHighlight(posInt);
    }
  }

  private void resetHighlight() {
    for (LobbyObject server : servers) {
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
    servers.get(listPos + current).setHighlighted();
    servers
        .get(listPos + current)
        .setText("=== " + servers.get(listPos + current).getText() + " ===");
    servers
        .get(listPos + current)
        .setPositionX(Window.getHalfWidth() - servers.get(listPos + current).getSize() / 2);
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
    for (LobbyObject server : servers) {	
    	server.setPosition(Window.getHalfWidth() - server.getSize() / 2,  Window.getHalfHeight() - yPos - current*GAP);
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
