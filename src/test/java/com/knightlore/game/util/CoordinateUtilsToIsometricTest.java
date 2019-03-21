package com.knightlore.game.util;

import java.util.Arrays;
import java.util.Collection;
import org.joml.Vector3f;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CoordinateUtilsToIsometricTest {

  @Parameters(name = "{index}: {0} == toIso({1})")
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {
        { new Vector3f(), new Vector3f() },
        { new Vector3f(0, 1, 0), new Vector3f(1, 1, 0)},
    });
  }

  private Vector3f isometric;
  private Vector3f cartesian;

  public CoordinateUtilsToIsometricTest(Vector3f isometric, Vector3f cartesian) {
    this.isometric = isometric;
    this.cartesian = cartesian;
  }

  @Test
  public void toIsometricTest() {
    Assert.assertEquals(isometric, CoordinateUtils.toIsometric(cartesian));
  }
}
