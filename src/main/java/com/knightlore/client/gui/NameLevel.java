package com.knightlore.client.gui;

import java.util.ArrayList;
import java.util.Arrays;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

/**
 * Screen to name and save a custom level
 * 
 * @author Adam W, Joseph
 *
 */
public class NameLevel implements IGui {

	/** Position of the top separator line */
  private static final int SEPARATOR_TOP_POS = 185;
  /** Position of the bottom separator line */
  private static final int SEPARATOR_BOT_POS = 200;
  /** Gap between each line of text */
  private static final int SEPARATOR_GAP = FONT_SIZE_SMALL;

  /** File path to custom maps */
  private static final String filePath = "customMaps/unplayable/";

  /** Bongo text object */
  private TextObject bongo;
  /** Congo text object */
  private TextObject congo;
  /** Top separator line text object */
  private TextObject separatorTop;
  /** Bottom separator line text object */
  private TextObject separatorBottom;
  /** Name level text object */
  private TextObject nameYourLevel;
  /** Cancel changes text object*/
  private TextObject cancel;
  /** Save changes and quit text object*/
  private TextObject saveAndQuit;
  /** Save changes and continue text object*/
  private TextObject saveAndContinue;
  /** Level name text object*/
  private TextObject levelName;
  /** Publish text object */
  private TextObject publish;
  /** Whether the level has been published */
  private boolean published;

  /** List of text objects */
  private TextObject[] textObjects;
  /** List of gui objects */
  private GuiObject[] guiObjects;

  /**
   * Create gui objects
   * 
   * @author Adam W
   * 
   */
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

    this.levelName = new TextObject("Untitled", LARGE);
    this.levelName.setColour(Colour.GREEN);

    this.separatorTop = new TextObject("------------------------------", SMALL);
    this.separatorTop.setColour(Colour.YELLOW);

    this.separatorBottom = new TextObject("------------------------------", SMALL);
    this.separatorBottom.setColour(Colour.YELLOW);
    
    this.publish = new TextObject ("Publish", SMALL);
    this.publish.setColour(Colour.YELLOW);

    this.published = false;
    
    textObjects = new TextObject[] {cancel, saveAndQuit, saveAndContinue, publish};
    guiObjects =
        new GuiObject[] {
          bongo,
          congo,
          nameYourLevel,
          cancel,
          saveAndQuit,
          saveAndContinue,
          levelName,
          separatorTop,
          separatorBottom,
          publish
        };
  }

  /**
   * Updates the position of the gui objects
   * 
   * @author Adam W
   * 
   */
  public void updateSize() {
	int publishGap = published ? 0 : 1;
    this.bongo.setPosition(
        Window.getHalfWidth() - bongo.getSize(), Window.getHalfHeight() - TITLE_POS);
    this.congo.setPosition(Window.getHalfWidth(), Window.getHalfHeight() - TITLE_POS);
    this.separatorTop.setPosition(
        Window.getHalfWidth() - separatorTop.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS);
    this.separatorBottom.setPosition(
        Window.getHalfWidth() - separatorBottom.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS);
    this.nameYourLevel.setPosition(
        Window.getHalfWidth() - nameYourLevel.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS - SEPARATOR_GAP);
    this.levelName.setPosition(
        Window.getHalfWidth() - levelName.getSize() / 2,
        Window.getHalfHeight() - levelName.getHeight() / 2);
    this.publish.setPosition(
    	Window.getHalfWidth() - publish.getSize() / 2,
    	Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP);
    this.saveAndContinue.setPosition(
        Window.getHalfWidth() - saveAndContinue.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * (1 + publishGap));

    this.saveAndQuit.setPosition(
        Window.getHalfWidth() - saveAndQuit.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * (2 + publishGap));

    this.cancel.setPosition(
        Window.getHalfWidth() - cancel.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * (3 + publishGap));
  }

  /**
   * Returns cancel
   * 
   * @return Cancel
   * @author Adam W
   * 
   */
  public TextObject getCancel() {
    return cancel;
  }
  
  /**
   * Returns pubish
   * 
   * @return Publish
   * @author Adam W
   */
  public TextObject getPublish() {
	return publish;
  }

  /**
   * Returns save and quit
   * 
   * @return SaveAndQuit
   * @author Adam W
   * 
   */
  public TextObject getSaveAndQuit() {
    return saveAndQuit;
  }

  /**
   * Returns save and continue
   * 
   * @return SaveAndContinue
   * @author Adam W
   * 
   */
  public TextObject getSaveAndContinue() {
    return saveAndContinue;
  }

  /**
   * Returns level name
   * 
   * @return LevelName
   * @author Adam W
   * 
   */
  public TextObject getLevelName() {
    return levelName;
  }
  
  public void removePublish() {
	  textObjects = Arrays.copyOfRange(textObjects, 0, 3);
	  guiObjects = Arrays.copyOfRange(guiObjects, 0, 9);
	  published = true;
  }
  
  public void showPublish() {
	  ArrayList<TextObject> tempT = new ArrayList<TextObject>(Arrays.asList(textObjects));
	  ArrayList<GuiObject> tempG = new ArrayList<GuiObject>(Arrays.asList(guiObjects));
	  tempT.add(publish);
	  tempG.add(publish);
	  textObjects = tempT.toArray(textObjects);
	  guiObjects = tempG.toArray(guiObjects);
	  published = false;
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
