package com.knightlore.client.gui;

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

public class MainMenu implements IGui {

  private static final int MENU_POS = 100;

  private final TextObject singleplayer;
  private final TextObject multiplayer;
  private final TextObject quit;
  private final TextObject soundOn;
  private final TextObject soundOff;
  private final TextObject options;
  private final TextObject levelEditor;
  private GuiObject[] guiObjects;
  private TextObject[] textObjects;

  public MainMenu() {
    TextObject bongo = new TextObject("Bongo", TITLE);
    bongo.setColour(LIGHT_BLUE);

    TextObject congo = new TextObject("Congo", TITLE);
    congo.setColour(RED);

    this.singleplayer = new TextObject("Singleplayer", SMALL);
    this.singleplayer.setColour(YELLOW);

    this.multiplayer = new TextObject("Multiplayer", SMALL);
    this.multiplayer.setColour(YELLOW);

    this.soundOn = new TextObject("(", LARGE);
    this.soundOn.setColour();

    this.soundOff = new TextObject("/", MEDIUM);
    this.soundOff.setColour(RED);

    this.quit = new TextObject("Quit", SMALL);
    this.quit.setColour(YELLOW);

    this.options = new TextObject("Options", SMALL);
    this.options.setColour(YELLOW);

    this.levelEditor = new TextObject("Level Editor", SMALL);
    this.levelEditor.getMesh().getMaterial().setColour(YELLOW);

    bongo.setPosition(Window.getHalfWidth() - bongo.getSize(), Window.getHalfHeight() - TITLE_POS);
    congo.setPosition(Window.getHalfWidth(), Window.getHalfHeight() - TITLE_POS);
    this.singleplayer.setPosition(
        Window.getHalfWidth() - singleplayer.getSize() / 2, Window.getHalfHeight() + MENU_POS);
    this.multiplayer.setPosition(
        Window.getHalfWidth() - multiplayer.getSize() / 2, Window.getHalfHeight() + MENU_POS + GAP);
    this.levelEditor.setPosition(
        Window.getHalfWidth() - levelEditor.getSize() / 2,
        Window.getHalfHeight() + MENU_POS + GAP * 2);
    this.options.setPosition(
        Window.getHalfWidth() - options.getSize() / 2, Window.getHalfHeight() + MENU_POS + GAP * 3);
    this.soundOn.setPosition(
        Window.getWidth() - soundOn.getSize(), Window.getHeight() - soundOn.getHeight());
    this.soundOff.setPosition(
        Window.getWidth() - soundOff.getSize(), Window.getHeight() - soundOff.getHeight());
    this.quit.setPosition(
        Window.getHalfWidth() - quit.getSize() / 2, Window.getHalfHeight() + MENU_POS + GAP * 4);

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

  @Override
  public TextObject[] getTextObjects() {
    return textObjects;
  }

  @Override
  public GuiObject[] getGuiObjects() {
    return guiObjects;
  }
}
