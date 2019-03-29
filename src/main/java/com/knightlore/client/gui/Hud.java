package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.Gui;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;
import com.knightlore.game.GameModel;
import com.knightlore.game.entity.Player;
import org.joml.Vector4f;

import java.util.Map;

/**
 * The Head-up display shown when the game is in play
 *
 * @author Joseph
 */
public class Hud extends Gui {

  /** Gap between the side of the screen and the score */
  private static final int SCORE_SIDE_GAP = 4;

  /** Gap between the side of the screen and the lives */
  private static final int LIVES_SIDE_GAP = 6;

  /** Position of the scores when hidden */
  private static final int SCORE_HIDE =
      -SCORE_SIDE_GAP - FONT_SIZE_SMALL * 10 - SCORE_SIDE_GAP - FONT_SIZE_LIVES * 3;

  /** Position of the lives when hidden */
  private static final int LIVES_HIDE = SCORE_HIDE + FONT_SIZE_SMALL * 10 + SCORE_SIDE_GAP;

  /** Maximum score */
  private static final int MAX_SCORE = 99999999;

  /** Player 1 score */
  private final TextObject player1Score;

  /** Player 1 lives */
  private final TextObject player1Lives;

  /** Player 2 score */
  private final TextObject player2Score;

  /** Player 2 lives */
  private final TextObject player2Lives;

  /** Player 3 score */
  private final TextObject player3Score;

  /** Player 3 lives */
  private final TextObject player3Lives;

  /** Player 4 score */
  private final TextObject player4Score;

  /** Player 4 lives */
  private final TextObject player4Lives;

  /** Player 5 score */
  private final TextObject player5Score;

  /** Player 5 lives */
  private final TextObject player5Lives;

  /** Player 6 score */
  private final TextObject player6Score;

  /** Player 6 lives */
  private final TextObject player6Lives;

  /** Counter to show time left in level */
  private final TextObject counter;

  /** Count-down at the start of each level */
  private final TextObject countDown;

  /** Current level */
  private final TextObject level;

  /** List of the scores */
  private TextObject[] scores;

  /** List of the lives */
  private TextObject[] lives;

  /** Create gui objects */
  public Hud() {
    this.player1Score = new TextObject("1:00000000", SMALL);
    this.player1Score.setColour();

    this.player2Score = new TextObject("2:00000000", SMALL);
    this.player2Score.setColour(Colour.YELLOW);

    this.player3Score = new TextObject("3:00000000", SMALL);
    this.player3Score.setColour(Colour.RED);

    this.player4Score = new TextObject("4:00000000", SMALL);
    this.player4Score.setColour(new Vector4f(0, 1, 1, 1));

    this.player5Score = new TextObject("5:00000000", SMALL);
    this.player5Score.setColour(new Vector4f(1, 0, 1, 1));

    this.player6Score = new TextObject("6:00000000", SMALL);
    this.player6Score.setColour(new Vector4f(0, 0, 1, 1));

    this.player1Lives = new TextObject("***", LIVES);
    this.player1Lives.setColour(Colour.RED);
    this.player1Lives.setScale((float) FONT_SIZE_SMALL / (float) FONT_SIZE_LIVES);

    this.player2Lives = new TextObject("***", LIVES);
    this.player2Lives.setColour(Colour.RED);
    this.player2Lives.setScale((float) FONT_SIZE_SMALL / (float) FONT_SIZE_LIVES);

    this.player3Lives = new TextObject("***", LIVES);
    this.player3Lives.setColour(Colour.RED);
    this.player3Lives.setScale((float) FONT_SIZE_SMALL / (float) FONT_SIZE_LIVES);

    this.player4Lives = new TextObject("***", LIVES);
    this.player4Lives.setColour(Colour.RED);
    this.player4Lives.setScale((float) FONT_SIZE_SMALL / (float) FONT_SIZE_LIVES);

    this.player5Lives = new TextObject("***", LIVES);
    this.player5Lives.setColour(Colour.RED);
    this.player5Lives.setScale((float) FONT_SIZE_SMALL / (float) FONT_SIZE_LIVES);

    this.player6Lives = new TextObject("***", LIVES);
    this.player6Lives.setColour(Colour.RED);
    this.player6Lives.setScale((float) FONT_SIZE_SMALL / (float) FONT_SIZE_LIVES);

    this.counter = new TextObject("90", LARGE);
    this.counter.setColour(Colour.YELLOW);
    this.counter.setScale(1.5f);

    this.countDown = new TextObject("5", LARGE);
    this.countDown.setColour(Colour.YELLOW);
    this.countDown.setScale(4.0f);

    this.level = new TextObject("L=1", SMALL);
    this.level.setColour(Colour.YELLOW);
    this.level.setScale(1f);

    this.countDown.setRender(false);

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
          countDown,
          level
        };
    textObjects = new TextObject[] {};

