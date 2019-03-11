package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;
import org.joml.Vector4f;

public class Hud implements IGui {

  private static final int SCORE_SIDE_GAP = 5;
  private static final int LIVES_SIDE_GAP = 1;
  private static final int SCORE_HIDE =
      -SCORE_SIDE_GAP - FONT_SIZE * 11 - SCORE_SIDE_GAP * 2 - FONT_SIZE_LIVES * 3;
  private static final int LIVES_HIDE = SCORE_HIDE + FONT_SIZE * 11 + SCORE_SIDE_GAP * 2;

  private static final int MAX_SCORE = 99999999;

  private final TextObject player1Score;
  private final TextObject player1Lives;
  private final TextObject player2Score;
  private final TextObject player2Lives;
  private final TextObject player3Score;
  private final TextObject player3Lives;
  private final TextObject player4Score;
  private final TextObject player4Lives;
  private final TextObject player5Score;
  private final TextObject player5Lives;
  private final TextObject player6Score;
  private final TextObject player6Lives;
  private final TextObject counter;
  private final TextObject soundOn;
  private final TextObject soundOff;
  private GuiObject[] guiObjects;
  private TextObject[] textObjects;

  public Hud() {
    this.player1Score = new TextObject("P1:00000000", SMALL);
    this.player1Score.setColour();

    this.player2Score = new TextObject("P2:00000000", SMALL);
    this.player2Score.setColour(Colour.YELLOW);

    this.player3Score = new TextObject("P3:00000000", SMALL);
    this.player3Score.setColour(Colour.RED);

    this.player4Score = new TextObject("P4:00000000", SMALL);
    this.player4Score.setColour(new Vector4f(0, 1, 1, 1));

    this.player5Score = new TextObject("P5:00000000", SMALL);
    this.player5Score.setColour(new Vector4f(1, 0, 1, 1));

    this.player6Score = new TextObject("P6:00000000", SMALL);
    this.player6Score.setColour(new Vector4f(0, 0, 1, 1));

    this.player1Lives = new TextObject("***", LIVES);
    this.player1Lives.setColour(Colour.RED);

    this.player2Lives = new TextObject("***", LIVES);
    this.player2Lives.setColour(Colour.RED);

    this.player3Lives = new TextObject("***", LIVES);
    this.player3Lives.setColour(Colour.RED);

    this.player4Lives = new TextObject("***", LIVES);
    this.player4Lives.setColour(Colour.RED);

    this.player5Lives = new TextObject("***", LIVES);
    this.player5Lives.setColour(Colour.RED);

    this.player6Lives = new TextObject("***", LIVES);
    this.player6Lives.setColour(Colour.RED);

    this.counter = new TextObject("90", MEDIUM);
    this.counter.setColour(Colour.YELLOW);

    this.soundOn = new TextObject("(", LARGE);
    this.soundOn.setColour();

    this.soundOff = new TextObject("/", MEDIUM);
    this.soundOff.setColour(Colour.RED);

    this.player1Score.setPosition(SCORE_SIDE_GAP, SCORE_SIDE_GAP);
    this.player2Score.setPosition(SCORE_HIDE, SCORE_SIDE_GAP + GAP);
    this.player3Score.setPosition(SCORE_HIDE, SCORE_SIDE_GAP + GAP * 2);
    this.player4Score.setPosition(SCORE_HIDE, SCORE_SIDE_GAP + GAP * 3);
    this.player5Score.setPosition(SCORE_HIDE, SCORE_SIDE_GAP + GAP * 4);
    this.player6Score.setPosition(SCORE_HIDE, SCORE_SIDE_GAP + GAP * 5);

    this.player1Lives.setPosition(
        SCORE_SIDE_GAP + player1Score.getSize() + SCORE_SIDE_GAP * 2, LIVES_SIDE_GAP);
    this.player2Lives.setPosition(LIVES_HIDE, LIVES_SIDE_GAP + GAP);
    this.player3Lives.setPosition(LIVES_HIDE, LIVES_SIDE_GAP + GAP * 2);
    this.player4Lives.setPosition(LIVES_HIDE, LIVES_SIDE_GAP + GAP * 3);
    this.player5Lives.setPosition(LIVES_HIDE, LIVES_SIDE_GAP + GAP * 4);
    this.player6Lives.setPosition(LIVES_HIDE, LIVES_SIDE_GAP + GAP * 5);

    this.counter.setPosition(1175, Window.getHeight() - counter.getHeight());
    this.soundOn.setPosition(
        Window.getWidth() - soundOn.getSize(), Window.getHeight() - soundOn.getHeight());
    this.soundOff.setPosition(
        Window.getWidth() - soundOff.getSize(), Window.getHeight() - soundOff.getHeight());

    this.soundOff.setRender();

    guiObjects =
        new GuiObject[] {
          player1Score,
          player2Score,
          player3Score,
          player4Score,
          player5Score,
          player6Score,
          player1Lives,
          player2Lives,
          player3Lives,
          player4Lives,
          player5Lives,
          player6Lives,
          counter,
          soundOn,
          soundOff
        };
    textObjects = new TextObject[] {};
  }

  public void moveScore(float move, float targetXPos) {
    float xPosScore = player2Score.getPositionX();
    float xPosLives = player2Lives.getPositionX();

    if (xPosScore < targetXPos && move > 0) {
      setPosition(move, xPosScore, xPosLives);
      if (player2Score.getPositionX() > targetXPos) {
        setPosition(0, targetXPos, targetXPos + player1Score.getSize() + SCORE_SIDE_GAP * 2);
      }
    } else if (xPosScore > targetXPos && move < 0) {
      setPosition(move, xPosScore, xPosLives);
    }
  }

  public void setPosition(float move, float xPosScore, float xPosLives) {
    player2Score.setPositionX(xPosScore + move);
    player3Score.setPositionX(xPosScore + move);
    player4Score.setPositionX(xPosScore + move);
    player5Score.setPositionX(xPosScore + move);
    player6Score.setPositionX(xPosScore + move);
    player2Lives.setPositionX(xPosLives + move);
    player3Lives.setPositionX(xPosLives + move);
    player4Lives.setPositionX(xPosLives + move);
    player5Lives.setPositionX(xPosLives + move);
    player6Lives.setPositionX(xPosLives + move);
  }

  public void setP1Lives(int lives) {
    if (lives <= 0) {
      this.player1Lives.setText("Dead");
    } else {
      String livesText = "";
      for (int i = 0; i < lives; i++) {
        livesText += "*";
      }
      this.player1Lives.setText(livesText);
    }
    this.player1Lives.setColour(Colour.RED);
  }

  public TextObject getCounter() {
    return counter;
  }

  public void setCounter(String statusText) {
    this.counter.setText(statusText);
    this.counter.setColour(Colour.YELLOW);
  }

  public TextObject getP1Score() {
    return player1Score;
  }

  public void setP1Score(int score) {
    if (score > MAX_SCORE) {
      score = MAX_SCORE;
    }
    String text = String.format("%08d", score);
    this.player1Score.setText("P1:" + text);
  }

  public TextObject getSound() {
    return soundOn;
  }

  public TextObject getSoundMute() {
    return soundOff;
  }

  public int getScoreHide() {
    return SCORE_HIDE;
  }

  public int getScoreSideGap() {
    return SCORE_SIDE_GAP;
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
