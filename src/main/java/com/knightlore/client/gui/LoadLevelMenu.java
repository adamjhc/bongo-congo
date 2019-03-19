package com.knightlore.client.gui;

import java.util.ArrayList;
import java.util.Arrays;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

public class LoadLevelMenu implements IGui {
	
	/**
	 * The position in the window of the top ---------------
	 */
	private static final int SEPARATOR_TOP_POS = 185;
	
	/**
	 * The position in the window of the bottom ----------------
	 */
	private static final int SEPARATOR_BOT_POS = 200;
	
	/**
	 * The size of of the gap between a separator and a word above or below it
	 */
	private static final int SEPARATOR_GAP = FONT_SIZE_SMALL;
	
	/**
	 * The maximum number of level names that can be displayed on screen at a given time
	 */
	private static final int MAX_LEVEL_COUNT = 12;
	
	/**
	 * The GuiObjects that will always be present on screen, regardless of the page number
	 */
	private final GuiObject[] setGuiObjects;
	
	/**
	 * The TextObjects that will always be present on screen, regardless of the page number
	 */
	private final TextObject[] setTextObjects;
	
	/**
	 * The number of pages necessary to display all level names
	 */
	private int pageCount;
	
	/**
	 * The index of the list of levels that we start displaying levels from
	 */
	private int levelIndex;
	
	/**
	 * The page number we are currently on
	 */
	private int currentPageNum;
	
	/**
	 * The TextObject used to display the page numbers
	 */
	private TextObject pageCounter;
	
	/**
	 * The TextObject for the word "Bongo"
	 */
	private TextObject bongo;
	
	/**
	 * The TextObject for the word "Congo"
	 */
	private TextObject congo;
	
	/**
	 * The TextObject for the first "----------------"
	 */
	private TextObject separatorTop;
	
	/**
	 * The TextObject for the second "----------------"
	 */
	private TextObject separatorBottom;
	
	/**
	 * The list of level names to be displayed on screen
	 */
	private TextObject[] levels;
	
	/**
	 * The TextObject for the words "Load Level"
	 */
	private TextObject loadLevel;
	
	/**
	 * The TextObject for the word "load"
	 */
	private TextObject load;
	
	/**
	 * The TextObject for the word "back"
	 */
	private TextObject back;
	
	/**
	 * The TextObject for the ">" icon
	 */
	private TextObject nextPage;
	
	/**
	 * The TextObject for the "<" icon
	 */
	private TextObject lastPage;
	
	/**
	 * The list of all the TextObjects that a user can click on
	 */
	private TextObject[] textObjects;
	
	/**
	 * The list of all the GuiObjects in this UI
	 */
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
		    
		    this.pageCounter = new TextObject("1/" + pageCount, SMALL);
		    this.pageCounter.setColour(Colour.YELLOW);
		    
		    levelIndex = 0;
		    currentPageNum = 1;
		    
		    setGuiObjects = new GuiObject[] {bongo, congo, loadLevel, separatorTop, separatorBottom, load, back, nextPage, lastPage, pageCounter};
		    setTextObjects = new TextObject[] {load, back, nextPage, lastPage};
		    
		    guiObjects = setGuiObjects;
		    textObjects = setTextObjects;
		    
		    
	}
	
	/**
	 * Method to get the word "load" from the GUI
	 * @return The TextObject for the word "load"
	 */
	public TextObject getLoad() {
		return load;
	}
	
	/**
	 * Method to get the word "back" from the GUI
	 * @return The TextObject for the word "back"
	 */
	public TextObject getBack() {
		return back;
	}
	
	/**
	 * Method to define the list of names of currently available levels
	 * @param l The names of all the map files retrieved
	 */
	public void setLevels(TextObject[] l) {
		levels = l;
		if (levels.length % 12 == 0) {
			pageCount = levels.length / 12;
		} else {
			pageCount = levels.length / 12 + 1;
		}
		System.out.println(levels.length);
	}
	
	/**
	 * Method to get a level name from the list of level names
	 * @param i The index of the TextObject in the list of level names
	 * @return The name of the level stored at the index i
	 */
	public TextObject getLevel(int i) {
		return levels[levelIndex + i];
	}
	
	@Override
	/**
	 * Method to get the list of TextObjects
	 * @return The list of TextObjects
	 */
	public TextObject[] getTextObjects() {
		return textObjects;
	}

	@Override
	/**
	 * Method to get the list of GuiObjects
	 * @return the list of GuiObjects
	 */
	public GuiObject[] getGuiObjects() {
		return guiObjects;
	}
	
	/**
	 * Method to increment the start pointer of the list of level names
	 */
	public void incPage() {
		if (levelIndex + 12 < levels.length) {
			levelIndex += 12;
			currentPageNum += 1;
		}
	}
	
	/**
	 * Method to decrement the start pointer of the list of level names
	 */
	public void decPage() {
		if (levelIndex >= 12) {
			levelIndex -= 12;
			currentPageNum -= 1;
		}
	}
	
	/**
	 * Method to get the ">" icon from the GUI
	 * @return The TextObject for ">" from the GUI
	 */
	public TextObject getNextPage() {
		return nextPage;
	}
	
	/**
	 * Method to get the "<" icon from the GUI
	 * @return The TextObject for "<" from the GUI
	 */
	public TextObject getLastPage() {
		return lastPage;
	}
	
	/**
	 * Method to determine how many level names are currently appearing on the screen
	 * @return The number of level names being displayed
	 */
	public int numOnScreenLevels() {
		return Math.min(MAX_LEVEL_COUNT, levels.length-levelIndex);
	}
	
	/**
	 * Method to update the position of all objects in the GUI so that the size of the window does not affect it
	 */
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
	    		(Window.getHalfWidth() + nextPage.getSize()*2) / 2,
	    		Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP + nextPage.getHeight()/2);
	    this.lastPage.setPosition(
	    		(Window.getHalfWidth() - lastPage.getSize() / 2) / 2,
	    		Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP + lastPage.getHeight()/2);
	    this.pageCounter.setText(currentPageNum + "/" + pageCount);
	    this.pageCounter.setPosition(
	    		(Window.getHalfWidth() - pageCounter.getSize() / 2) * 1.5f,
	    		Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP + pageCounter.getHeight());
	    
	    ArrayList<GuiObject> tempG = new ArrayList<GuiObject>(Arrays.asList(setGuiObjects));
	    ArrayList<TextObject> tempT = new ArrayList<TextObject>(Arrays.asList(setTextObjects));
	    
	    for (int i = 0; i < Math.min(MAX_LEVEL_COUNT, levels.length-levelIndex); i++) {
	    	tempG.add(levels[levelIndex + i]);
	    	tempT.add(levels[levelIndex + i]);
	    	levels[levelIndex + i].setPosition(
	    			Window.getHalfWidth() - levels[levelIndex + i].getSize() / 2,
	    			Window.getHalfHeight() - SEPARATOR_TOP_POS + SEPARATOR_GAP + (levels[levelIndex + i].getHeight()*i));
	    }
	    
	    guiObjects = tempG.toArray(setGuiObjects);
	    textObjects = tempT.toArray(setTextObjects);
	}
	
	

}
