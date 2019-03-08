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
    try (InputStream myStream =
        new BufferedInputStream(
            new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"))) {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    } catch (IOException | FontFormatException e) {
      e.printStackTrace();
    }

    FontTexture fontSmall = new FontTexture(FONT_SMALL, CHARSET);
    FontTexture fontMedium = new FontTexture(FONT_MEDIUM, CHARSET);
    FontTexture fontLarge = new FontTexture(FONT_LARGE, CHARSET);
    FontTexture fontTitle = new FontTexture(FONT_TITLE, CHARSET);

    TextObject bongo = new TextObject("Bongo", fontTitle);
    bongo.setColour(LIGHT_BLUE);

    TextObject congo = new TextObject("Congo", fontTitle);
    congo.setColour(RED);

    this.singleplayer = new TextObject("Singleplayer", fontSmall);
    this.singleplayer.setColour(YELLOW);

    this.multiplayer = new TextObject("Multiplayer", fontSmall);
    this.multiplayer.setColour(YELLOW);

    this.soundOn = new TextObject("(", fontLarge);
    this.soundOn.setColour();

    this.soundOff = new TextObject("/", fontMedium);
    this.soundOff.setColour(RED);

    this.quit = new TextObject("Quit", fontSmall);
    this.quit.setColour(YELLOW);

    this.options = new TextObject("Options", fontSmall);
    this.options.setColour(YELLOW);

    this.levelEditor = new TextObject("Level Editor", fontSmall);
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
