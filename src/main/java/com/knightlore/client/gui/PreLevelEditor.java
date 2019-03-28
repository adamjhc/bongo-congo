package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.Gui;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

/**
 * Screen that allows you to create a new level or load an existing one
 *
 * @author Adam W, Joseph
 */
public class PreLevelEditor extends Gui {

  /** Position of the top separator line */
  private static final int SEPARATOR_TOP_POS = 185;

  /** Position of the bottom separator line */
  private static final int SEPARATOR_BOT_POS = 200;

  /** Gap between each line of text */
  private static final int SEPARATOR_GAP = FONT_SIZE_SMALL;

  /** The maximum width of a level */
  private static final int MAX_WIDTH = 50;

  /** The maximum length of a level */
  private static final int MAX_LENGTH = 50;

  /** The maximum height of a level */
  private static final int MAX_HEIGHT = 50;

  /** Width text */
  private final TextObject width;

  /** Length text */
  private final TextObject length;

  /** Height text */
  private final TextObject height;

  /** width text */
  private final TextObject wNum;

  /** Length text */
  private final TextObject lNum;

  /** Height text */
  private final TextObject hNum;

  /** Decrease width */
  private final TextObject wLeft;

  /** Increase width */
  private final TextObject wRight;

  /** Decrease length */
  private final TextObject lLeft;

  /** Increase length */
  private final TextObject lRight;

  /** Decrease height */
  private final TextObject hLeft;

  /** Increase height */
  private final TextObject hRight;

  /** Create level text */
  private final TextObject createLevel;

  /** Exit text */
  private final TextObject exit;

  /** Load level text */
  private final TextObject loadLevel;

  /** Bongo text */
  private final TextObject bongo;

  /** Congo text */
  private final TextObject congo;

  /** Level editor text */
  private final TextObject editor;

  /** Top separator line text */
  private final TextObject separatorTop;

  /** Bottom separator line text */
  private final TextObject separatorBottom;

  /**
   * Create gui objects
   *
   * @author Adam W
   */
  public PreLevelEditor() {
    this.bongo = new TextObject("Bongo", TITLE);
    this.bongo.setColour(Colour.LIGHT_BLUE);

    this.congo = new TextObject("Congo", TITLE);
    this.congo.setColour(Colour.RED);

    this.editor = new TextObject("Level Editor", SMALL);
    this.editor.setColour(Colour.YELLOW);

    this.width = new TextObject("Width", SMALL);
    this.width.setColour(Colour.YELLOW);

    this.length = new TextObject("Length", SMALL);
    this.length.setColour(Colour.YELLOW);

    this.height = new TextObject("Height", SMALL);
    this.height.setColour(Colour.YELLOW);

    this.wNum = new TextObject("10", LARGE);
    this.wNum.setColour(Colour.YELLOW);

    this.lNum = new TextObject("10", LARGE);
    this.lNum.setColour(Colour.YELLOW);

    this.hNum = new TextObject("10", LARGE);
    this.hNum.setColour(Colour.YELLOW);

    this.wLeft = new TextObject("<", LARGE);
    this.wLeft.setColour(Colour.YELLOW);
    this.wLeft.setId("wLeft");

    this.wRight = new TextObject(">", LARGE);
    this.wRight.setColour(Colour.YELLOW);
    this.wRight.setId("wRight");

    this.lLeft = new TextObject("<", LARGE);
    this.lLeft.setColour(Colour.YELLOW);
    this.lLeft.setId("lLeft");

    this.lRight = new TextObject(">", LARGE);
    this.lRight.setColour(Colour.YELLOW);
    this.lRight.setId("lRight");

    this.hLeft = new TextObject("<", LARGE);
    this.hLeft.setColour(Colour.YELLOW);
    this.hLeft.setId("hLeft");

    this.hRight = new TextObject(">", LARGE);
    this.hRight.setColour(Colour.YELLOW);
    this.hRight.setId("hRight");

    this.separatorTop = new TextObject("------------------------------", SMALL);
    this.separatorTop.setColour(Colour.YELLOW);

    this.separatorBottom = new TextObject("------------------------------", SMALL);
    this.separatorBottom.setColour(Colour.YELLOW);

    this.createLevel = new TextObject("Create Level", SMALL);
    this.createLevel.setColour(Colour.YELLOW);

    this.exit = new TextObject("Exit", SMALL);
    this.exit.setColour(Colour.YELLOW);

    this.loadLevel = new TextObject("Load Level", SMALL);
    this.loadLevel.setColour(Colour.YELLOW);

    guiObjects =
        new GuiObject[] {
          bongo,
          congo,
          editor,
          width,
          length,
          height,
          wNum,
          lNum,
          hNum,
          wLeft,
          wRight,
          lLeft,
          lRight,
          hLeft,
          hRight,
          separatorTop,
          separatorBottom,
          createLevel,
          exit,
          loadLevel
        };
    textObjects =
        new TextObject[] {
          wLeft, wRight, lLeft, lRight, hLeft, hRight, createLevel, exit, loadLevel
        };
  }

  /**
   * Increase the width of the level
   *
   * @author Adam W
   */
  public void incWidth() {
    int newWidth = Integer.parseInt(this.wNum.getText());
    if (newWidth < MAX_WIDTH) newWidth++;
    this.wNum.setText((String.format("%02d", newWidth)));
    this.wNum.setColour(Colour.YELLOW);
  }

