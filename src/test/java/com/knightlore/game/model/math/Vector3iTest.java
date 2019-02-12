package com.knightlore.game.model.math;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class Vector3iTest {

  @Test
  public void canCreateVector() {
    Vector3i vector = new Vector3i(1, 2, 3);

    assertThat(1, equalTo(vector.x));
    assertThat(2, equalTo(vector.y));
    assertThat(3, equalTo(vector.z));
  }
}
