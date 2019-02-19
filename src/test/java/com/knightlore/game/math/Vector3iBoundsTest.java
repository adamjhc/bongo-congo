package com.knightlore.game.math;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class Vector3iBoundsTest {

  @Parameters(name = "{index}: {0} {1}")
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {
        {new Vector3i(3, 3, 3), true},
        {new Vector3i(10, 10, 10), true},
        {new Vector3i(10, 3, 3), true},
        {new Vector3i(3, 10, 3), true},
        {new Vector3i(3, 3, 10), true},
        {new Vector3i(11, 11, 11), false},
        {new Vector3i(11, 3, 3), false},
        {new Vector3i(3, 11, 3), false},
        {new Vector3i(3, 3, 11), false},
        {new Vector3i(0, 0, 0), true},
        {new Vector3i(0, 3, 3), true},
        {new Vector3i(3, 0, 3), true},
        {new Vector3i(3, 3, 0), true},
        {new Vector3i(-1, -1, -1), false},
        {new Vector3i(-1, 3, 3), false},
        {new Vector3i(3, -1, 3), false},
        {new Vector3i(3, 3, -1), false},
    });
  }

  private Vector3i map = new Vector3i(10, 10, 10);
  private Vector3i position;
  private boolean expected;

  public Vector3iBoundsTest(Vector3i position, boolean expected) {
    super();
    this.position = position;
    this.expected = expected;
  }

  @Test
  public void test() {
    boolean result = map.hasPosition(position);

    Assert.assertEquals(expected, result);
  }
}
