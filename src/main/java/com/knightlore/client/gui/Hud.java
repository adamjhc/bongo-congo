package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;
import com.knightlore.game.GameModel;
import com.knightlore.game.entity.Player;

import java.util.Map;

import org.joml.Vector4f;

public class Hud implements IGui {

  private static final int SCORE_SIDE_GAP = 4;
  private static final int LIVES_SIDE_GAP = 6;
  private static final int SCORE_HIDE =
      - SCORE_SIDE_GAP - FONT_SIZE_SMALL * 11 - SCORE_SIDE_GAP - FONT_SIZE_LIVES * 3;
  private static final int LIVES_HIDE = SCORE_HIDE + FONT_SIZE_SMALL * 11 + SCORE_SIDE_GAP;

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
  private final TextObject countDown;
  private GuiObject[] guiObjects;
  private TextObject[] textObjects;
  private TextObject[] scores;
  private TextObject[] lives;

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
    this.player1Lives.setScale((float)FONT_SIZE_SMALL / (float)FONT_SIZE_LIVES);

    this.player2Lives = new TextObject("***", LIVES);
    this.player2Lives.setColour(Colour.RED);
    this.player2Lives.setScale((float)FONT_SIZE_SMALL / (float)FONT_SIZE_LIVES);
    
    this.player3Lives = new TextObject("***", LIVES);
    this.player3Lives.setColour(Colour.RED);
    this.player3Lives.setScale((float)FONT_SIZE_SMALL / (float)FONT_SIZE_LIVES);
    
    this.player4Lives = new TextObject("***", LIVES);
    this.player4Lives.setColour(Colour.RED);
    this.player4Lives.setScale((float)FONT_SIZE_SMALL / (float)FONT_SIZE_LIVES);
    
    this.player5Lives = new TextObject("***", LIVES);
    this.player5Lives.setColour(Colour.RED);
    this.player5Lives.setScale((float)FONT_SIZE_SMALL / (float)FONT_SIZE_LIVES);
    
    this.player6Lives = new TextObject("***", LIVES);
    this.player6Lives.setColour(Colour.RED);
    this.player6Lives.setScale((float)FONT_SIZE_SMALL / (float)FONT_SIZE_LIVES);
    
    this.counter = new TextObject("90", LARGE);
    this.counter.setColour(Colour.YELLOW);
    this.counter.setScale(1.5f);
    
    this.countDown = new TextObject("5", LARGE);
    this.countDown.setColour(Colour.YELLOW);
    this.countDown.setScale(2.0f);
    
    this.player1Score.setPosition(SCORE_SIDE_GAP, SCORE_SIDE_GAP);
    this.player2Score.setPosition(SCORE_HIDE, SCORE_SIDE_GAP + GAP);
    this.player3Score.setPosition(SCORE_HIDE, SCORE_SIDE_GAP + GAP * 2);
    this.player4Score.setPosition(SCORE_HIDE, SCORE_SIDE_GAP + GAP * 3);
    this.player5Score.setPosition(SCORE_HIDE, SCORE_SIDE_GAP + GAP * 4);
    this.player6Score.setPosition(SCORE_HIDE, SCORE_SIDE_GAP + GAP * 5);

    this.player1Lives.setPosition(
        SCORE_SIDE_GAP + player1Score.getSize() + SCORE_SIDE_GAP, LIVES_SIDE_GAP);
    this.player2Lives.setPosition(LIVES_HIDE, LIVES_SIDE_GAP + GAP);
    this.player3Lives.setPosition(LIVES_HIDE, LIVES_SIDE_GAP + GAP * 2);
    this.player4Lives.setPosition(LIVES_HIDE, LIVES_SIDE_GAP + GAP * 3);
    this.player5Lives.setPosition(LIVES_HIDE, LIVES_SIDE_GAP + GAP * 4);
    this.player6Lives.setPosition(LIVES_HIDE, LIVES_SIDE_GAP + GAP * 5);

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
          countDown
        };
    textObjects = new TextObject[] {};
    
    scores =
    		new TextObject[] {
            player1Score,
            player2Score,
            player3Score,
            player4Score,
            player5Score,
            player6Score
    		};
    lives = 
    		new TextObject[] {
            player1Lives,
            player2Lives,
            player3Lives,
            player4Lives,
            player5Lives,
            player6Lives
    		};
  }

  public void moveScore(float move, float targetXPos) {
    float xPosScore = player2Score.getPositionX();
    float xPosLives = player2Lives.getPositionX();

    if (xPosScore < targetXPos && move > 0) {
      setPosition(move, xPosScore, xPosLives);
      if (player2Score.getPositionX() > targetXPos) {
        setPosition(0, targetXPos, targetXPos + player1Score.getSize() + SCORE_SIDE_GAP);
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

  public void setLives(int index, int lives) {
    if (lives <= 0) {
      this.lives[index].setText("");
    } else {
      String livesText = "";
      for (int i = 0; i < lives; i++) {
        livesText += "*";
      }
      this.lives[index].setText(livesText);
    }
  }

  public TextObject getCounter() {
    return counter;
  }

  public void setCounter(String statusText) {
    this.counter.setText(statusText);
  }
  
  public TextObject getCountDown() {
  	return countDown;
  }
  
  public void setCountDown(String statusText) {
  	this.countDown.setText(statusText);
  }

  public TextObject getScore(int index) {
    return this.scores[index];
  }

  public void setScore(int index, int score) {
    if (score > MAX_SCORE) {
      score = MAX_SCORE;
    }
    String text = String.format("%08d", score);
    this.scores[index].setText("P"+(index+1)+":" + text);
  }

  public int getScoreHide() {
    return SCORE_HIDE;
  }

  public int getScoreSideGap() {
    return SCORE_SIDE_GAP;
  }
  
  public void updateSize() {
    this.counter.setPosition(Window.getWidth() - counter.getSize()*counter.getScale(),
    		Window.getHeight() - counter.getHeight()*counter.getScale());
    this.countDown.setPosition(Window.getHalfWidth() - countDown.getSize()*countDown.getScale()/2,
    		Window.getHalfHeight() - countDown.getHeight()*countDown.getScale()/2+60);
  }
  
  public void renderScores(GameModel gameModel) {
  	Map<String, Player> players = gameModel.getPlayers();
  	int numPlayers = players.size();
  	
  	player2Score.setRender(numPlayers > 1 ? true : false);
  	player2Lives.setRender(numPlayers > 1 ? true : false);
  	player3Score.setRender(numPlayers > 2 ? true : false);
  	player3Lives.setRender(numPlayers > 2 ? true : false);
  	player4Score.setRender(numPlayers > 3 ? true : false);
  	player4Lives.setRender(numPlayers > 3 ? true : false);
  	player5Score.setRender(numPlayers > 4 ? true : false);
  	player5Lives.setRender(numPlayers > 4 ? true : false);
  	player6Score.setRender(numPlayers > 5 ? true : false);
  	player6Lives.setRender(numPlayers > 5 ? true : false);
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
