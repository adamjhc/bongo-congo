package com.knightlore.game.map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import org.joml.Vector3f;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Enclosed.class)
public class TileTest {

  @Test
  public void canSetPosition() {
    // arrange
    Tile tile = new Tile(TileType.AIR);
    Vector3f expectedPosition = new Vector3f(1, 1, 1);

    // act
    tile.setPosition(expectedPosition);

    // assert
    assertThat(tile.getPosition(), is(equalTo(expectedPosition)));
  }

  @Test
  public void canSetType() {
    // arrange
    Tile tile = new Tile(TileType.AIR);
    TileType expectedType = TileType.FLOOR;

    // act
    tile.setType(expectedType);

    // assert
    assertThat(tile.getType(), is(equalTo(expectedType)));
  }

  @RunWith(Parameterized.class)
  public static class CanCreateTileTest {

    private TileType tileType;

    public CanCreateTileTest(TileType tileType) {
      this.tileType = tileType;
    }

    @Parameters
    public static Collection<Object> data() {
      return Arrays.asList(
          new Object[] {
            TileType.AIR,
            TileType.FLOOR,
            TileType.WALL,
            TileType.CLIMBABLE_WALL,
            TileType.HAZARD,
            TileType.GOAL,
            TileType.SPAWN_WALKER,
            TileType.SPAWN_RANDOMER,
            TileType.SPAWN_CIRCLER,
            TileType.SPAWN_CHARGER
          });
    }

    @Test
    public void canCreateTile() {
      Tile tile = new Tile(tileType);

      assertThat(tile.getType(), is(equalTo(tileType)));
    }

    @Test
    public void canGetIndex() {
      Tile tile = new Tile(tileType);

      assertThat(tile.getIndex(), is(equalTo(tileType.ordinal())));
    }
  }
}
