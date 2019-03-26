package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

public class NameLevel implements IGui {
	
	/**
	 * The position of the top horizontal separator
	 */
	private static final int SEPARATOR_TOP_POS = 185;
	
	/**
	 * The position of the bottom horizontal separator
	 */
	private static final int SEPARATOR_BOT_POS = 200;
	
	/**
	 * The gap between a separator and the title above it
	 */
	private static final int SEPARATOR_GAP = FONT_SIZE_SMALL;
	
	/**
	 * The beginning of the filepath to the directory to save the level
	 */
	private static final String filePath = "customMaps/unplayable/";
	
	/**
	 * The TextObject for the word "Bongo"
	 */
	private TextObject bongo;
	
	/**
	 * The TextObject for the word "Congo"
	 */
	private TextObject congo;
	
	/**
	 * The TextObject for the top "-------------------------"
	 */
	private TextObject separatorTop;
	
	/**
	 * The TextObject for the bottom "------------------------"
	 */
	private TextObject separatorBottom;
	
	/**
	 * The TextObject for the words "Name Your Level"
	 */
	private TextObject nameYourLevel;
	
	/**
	 * The TextObject for the word "cancel"
	 */
	private TextObject cancel;
	
	/**
	 * The TextObject for the words "Save and Quit"
	 */
	private TextObject saveAndQuit;
	
	/**
	 * The TextObject for the words "Save and Continue"
	 */
	private TextObject saveAndContinue;
	
	/**
	 * The TextObject for the displayed level name
	 */
	private TextObject levelName;
	
	/**
	 * The TextObjects you can click on
	 */
	private TextObject[] textObjects;
	
	/**
	 * The GuiObjects being displayed on the screen
	 */
	private GuiObject[] guiObjects;
	
	public NameLevel() {
		this.bongo = new TextObject("Bongo", TITLE);
	    this.bongo.setColour(Colour.LIGHT_BLUE);

	    this.congo = new TextObject("Congo", TITLE);
	    this.congo.setColour(Colour.RED);
		
		this.nameYourLevel = new TextObject("Name Your Level", SMALL);
		this.nameYourLevel.setColour(Colour.YELLOW);
		
		this.cancel = new TextObject("Cancel", SMALL);
		this.cancel.setColour(Colour.YELLOW);
		
		this.saveAndQuit = new TextObject("Save and Quit", SMALL);
		this.saveAndQuit.setColour(Colour.YELLOW);
		
		this.saveAndContinue = new TextObject("Save and Continue", SMALL);
		this.saveAndContinue.setColour(Colour.YELLOW);
		
		this.levelName = new TextObject("UNTITLED", LARGE);
		this.levelName.setColour(Colour.GREEN);
		
		this.separatorTop = new TextObject("------------------------------", SMALL);
	    this.separatorTop.setColour(Colour.YELLOW);

	    this.separatorBottom = new TextObject("------------------------------", SMALL);
	    this.separatorBottom.setColour(Colour.YELLOW);
	    
	    textObjects = new TextObject[] {cancel, saveAndQuit, saveAndContinue};
	    guiObjects = new GuiObject[] {bongo, congo, nameYourLevel, cancel, saveAndQuit, saveAndContinue, levelName, separatorTop, separatorBottom};
	}
	
	/**
	 * Method to update the position of the GuiObjects on the screen
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
	    this.nameYourLevel.setPosition(
	    		Window.getHalfWidth() - nameYourLevel.getSize()/2, 
	    		Window.getHalfHeight() - SEPARATOR_TOP_POS - SEPARATOR_GAP);
	    this.levelName.setPosition(
	    		Window.getHalfWidth() - levelName.getSize() / 2,
	    		Window.getHalfHeight() - levelName.getHeight() / 2);
	    this.saveAndContinue.setPosition(
	            Window.getHalfWidth() - saveAndContinue.getSize() / 2,
	            Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP);

	    this.saveAndQuit.setPosition(
	            Window.getHalfWidth() - saveAndQuit.getSize() / 2,
	            Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * 2);
	        
	    this.cancel.setPosition(
	    		Window.getHalfWidth() - cancel.getSize() / 2,
	        	Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * 3);
	        
	}
	
	/**
	 * Method to get the word "cancel"
	 * @return The TextObject for the word "cancel"
	 */
	public TextObject getCancel() {
		return cancel;
	}
	
	/**
	 * Method to get the words "Save and Quit"
	 * @return The TextObject for the words "Save and Quit"
	 */
	public TextObject getSaveAndQuit() {
		return saveAndQuit;
	}
	
	/**
	 * Method to get the words "Save and Continue"
	 * @return The TextObject for the words "Save and Continue"
	 */
	public TextObject getSaveAndContinue() {
		return saveAndContinue;
	}
	
	/**
	 * Method to get the current level name
	 * @return The name of the level
	 */
	public TextObject getLevelName() {
		return levelName;
	}

	/**
	 * Method to get all clickable TextObjects
	 * @return All TextObjects you can click on
	 */
	@Override
	public TextObject[] getTextObjects() {
		// TODO Auto-generated method stub
		return textObjects;
	}

	/**
	 * Method to get all displayed GuiObjects on screen
	 * @return All displayed objects on screen
	 */
	@Override
	public GuiObject[] getGuiObjects() {
		// TODO Auto-generated method stub
		return guiObjects; 
	}

}
