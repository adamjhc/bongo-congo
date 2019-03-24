package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

public class PreLevelEditor implements IGui {

  /**
   * The position of the top horizontal separator
   */
  private static final int SEPARATOR_TOP_POS = 185;
  
  /**
   * The position of the bottom horizontal separator
   */
  private static final int SEPARATOR_BOT_POS = 200;
  
  /**
   * The size of the gap between the separator and the title above it
   */
  private static final int SEPARATOR_GAP = FONT_SIZE_SMALL;

  /**
   * The maximum width of a level
   */
  private static final int MAX_WIDTH = 50;
  
  /**
   * The maximum length of a level
   */
  private static final int MAX_LENGTH = 50;
  
  /**
   * The maximum height of a level
   */
  private static final int MAX_HEIGHT = 50;

  /**
   * The TextObject for the word "width"
   */
  private final TextObject width;
  
  /**
   * The TextObject for the word "length"
   */
  private final TextObject length;
  
  /**
   * The TextObject for the word "height"
   */
  private final TextObject height;
  
  /**
   * The TextObject for the value of the width of the level
   */
  private final TextObject wNum;
  
  /**
   * The TextObject for the value of the length of the level
   */
  private final TextObject lNum;
  
  /**
   * The TextObject for the value of the height of the level
   */
  private final TextObject hNum;
  
  /**
   * The TextObject for the "<" decrementing the width
   */
  private final TextObject wLeft;
  
  /**
   * The TextObject for the ">" incrementing the width
   */
  private final TextObject wRight;
  
  /**
   * The TextObject for the "<" decrementing the length
   */
  private final TextObject lLeft;
  
  /**
   * The TextObject for the ">" incrementing the length
   */
  private final TextObject lRight;
  
  /**
   * The TextObject for the "<" decrementing the height
   */
  private final TextObject hLeft;
  
  /**
   * The TextObject for the ">" incrementing the height
   */
  private final TextObject hRight;
  
  /**
   * The TextObject for the words "Create Level"
   */
  private final TextObject createLevel;
  
  /**
   * The TextObject for the word "exit"
   */
  private final TextObject exit;
  
  /**
   * The TextObject for the words "Load Level"
   */
  private final TextObject loadLevel;
  
  /**
   * The TextObject for the word "Bongo"
   */
  private final TextObject bongo;
  
  /** 
   * The TextObject for the word "Congo"
   */
  private final TextObject congo;
  
  /**
   * The TextObject for the words "Level Editor"
   */
  private final TextObject editor;
  
  /**
   * The TextObject for the top "---------------------"
   */
  private final TextObject separatorTop;
  
  /**
   * The TextObject for the bottom "---------------------"
   */
  private final TextObject separatorBottom;
  
  /**
   * The list of all displayed GuiObjects
   */
  private GuiObject[] guiObjects;
  
  /**
   * The list of all TextObjects you can click on
   */
  private TextObject[] textObjects;

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
   * Method to increment the width of the level
   */
  public void incWidth() {
    int newWidth = Integer.parseInt(this.wNum.getText());
    if (newWidth < MAX_WIDTH) newWidth++;
    this.wNum.setText((String.format("%02d", newWidth)));
    this.wNum.setColour(Colour.YELLOW);
  }

  /**
   * Method to decrement the width of the level
   */
  public void decWidth() {
    int newWidth = Integer.parseInt(this.wNum.getText());
    if (newWidth > 1) newWidth--;
    this.wNum.setText((String.format("%02d", newWidth)));
    this.wNum.setColour(Colour.YELLOW);
  }

  /**
   * Method to increment the length of the level
   */
  public void incLength() {
    int newLength = Integer.parseInt(this.lNum.getText());
    if (newLength < MAX_LENGTH) newLength++;
    this.lNum.setText((String.format("%02d", newLength)));
    this.lNum.setColour(Colour.YELLOW);
  }

  /**
   * Method to decrement the length of the level
   */
  public void decLength() {
    int newLength = Integer.parseInt(this.lNum.getText());
    if (newLength > 1) newLength--;
    this.lNum.setText((String.format("%02d", newLength)));
    this.lNum.setColour(Colour.YELLOW);
  }

  /**
   * Method to increment the height of the level
   */
  public void incHeight() {
    int newHeight = Integer.parseInt(this.hNum.getText());
    if (newHeight < MAX_HEIGHT) newHeight++;
    this.hNum.setText((String.format("%02d", newHeight)));
    this.hNum.setColour(Colour.YELLOW);
  }

  /**
   * Method to decrement the height of the level
   */
  public void decHeight() {
    int newHeight = Integer.parseInt(this.hNum.getText());
    if (newHeight > 1) newHeight--;
    this.hNum.setText((String.format("%02d", newHeight)));
    this.hNum.setColour(Colour.YELLOW);
  }

  /**
   * Method to get the current value of the level's width
   * @return The value of the width
   */
  public int getWidthNum() {
    return Integer.parseInt(this.wNum.getText());
  }

  /**
   * Method to get the current value of the level's length
   * @return The value of the length
   */
  public int getLengthNum() {
    return Integer.parseInt(this.lNum.getText());
  }

  /**
   * Method to get the current value of the level's height
   * @return The value of the height
   */
  public int getHeightNum() {
    return Integer.parseInt(this.hNum.getText());
  }

  /**
   * Method to get the "<" that decrements width
   * @return The TextObject for the width's "<"
   */
  public TextObject getWLeft() {
    return wLeft;
  }

  /**
   * Method to get the ">" that increments width
   * @return The TextObject for the width's ">"
   */
  public TextObject getWRight() {
    return wRight;
  }

  /**
   * Method to get the "<" that decrements length
   * @return The TextObject for the length's "<"
   */
  public TextObject getLLeft() {
    return lLeft;
  }

  /**
   * Method to get the ">" that increments length
   * @return The TextObject for the length's ">"
   */
  public TextObject getLRight() {
    return lRight;
  }

  /**
   * Method to get the "<" that decrements height
   * @return The TextObject for the height's "<"
   */
  public TextObject getHLeft() {
    return hLeft;
  }

  /**
   * Method to get the ">" that increments height
   * @return The TextObject for the height's ">"
   */
  public TextObject getHRight() {
    return hRight;
  }

  /**
   * Method to get the word "exit"
   * @return The TextObject for the word "exit"
   */
  public TextObject getBack() {
    return exit;
  }

  /**
   * Method to get the words "Create Level"
   * @return The TextObject for the words "Create Level"
   */
  public TextObject getCreateLevel() {
    return createLevel;
  }

  
  /**
   * Method to get the words "Load Level"
   * @return The TextObject for the words "Load Level"
   */
  public TextObject getLoadLevel() {
    return loadLevel;
  }

  /**
   * Method to update the position of all displayed objects on the screen
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

  /**
   * Method to get all displayed GuiObjects on the screen
   * @return The list of all displayed objects
   */
  @Override
  public GuiObject[] getGuiObjects() {
    return guiObjects;
  }

  /**
   * Method to get all clickable TextObjects
   * @return The list of all TextObjects you can click on
   */
  @Override
  public TextObject[] getTextObjects() {
    return textObjects;
  }
}
