package com.knightlore.client.gui.engine;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.joml.Vector4f;

import com.knightlore.client.gui.engine.graphics.FontTexture;

public interface IGui {
	
	int FONT_SIZE = 32;
	int FONT_SIZE_LARGE = 40;
	int FONT_SIZE_LIVES = FONT_SIZE+5;

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

  Vector4f YELLOW = new Vector4f(1, 1, 0, 1);
  Vector4f RED = new Vector4f(1, 0, 0, 1);
  Vector4f LIGHT_BLUE = new Vector4f(0.29f, 0.92f, 0.95f, 1);

  int TITLE_POS = 300;

  int GAP = FONT_SIZE+5;
  int GAP_LARGE = FONT_SIZE_LARGE+5;
  
  TextObject[] getTextObjects();

  GuiObject[] getGuiObjects();

  default void cleanup() {
    GuiObject[] guiObjects = getGuiObjects();
    for (GuiObject guiObject : guiObjects) {
      guiObject.getMesh().cleanUp();
    }
  }
}
