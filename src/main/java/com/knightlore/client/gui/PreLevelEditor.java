package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

public class PreLevelEditor implements IGui {

  private static final int SEPARATOR_TOP_POS = 185;
  private static final int SEPARATOR_BOT_POS = 200;
  private static final int SEPARATOR_GAP = FONT_SIZE_SMALL;

  private static final int MAX_WIDTH = 50;
  private static final int MAX_LENGTH = 50;
  private static final int MAX_HEIGHT = 50;

  private final TextObject width;
  private final TextObject length;
  private final TextObject height;
  private final TextObject wNum;
  private final TextObject lNum;
  private final TextObject hNum;
  private final TextObject wLeft;
  private final TextObject wRight;
  private final TextObject lLeft;
  private final TextObject lRight;
  private final TextObject hLeft;
  private final TextObject hRight;
  private final TextObject createLevel;
  private final TextObject exit;
  private final TextObject loadLevel;
  private final TextObject bongo;
  private final TextObject congo;
  private final TextObject editor;
  private final TextObject separatorTop;
  private final TextObject separatorBottom;
  private GuiObject[] guiObjects;
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

    bongo.setPosition(Window.getHalfWidth() - bongo.getSize(), Window.getHalfHeight() - TITLE_POS);
    congo.setPosition(Window.getHalfWidth(), Window.getHalfHeight() - TITLE_POS);
    separatorTop.setPosition(
        Window.getHalfWidth() - separatorTop.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS);
    separatorBottom.setPosition(
        Window.getHalfWidth() - separatorBottom.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS);
    editor.setPosition(
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
    
    this.loadLevel.setPosition(
        	Window.getHalfWidth() - loadLevel.getSize() / 2, Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * 2);

    this.exit.setPosition(
        Window.getHalfWidth() - exit.getSize() / 2, Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * 3);


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
    textObjects = new TextObject[] {wLeft, wRight, lLeft, lRight, hLeft, hRight, createLevel, exit, loadLevel};
  }

  public void incWidth() {
    int newWidth = Integer.parseInt(this.wNum.getText());
    if (newWidth < MAX_WIDTH) newWidth++;
    this.wNum.setText((String.format("%02d", newWidth)));
    this.wNum.setColour(Colour.YELLOW);
  }

  public void decWidth() {
    int newWidth = Integer.parseInt(this.wNum.getText());
    if (newWidth > 1) newWidth--;
    this.wNum.setText((String.format("%02d", newWidth)));
    this.wNum.setColour(Colour.YELLOW);
  }

  public void incLength() {
    int newLength = Integer.parseInt(this.lNum.getText());
    if (newLength < MAX_LENGTH) newLength++;
    this.lNum.setText((String.format("%02d", newLength)));
    this.lNum.setColour(Colour.YELLOW);
  }

  public void decLength() {
    int newLength = Integer.parseInt(this.lNum.getText());
    if (newLength > 1) newLength--;
    this.lNum.setText((String.format("%02d", newLength)));
    this.lNum.setColour(Colour.YELLOW);
  }

  public void incHeight() {
    int newHeight = Integer.parseInt(this.hNum.getText());
    if (newHeight < MAX_HEIGHT) newHeight++;
    this.hNum.setText((String.format("%02d", newHeight)));
    this.hNum.setColour(Colour.YELLOW);
  }

  public void decHeight() {
    int newHeight = Integer.parseInt(this.hNum.getText());
    if (newHeight > 1) newHeight--;
    this.hNum.setText((String.format("%02d", newHeight)));
    this.hNum.setColour(Colour.YELLOW);
  }

  public int getWidthNum() {
    return Integer.parseInt(this.wNum.getText());
  }

  public int getLengthNum() {
    return Integer.parseInt(this.lNum.getText());
  }

  public int getHeightNum() {
    return Integer.parseInt(this.hNum.getText());
  }

  public TextObject getWidth() {
    return width;
  }

  public TextObject getLength() {
    return length;
  }

  public TextObject getHeight() {
    return height;
  }

  public TextObject getWLeft() {
    return wLeft;
  }

  public TextObject getWRight() {
    return wRight;
  }

  public TextObject getLLeft() {
    return lLeft;
  }

  public TextObject getLRight() {
    return lRight;
  }

  public TextObject getHLeft() {
    return hLeft;
  }

  public TextObject getHRight() {
    return hRight;
  }

  public TextObject getBack() {
    return exit;
  }

  public TextObject getCreateLevel() {
    return createLevel;
  }
  
  public TextObject getLoadLevel() {
	  return loadLevel;
  }
  
  public void updateSize() {
    this.bongo.setPosition(Window.getHalfWidth() - bongo.getSize(), Window.getHalfHeight() - TITLE_POS);
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

  @Override
  public GuiObject[] getGuiObjects() {
    return guiObjects;
  }

  @Override
  public TextObject[] getTextObjects() {
    return textObjects;
  }
}
