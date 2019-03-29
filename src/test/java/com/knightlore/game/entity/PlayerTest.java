package com.knightlore.game.entity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.knightlore.client.gui.engine.Colour;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.junit.Test;

public class PlayerTest {

  @Test
  public void getAssociatedSessionTest() {
    // arrange
    String expectedSessionID = "session";
    Player player = new Player(expectedSessionID, 0, Colour.GREEN);

    // assert
    assertThat(player.getAssociatedSession(), is(equalTo(expectedSessionID)));
  }

  @Test
  public void idleIsDefaultPlayerStateTest() {
    // arrange
    Player player = new Player("", 0, Colour.GREEN);

    // assert
    assertThat(player.getPlayerState(), is(equalTo(Player.START_PLAYER_STATE)));
  }

  @Test
  public void setPlayerStateTest() {
    // arrange
    Player player = new Player("", 0, Colour.GREEN);
    PlayerState expectedPlayerState = PlayerState.MOVING;

    // act
    player.setPlayerState(expectedPlayerState);

    // assert
    assertThat(player.getPlayerState(), is(equalTo(expectedPlayerState)));
  }

  @Test
  public void getColourTest() {
    // arrange
    Vector4f expectedColour = Colour.GREEN;
    Player player = new Player("", 0, expectedColour);

    // assert
    assertThat(player.getColour(), is(equalTo(expectedColour)));
  }

  @Test
  public void threeIsDefaultLivesTest() {
    // arrange
    Player player = new Player("", 0, Colour.GREEN);

    // assert
    assertThat(player.getLives(), is(equalTo(Player.START_LIVES)));
  }

  @Test
  public void zeroIsDefaultScoreTest() {
    // arrange
    Player player = new Player("", 0, Colour.GREEN);

    // assert
    assertThat(player.getScore(), is(equalTo(0)));
  }

  @Test
  public void addToScoreTest() {
    // arrange
    Player player = new Player("", 0, Colour.GREEN);
    int expectedScore = 1000;

    // act
    player.addToScore(expectedScore);

    // assert
    assertThat(player.getScore(), is(equalTo(expectedScore)));
  }

  @Test
  public void addToScoreTwiceTest() {
    // arrange
    Player player = new Player("", 0, Colour.GREEN);
    int expectedScore = 1000;

    // act
    player.addToScore(expectedScore / 2);
    player.addToScore(expectedScore / 2);

    // assert
    assertThat(player.getScore(), is(equalTo(expectedScore)));
  }

  @Test
  public void zeroIsDefaultRollCooldownTest() {
    // arrange
    Player player = new Player("", 0, Colour.GREEN);

    // assert
    assertThat(player.getCooldown(), is(equalTo(0)));
  }

  @Test
  public void setCooldownTest() {
    // arrange
    Player player = new Player("", 0, Colour.GREEN);
    int expectedCooldown = 20;

    // act
    player.setCooldown(expectedCooldown);

    // assert
    assertThat(player.getCooldown(), is(equalTo(expectedCooldown)));
  }

  @Test
  public void middleOfBottomTileIsDefaultPositionTest() {
    // arrange
    Player player = new Player("", 0, Colour.GREEN);

    // assert
    assertThat(player.getPosition(), is(equalTo(Player.START_POSITION)));
  }

  @Test
  public void loseLifeWhileOn3Test() {
    // arrange
    Player player = new Player("", 0, Colour.GREEN);
    player.setPosition(new Vector3f(20, 20, 20));
    player.setDirection(Direction.SOUTH_WEST);
    player.setPlayerState(PlayerState.MOVING);

    // act
    player.loseLife();

    // assert
    assertPlayerDefaults(player);
    assertThat(player.getLives(), is(equalTo(2)));
  }

  @Test
  public void loseLifeWhileOn1Test() {
    // arrange
    Player player = new Player("", 0, Colour.GREEN);
    player.loseLife();
    player.loseLife();

    // act
    player.loseLife();

    // assert
    assertThat(player.getPlayerState(), is(equalTo(PlayerState.DEAD)));
    assertThat(player.getLives(), is(equalTo(0)));
  }

  @Test
  public void resetPlayerToDefaultsTest() {
    // arrange
    Player player = new Player("", 0, Colour.GREEN);
    player.loseLife();
    player.setCooldown(20);
    player.setPlayerState(PlayerState.MOVING);
    player.setDirection(Direction.SOUTH_WEST);
    player.setPosition(new Vector3f(20, 20, 20));

    // act
    player.reset();

    // assert
    assertThat(player.getLives(), is(equalTo(3)));
    assertPlayerDefaults(player);
  }

  private void assertPlayerDefaults(Player player) {
    assertThat(player.getPosition(), is(equalTo(Player.START_POSITION)));
    assertThat(player.getDirection(), is(equalTo(Player.START_DIRECTION)));
    assertThat(player.getPlayerState(), is(equalTo(Player.START_PLAYER_STATE)));
    assertThat(player.getCooldown(), is(equalTo(Player.START_ROLL_COOLDOWN)));
  }
}
