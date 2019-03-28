package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.Gui;
import com.knightlore.client.gui.engine.GuiObject;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Head-up display shown when using the level editor
 *
 * @author Adam W, Joseph
 */
public class LevelEditorHud extends Gui {

  private static final int CONTROLS_SIDE_GAP = 6;

  private static final int CONTROLS_HIDE =
      -CONTROLS_SIDE_GAP - FONT_SIZE_SMALL * 28 - CONTROLS_SIDE_GAP;

  /** Save text */
  private final TextObject save;

  /** Tiles text */
  private final TextObject tiles;

  /** Empty text */
  private final TextObject empty;

  /** Floor text */
  private final TextObject floor;

  /** Slab text */
  private final TextObject slab;

  /** Block text */
  private final TextObject block;

  /** Hazard text */
  private final TextObject hazard;

  /** Finish text */
  private final TextObject finish;

  /** Walker text */
  private final TextObject walker;

  /** Randomer text */
  private final TextObject randomer;

  /** Circler text */
  private final TextObject circler;

  /** Spawners text */
  private final TextObject spawners;

  /** wasd text */
  private final TextObject selectTile;

  /** qe text */
  private final TextObject upDownLayer;

  /** mouse text */
  private final TextObject moveCamera;

  /** zx text */
  private final TextObject zoomInOut;

  /** ,. text */
  private final TextObject rotateLeftRight;

  /** controls text */
  private final TextObject controls;

  /** enter text */
  private final TextObject testLevel;

  /** Gui vertical divider */
  private final TextObject[] vDivider;

  /** Gui horizontal divider */
  private final TextObject hDivider;

