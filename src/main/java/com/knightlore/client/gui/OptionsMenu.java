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

public class OptionsMenu implements IGui {

  private static final int SEPARATOR_TOP_POS = 185;
  private static final int SEPARATOR_BOT_POS = 200;
  private static final int SEPARATOR_GAP = FONT_SIZE;

  private static final int MAX_VOLUME = 100;

  private final TextObject incVolume;
  private final TextObject decVolume;
  private final TextObject volume;
  private final TextObject exit;
  private final TextObject isFullscreen;
  private final TextObject bongo;
  private final TextObject congo;
  private final TextObject options;
  private final TextObject separatorTop;
  private final TextObject separatorBot;
  private final TextObject musicVolume;
  private final TextObject fullScreen;
  
  private GuiObject[] guiObjects;
  private TextObject[] textObjects;

  public OptionsMenu() {
    this.bongo = new TextObject("Bongo", TITLE);
    this.bongo.setColour(LIGHT_BLUE);

    this.congo = new TextObject("Congo", TITLE);
    this.congo.setColour(RED);

    this.incVolume = new TextObject(">", LARGE);
    this.incVolume.setColour(YELLOW);

    this.decVolume = new TextObject("<", LARGE);
    this.decVolume.setColour(YELLOW);

    this.volume = new TextObject("070", LARGE);
    this.volume.setColour(YELLOW);

    this.exit = new TextObject("Exit", SMALL);
    this.exit.setColour(YELLOW);
    
    this.isFullscreen = new TextObject("Off", LARGE);
    this.isFullscreen.setColour(YELLOW);

    this.options = new TextObject("Options", SMALL);
    this.options.setColour(YELLOW);

    this.separatorTop = new TextObject("------------------------------", SMALL);
    this.separatorTop.setColour(YELLOW);

    this.separatorBot = new TextObject("------------------------------", SMALL);
    this.separatorBot.setColour(YELLOW);

    this.musicVolume = new TextObject("Game music volume", SMALL);
    this.musicVolume.setColour(YELLOW);
    
    this.fullScreen = new TextObject("Fullscreen", SMALL);
    this.fullScreen.setColour(YELLOW);

    guiObjects =
        new GuiObject[] {
          bongo,
          congo,
          incVolume,
          decVolume,
          volume,
          exit,
          isFullscreen,
          options,
          separatorTop,
          separatorBot,
          musicVolume,
          fullScreen
        };
    textObjects = new TextObject[] {incVolume, decVolume, exit, isFullscreen};
  }

  public void incVolume() {
    int volumeAmount = Integer.parseInt(this.volume.getText());
    if (volumeAmount < MAX_VOLUME) volumeAmount++;
    this.volume.setText((String.format("%03d", volumeAmount)));
    this.volume.setColour(YELLOW);
  }

  public void decVolume() {
    int volumeAmount = Integer.parseInt(this.volume.getText());
    if (volumeAmount > 0) volumeAmount--;
    this.volume.setText((String.format("%03d", volumeAmount)));
    this.volume.setColour(YELLOW);
  }
  
  public void setFullscreen() {
  	if (this.isFullscreen.getText() == "Off") this.isFullscreen.setText("On");
  	else this.isFullscreen.setText("Off");
  	this.isFullscreen.setPositionX(Window.getHalfWidth() - isFullscreen.getSize() / 2);
  	this.isFullscreen.setColour(YELLOW);
  	
  	Window.setFullscreen();
  }
  
  public TextObject getIncVolume() {
    return incVolume;
  }

  public TextObject getDecVolume() {
    return decVolume;
  }

  public TextObject getExit() {
    return exit;
  }

  public TextObject getIsFullscreen() {
  	return isFullscreen;
  }
  
  public void updateSize() {
    this.bongo.setPosition(Window.getHalfWidth() - bongo.getSize(), Window.getHalfHeight() - TITLE_POS);
    this.congo.setPosition(Window.getHalfWidth(), Window.getHalfHeight() - TITLE_POS);
    this.volume.setPosition(
        Window.getHalfWidth() - volume.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 2);
    this.incVolume.setPosition(
        Window.getHalfWidth() - incVolume.getSize() / 2 + 100,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 2);
    this.decVolume.setPosition(
        Window.getHalfWidth() - decVolume.getSize() / 2 - 100,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 2);
    this.exit.setPosition(
        Window.getHalfWidth() - exit.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP);
    this.isFullscreen.setPosition(
    		Window.getHalfWidth() - isFullscreen.getSize() / 2,
    		Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 4 + GAP_LARGE);
    this.options.setPosition(
        Window.getHalfWidth() - options.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS - SEPARATOR_GAP);
    this.separatorTop.setPosition(
        Window.getHalfWidth() - separatorTop.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS);
    this.separatorBot.setPosition(
        Window.getHalfWidth() - separatorBot.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS);
    this.musicVolume.setPosition(
        Window.getHalfWidth() - musicVolume.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP);
    this.fullScreen.setPosition(
    		Window.getHalfWidth() - fullScreen.getSize() / 2,
    		Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 3 + GAP_LARGE);
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
