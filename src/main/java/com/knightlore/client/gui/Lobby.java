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

public class Lobby implements IGui {

  private final TextObject bongo;

  private final TextObject congo;

  private GuiObject[] guiObjects;

  private TextObject[] textObjects;

  public Lobby() {
    try (InputStream myStream =
        new BufferedInputStream(
            new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"))) {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    } catch (IOException | FontFormatException e) {
      e.printStackTrace();
    }

    FontTexture fontSmall = new FontTexture(FONT_SMALL, CHARSET);
    FontTexture fontTitle = new FontTexture(FONT_TITLE, CHARSET);

    this.bongo = new TextObject("Bongo", fontTitle);
    this.bongo.setColour(LIGHT_BLUE);

    this.congo = new TextObject("Congo", fontTitle);
    this.congo.setColour(RED);

    this.bongo.setPosition(
        Window.getHalfWidth() - bongo.getSize(), Window.getHalfHeight() / 2 - TITLE_POS);
    this.congo.setPosition(Window.getHalfWidth() / 2, Window.getHalfHeight() / 2 - TITLE_POS);

    guiObjects = new GuiObject[] {bongo, congo};
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
