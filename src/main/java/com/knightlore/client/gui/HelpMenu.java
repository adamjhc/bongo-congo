package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

public class HelpMenu implements IGui {
	
	/** Position of the top separator line */
	private static final int SEPARATOR_TOP_POS = 185;
	/** Position of the bottom separator line */
	private static final int SEPARATOR_BOT_POS = 200;
	/** Gap between each line of text */
	private static final int SEPARATOR_GAP = FONT_SIZE_SMALL;
	/**Bongo text */
    private final TextObject bongo;
	/** Congo text */ 
	private final TextObject congo;
	/** Top separator line text */
	private final TextObject separatorTop;
	/** Bottom separator line text */
	private final TextObject separatorBot;
	/** Help text */
	private TextObject help;
	/** Exit text */
	private TextObject exit;
	/** wasd text */
	private TextObject move;
	/** space text */
	private TextObject climb;
	/** right shift text */
	private TextObject roll;
	/** esc text */
	private TextObject escape;
	/** left shift text */
	private TextObject seeMore;
	/** left shit text pt II */
	private TextObject moreSeeMore;
	
	private TextObject[] textObjects;
	
	private GuiObject[] guiObjects;
	
	public HelpMenu() {
		
		this.help = new TextObject("Help", SMALL);
		this.help.setColour(Colour.YELLOW);
		
		this.exit = new TextObject("Exit", SMALL);
		this.exit.setColour(Colour.YELLOW);

	    this.bongo = new TextObject("Bongo", TITLE);
	    this.bongo.setColour(Colour.LIGHT_BLUE);

	    this.congo = new TextObject("Congo", TITLE);
	    this.congo.setColour(Colour.RED);
	    
	    this.separatorTop = new TextObject("------------------------------", SMALL);
	    this.separatorTop.setColour(Colour.YELLOW);

	    this.separatorBot = new TextObject("------------------------------", SMALL);
	    this.separatorBot.setColour(Colour.YELLOW);
	    
	    this.move = new TextObject("WASD : Move", SMALL);
	    this.move.setColour(Colour.YELLOW);
	    
	    this.climb = new TextObject("              SPACE : Climb up/down block", SMALL);
	    this.climb.setColour(Colour.YELLOW);
	    
	    this.roll = new TextObject(" RIGHT SHIFT : Roll forward", SMALL);
	    this.roll.setColour(Colour.YELLOW);
	    
	    this.escape = new TextObject(" ESC : Exit", SMALL);
	    this.escape.setColour(Colour.YELLOW);
	    
	    this.seeMore = new TextObject("  LEFT SHIFT : See scores /", SMALL);
	    this.seeMore.setColour(Colour.YELLOW);
	    
	    this.moreSeeMore = new TextObject("                        level editor controls", SMALL);
	    this.moreSeeMore.setColour(Colour.YELLOW);
	    
	    textObjects = new TextObject[] {exit};
	    guiObjects = new GuiObject[] {help, exit, bongo, congo, separatorTop, separatorBot, move, climb, roll, escape, seeMore, moreSeeMore};
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
	    this.help.setPosition(
	    	 Window.getHalfWidth() - help.getSize() / 2,
	    	 Window.getHalfHeight() - SEPARATOR_TOP_POS - GAP);
	    this.exit.setPosition(
	         Window.getHalfWidth() - exit.getSize() / 2,
	         Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP);
	    this.move.setPosition(
	    	 Window.getHalfWidth() - move.getSize() / 2,
	    	 Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 2);
	    this.climb.setPosition(
	    	 Window.getHalfWidth() - climb.getSize() / 2,
	    	 Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 4);
	    this.roll.setPosition(
	    	 Window.getHalfWidth() - roll.getSize() / 2,
	    	 Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 6);
	    this.escape.setPosition(
	    	 Window.getHalfWidth() - escape.getSize() / 2,
	    	 Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 8);
	    this.seeMore.setPosition(
	    	 Window.getHalfWidth() - seeMore.getSize() / 2,
	    	 Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 10);
	    this.moreSeeMore.setPosition(
	    	 Window.getHalfWidth() - moreSeeMore.getSize() / 2,
	    	 Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 11);
	}

}