    scores =
        new TextObject[] {
          player1Score, player2Score, player3Score, player4Score, player5Score, player6Score
        };
    lives =
        new TextObject[] {
          player1Lives, player2Lives, player3Lives, player4Lives, player5Lives, player6Lives
        };
  }

  /**
   * Moves the hidden scores for other players onto or off the screen
   *
   * @param move The number of pixels to move
   * @param targetXPos The target position to reach
   */
  public void moveScore(float move, float targetXPos) {
    float xPosScore = player2Score.getPositionX();
    float xPosLives = player2Lives.getPositionX();

    if (xPosScore < targetXPos && move > 0) {
      setPosition(move, xPosScore, xPosLives);
      if (player2Score.getPositionX() > targetXPos) {
        setPosition(0, targetXPos, targetXPos + player2Score.getSize() + SCORE_SIDE_GAP);
      }
    } else if (xPosScore > targetXPos && move < 0) {
      setPosition(move, xPosScore, xPosLives);
    }
  }

  /**
   * Sets the position for the lives and scores
   *
   * @param move The number of pixels to move
   * @param xPosScore The x position for scores
   * @param xPosLives The x position for lives
   */
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

  /**
   * Sets the lives for a given player
   *
   * @param index The player index
   * @param lives The number of lives for that player
   */
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

  /**
   * Sets the current level
   *
   * @param levelIndex The current level index
   */
  public void setLevel(int levelIndex) {
    level.setText("L=" + (levelIndex + 1));
  }

  /**
   * Returns counter
   *
   * @return Counter
   */
  public TextObject getCounter() {
    return counter;
  }

  /**
   * Sets the counter to the statusText
   *
   * @param statusText The counter text
   */
  public void setCounter(String statusText) {
    this.counter.setText(statusText);
  }

  /**
   * Returns countDown
   *
   * @return countDown
   */
  public TextObject getCountDown() {
    return countDown;
  }

  /**
   * Sets the countdown to the statusText
   *
   * @param statusText The countdown text
   */
  public void setCountDown(String statusText) {
    this.countDown.setText(statusText);
  }

  /**
   * Gets the score for a player using their index
   *
   * @param index The player index
   * @return The score for the indexed player
   */
  public TextObject getScore(int index) {
    return this.scores[index];
  }

  /**
   * Sets the score for a given player
   *
   * @param index The player index
   * @param score The new score
   * @param name The player name
   */
  public void setScore(int index, int score, String name) {
    if (score > MAX_SCORE) {
      score = MAX_SCORE;
    }
    String text = String.format("%08d", score);
    this.scores[index].setText(name + ":" + text);
  }

  /**
   * Returns the position to hide scores in
   *
   * @return SCORE_HIDE
   */
  public int getScoreHide() {
    return SCORE_HIDE;
  }

  /**
   * Returns the gap for scores
   *
   * @return SCORE_SIDE_GAP
   */
  public int getScoreSideGap() {
    return SCORE_SIDE_GAP;
  }

  /** Updates the position of the gui objects */
  public void updateSize() {
    this.counter.setPosition(
        Window.getWidth() - counter.getSize() * counter.getScale(),
        Window.getHeight() - counter.getHeight() * counter.getScale());
    this.countDown.setPosition(
        Window.getHalfWidth() - countDown.getSize() * countDown.getScale() / 2,
        Window.getHeight() - countDown.getHeight() * countDown.getScale());
    this.level.setPosition(
        Window.getWidth()
            - counter.getSize() * counter.getScale()
            - level.getSize() * level.getScale(),
        Window.getHeight() - level.getHeight() * level.getScale());
  }

  /**
   * Sets which scores need to be rendered
   *
   * @param gameModel The game model containing players
   */
  public void renderScores(GameModel gameModel) {
    Map<String, Player> players = gameModel.getPlayers();
    int numPlayers = players.size();

    player2Score.setRender(numPlayers > 1);
    player2Lives.setRender(numPlayers > 1);
    player3Score.setRender(numPlayers > 2);
    player3Lives.setRender(numPlayers > 2);
    player4Score.setRender(numPlayers > 3);
    player4Lives.setRender(numPlayers > 3);
    player5Score.setRender(numPlayers > 4);
    player5Lives.setRender(numPlayers > 4);
    player6Score.setRender(numPlayers > 5);
    player6Lives.setRender(numPlayers > 5);
  }
}
