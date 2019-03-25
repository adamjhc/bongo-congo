package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

public class Loading implements IGui {

  private TextObject[] textObjects;
  private GuiObject[] guiObjects;

  private TextObject loading;

  public Loading() {
    loading = new TextObject("Loading...", TITLE);
    updateSize();

    textObjects = new TextObject[0];
    guiObjects = new GuiObject[] {loading};
  }

  @Override
  public TextObject[] getTextObjects() {
    return textObjects;
  }

  @Override
  public GuiObject[] getGuiObjects() {
    return guiObjects;
  }

  public void updateSize() {
    loading.setPosition(Window.getHalfWidth() - loading.getSize() / 2, Window.getHalfHeight());
  }
}
