package com.knightlore.client.gui.engine;

import com.knightlore.client.gui.engine.graphics.FontTexture;
import java.awt.Font;

/**
 * Interface for GUI screens
 *
 * @author Joseph
 */
public abstract class Gui {

  /** Small font size */
  protected static final int FONT_SIZE_SMALL = 24;

  /** Medium font size */
  protected static final int FONT_SIZE_MEDIUM = 32;

  /** Large font size */
  protected static final int FONT_SIZE_LARGE = 32;

  /** Font size for titles */
  protected static final int FONT_SIZE_TITLE = 72;

  /** Font size for player lives */
  protected static final int FONT_SIZE_LIVES = 72;

  /** Create small font */
  protected static final Font FONT_SMALL = new Font("Press Start 2P", Font.PLAIN, FONT_SIZE_SMALL);

  /** Create medium font */
  protected static final Font FONT_MEDIUM =
      new Font("Press Start 2P", Font.PLAIN, FONT_SIZE_MEDIUM);

  /** Create large font */
  protected static final Font FONT_LARGE = new Font("Press Start 2P", Font.PLAIN, FONT_SIZE_LARGE);

  /** Create title font */
  protected static final Font FONT_TITLE = new Font("Press Start 2P", Font.PLAIN, FONT_SIZE_TITLE);

  /** Create lives font */
  protected static final Font FONT_LIVES = new Font("Press Start 2P", Font.PLAIN, FONT_SIZE_LIVES);

  /** Character set used */
  protected static final String CHARSET = "ISO-8859-1";

  /** Create small font texture */
  public static final FontTexture SMALL = new FontTexture(FONT_SMALL, CHARSET);

  /** Create medium font texture */
  public static final FontTexture MEDIUM = new FontTexture(FONT_MEDIUM, CHARSET);

  /** Create large font texture */
  public static final FontTexture LARGE = new FontTexture(FONT_LARGE, CHARSET);

  /** Create title font texture */
  public static final FontTexture TITLE = new FontTexture(FONT_TITLE, CHARSET);

  /** Create lives font texture */
  public static final FontTexture LIVES = new FontTexture(FONT_LIVES, CHARSET);

  /** Position of the title */
  protected static final int TITLE_POS = 300;

  /** Gap between text items */
  protected static final int GAP = FONT_SIZE_SMALL + 5;

  /** Gap between large text items */
  protected static final int GAP_LARGE = FONT_SIZE_LARGE + 5;

  /** List of text objects that have interaction */
  protected TextObject[] textObjects;

  /** List of all gui objects */
  protected GuiObject[] guiObjects;

  /** Return the text objects */
  public TextObject[] getTextObjects() {
    return textObjects;
  }

  /** Return the gui objects */
  public GuiObject[] getGuiObjects() {
    return guiObjects;
  }

  /** Cleanup the meshes */
  public void cleanup() {
    for (GuiObject guiObject : guiObjects) {
      guiObject.getMesh().cleanUp();
    }
  }
}
