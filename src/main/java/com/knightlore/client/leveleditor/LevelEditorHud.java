package com.knightlore.client.leveleditor;

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
import org.joml.Vector4f;

public class LevelEditorHud implements IGui {

  private static final Font FONT_LARGE = new Font("Press Start 2P", Font.PLAIN, 40);

  private static final String CHARSET = "ISO-8859-1";
  
  private final TextObject save;
  private final TextObject tiles;
  private final TextObject empty;
  private final TextObject floor;
  private final TextObject slab;
  private final TextObject block;
  private final TextObject hazard;
  private final TextObject finish;
  private final TextObject walkerSpawner;
  private final TextObject randomerSpawner;
  private final TextObject circlerSpawner;
  private final TextObject chargerSpawner;

  private TextObject[] textObjects;
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
    
    this.walkerSpawner = new TextObject("Walker\nSpawner", fontTextureSmall);
    this.walkerSpawner.setColour(Colour.YELLOW);
    
    this.randomerSpawner = new TextObject("Randomer\nSpawner", fontTextureSmall);
    this.randomerSpawner.setColour(Colour.YELLOW);
    
    this.circlerSpawner = new TextObject("Circler\nSpawner", fontTextureSmall);
    this.circlerSpawner.setColour(Colour.YELLOW);
    
    this.chargerSpawner = new TextObject("Charger\nSpawner", fontTextureSmall);
    this.chargerSpawner.setColour(Colour.YELLOW);

    guiObjects = new GuiObject[] {save, tiles, empty, floor, slab, block, hazard, finish, walkerSpawner, randomerSpawner, circlerSpawner, chargerSpawner};
    textObjects = new TextObject[] {save, empty, floor, slab, block, hazard, finish, walkerSpawner, randomerSpawner, circlerSpawner, chargerSpawner};
  }
  
  public TextObject getSave() {
	  return save;
  }
  
  public TextObject getTiles() {
	  return tiles;
  }
  
  public TextObject getEmpty() {
	  return empty;
  }
  
  public TextObject getFloor() {
	  return floor;
  }
  
  public TextObject getSlab() {
	  return slab;
  }
  
  public TextObject getBlock() {
	  return block;
  }
  
  public TextObject getHazard() {
	  return hazard;
  }
  
  public TextObject getFinish() {
	  return finish;
  }
  
  public void updateSize() {
	  this.save.setPosition(Window.getWidth()-save.getSize()*1.1f, 10);
	  this.tiles.setPosition(Window.getWidth()/2-tiles.getSize()/2, Window.getHeight()-tiles.getHeight()-GAP*4);  
	  this.empty.setPosition(Window.getWidth()/2-empty.getSize()-GAP*18, Window.getHeight()-empty.getHeight()-GAP);
	  this.floor.setPosition(Window.getWidth()/2-floor.getSize()-GAP*10, Window.getHeight()-floor.getHeight()-GAP);
	  this.slab.setPosition(Window.getWidth()/2-slab.getSize()-GAP*2, Window.getHeight()-slab.getHeight()-GAP);
	  this.block.setPosition(Window.getWidth()/2-block.getSize()+GAP*6, Window.getHeight()-block.getHeight()-GAP);
	  this.hazard.setPosition(Window.getWidth()/2-hazard.getSize()+GAP*14, Window.getHeight()-hazard.getHeight()-GAP);
	  this.finish.setPosition(Window.getWidth()/2-finish.getSize()+GAP*22, Window.getHeight()-finish.getHeight()-GAP);
  }

  @Override
  public GuiObject[] getGuiObjects() {
    return guiObjects;
  }

  @Override
  public TextObject[] getTextObjects() {
    return textObjects;
  }
}
