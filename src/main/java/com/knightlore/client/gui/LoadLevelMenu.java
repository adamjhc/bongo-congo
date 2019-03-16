package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

public class LoadLevelMenu implements IGui {
	
	private static final int SEPARATOR_TOP_POS = 185;
	private static final int SEPARATOR_BOT_POS = 200;
	private static final int SEPARATOR_GAP = FONT_SIZE_SMALL;
	private static final int MAX_LEVEL_COUNT = 12;
	
	private TextObject bongo;
	
	private TextObject congo;
	
	private TextObject separatorTop;
	
	private TextObject separatorBottom;
	
	private TextObject[] levels;
	
	private TextObject loadLevel;
	
	private TextObject load;
	
	private TextObject back;
	
	private TextObject nextPage;
	
	private TextObject lastPage;
	
	private TextObject[] textObjects;
	
	private GuiObject[] guiObjects;
	
	public LoadLevelMenu() {
			this.bongo = new TextObject("Bongo", TITLE);
		    this.bongo.setColour(Colour.LIGHT_BLUE);

		    this.congo = new TextObject("Congo", TITLE);
		    this.congo.setColour(Colour.RED);
		    
		    this.loadLevel = new TextObject("Load Level", SMALL);
		    this.loadLevel.setColour(Colour.YELLOW);
		    
			this.separatorTop = new TextObject("------------------------------", SMALL);
		    this.separatorTop.setColour(Colour.YELLOW);

		    this.separatorBottom = new TextObject("------------------------------", SMALL);
		    this.separatorBottom.setColour(Colour.YELLOW);
		    
		    this.load = new TextObject("Load", SMALL);
		    this.load.setColour(Colour.YELLOW);
		    
		    this.back = new TextObject("Back", SMALL);
		    this.back.setColour(Colour.YELLOW);
		    
		    this.nextPage = new TextObject(">", LARGE);
		    this.nextPage.setColour(Colour.YELLOW);
		    
		    this.lastPage = new TextObject("<", LARGE);
		    this.lastPage.setColour(Colour.YELLOW);
	}
	
	public TextObject getLoad() {
		return load;
	}
	
	public TextObject getBack() {
		return back;
	}
	
	public void setLevels(TextObject[] l) {
		levels = l;
	}
	
	public TextObject getLevel(int i) {
		return levels[i];
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
		this.bongo.setPosition(Window.getHalfWidth() - bongo.getSize(), Window.getHalfHeight() - TITLE_POS);
	    this.congo.setPosition(Window.getHalfWidth(), Window.getHalfHeight() - TITLE_POS);
	    this.separatorTop.setPosition(
	            Window.getHalfWidth() - separatorTop.getSize() / 2,
	            Window.getHalfHeight() - SEPARATOR_TOP_POS);
	    this.separatorBottom.setPosition(
	            Window.getHalfWidth() - separatorBottom.getSize() / 2,
	            Window.getHalfHeight() + SEPARATOR_BOT_POS);
	    this.loadLevel.setPosition(
	    		Window.getHalfWidth() - loadLevel.getSize()/2, 
	    		Window.getHalfHeight() - SEPARATOR_TOP_POS - SEPARATOR_GAP);
	    this.load.setPosition(
	            Window.getHalfWidth() - load.getSize() / 2,
	            Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP);
	    this.back.setPosition(
	            Window.getHalfWidth() - back.getSize() / 2,
	            Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * 2);
	    this.nextPage.setPosition(
	    		(Window.getHalfWidth() - nextPage.getSize() / 2) / 2,
	    		Window.getHalfHeight() + SEPARATOR_BOT_POS - GAP - nextPage.getSize());
	    this.lastPage.setPosition(
	    		(Window.getHalfWidth() - nextPage.getSize() / 2) * 1.25f,
	    		Window.getHalfHeight() + SEPARATOR_BOT_POS - GAP - lastPage.getSize());
	    
	    for (int i = 0; i < Math.min(MAX_LEVEL_COUNT, levels.length); i++) {
	    	levels[i].setPosition(
	    			Window.getHalfWidth() - levels[i].getSize() / 2,
	    			Window.getHalfHeight() + SEPARATOR_TOP_POS + GAP + (levels[i].getHeight()*i));
	    }
	}
	
	

}
