package com.knightlore.game.util;

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
public class CoordinateUtilsTest {

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
    public void toIsometricTest() {
      Vector3f actualIsometric = CoordinateUtils.toIsometric(cartesian);

      assertThat(actualIsometric, is(equalTo(expectedIsometric)));
    }
  }
}