  /**
   * Decrease the width of the level
   *
   * @author Adam W
   */
  public void decWidth() {
    int newWidth = Integer.parseInt(this.wNum.getText());
    if (newWidth > 1) newWidth--;
    this.wNum.setText((String.format("%02d", newWidth)));
    this.wNum.setColour(Colour.YELLOW);
  }

  /**
   * Increase the length of the level
   *
   * @author Adam W
   */
  public void incLength() {
    int newLength = Integer.parseInt(this.lNum.getText());
    if (newLength < MAX_LENGTH) newLength++;
    this.lNum.setText((String.format("%02d", newLength)));
    this.lNum.setColour(Colour.YELLOW);
  }

  /**
   * Decrease the length of the level
   *
   * @author Adam W
   */
  public void decLength() {
    int newLength = Integer.parseInt(this.lNum.getText());
    if (newLength > 1) newLength--;
    this.lNum.setText((String.format("%02d", newLength)));
    this.lNum.setColour(Colour.YELLOW);
  }

  /**
   * Increase the height of the level
   *
   * @author Adam W
   */
  public void incHeight() {
    int newHeight = Integer.parseInt(this.hNum.getText());
    if (newHeight < MAX_HEIGHT) newHeight++;
    this.hNum.setText((String.format("%02d", newHeight)));
    this.hNum.setColour(Colour.YELLOW);
  }

  /**
   * Decrease the height of the level
   *
   * @author Adam W
   */
  public void decHeight() {
    int newHeight = Integer.parseInt(this.hNum.getText());
    if (newHeight > 1) newHeight--;
    this.hNum.setText((String.format("%02d", newHeight)));
    this.hNum.setColour(Colour.YELLOW);
  }

  /**
   * Return the current width
   *
   * @return Width
   * @author Adam W
   */
  public int getWidthNum() {
    return Integer.parseInt(this.wNum.getText());
  }

  /**
   * Return the current length
   *
   * @return Length
   * @author Adam W
   */
  public int getLengthNum() {
    return Integer.parseInt(this.lNum.getText());
  }

  /**
   * Return the current height
   *
   * @return Height
   * @author Adam W
   */
  public int getHeightNum() {
    return Integer.parseInt(this.hNum.getText());
  }

  /**
   * Return decrease width
   *
   * @return Decrease width
   * @author Adam W
   */
  public TextObject getWLeft() {
    return wLeft;
  }

  /**
   * Return increase width
   *
   * @return Increase width
   * @author Adam W
   */
  public TextObject getWRight() {
    return wRight;
  }

  /**
   * Return decrease length
   *
   * @return Decrease length
   * @author Adam W
   */
  public TextObject getLLeft() {
    return lLeft;
  }

  /**
   * Return increase length
   *
   * @return Increase length
   * @author Adam W
   */
  public TextObject getLRight() {
    return lRight;
  }

  /**
   * Return decrease height
   *
   * @return Decrease height
   * @author Adam W
   */
  public TextObject getHLeft() {
    return hLeft;
  }

  /**
   * Return increase height
   *
   * @return Increase height
   * @author Adam W
   */
  public TextObject getHRight() {
    return hRight;
  }

  /**
   * Return exit
   *
   * @return Exit
   * @author Adam W
   */
  public TextObject getExit() {
    return exit;
  }

  /**
   * Return create level
   *
   * @return Create level
   * @author Adam W
   */
  public TextObject getCreateLevel() {
    return createLevel;
  }

  /**
   * Return load level
   *
   * @return Load level
   * @author Adam W
   */
  public TextObject getLoadLevel() {
    return loadLevel;
  }

  /**
   * Updates the position of the gui objects
   *
   * @author Adam W
   */
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
    this.editor.setPosition(
        Window.getHalfWidth() - editor.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS - SEPARATOR_GAP);

    this.width.setPosition(
        Window.getHalfWidth() - width.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP);
    this.length.setPosition(
        Window.getHalfWidth() - length.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 3 + GAP_LARGE);
    this.height.setPosition(
        Window.getHalfWidth() - height.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 5 + GAP_LARGE * 2);

    this.wNum.setPosition(
        Window.getHalfWidth() - wNum.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 2);
    this.lNum.setPosition(
        Window.getHalfWidth() - lNum.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 4 + GAP_LARGE);
    this.hNum.setPosition(
        Window.getHalfWidth() - hNum.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 6 + GAP_LARGE * 2);

    this.wLeft.setPosition(
        Window.getHalfWidth() - wLeft.getSize() / 2 - 100,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 2);
    this.wRight.setPosition(
        Window.getHalfWidth() - wRight.getSize() / 2 + 100,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 2);

    this.lLeft.setPosition(
        Window.getHalfWidth() - lLeft.getSize() / 2 - 100,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 4 + GAP_LARGE);
    this.lRight.setPosition(
        Window.getHalfWidth() - lRight.getSize() / 2 + 100,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 4 + GAP_LARGE);

    this.hLeft.setPosition(
        Window.getHalfWidth() - hLeft.getSize() / 2 - 100,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 6 + GAP_LARGE * 2);
    this.hRight.setPosition(
        Window.getHalfWidth() - hRight.getSize() / 2 + 100,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 6 + GAP_LARGE * 2);

    this.createLevel.setPosition(
        Window.getHalfWidth() - createLevel.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP);

    this.exit.setPosition(
        Window.getHalfWidth() - exit.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * 3);

    this.loadLevel.setPosition(
        Window.getHalfWidth() - loadLevel.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * 2);
  }
}
