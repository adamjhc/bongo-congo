package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

public class MainMenu implements IGui {

  private static final int MENU_POS = 0;

  private final TextObject singleplayer;
  private final TextObject multiplayer;
  private final TextObject quit;
  private final TextObject soundOn;
  private final TextObject soundOff;
  private final TextObject options;
  private final TextObject levelEditor;
  private final TextObject bongo;
  private final TextObject congo;
  private GuiObject[] guiObjects;
  private TextObject[] textObjects;

  public MainMenu() {
    this.bongo = new TextObject("Bongo", TITLE);
    this.bongo.setColour(Colour.LIGHT_BLUE);

    this.congo = new TextObject("Congo", TITLE);
    this.congo.setColour(Colour.RED);

    this.singleplayer = new TextObject("Singleplayer", SMALL);
    this.singleplayer.setColour(Colour.YELLOW);

    this.multiplayer = new TextObject("Multiplayer", SMALL);
    this.multiplayer.setColour(Colour.YELLOW);

    this.soundOn = new TextObject("(", LARGE);
    this.soundOn.setColour();
    this.soundOn.setScale(1.25f);

    this.soundOff = new TextObject("/", MEDIUM);
    this.soundOff.setColour(Colour.RED);

    this.quit = new TextObject("Quit", SMALL);
    this.quit.setColour(Colour.YELLOW);

    this.options = new TextObject("Options", SMALL);
    this.options.setColour(Colour.YELLOW);

    this.levelEditor = new TextObject("Editor", SMALL);
    this.levelEditor.setColour(Colour.YELLOW);

    this.soundOff.setRender();

    guiObjects =
        new GuiObject[] {
          bongo, congo, singleplayer, multiplayer, quit, options, soundOn, soundOff, levelEditor
        };
    textObjects = new TextObject[] {singleplayer, multiplayer, options, quit, soundOn, levelEditor};
  }

  public TextObject getSingleplayer() {
    return singleplayer;
  }

  public TextObject getMultiplayer() {
    return multiplayer;
  }

  public TextObject getSound() {
    return soundOn;
  }

  public TextObject getSoundMute() {
    return soundOff;
  }

  public TextObject getQuit() {
    return quit;
  }

  public TextObject getOptions() {
    return options;
  }

  public TextObject getLevelEditor() {
    return levelEditor;
  }
  
  public void updateSize() {
  	int gap = singleplayer.getHeight() + 5;
  	
    this.bongo.setPosition(Window.getHalfWidth() - bongo.getSize(), Window.getHalfHeight() - TITLE_POS);
    this.congo.setPosition(Window.getHalfWidth(), Window.getHalfHeight() - TITLE_POS);
    this.singleplayer.setPosition(
        Window.getHalfWidth() - singleplayer.getSize() / 2, Window.getHalfHeight() + MENU_POS);
    this.multiplayer.setPosition(
        Window.getHalfWidth() - multiplayer.getSize() / 2, Window.getHalfHeight() + MENU_POS + gap);
    this.levelEditor.setPosition(
        Window.getHalfWidth() - levelEditor.getSize() / 2,
        Window.getHalfHeight() + MENU_POS + gap * 3);
    this.options.setPosition(
        Window.getHalfWidth() - options.getSize() / 2, Window.getHalfHeight() + MENU_POS + gap * 2);
    this.soundOn.setPosition(
        Window.getWidth() - soundOn.getSize()*soundOn.getScale(), Window.getHeight() - soundOn.getHeight()*soundOn.getScale());
    this.soundOff.setPosition(
        Window.getWidth() - soundOff.getSize(), Window.getHeight() - soundOff.getHeight());
    this.quit.setPosition(
        Window.getHalfWidth() - quit.getSize() / 2, Window.getHalfHeight() + MENU_POS + gap * 4);
  }
  
  public void incFont() {
  	singleplayer.setFontTexture(MEDIUM);
  	multiplayer.setFontTexture(MEDIUM);
  	levelEditor.setFontTexture(MEDIUM);
  	options.setFontTexture(MEDIUM);
  	quit.setFontTexture(MEDIUM);
  }
  
  public void decFont() {
  	singleplayer.setFontTexture(SMALL);
  	multiplayer.setFontTexture(SMALL);
  	levelEditor.setFontTexture(SMALL);
  	options.setFontTexture(SMALL);
  	quit.setFontTexture(SMALL);
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
