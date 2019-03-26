package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.gui.engine.graphics.FontTexture;
import com.knightlore.client.io.Window;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.joml.Vector4f;

public class LevelEditorHud implements IGui {

  /**
   * The Large font
   */
  private static final Font FONT_LARGE = new Font("Press Start 2P", Font.PLAIN, 40);

  /**
   * The character set used for the text
   */
  private static final String CHARSET = "ISO-8859-1";
  
  /**
   * The TextObject for the word "save"
   */
  private final TextObject save;
  
  /**
   * The TextObject for the word "tiles"
   */
  private final TextObject tiles;
  
  /**
   * The TextObject for the word "empty"
   */
  private final TextObject empty;
  
  /**
   * The TextObject for the word "floor"
   */
  private final TextObject floor;
  
  /**
   * The TextObject for the word "slab"
   */
  private final TextObject slab;
  
  /**
   * The TextObject for the word "block"
   */
  private final TextObject block;
  
  /**
   * The TextObject for the word "hazard"
   */
  private final TextObject hazard;
  
  /**
   * The TextObject for the word "finish"
   */
  private final TextObject finish;
  
  /** 
   * The TextObject for the word "walker"
   */
  private final TextObject walker;
  
  /**
   * The TextObject for the word "randomer"
   */
  private final TextObject randomer;
  
  /**
   * The TextObject for the word "circler"
   */
  private final TextObject circler;
  
  /** 
   * The TextObject for the word "charger"
   */
  private final TextObject charger;
  
  /**
   * The TextObject for the word "spawners"
   */
  private final TextObject spawners;
  
  /**
   * The TextObject for the vertical divider between GUI buttons
   */
  private final TextObject[] vDivider;
  
  /**
   * The TextObject for the horizontal divider between the section names of the GUI
   */
  private final TextObject hDivider;

  /**
   * The list of all TextObjects you can click on
   */
  private TextObject[] textObjects;
  
  /**
   * The list of all GuiObjects that are displayed on the screen
   */
  private GuiObject[] guiObjects;

