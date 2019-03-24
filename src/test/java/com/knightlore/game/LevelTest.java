package com.knightlore.game;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import com.knightlore.game.map.LevelMap;
import org.junit.Test;

public class LevelTest {

  @Test
  public void canCreateLevel() {
    Level level = new Level(new LevelMap(new int[][][] {{{1}}}));

    assertThat(level, is(notNullValue()));
  }
}
