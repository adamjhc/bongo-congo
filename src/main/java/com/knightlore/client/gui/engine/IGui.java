package com.knightlore.client.gui.engine;

import com.knightlore.client.gui.engine.graphics.FontTexture;

import java.awt.Font;

/**
 * Interface for GUI screens
 * 
 * @author Joseph
 *
 */
public interface IGui {
	
	/** Small font size */
	int FONT_SIZE_SMALL = 24;
	/** Medium font size */
	int FONT_SIZE_MEDIUM = 32;
	/** Large font size */
	int FONT_SIZE_LARGE = 32;
	/** Font size for titles */
	int FONT_SIZE_TITLE = 72;
	/** Font size for player lives */
	int FONT_SIZE_LIVES = 72;

	/** Create small font */
  Font FONT_SMALL = new Font("Press Start 2P", Font.PLAIN, FONT_SIZE_SMALL);
  /** Create medium font */
  Font FONT_MEDIUM = new Font("Press Start 2P", Font.PLAIN, FONT_SIZE_MEDIUM);
  /** Create large font */
  Font FONT_LARGE = new Font("Press Start 2P", Font.PLAIN, FONT_SIZE_LARGE);
  /** Create title font */
  Font FONT_TITLE = new Font("Press Start 2P", Font.PLAIN, FONT_SIZE_TITLE);
  /** Create lives font */
  Font FONT_LIVES = new Font("Press Start 2P", Font.PLAIN, FONT_SIZE_LIVES);

  /** Character set used */
  String CHARSET = "ISO-8859-1";

  /** Create small font texture */
  FontTexture SMALL = new FontTexture(FONT_SMALL, CHARSET);
  /** Create medium font texture */
  FontTexture MEDIUM = new FontTexture(FONT_MEDIUM, CHARSET);
  /** Create large font texture */
  FontTexture LARGE = new FontTexture(FONT_LARGE, CHARSET);
  /** Create title font texture */
  FontTexture TITLE = new FontTexture(FONT_TITLE, CHARSET);
  /** Create lives font texture */
  FontTexture LIVES = new FontTexture(FONT_LIVES, CHARSET);

  /** Position of the title */
  int TITLE_POS = 300;

  /** Gap between text items */
  int GAP = FONT_SIZE_SMALL+5;
  /** Gap between large text items */
  int GAP_LARGE = FONT_SIZE_LARGE+5;

  /** Return the text objects */
  TextObject[] getTextObjects();
  /** Return the gui objects */
  GuiObject[] getGuiObjects();

  /** Cleanup the meshes */
  default void cleanup() {
    GuiObject[] guiObjects = getGuiObjects();
    for (GuiObject guiObject : guiObjects) {
      guiObject.getMesh().cleanUp();
    }
  }
}