  public LevelEditorHud() {
    try (InputStream myStream =
        new BufferedInputStream(
            new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"))) {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    } catch (FontFormatException | IOException e) {
      e.printStackTrace();
    }

    FontTexture fontTextureLarge = new FontTexture(FONT_LARGE, CHARSET);
    FontTexture fontTextureMedium = new FontTexture(FONT_MEDIUM, CHARSET);
    FontTexture fontTextureSmall = new FontTexture(FONT_SMALL, CHARSET);

    this.save = new TextObject("Save", fontTextureLarge);
    this.save.setColour(Colour.YELLOW);

    this.tiles = new TextObject("Tiles", fontTextureMedium);
    this.tiles.setColour(Colour.YELLOW);
    
    this.empty = new TextObject("Empty", fontTextureSmall);
    this.empty.setColour(Colour.YELLOW);
    
    this.floor = new TextObject("Floor", fontTextureSmall);
    this.floor.setColour(Colour.YELLOW);
    
    this.slab = new TextObject("Slab", fontTextureSmall);
    this.slab.setColour(Colour.YELLOW);
    
    this.block = new TextObject("Block", fontTextureSmall);
    this.block.setColour(Colour.YELLOW);
    
    this.hazard = new TextObject("Hazard", fontTextureSmall);
    this.hazard.setColour(Colour.YELLOW);
    
    this.finish = new TextObject("Finish", fontTextureSmall);
    this.finish.setColour(Colour.YELLOW);
    
    this.walker = new TextObject("Walker", fontTextureSmall);
    this.walker.setColour(Colour.YELLOW);
    
    this.randomer = new TextObject("Randomer", fontTextureSmall);
    this.randomer.setColour(Colour.YELLOW);
    
    this.circler = new TextObject("Circler", fontTextureSmall);
    this.circler.setColour(Colour.YELLOW);
    
    this.charger = new TextObject("Charger", fontTextureSmall);
    this.charger.setColour(Colour.YELLOW);
    
    this.spawners = new TextObject("Spawners", fontTextureMedium);
    this.spawners.setColour(Colour.YELLOW);
    
    this.hDivider = new TextObject("------------------------------------", fontTextureSmall);
    this.hDivider.setColour(Colour.YELLOW);

    guiObjects = new GuiObject[] {save, tiles, empty, floor, slab, block, hazard, finish, walker, randomer, circler, charger, spawners, hDivider};
    textObjects = new TextObject[] {save, empty, floor, slab, block, hazard, finish, walker, randomer, circler, charger};
    
    this.vDivider = new TextObject[8];
    ArrayList<GuiObject> tempG = new ArrayList<GuiObject>(Arrays.asList(guiObjects));
    for (int i = 0; i < 8; i++) {
    	this.vDivider[i] = new TextObject("|", fontTextureLarge);
    	this.vDivider[i].setColour(Colour.YELLOW);
    	tempG.add(this.vDivider[i]);
    }
    
    guiObjects = tempG.toArray(guiObjects);
    
  }
  
  /**
   * Method to get the word "save"
   * @return The TextObject for the word "save"
   */
  public TextObject getSave() {
	  return save;
  }
  
  /**
   * Method to get the word "tiles"
   * @return The TextObject for the word "tiles"
   */
  public TextObject getTiles() {
	  return tiles;
  }
  
  /**
   * Method to get the word "empty"
   * @return The TextObject for the word "empty"
   */
  public TextObject getEmpty() {
	  return empty;
  }
  
  /**
   * Method to get the word "floor"
   * @return The TextObject for the word "floor"
   */
  public TextObject getFloor() {
	  return floor;
  }
  
  /**
   * Method to get the word "slab"
   * @return The TextObject for the word "slab"
   */
  public TextObject getSlab() {
	  return slab;
  }
  
  /**
   * Method to get the word "block"
   * @return The TextObject for the word "block"
   */
  public TextObject getBlock() {
	  return block;
  }
  
  /**
   * Method to get the word "hazard"
   * @return The TextObject for the word "hazard"
   */
  public TextObject getHazard() {
	  return hazard;
  }
  
  /**
   * Method to get the word "finish"
   * @return The TextObject for the word "finish"
   */
  public TextObject getFinish() {
	  return finish;
  }
  
  /**
   * Method to get the word "walker"
   * @return The TextObject for the word "walker"
   */
  public TextObject getWalker() {
	  return walker;
  }
  
  /**
   * Method to get the word "randomer"
   * @return The TextObject for the word "randomer"
   */
  public TextObject getRandomer() {
	  return randomer;
  }
  
  /**
   * Method to get the word "circler"
   * @return The TextObject for the word "circler"
   */
  public TextObject getCircler() {
	  return circler;
  }
  
  /**
   * Method to get the word "charger"
   * @return The TextObject for the word "charger"
   */
  public TextObject getCharger() {
	  return charger;
  }
  
  /**
   * Method to update the position of all objects in the GUI
   */
  public void updateSize() {
	  this.save.setPosition(Window.getWidth()-save.getSize()*1.1f, 10);
	  this.tiles.setPosition(Window.getWidth()/2-tiles.getSize()/2 - GAP*11, Window.getHeight()-tiles.getHeight()-GAP*8);
	  this.spawners.setPosition(Window.getWidth()/2-spawners.getSize()/2 + GAP*10, Window.getHeight()-spawners.getHeight()-GAP*8);
	  this.empty.setPosition(Window.getWidth()/2-empty.getSize()-GAP*12, Window.getHeight()-empty.getHeight()-GAP*6);
	  this.floor.setPosition(Window.getWidth()/2-floor.getSize()-GAP*5, Window.getHeight()-floor.getHeight()-GAP*6);
	  this.slab.setPosition(Window.getWidth()/2-slab.getSize()-GAP*12, Window.getHeight()-slab.getHeight()-GAP*4);
	  this.block.setPosition(Window.getWidth()/2-block.getSize()-GAP*5, Window.getHeight()-block.getHeight()-GAP*4);
	  this.hazard.setPosition(Window.getWidth()/2-hazard.getSize()-GAP*12, Window.getHeight()-hazard.getHeight()-GAP*2);
	  this.finish.setPosition(Window.getWidth()/2-finish.getSize()-GAP*5, Window.getHeight()-finish.getHeight()-GAP*2);
	  this.walker.setPosition(Window.getWidth()/2-walker.getSize()+GAP*8, Window.getHeight()-walker.getHeight()-GAP*5);
	  this.randomer.setPosition(Window.getWidth()/2-randomer.getSize()+GAP*16, Window.getHeight()-randomer.getHeight()-GAP*5);
	  this.circler.setPosition(Window.getWidth()/2-circler.getSize()+GAP*8, Window.getHeight()-circler.getHeight()-GAP*3);
	  this.charger.setPosition(Window.getWidth()/2-charger.getSize()+GAP*16, Window.getHeight()-charger.getHeight()-GAP*3);
	  this.hDivider.setPosition(Window.getWidth()/2-hDivider.getSize()/2, Window.getHeight()-hDivider.getHeight()-GAP*7);
	  
	  for (int i = 0; i < 8; i++) {
		  this.vDivider[i].setPosition(Window.getHalfWidth()-vDivider[i].getSize()/2, Window.getHeight()-vDivider[i].getHeight()-GAP*i);
	  }
  }

  /**
   * Method to get all GuiObjects displayed on the screen
   * @return All displayed GuiObjects on screen
   */
  @Override
  public GuiObject[] getGuiObjects() {
    return guiObjects;
  }

  /**
   * Method to get all clickable TextObjects on the screen
   * @return All TextObjects that you can click on
   */
  @Override
  public TextObject[] getTextObjects() {
    return textObjects;
  }
}
