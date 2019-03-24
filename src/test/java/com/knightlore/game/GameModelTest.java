package com.knightlore.game;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class GameModelTest {

  @Test
  public void canCreateGameModel() {
    GameModel gameModel = new GameModel("");

    assertThat(gameModel, instanceOf(GameModel.class));
  }

  @Test
  public void canSetState() {
    // arrange
    GameModel gameModel = new GameModel("");
    GameState expectedState = GameState.PLAYING;

    // act
    gameModel.setState(expectedState);

    // assert
    assertThat(gameModel.getState(), is(equalTo(expectedState)));
  }
}
