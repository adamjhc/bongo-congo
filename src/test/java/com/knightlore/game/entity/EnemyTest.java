package com.knightlore.game.entity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Enclosed.class)
public class EnemyTest {

  @Test
  public void idleStateIsDefaultTest() {
    Enemy enemy = new Enemy(EnemyType.CHARGER);

    assertThat(enemy.getCurrentState(), is(equalTo(EnemyState.IDLE)));
  }

  @Test
  public void canSetCurrentState() {
    // arrange
    Enemy enemy = new Enemy(EnemyType.CHARGER);
    EnemyState expectedState = EnemyState.MOVING;

    // act
    enemy.setCurrentState(expectedState);

    // assert
    assertThat(enemy.getCurrentState(), is(equalTo(expectedState)));
  }

  @RunWith(Parameterized.class)
  public static class EnemyTypeTest {

    private EnemyType enemyType;

    public EnemyTypeTest(EnemyType enemyType) {
      this.enemyType = enemyType;
    }

    @Parameters(name = "{index}: {0}")
    public static Collection<Object> data() {
      return Arrays.asList((Object[]) EnemyType.values());
    }

    @Test
    public void canCreateEnemyTest() {
      Enemy enemy = new Enemy(enemyType);

      assertThat(enemy.getEnemyType(), is(equalTo(enemyType)));
    }
  }
}