  /**
   * Create gui objects
   *
   * @author Adam W
   */
  public LevelEditorHud() {
    try (InputStream myStream =
        new BufferedInputStream(
            new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"))) {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    } catch (FontFormatException | IOException e) {
      e.printStackTrace();
    }

    FontTexture fontTextureLarge = new FontTexture(FONT_LARGE, CHARSET);
    FontTexture fontTextureMedium = new FontTexture(FONT_MEDIUM, CHARSET);
    FontTexture fontTextureSmall = new FontTexture(FONT_SMALL, CHARSET);

    this.save = new TextObject("Save", fontTextureLarge);
    this.save.setColour(Colour.YELLOW);

    this.tiles = new TextObject("Tiles", fontTextureMedium);
    this.tiles.setColour(Colour.YELLOW);

    this.empty = new TextObject("Empty", fontTextureSmall);
    this.empty.setColour(Colour.YELLOW);

    this.floor = new TextObject("Floor", fontTextureSmall);
    this.floor.setColour(Colour.YELLOW);

    this.slab = new TextObject("Slab", fontTextureSmall);
    this.slab.setColour(Colour.YELLOW);

    this.block = new TextObject("Block", fontTextureSmall);
    this.block.setColour(Colour.YELLOW);

    this.hazard = new TextObject("Hazard", fontTextureSmall);
    this.hazard.setColour(Colour.YELLOW);

    this.finish = new TextObject("Finish", fontTextureSmall);
    this.finish.setColour(Colour.YELLOW);

    this.walker = new TextObject("Walker", fontTextureSmall);
    this.walker.setColour(Colour.YELLOW);

    this.randomer = new TextObject("Randomer", fontTextureSmall);
    this.randomer.setColour(Colour.YELLOW);

    this.circler = new TextObject("Circler", fontTextureSmall);
    this.circler.setColour(Colour.YELLOW);

    this.spawners = new TextObject("Spawners", fontTextureMedium);
    this.spawners.setColour(Colour.YELLOW);

    this.hDivider = new TextObject("------------------------------------", fontTextureSmall);
    this.hDivider.setColour(Colour.YELLOW);

    this.controls = new TextObject("Controls", fontTextureLarge);
    this.controls.setColour(Colour.YELLOW);

    this.selectTile = new TextObject("WASD  : Select Tile", fontTextureSmall);
    this.selectTile.setColour(Colour.YELLOW);

    this.upDownLayer = new TextObject("Q/E   : Down/Up layer", fontTextureSmall);
    this.upDownLayer.setColour(Colour.YELLOW);

    this.zoomInOut = new TextObject("Z/X   : Zoom in/out", fontTextureSmall);
    this.zoomInOut.setColour(Colour.YELLOW);

    this.rotateLeftRight = new TextObject(",/.   : Rotate left/right", fontTextureSmall);
    this.rotateLeftRight.setColour(Colour.YELLOW);

    this.moveCamera = new TextObject("Mouse : Move camera", fontTextureSmall);
    this.moveCamera.setColour(Colour.YELLOW);

    this.testLevel = new TextObject("Enter : Test Level", fontTextureSmall);
    this.testLevel.setColour(Colour.YELLOW);

    guiObjects =
        new GuiObject[] {
          save,
          tiles,
          empty,
          floor,
          slab,
          block,
          hazard,
          finish,
          walker,
          randomer,
          circler,
          spawners,
          hDivider,
          controls,
          selectTile,
          upDownLayer,
          zoomInOut,
          rotateLeftRight,
          moveCamera,
          testLevel
        };
    textObjects =
        new TextObject[] {
          save, empty, floor, slab, block, hazard, finish, walker, randomer, circler
        };

    controls.setPosition(CONTROLS_HIDE, CONTROLS_SIDE_GAP);
    selectTile.setPosition(CONTROLS_HIDE, CONTROLS_SIDE_GAP + GAP * 1.75f);
    upDownLayer.setPosition(CONTROLS_HIDE, CONTROLS_SIDE_GAP + GAP * 2.75f);
    zoomInOut.setPosition(CONTROLS_HIDE, CONTROLS_SIDE_GAP + GAP * 3.75f);
    rotateLeftRight.setPosition(CONTROLS_HIDE, CONTROLS_SIDE_GAP + GAP * 4.75f);
    moveCamera.setPosition(CONTROLS_HIDE, CONTROLS_SIDE_GAP + GAP * 5.75f);
    testLevel.setPosition(CONTROLS_HIDE, CONTROLS_SIDE_GAP + GAP * 6.75f);

    this.vDivider = new TextObject[8];
    List<GuiObject> tempG = new ArrayList<>(Arrays.asList(guiObjects));
    for (int i = 0; i < 8; i++) {
      this.vDivider[i] = new TextObject("|", fontTextureLarge);
      this.vDivider[i].setColour(Colour.YELLOW);
      tempG.add(this.vDivider[i]);
    }

    guiObjects = tempG.toArray(guiObjects);
  }

  public void moveScore(float move, float targetXPos) {
    float xPosControls = controls.getPositionX();

    if (xPosControls < targetXPos && move > 0) {
      setPosition(move, xPosControls);
      if (controls.getPositionX() > targetXPos) {
        setPosition(0, targetXPos);
      }
    } else if (xPosControls > targetXPos && move < 0) {
      setPosition(move, xPosControls);
    }
  }

  public void setPosition(float move, float xPosControls) {
    controls.setPositionX(xPosControls + move);
    selectTile.setPositionX(xPosControls + move);
    moveCamera.setPositionX(xPosControls + move);
    rotateLeftRight.setPositionX(xPosControls + move);
    zoomInOut.setPositionX(xPosControls + move);
    upDownLayer.setPositionX(xPosControls + move);
    testLevel.setPositionX(xPosControls + move);
  }

  public int getControlsSideGap() {
    return CONTROLS_SIDE_GAP;
  }

  public int getControlsHide() {
    return CONTROLS_HIDE;
  }

  /**
   * Return save
   *
   * @return Save
   * @author Adam W
   */
  public TextObject getSave() {
    return save;
  }

  /**
   * Return tiles
   *
   * @return Tiles
   * @author Adam W
   */
  public TextObject getTiles() {
    return tiles;
  }

  /**
   * Return empty
   *
   * @return Empty
   * @author Adam W
   */
  public TextObject getEmpty() {
    return empty;
  }

  /**
   * Return floor
   *
   * @return Floor
   * @author Adam W
   */
  public TextObject getFloor() {
    return floor;
  }

  /**
   * Return slab
   *
   * @return Slab
   * @author Adam W
   */
  public TextObject getSlab() {
    return slab;
  }

  /**
   * Return block
   *
   * @return Block
   * @author Adam W
   */
  public TextObject getBlock() {
    return block;
  }

  /**
   * Return hazard
   *
   * @return Hazard
   * @author Adam W
   */
  public TextObject getHazard() {
    return hazard;
  }

  /**
   * Return finish
   *
   * @return Finish
   * @author Adam W
   */
  public TextObject getFinish() {
    return finish;
  }

  /**
   * Return walker
   *
   * @return Walker
   * @author Adam W
   */
  public TextObject getWalker() {
    return walker;
  }

  /**
   * Return randomer
   *
   * @return Randomer
   * @author Adam W
   */
  public TextObject getRandomer() {
    return randomer;
  }

  /**
   * Return circler
   *
   * @return Circler
   * @author Adam W
   */
  public TextObject getCircler() {
    return circler;
  }

  /**
   * Updates the position of the gui objects
   *
   * @author Adam W
   */
  public void updateSize() {
    this.save.setPosition(Window.getWidth() - save.getSize() * 1.1f, 10);
    this.tiles.setPosition(
        Window.getHalfWidth() - tiles.getSize() / 2 - GAP * 11,
        Window.getHeight() - tiles.getHeight() - GAP * 8);
    this.spawners.setPosition(
        Window.getHalfWidth() - spawners.getSize() / 2 + GAP * 10,
        Window.getHeight() - spawners.getHeight() - GAP * 8);
    this.empty.setPosition(
        Window.getHalfWidth() - empty.getSize() - GAP * 12,
        Window.getHeight() - empty.getHeight() - GAP * 6);
    this.floor.setPosition(
        Window.getHalfWidth() - floor.getSize() - GAP * 5,
        Window.getHeight() - floor.getHeight() - GAP * 6);
    this.slab.setPosition(
        Window.getHalfWidth() - slab.getSize() - GAP * 12,
        Window.getHeight() - slab.getHeight() - GAP * 4);
    this.block.setPosition(
        Window.getHalfWidth() - block.getSize() - GAP * 5,
        Window.getHeight() - block.getHeight() - GAP * 4);
    this.hazard.setPosition(
        Window.getHalfWidth() - hazard.getSize() - GAP * 12,
        Window.getHeight() - hazard.getHeight() - GAP * 2);
    this.finish.setPosition(
        Window.getHalfWidth() - finish.getSize() - GAP * 5,
        Window.getHeight() - finish.getHeight() - GAP * 2);
    this.walker.setPosition(
        Window.getHalfWidth() - walker.getSize() + GAP * 8,
        Window.getHeight() - walker.getHeight() - GAP * 5);
    this.randomer.setPosition(
        Window.getHalfWidth() - randomer.getSize() + GAP * 16,
        Window.getHeight() - randomer.getHeight() - GAP * 5);
    this.circler.setPosition(
        Window.getHalfWidth() - circler.getSize() + GAP * 12,
        Window.getHeight() - circler.getHeight() - GAP * 3);
    this.hDivider.setPosition(
        Window.getHalfWidth() - hDivider.getSize() / 2,
        Window.getHeight() - hDivider.getHeight() - GAP * 7);

    for (int i = 0; i < 8; i++) {
      this.vDivider[i].setPosition(
          Window.getHalfWidth() - vDivider[i].getSize() / 2,
          Window.getHeight() - vDivider[i].getHeight() - GAP * i);
    }
  }
}
