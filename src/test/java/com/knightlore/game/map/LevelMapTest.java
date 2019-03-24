package com.knightlore.game.map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.joml.Vector3f;
import org.joml.Vector3i;
import org.junit.Before;
import org.junit.Test;

public class LevelMapTest {

  private TileSet tileSet;

  @Before
  public void setup() {
    tileSet = new TileSet();
  }

  @Test
  public void getTilesTest() {
    // arrange
    Tile tile = tileSet.getTile(1, new Vector3f());
    Tile[][][] expectedTiles = new Tile[][][] {{{tile}}};
    LevelMap levelMap = new LevelMap(new int[][][] {{{tile.getIndex()}}});

    // assert
    assertThat(levelMap.getTiles(), is(equalTo(expectedTiles)));
  }

  @Test
  public void getValidTileTest() {
    // arrange
    Tile expectedTile = new Tile(TileType.FLOOR);
    expectedTile.setPosition(new Vector3f());
    LevelMap levelMap = new LevelMap(new int[][][] {{{expectedTile.getIndex()}}});

    // assert
    assertThat(levelMap.getTile(new Vector3i()), is(equalTo(expectedTile)));
  }

  @Test
  public void getInvalidTileTest() {
    // arrange
    Tile expectedTile = new Tile(TileType.FLOOR);
    LevelMap levelMap = new LevelMap(new int[][][] {{{expectedTile.getIndex()}}});

    // assert
    assertThat(levelMap.getTile(new Vector3i(1, 1, 1)), is(nullValue()));
  }

  @Test
  public void getSizeTest() {
    // arrange
    Tile expectedTile = new Tile(TileType.FLOOR);
    LevelMap levelMap = new LevelMap(new int[][][] {{{expectedTile.getIndex()}}});

    // assert
    assertThat(levelMap.getSize(), is(equalTo(new Vector3i(1, 1, 1))));
  }
}
