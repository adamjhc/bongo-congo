package com.knightlore.game.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Enclosed.class)
public class CoordinateUtilsTest {

  @RunWith(Parameterized.class)
  public static class ToIsometricTest {

    private Vector3f expectedIsometric;
    private Vector3f cartesian;

    public ToIsometricTest(Vector3f expectedIsometric, Vector3f cartesian) {
      this.expectedIsometric = expectedIsometric;
      this.cartesian = cartesian;
    }

    @Parameters(name = "{index}: {0} == toIso({1})")
    public static Collection<Object[]> data() {
      return Arrays.asList(
          new Object[][] {
            {new Vector3f(), new Vector3f()},
            {new Vector3f(0, 1, 0), new Vector3f(1, 1, 0)},
            {new Vector3f(0, 2, 0), new Vector3f(2, 2, 0)},
            {new Vector3f(-1, 0.5f, 0), new Vector3f(0, 1, 0)},
            {new Vector3f(1, 0.5f, 0), new Vector3f(1, 0, 0)},
            {new Vector3f(-1f, 1.5f, 0), new Vector3f(1, 2, 0)},
            {new Vector3f(0, 1, 0), new Vector3f(0, 0, 1)},
            {new Vector3f(0, 2, 0), new Vector3f(1, 1, 1)},
            {new Vector3f(0, 3, 0), new Vector3f(2, 2, 1)},
            {new Vector3f(-1, 1.5f, 0), new Vector3f(0, 1, 1)},
            {new Vector3f(1, 1.5f, 0), new Vector3f(1, 0, 1)},
            {new Vector3f(-1f, 2.5f, 0), new Vector3f(1, 2, 1)},
          });
    }

    @Test
    public void toIsometricVectorTest() {
      Vector3f actualIsometric = CoordinateUtils.toIsometric(cartesian);

      assertThat(actualIsometric, is(equalTo(expectedIsometric)));
    }

    @Test
    public void toIsometricFloatTest() {
      Vector3f actualIsometric = CoordinateUtils.toIsometric(cartesian.x, cartesian.y, cartesian.z);

      assertThat(actualIsometric, is(equalTo(expectedIsometric)));
    }
  }

  @RunWith(Parameterized.class)
  public static class ToCartesianTest {
    private Vector3f expectedCartesian;
    private Vector3f isometric;

    public ToCartesianTest(Vector3f expectedCartesian, Vector3f isometric) {
      this.expectedCartesian = expectedCartesian;
      this.isometric = isometric;
    }

    @Parameters(name = "{index}: {0} == toCart({1})")
    public static Collection<Object[]> data() {
      return Arrays.asList(
          new Object[][] {
            {new Vector3f(), new Vector3f()},
            {new Vector3f(1, 1, 0), new Vector3f(0, 1, 0)},
            {new Vector3f(2, 2, 0), new Vector3f(0, 2, 0)},
            {new Vector3f(0, 1, 0), new Vector3f(-1, 0.5f, 0)},
            {new Vector3f(1, 0, 0), new Vector3f(1, 0.5f, 0)},
            {new Vector3f(1, 2, 0), new Vector3f(-1f, 1.5f, 0)},
          });
    }

    @Test
    public void toCartesianTest() {
      Vector3f actualCartesian = CoordinateUtils.toCartesian(isometric.x, isometric.y);

      assertThat(actualCartesian, is(equalTo(expectedCartesian)));
    }
  }

  @RunWith(Parameterized.class)
  public static class MapHasPositionTest {

    private boolean expected;
    private Vector3i mapSize;
    private Vector3i position;

    public MapHasPositionTest(boolean expected, Vector3i mapSize, Vector3i position) {
      this.expected = expected;
      this.mapSize = mapSize;
      this.position = position;
    }

    @Parameters
    public static Collection<Object[]> data() {
      return Arrays.asList(
          new Object[][] {
            {true, new Vector3i(1, 1, 1), new Vector3i()},
            {true, new Vector3i(1, 1, 2), new Vector3i(0, 0, 1)},
            {true, new Vector3i(1, 2, 1), new Vector3i(0, 1, 0)},
            {true, new Vector3i(1, 2, 2), new Vector3i(0, 1, 1)},
            {true, new Vector3i(2, 1, 1), new Vector3i(1, 0, 0)},
            {true, new Vector3i(2, 1, 2), new Vector3i(1, 0, 1)},
            {true, new Vector3i(2, 2, 1), new Vector3i(1, 1, 0)},
            {true, new Vector3i(2, 2, 2), new Vector3i(1, 1, 1)},
            {false, new Vector3i(), new Vector3i()},
            {false, new Vector3i(1, 1, 1), new Vector3i(0, 0, 1)},
            {false, new Vector3i(1, 1, 1), new Vector3i(0, 1, 0)},
            {false, new Vector3i(1, 1, 1), new Vector3i(0, 1, 1)},
            {false, new Vector3i(1, 1, 1), new Vector3i(1, 0, 0)},
            {false, new Vector3i(1, 1, 1), new Vector3i(1, 0, 1)},
            {false, new Vector3i(1, 1, 1), new Vector3i(1, 1, 0)},
            {false, new Vector3i(1, 1, 1), new Vector3i(1, 1, 1)},
          });
    }

    @Test
    public void mapHasPositionTest() {
      boolean actual = CoordinateUtils.mapHasPosition(mapSize, position);

      assertThat(actual, is(equalTo(expected)));
    }
  }

  @RunWith(Parameterized.class)
  public static class GetTileCoordTest {

    private Vector3i expectedTileCoord;
    private Vector3f worldCoords;

    public GetTileCoordTest(Vector3i expectedTileCoord, Vector3f worldCoords) {
      this.expectedTileCoord = expectedTileCoord;
      this.worldCoords = worldCoords;
    }

    @Parameters
    public static Collection<Object[]> data() {
      return Arrays.asList(
          new Object[][] {
            {new Vector3i(), new Vector3f()},
          });
    }

    @Test
    public void getTileCoordTest() {
      Vector3i actualTileCoord = CoordinateUtils.getTileCoord(worldCoords);

      assertThat(actualTileCoord, is(equalTo(expectedTileCoord)));
    }
  }
}
