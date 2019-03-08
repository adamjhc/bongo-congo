package com.knightlore.client.gui.engine;

import java.awt.Font;
import org.joml.Vector4f;

public interface IGui {

  Font FONT_SMALL = new Font("Press Start 2P", Font.PLAIN, 16);
  Font FONT_MEDIUM = new Font("Press Start 2P", Font.PLAIN, 30);
  Font FONT_LARGE = new Font("Press Start 2P", Font.PLAIN, 40);
  Font FONT_TITLE = new Font("Press Start 2P", Font.PLAIN, 72);
  Font FONT_LIVES = new Font("Press Start 2P", Font.PLAIN, 20);

  String CHARSET = "ISO-8859-1";

  Vector4f YELLOW = new Vector4f(1, 1, 0, 1);
  Vector4f RED = new Vector4f(1, 0, 0, 1);
  Vector4f LIGHT_BLUE = new Vector4f(0.29f, 0.92f, 0.95f, 1);

  int TITLE_POS = 300;

  int GAP = 20;

  TextObject[] getTextObjects();

  GuiObject[] getGuiObjects();

  default void cleanup() {
    GuiObject[] guiObjects = getGuiObjects();
    for (GuiObject guiObject : guiObjects) {
      guiObject.getMesh().cleanUp();
    }
  }
}
