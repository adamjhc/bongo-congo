package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

/**
 * The main menu which has buttons for:
 * Singleplayer
 * Multiplayer
 * Options
 * Editor
 * 
 * @author Joseph
 */
public class MainMenu implements IGui {

  /** Position of the menu */
  private static final int MENU_POS = 0;

  /** Singleplayer text */
  private final TextObject singleplayer;

  /** Multiplayer text */
  private final TextObject multiplayer;

  /** Level editor text */
  private final TextObject levelEditor;

  /** HighScore text */
  private final TextObject highScore;

  /** Options text */
  private final TextObject options;

  /** Quit game text */
  private final TextObject quit;

  /** Sound icon text */
  private final TextObject soundOn;

  /** Sound off icon text */
  private final TextObject soundOff;

  /** Bongo text */
  private final TextObject bongo;

  /** Congo text */
  private final TextObject congo;

  /** List of gui objects */
  private GuiObject[] guiObjects;

  /** List of text objects */
  private TextObject[] textObjects;

  /**
   * Create gui objects
   *
   * @author Joseph
   */
  public MainMenu() {
    this.bongo = new TextObject("Bongo", TITLE);
    this.bongo.setColour(Colour.LIGHT_BLUE);

    this.congo = new TextObject("Congo", TITLE);
    this.congo.setColour(Colour.RED);

    this.singleplayer = new TextObject("Singleplayer", SMALL);
    this.singleplayer.setColour(Colour.YELLOW);

    this.multiplayer = new TextObject("Multiplayer", SMALL);
    this.multiplayer.setColour(Colour.YELLOW);

    this.levelEditor = new TextObject("Editor", SMALL);
    this.levelEditor.setColour(Colour.YELLOW);

    this.highScore = new TextObject("Highscores", SMALL);
    this.highScore.setColour(Colour.YELLOW);

    this.options = new TextObject("Options", SMALL);
    this.options.setColour(Colour.YELLOW);

    this.quit = new TextObject("Quit", SMALL);
    this.quit.setColour(Colour.YELLOW);

    this.soundOn = new TextObject("(", LARGE);
    this.soundOn.setColour();
    this.soundOn.setScale(1.25f);

    this.soundOff = new TextObject("/", MEDIUM);
    this.soundOff.setColour(Colour.RED);
    this.soundOff.setRender();

    guiObjects =
        new GuiObject[] {
          bongo,
          congo,
          singleplayer,
          multiplayer,
          quit,
          options,
          soundOn,
          soundOff,
          levelEditor,
          highScore
        };
    textObjects =
        new TextObject[] {
          singleplayer, multiplayer, options, quit, soundOn, levelEditor, highScore
        };
  }

  /**
   * Returns singleplayer
   *
   * @return Singleplayer
   * @author Joseph
   */
  public TextObject getSingleplayer() {
    return singleplayer;
  }

  /**
   * Returns multiplayer
   *
   * @return Multiplayer
   * @author Joseph
   */
  public TextObject getMultiplayer() {
    return multiplayer;
  }

  /**
   * Returns level editor
   *
   * @return Level editor
   * @author Joseph
   */
  public TextObject getLevelEditor() {
    return levelEditor;
  }

  /**
   * Returns highScore
   *
   * @return HighScore
   * @author Adam C
   */
  public TextObject getHighScore() {
    return highScore;
  }

  /**
   * Return options
   *
   * @return Options
   * @author Joseph
   */
  public TextObject getOptions() {
    return options;
  }

  /**
   * Returns quit
   *
   * @return Quit
   * @author Joseph
   */
  public TextObject getQuit() {
    return quit;
  }

  /**
   * Returns sound
   *
   * @return SoundOn
   * @author Joseph
   */
  public TextObject getSound() {
    return soundOn;
  }

  /**
   * Returns sound off
   *
   * @return SoundOff
   * @author Joseph
   */
  public TextObject getSoundMute() {
    return soundOff;
  }

  /**
   * Updates the position of the gui objects
   *
   * @author Joseph
   */
  public void updateSize() {
    int gap = singleplayer.getHeight() + 5;

    this.bongo.setPosition(
        Window.getHalfWidth() - bongo.getSize(), Window.getHalfHeight() - TITLE_POS);
    this.congo.setPosition(Window.getHalfWidth(), Window.getHalfHeight() - TITLE_POS);

    this.singleplayer.setPosition(
        Window.getHalfWidth() - singleplayer.getSize() / 2, Window.getHalfHeight() + MENU_POS);
    this.multiplayer.setPosition(
        Window.getHalfWidth() - multiplayer.getSize() / 2, Window.getHalfHeight() + MENU_POS + gap);
    this.levelEditor.setPosition(
        Window.getHalfWidth() - levelEditor.getSize() / 2,
        Window.getHalfHeight() + MENU_POS + gap * 2);
    this.highScore.setPosition(
        Window.getHalfWidth() - highScore.getSize() / 2,
        Window.getHalfHeight() + MENU_POS + gap * 3);
    this.options.setPosition(
        Window.getHalfWidth() - options.getSize() / 2, Window.getHalfHeight() + MENU_POS + gap * 4);
    this.quit.setPosition(
        Window.getHalfWidth() - quit.getSize() / 2, Window.getHalfHeight() + MENU_POS + gap * 5);

    this.soundOn.setPosition(
        Window.getWidth() - soundOn.getSize() * soundOn.getScale(),
        Window.getHeight() - soundOn.getHeight() * soundOn.getScale());
    this.soundOff.setPosition(
        Window.getWidth() - soundOff.getSize(), Window.getHeight() - soundOff.getHeight());
  }

  /**
   * Increases the size of the menu font
   *
   * @author Joseph
   */
  public void incFont() {
    singleplayer.setFontTexture(MEDIUM);
    multiplayer.setFontTexture(MEDIUM);
    levelEditor.setFontTexture(MEDIUM);
    highScore.setFontTexture(MEDIUM);
    options.setFontTexture(MEDIUM);
    quit.setFontTexture(MEDIUM);
  }

  /**
   * Decreases the size of the menu font
   *
   * @author Joseph
   */
  public void decFont() {
    singleplayer.setFontTexture(SMALL);
    multiplayer.setFontTexture(SMALL);
    levelEditor.setFontTexture(SMALL);
    highScore.setFontTexture(SMALL);
    options.setFontTexture(SMALL);
    quit.setFontTexture(SMALL);
  }

  @Override
  public TextObject[] getTextObjects() {
    return textObjects;
  }

  @Override
  public GuiObject[] getGuiObjects() {
    return guiObjects;
  }
}
