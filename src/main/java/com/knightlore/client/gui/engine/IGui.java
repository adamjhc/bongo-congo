package com.knightlore.client.gui.engine;

import com.knightlore.client.gui.engine.graphics.FontTexture;
import java.awt.Font;

public interface IGui {

  int FONT_SIZE = 16;
  int FONT_SIZE_LARGE = 40;
  int FONT_SIZE_LIVES = FONT_SIZE + 5;

  Font FONT_SMALL = new Font("Press Start 2P", Font.PLAIN, FONT_SIZE);
  Font FONT_MEDIUM = new Font("Press Start 2P", Font.PLAIN, 30);
  Font FONT_LARGE = new Font("Press Start 2P", Font.PLAIN, FONT_SIZE_LARGE);
  Font FONT_TITLE = new Font("Press Start 2P", Font.PLAIN, 72);
  Font FONT_LIVES = new Font("Press Start 2P", Font.PLAIN, FONT_SIZE_LIVES);

  String CHARSET = "ISO-8859-1";

  FontTexture SMALL = new FontTexture(FONT_SMALL, CHARSET);
  FontTexture MEDIUM = new FontTexture(FONT_MEDIUM, CHARSET);
  FontTexture LARGE = new FontTexture(FONT_LARGE, CHARSET);
  FontTexture TITLE = new FontTexture(FONT_TITLE, CHARSET);
  FontTexture LIVES = new FontTexture(FONT_LIVES, CHARSET);

  int TITLE_POS = 300;

  int GAP = FONT_SIZE + 5;
  int GAP_LARGE = FONT_SIZE_LARGE + 5;

  TextObject[] getTextObjects();

  GuiObject[] getGuiObjects();

  default void cleanup() {
    GuiObject[] guiObjects = getGuiObjects();
    for (GuiObject guiObject : guiObjects) {
      guiObject.getMesh().cleanUp();
    }
  }
}
