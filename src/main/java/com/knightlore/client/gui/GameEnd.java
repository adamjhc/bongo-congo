package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

public class GameEnd implements IGui {
	
  private static final int SEPARATOR_TOP_POS = 185;
  private static final int SEPARATOR_BOT_POS = 200;
  private static final int SEPARATOR_GAP = FONT_SIZE_SMALL;

  private final TextObject bongo;
  private final TextObject congo;
  private final TextObject exit;
  private final TextObject separatorTop;
  private final TextObject separatorBot;
  private final TextObject winner;
  
  private GuiObject[] guiObjects;
  private TextObject[] textObjects;
  
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
    
    guiObjects =
        new GuiObject[] {
          bongo,
          congo,
          exit,
          separatorTop,
          separatorBot,
          winner
    		};
    textObjects = new TextObject[] {exit};
  }
  
  public void updateSize() {
    this.bongo.setPosition(Window.getHalfWidth() - bongo.getSize(), Window.getHalfHeight() - TITLE_POS);
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
  
  public TextObject getWinner() {
  	return winner;
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
