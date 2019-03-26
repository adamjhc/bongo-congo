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

    guiObjects = new GuiObject[] {save, tiles, empty, floor, slab, block, hazard, finish};
    textObjects = new TextObject[] {save, empty, floor, slab, block, hazard, finish};
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
    this.save.setPosition(Window.getWidth() - save.getSize() * 1.1f, 10);
    this.tiles.setPosition(
        Window.getHalfWidth() - tiles.getSize() / 2,
        Window.getHeight() - tiles.getHeight() - GAP * 4f);
    this.empty.setPosition(
        Window.getHalfWidth() - empty.getSize() - GAP * 18,
        Window.getHeight() - empty.getHeight() - (float) GAP);
    this.floor.setPosition(
        Window.getHalfWidth() - floor.getSize() - GAP * 10,
        Window.getHeight() - floor.getHeight() - (float) GAP);
    this.slab.setPosition(
        Window.getHalfWidth() - slab.getSize() - GAP * 2,
        Window.getHeight() - slab.getHeight() - (float) GAP);
    this.block.setPosition(
        Window.getHalfWidth() - block.getSize() + GAP * 6,
        Window.getHeight() - block.getHeight() - (float) GAP);
    this.hazard.setPosition(
        Window.getHalfWidth() - hazard.getSize() + GAP * 14,
        Window.getHeight() - hazard.getHeight() - (float) GAP);
    this.finish.setPosition(
        Window.getHalfWidth() - finish.getSize() + GAP * 22,
        Window.getHeight() - finish.getHeight() - (float) GAP);
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
