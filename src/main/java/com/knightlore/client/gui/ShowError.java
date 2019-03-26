package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

public class ShowError implements IGui {

  private static final int SEPARATOR_BOT_POS = 200;

  private TextObject[] textObjects;
  private GuiObject[] guiObjects;

  private TextObject errorMessage;
  private TextObject exit;

  public ShowError() {
    errorMessage = new TextObject("", SMALL);

    exit = new TextObject("Exit", SMALL);
    exit.setColour(Colour.YELLOW);

    updateSize();

    textObjects = new TextObject[] {exit};
    guiObjects = new GuiObject[] {errorMessage, exit};
  }

  @Override
  public TextObject[] getTextObjects() {
    return textObjects;
  }

  @Override
  public GuiObject[] getGuiObjects() {
    return guiObjects;
  }

  public TextObject getExit() {
    return exit;
  }

  public void setErrorMessageText(String errorMessageText) {
    errorMessage.setText(errorMessageText);
  }

  public void updateSize() {
    errorMessage.setPosition(
        Window.getHalfWidth() - errorMessage.getSize() / 2, Window.getHalfHeight());

    exit.setPosition(
        Window.getHalfWidth() - exit.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * 2);
  }
}
