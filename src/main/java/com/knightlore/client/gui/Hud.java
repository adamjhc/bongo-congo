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

public class Hud implements IGui {

  private static final int MAX_SCORE = 99999999;
  private static final int SCORE_HIDE = -230;
  private static final int LIVES_HIDE = -60;
  private static final int SIDE_GAP = 5;
  private static final int SCORE_POS = 25;
  private static final int LIVES_POS = 21;

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
    try (InputStream myStream =
        new BufferedInputStream(
            new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"))) {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    } catch (IOException | FontFormatException e) {
      e.printStackTrace();
    }

    FontTexture fontSmall = new FontTexture(FONT_SMALL, CHARSET);
    FontTexture fontMedium = new FontTexture(FONT_MEDIUM, CHARSET);
    FontTexture fontLarge = new FontTexture(FONT_LARGE, CHARSET);
    FontTexture fontLives = new FontTexture(FONT_LIVES, CHARSET);

    this.player1Score = new TextObject("P1:00000000", fontSmall);
    this.player1Score.setColour();

    this.player2Score = new TextObject("P2:00000000", fontSmall);
    this.player2Score.setColour(YELLOW);

    this.player3Score = new TextObject("P3:00000000", fontSmall);
    this.player3Score.setColour(RED);

    this.player4Score = new TextObject("P4:00000000", fontSmall);
    this.player4Score.setColour(new Vector4f(0, 1, 1, 1));

    this.player5Score = new TextObject("P5:00000000", fontSmall);
    this.player5Score.setColour(new Vector4f(1, 0, 1, 1));

    this.player6Score = new TextObject("P6:00000000", fontSmall);
    this.player6Score.setColour(new Vector4f(0, 0, 1, 1));

    this.player1Lives = new TextObject("***", fontLives);
    this.player1Lives.setColour(RED);

    this.player2Lives = new TextObject("***", fontLives);
    this.player2Lives.setColour(RED);

    this.player3Lives = new TextObject("***", fontLives);
    this.player3Lives.setColour(RED);

    this.player4Lives = new TextObject("***", fontLives);
    this.player4Lives.setColour(RED);

    this.player5Lives = new TextObject("***", fontLives);
    this.player5Lives.setColour(RED);

    this.player6Lives = new TextObject("***", fontLives);
    this.player6Lives.setColour(RED);

    this.counter = new TextObject("90", fontMedium);
    this.counter.setColour(YELLOW);

    this.soundOn = new TextObject("(", fontLarge);
    this.soundOn.setColour();

    this.soundOff = new TextObject("/", fontMedium);
    this.soundOff.setColour(RED);

    this.player1Score.setPosition(SIDE_GAP, SIDE_GAP);
    this.player2Score.setPosition(SCORE_HIDE, SCORE_POS);
    this.player3Score.setPosition(SCORE_HIDE, SCORE_POS + GAP);
    this.player4Score.setPosition(SCORE_HIDE, SCORE_POS + GAP * 2);
    this.player5Score.setPosition(SCORE_HIDE, SCORE_POS + GAP * 3);
    this.player6Score.setPosition(SCORE_HIDE, SCORE_POS + GAP * 4);

    this.player1Lives.setPosition(player1Score.getSize() + SIDE_GAP * 2, 1);
    this.player2Lives.setPosition(LIVES_HIDE, LIVES_POS);
    this.player3Lives.setPosition(LIVES_HIDE, LIVES_POS + GAP);
    this.player4Lives.setPosition(LIVES_HIDE, LIVES_POS + GAP * 2);
    this.player5Lives.setPosition(LIVES_HIDE, LIVES_POS + GAP * 3);
    this.player6Lives.setPosition(LIVES_HIDE, LIVES_POS + GAP * 4);

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
        setPosition(0, targetXPos, targetXPos + 170);
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
    this.player1Lives.setColour(RED);
  }

  public TextObject getCounter() {
    return counter;
  }

  public void setCounter(String statusText) {
    this.counter.setText(statusText);
    this.counter.getMesh().getMaterial().setColour(YELLOW);
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

  @Override
  public TextObject[] getTextObjects() {
    return textObjects;
  }

  @Override
  public GuiObject[] getGuiObjects() {
    return guiObjects;
  }
}
