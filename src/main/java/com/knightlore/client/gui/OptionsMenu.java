package com.knightlore.client.gui;

import com.knightlore.client.audio.Audio;
import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.Gui;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

/**
 * Screen that allows you to change various options in the game
 *
 * @author Joseph
 */
public class OptionsMenu extends Gui {

  /** Position of the top separator line */
  private static final int SEPARATOR_TOP_POS = 185;

  /** Position of the bottom separator line */
  private static final int SEPARATOR_BOT_POS = 200;

  /** Gap between each line of text */
  private static final int SEPARATOR_GAP = FONT_SIZE_SMALL;

  /** Maximum volume value */
  private static final int MAX_VOLUME = 100;

  /** Increase volume text */
  private final TextObject incVolume;

  /** Decrease volume text */
  private final TextObject decVolume;

  /** Current volume text */
  private final TextObject volume;

  /** Exit screen text */
  private final TextObject exit;

  /** Is the game fullscreen */
  private final TextObject isFullscreen;

  /** Bongo text */
  private final TextObject bongo;

  /** Congo text */
  private final TextObject congo;

  /** Options title text */
  private final TextObject options;

  /** Top separator line text */
  private final TextObject separatorTop;

  /** Bottom separator line text */
  private final TextObject separatorBot;

  /** Music volume title text */
  private final TextObject musicVolume;

  /** Fullscreen title text */
  private final TextObject fullScreen;

  /** Mute audio title text */
  private final TextObject mute;

  /** Is the audio muted */
  private final TextObject isMute;

  /**
   * Create gui objects
   */
  public OptionsMenu() {
    this.bongo = new TextObject("Bongo", TITLE);
    this.bongo.setColour(Colour.LIGHT_BLUE);

    this.congo = new TextObject("Congo", TITLE);
    this.congo.setColour(Colour.RED);

    this.incVolume = new TextObject(">", LARGE);
    this.incVolume.setColour(Colour.YELLOW);

    this.decVolume = new TextObject("<", LARGE);
    this.decVolume.setColour(Colour.YELLOW);

    this.volume = new TextObject("070", LARGE);
    this.volume.setColour(Colour.YELLOW);

    this.exit = new TextObject("Exit", SMALL);
    this.exit.setColour(Colour.YELLOW);

    this.isFullscreen = new TextObject("Off", LARGE);
    this.isFullscreen.setColour(Colour.YELLOW);

    this.options = new TextObject("Options", SMALL);
    this.options.setColour(Colour.YELLOW);

    this.separatorTop = new TextObject("------------------------------", SMALL);
    this.separatorTop.setColour(Colour.YELLOW);

    this.separatorBot = new TextObject("------------------------------", SMALL);
    this.separatorBot.setColour(Colour.YELLOW);

    this.musicVolume = new TextObject("Game music volume", SMALL);
    this.musicVolume.setColour(Colour.YELLOW);

    this.fullScreen = new TextObject("Fullscreen", SMALL);
    this.fullScreen.setColour(Colour.YELLOW);

    this.mute = new TextObject("Mute audio", SMALL);
    this.mute.setColour(Colour.YELLOW);

    this.isMute = new TextObject("Yes", LARGE);
    this.isMute.setColour(Colour.YELLOW);

    guiObjects =
        new GuiObject[] {
          bongo,
          congo,
          incVolume,
          decVolume,
          volume,
          exit,
          isFullscreen,
          options,
          separatorTop,
          separatorBot,
          musicVolume,
          fullScreen,
          mute,
          isMute
        };
    textObjects = new TextObject[] {incVolume, decVolume, exit, isFullscreen, isMute};
  }

  /**
   * Increase the volume
   */
  public void incVolume() {
    int volumeAmount = Integer.parseInt(this.volume.getText());
    if (volumeAmount < MAX_VOLUME) volumeAmount++;
    this.volume.setText((String.format("%03d", volumeAmount)));
    this.volume.setColour(Colour.YELLOW);
  }

  /**
   * Decrease the volume
   */
  public void decVolume() {
    int volumeAmount = Integer.parseInt(this.volume.getText());
    if (volumeAmount > 0) volumeAmount--;
    this.volume.setText((String.format("%03d", volumeAmount)));
    this.volume.setColour(Colour.YELLOW);
  }

  /**
   * Set the game to be fullscreen
   */
  public void setFullscreen() {
    if (this.isFullscreen.getText().equals("Off")) this.isFullscreen.setText("On");
    else this.isFullscreen.setText("Off");
    this.isFullscreen.setPositionX(Window.getHalfWidth() - isFullscreen.getSize() / 2);

    Window.setFullscreen();
  }

  /**
   * Set the audio to be muted
   */
  public void setMute() {
    if (Audio.isOn()) this.isMute.setText("Off");
    else this.isMute.setText("On");
    this.isMute.setPositionY(Window.getHalfWidth() - isMute.getSize() / 2);
  }

  /**
   * Return volume increase
   *
   * @return IncVolume
   */
  public TextObject getIncVolume() {
    return incVolume;
  }

  /**
   * Return volume decrease
   *
   * @return DecVolume
   */
  public TextObject getDecVolume() {
    return decVolume;
  }

  /**
   * Return exit
   *
   * @return Exit
   */
  public TextObject getExit() {
    return exit;
  }

  /**
   * Return is fullscreen enabled
   *
   * @return IsFullscreen
   */
  public TextObject getIsFullscreen() {
    return isFullscreen;
  }

  /**
   * Return is audio muted
   *
   * @return IsMute
   */
  public TextObject getIsMute() {
    return isMute;
  }

  /**
   * Updates the position of the gui objectss
   */
  public void updateSize() {
    this.bongo.setPosition(
        Window.getHalfWidth() - bongo.getSize(), Window.getHalfHeight() - TITLE_POS);
    this.congo.setPosition(Window.getHalfWidth(), Window.getHalfHeight() - TITLE_POS);
    this.volume.setPosition(
        Window.getHalfWidth() - volume.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 2);
    this.incVolume.setPosition(
        Window.getHalfWidth() - incVolume.getSize() / 2 + 100,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 2);
    this.decVolume.setPosition(
        Window.getHalfWidth() - decVolume.getSize() / 2 - 100,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 2);
    this.exit.setPosition(
        Window.getHalfWidth() - exit.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP);
    this.isFullscreen.setPosition(
        Window.getHalfWidth() - isFullscreen.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 4 + GAP_LARGE);
    this.options.setPosition(
        Window.getHalfWidth() - options.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS - SEPARATOR_GAP);
    this.separatorTop.setPosition(
        Window.getHalfWidth() - separatorTop.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS);
    this.separatorBot.setPosition(
        Window.getHalfWidth() - separatorBot.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS);
    this.musicVolume.setPosition(
        Window.getHalfWidth() - musicVolume.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP);
    this.fullScreen.setPosition(
        Window.getHalfWidth() - fullScreen.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 3 + GAP_LARGE);
    this.mute.setPosition(
        Window.getHalfWidth() - mute.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 5 + GAP_LARGE * 2);
    this.isMute.setPosition(
        Window.getHalfWidth() - isMute.getSize() / 2,
        Window.getHalfHeight() - SEPARATOR_TOP_POS + GAP * 6 + GAP_LARGE * 2);
  }
}
