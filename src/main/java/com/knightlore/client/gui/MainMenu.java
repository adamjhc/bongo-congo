package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.Gui;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

/**
 * The main menu which has buttons for: Singleplayer Multiplayer Options Editor
 *
 * @author Joseph
 */
public class MainMenu extends Gui {

  /** Position of the menu */
  private static final int MENU_POS = -60;

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

  /** Help text */
  private final TextObject help;

  /** Sound icon text */
  private final TextObject soundOn;

  /** Sound off icon text */
  private final TextObject soundOff;

  /** Bongo text */
  private final TextObject bongo;

  /** Congo text */
  private final TextObject congo;

  /** Create gui objects */
  public MainMenu() {
    this.bongo = new TextObject("Bongo", TITLE);
    this.bongo.setColour(Colour.LIGHT_BLUE);

    this.congo = new TextObject("Congo", TITLE);
    this.congo.setColour(Colour.RED);

    this.singleplayer = new TextObject("Singleplayer", SMALL);
    this.singleplayer.setColour(Colour.YELLOW);

    this.multiplayer = new TextObject("Multiplayer", SMALL);
    this.multiplayer.setColour(Colour.YELLOW);

    this.levelEditor = new TextObject("Level Editor", SMALL);
    this.levelEditor.setColour(Colour.YELLOW);

    this.highScore = new TextObject("Highscores", SMALL);
    this.highScore.setColour(Colour.YELLOW);

    this.options = new TextObject("Options", SMALL);
    this.options.setColour(Colour.YELLOW);

    this.help = new TextObject("Help", SMALL);
    this.help.setColour(Colour.YELLOW);

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
          highScore,
          help
        };
    textObjects =
        new TextObject[] {
          singleplayer, multiplayer, options, quit, soundOn, levelEditor, highScore, help
        };
  }

  /**
   * Returns singleplayer
   *
   * @return Singleplayer
   */
  public TextObject getSingleplayer() {
    return singleplayer;
  }

  /**
   * Returns multiplayer
   *
   * @return Multiplayer
   */
  public TextObject getMultiplayer() {
    return multiplayer;
  }

  /**
   * Returns level editor
   *
   * @return Level editor
   */
  public TextObject getLevelEditor() {
    return levelEditor;
  }

  /**
   * Returns highScore
   *
   * @return HighScore
   */
  public TextObject getHighScore() {
    return highScore;
  }

  /**
   * Return options
   *
   * @return Options
   */
  public TextObject getOptions() {
    return options;
  }

  /**
   * Returns quit
   *
   * @return Quit
   */
  public TextObject getQuit() {
    return quit;
  }

  /**
   * Returns help
   *
   * @return Help
   */
  public TextObject getHelp() {
    return help;
  }

  /**
   * Returns sound
   *
   * @return SoundOn
   */
  public TextObject getSound() {
    return soundOn;
  }

  /**
   * Returns sound off
   *
   * @return SoundOff
   */
  public TextObject getSoundMute() {
    return soundOff;
  }

  /** Updates the position of the gui objects */
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
    this.help.setPosition(
        Window.getHalfWidth() - help.getSize() / 2, Window.getHalfHeight() + MENU_POS + gap * 5);
    this.quit.setPosition(
        Window.getHalfWidth() - quit.getSize() / 2, Window.getHalfHeight() + MENU_POS + gap * 6);

    this.soundOn.setPosition(
        Window.getWidth() - soundOn.getSize() * soundOn.getScale(),
        Window.getHeight() - soundOn.getHeight() * soundOn.getScale());
    this.soundOff.setPosition(
        Window.getWidth() - soundOff.getSize(), Window.getHeight() - soundOff.getHeight());
  }

  /** Increases the size of the menu font */
  public void incFont() {
    singleplayer.setFontTexture(MEDIUM);
    multiplayer.setFontTexture(MEDIUM);
    levelEditor.setFontTexture(MEDIUM);
    highScore.setFontTexture(MEDIUM);
    options.setFontTexture(MEDIUM);
    quit.setFontTexture(MEDIUM);
  }

  /** Decreases the size of the menu font */
  public void decFont() {
    singleplayer.setFontTexture(SMALL);
    multiplayer.setFontTexture(SMALL);
    levelEditor.setFontTexture(SMALL);
    highScore.setFontTexture(SMALL);
    options.setFontTexture(SMALL);
    quit.setFontTexture(SMALL);
  }
}
