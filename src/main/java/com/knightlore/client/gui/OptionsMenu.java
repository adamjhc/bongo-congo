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
  private static final int SEPARATOR_GAP = 16;

  private static final int MAX_VOLUME = 100;

  private final TextObject incVolume;
  private final TextObject decVolume;
  private final TextObject volume;
  private final TextObject exit;
  private GuiObject[] guiObjects;
  private TextObject[] textObjects;

  public OptionsMenu() {
    TextObject bongo = new TextObject("Bongo", TITLE);
    bongo.setColour(LIGHT_BLUE);

    TextObject congo = new TextObject("Congo", TITLE);
    congo.setColour(RED);

    this.incVolume = new TextObject(">", LARGE);
    this.incVolume.setColour(YELLOW);

    this.decVolume = new TextObject("<", LARGE);
    this.decVolume.setColour(YELLOW);

    this.volume = new TextObject("070", LARGE);
    this.volume.setColour(YELLOW);

    this.exit = new TextObject("Exit", SMALL);
    this.exit.setColour(YELLOW);

    TextObject options = new TextObject("Options", SMALL);
    options.setColour(YELLOW);

    TextObject separatorTop = new TextObject("------------------------------", SMALL);
    separatorTop.setColour(YELLOW);

    TextObject separatorBot = new TextObject("------------------------------", SMALL);
    separatorBot.setColour(YELLOW);

    TextObject musicVolume = new TextObject("Game music volume", SMALL);
    musicVolume.setColour(YELLOW);

    bongo.setPosition(Window.getHalfWidth() - bongo.getSize(), Window.getHalfHeight() - TITLE_POS);
    congo.setPosition(Window.getHalfWidth(), Window.getHalfHeight() - TITLE_POS);
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
        Window.getHalfHeight() + SEPARATOR_BOT_POS + SEPARATOR_GAP);
    options.setPosition(
        Window.getHalfWidth() - options.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS - SEPARATOR_GAP);
    separatorTop.setPosition(
        Window.getHalfWidth() - separatorTop.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS);
    separatorBot.setPosition(
        Window.getHalfWidth() - separatorBot.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS);
    musicVolume.setPosition(
        Window.getHalfWidth() - musicVolume.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP);

    guiObjects =
        new GuiObject[] {
          bongo,
          congo,
          incVolume,
          decVolume,
          volume,
          exit,
          options,
          separatorTop,
          separatorBot,
          musicVolume
        };
    textObjects = new TextObject[] {incVolume, decVolume, exit};
  }

  public void incVolume() {
    int volumeAmount = Integer.parseInt(this.volume.getText());
    if (volumeAmount < MAX_VOLUME) volumeAmount++;
    this.volume.setText((String.format("%03d", volumeAmount)));
    this.volume.getMesh().getMaterial().setColour(YELLOW);
  }

  public void decVolume() {
    int volumeAmount = Integer.parseInt(this.volume.getText());
    if (volumeAmount > 0) volumeAmount--;
    this.volume.setText((String.format("%03d", volumeAmount)));
    this.volume.getMesh().getMaterial().setColour(YELLOW);
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

  @Override
  public TextObject[] getTextObjects() {
    return textObjects;
  }

  @Override
  public GuiObject[] getGuiObjects() {
    return guiObjects;
  }
}
