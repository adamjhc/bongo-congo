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
	
	int FONT_SIZE_SMALL = 24;
	int FONT_SIZE_MEDIUM = 32;
	int FONT_SIZE_LARGE = 32;
	int FONT_SIZE_TITLE = 72;
	int FONT_SIZE_LIVES = 124;

  Font FONT_SMALL = new Font("Press Start 2P", Font.PLAIN, FONT_SIZE_SMALL);
  Font FONT_MEDIUM = new Font("Press Start 2P", Font.PLAIN, FONT_SIZE_MEDIUM);
  Font FONT_LARGE = new Font("Press Start 2P", Font.PLAIN, FONT_SIZE_LARGE);
  Font FONT_TITLE = new Font("Press Start 2P", Font.PLAIN, FONT_SIZE_TITLE);
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

  int GAP = FONT_SIZE_SMALL+5;
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
