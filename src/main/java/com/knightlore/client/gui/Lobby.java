package com.knightlore.client.gui;

import java.util.ArrayList;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

public class Lobby implements IGui {

  private static final int SEPARATOR_TOP_POS = 185;
  private static final int SEPARATOR_BOT_POS = 200;
  private static final int SEPARATOR_GAP = FONT_SIZE_SMALL;
	
  private final TextObject bongo;
  private final TextObject congo;
  private final TextObject lobby;
  private final TextObject separatorTop;
  private final TextObject separatorBot;
  private final TextObject exit;
  private final TextObject start;
  private int length;
  private ArrayList<TextObject> players;
  private GuiObject[] guiObjects;
  private TextObject[] textObjects;
  
  private int yPos = SEPARATOR_TOP_POS - GAP;

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
  
  public void setLobbyName(String name) {
  	this.lobby.setText(name);
  }
  
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
  
  public void addPlayers() {
  	GuiObject[] guiObjectsNew = new GuiObject[guiObjects.length + players.size()];
  	System.arraycopy(guiObjects, 0, guiObjectsNew, 0, guiObjects.length);
    for (int i = guiObjects.length; i < guiObjects.length + players.size(); i++) {
      guiObjectsNew[i] = players.get(i - guiObjects.length);
    }
    guiObjects = guiObjectsNew.clone();
  }
  
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

  public TextObject getStart() {
  	return start;
  }
  
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
