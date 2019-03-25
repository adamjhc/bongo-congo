package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

public class NameLevel implements IGui {

  private static final int SEPARATOR_TOP_POS = 185;
  private static final int SEPARATOR_BOT_POS = 200;
  private static final int SEPARATOR_GAP = FONT_SIZE_SMALL;

  private static final String filePath = "customMaps/unplayable/";

  private TextObject bongo;

  private TextObject congo;

  private TextObject separatorTop;

  private TextObject separatorBottom;

  private TextObject nameYourLevel;

  private TextObject cancel;

  private TextObject saveAndQuit;

  private TextObject saveAndContinue;

  private TextObject levelName;

  private TextObject[] textObjects;

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

    this.levelName = new TextObject("Untitled", LARGE);
    this.levelName.setColour(Colour.GREEN);

    this.separatorTop = new TextObject("------------------------------", SMALL);
    this.separatorTop.setColour(Colour.YELLOW);

    this.separatorBottom = new TextObject("------------------------------", SMALL);
    this.separatorBottom.setColour(Colour.YELLOW);

    textObjects = new TextObject[] {cancel, saveAndQuit, saveAndContinue};
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
          separatorBottom
        };
  }

  public void updateSize() {
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

  public TextObject getCancel() {
    return cancel;
  }

  public TextObject getSaveAndQuit() {
    return saveAndQuit;
  }

  public TextObject getSaveAndContinue() {
    return saveAndContinue;
  }

  public TextObject getLevelName() {
    return levelName;
  }

  @Override
  public TextObject[] getTextObjects() {
    // TODO Auto-generated method stub
    return textObjects;
  }

  @Override
  public GuiObject[] getGuiObjects() {
    // TODO Auto-generated method stub
    return guiObjects;
  }
}
