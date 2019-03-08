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
import org.joml.Vector4f;

public class PreLevelEditor implements IGui {

  private static final int SEPARATOR_TOP_POS = 185;
  private static final int SEPARATOR_BOT_POS = 200;
  private static final int SEPARATOR_GAP = 16;

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
  private final TextObject back;
  private GuiObject[] guiObjects;
  private TextObject[] textObjects;

  public PreLevelEditor() {
    TextObject bongo = new TextObject("Bongo", TITLE);
    bongo.getMesh().getMaterial().setColour(LIGHT_BLUE);

    TextObject congo = new TextObject("Congo", TITLE);
    congo.getMesh().getMaterial().setColour(RED);

    this.width = new TextObject("Width", MEDIUM);
    this.width.getMesh().getMaterial().setColour(YELLOW);

    this.length = new TextObject("Length", MEDIUM);
    this.length.getMesh().getMaterial().setColour(YELLOW);

    this.height = new TextObject("Height", MEDIUM);
    this.height.getMesh().getMaterial().setColour(YELLOW);

    this.wNum = new TextObject("10", SMALL);
    this.wNum.getMesh().getMaterial().setColour(YELLOW);

    this.lNum = new TextObject("10", SMALL);
    this.lNum.getMesh().getMaterial().setColour(YELLOW);

    this.hNum = new TextObject("10", SMALL);
    this.hNum.getMesh().getMaterial().setColour(YELLOW);

    this.wLeft = new TextObject("<", SMALL);
    this.wLeft.getMesh().getMaterial().setColour(YELLOW);
    this.wLeft.setId("wLeft");

    this.wRight = new TextObject(">", SMALL);
    this.wRight.getMesh().getMaterial().setColour(YELLOW);
    this.wRight.setId("wRight");

    this.lLeft = new TextObject("<", SMALL);
    this.lLeft.getMesh().getMaterial().setColour(YELLOW);
    this.lLeft.setId("lLeft");

    this.lRight = new TextObject(">", SMALL);
    this.lRight.getMesh().getMaterial().setColour(YELLOW);
    this.lRight.setId("lRight");

    this.hLeft = new TextObject("<", SMALL);
    this.hLeft.getMesh().getMaterial().setColour(YELLOW);
    this.hLeft.setId("hLeft");

    this.hRight = new TextObject(">", SMALL);
    this.hRight.getMesh().getMaterial().setColour(YELLOW);
    this.hRight.setId("hRight");

    TextObject separatorTop = new TextObject("------------------------------", SMALL);
    separatorTop.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));

    TextObject separatorBottom = new TextObject("------------------------------", SMALL);
    separatorBottom.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));

    this.createLevel = new TextObject("Create Level", MEDIUM);
    this.createLevel.getMesh().getMaterial().setColour(YELLOW);

    this.back = new TextObject("Back", SMALL);
    this.back.getMesh().getMaterial().setColour(YELLOW);

    bongo.setPosition(Window.getHalfWidth() - bongo.getSize(), Window.getHalfHeight() - TITLE_POS);
    congo.setPosition(Window.getHalfWidth(), Window.getHalfHeight() - TITLE_POS);
    separatorTop.setPosition(
        Window.getHalfWidth() - separatorTop.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS);
    separatorBottom.setPosition(
        Window.getHalfWidth() - separatorBottom.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS);

    this.width.setPosition(
        Window.getHalfWidth() - width.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP);
    this.length.setPosition(
        Window.getHalfWidth() - length.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 6);
    this.height.setPosition(
        Window.getHalfWidth() - height.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 11);

    this.wNum.setPosition(
        Window.getHalfWidth() - wNum.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 4);
    this.lNum.setPosition(
        Window.getHalfWidth() - lNum.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 9);
    this.hNum.setPosition(
        Window.getHalfWidth() - hNum.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 14);

    this.wLeft.setPosition(
        Window.getHalfWidth() - wLeft.getSize() / 2 - 50,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 4);
    this.wRight.setPosition(
        Window.getHalfWidth() - wRight.getSize() / 2 + 50,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 4);

    this.lLeft.setPosition(
        Window.getHalfWidth() - lLeft.getSize() / 2 - 50,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 9);
    this.lRight.setPosition(
        Window.getHalfWidth() - lRight.getSize() / 2 + 50,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 9);

    this.hLeft.setPosition(
        Window.getHalfWidth() - hLeft.getSize() / 2 - 50,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 14);
    this.hRight.setPosition(
        Window.getHalfWidth() - hRight.getSize() / 2 + 50,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 14);

    this.createLevel.setPosition(
        Window.getHalfWidth() - createLevel.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP);

    this.back.setPosition(
        Window.getHalfWidth() - back.getSize() / 2, Window.getHalfHeight() + SEPARATOR_TOP_POS);

    guiObjects =
        new GuiObject[] {
          bongo,
          congo,
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
          back
        };
    textObjects = new TextObject[] {wLeft, wRight, lLeft, lRight, hLeft, hRight, createLevel, back};
  }

  public void incWidth() {
    int newWidth = Integer.parseInt(this.wNum.getText());
    if (newWidth < MAX_WIDTH) newWidth++;
    this.wNum.setText((String.format("%02d", newWidth)));
    this.wNum.getMesh().getMaterial().setColour(YELLOW);
  }

  public void decWidth() {
    int newWidth = Integer.parseInt(this.wNum.getText());
    if (newWidth > 1) newWidth--;
    this.wNum.setText((String.format("%02d", newWidth)));
    this.wNum.getMesh().getMaterial().setColour(YELLOW);
  }

  public void incLength() {
    int newLength = Integer.parseInt(this.lNum.getText());
    if (newLength < MAX_LENGTH) newLength++;
    this.lNum.setText((String.format("%02d", newLength)));
    this.lNum.getMesh().getMaterial().setColour(YELLOW);
  }

  public void decLength() {
    int newLength = Integer.parseInt(this.lNum.getText());
    if (newLength > 1) newLength--;
    this.lNum.setText((String.format("%02d", newLength)));
    this.lNum.getMesh().getMaterial().setColour(YELLOW);
  }

  public void incHeight() {
    int newHeight = Integer.parseInt(this.hNum.getText());
    if (newHeight < MAX_HEIGHT) newHeight++;
    this.hNum.setText((String.format("%02d", newHeight)));
    this.hNum.getMesh().getMaterial().setColour(YELLOW);
  }

  public void decHeight() {
    int newHeight = Integer.parseInt(this.hNum.getText());
    if (newHeight > 1) newHeight--;
    this.hNum.setText((String.format("%02d", newHeight)));
    this.hNum.getMesh().getMaterial().setColour(YELLOW);
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
    return back;
  }

  public TextObject getCreateLevel() {
    return createLevel;
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
