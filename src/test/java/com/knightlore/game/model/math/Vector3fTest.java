package com.knightlore.game.model.math;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class Vector3fTest {

  @Test
  public void canCreateVector() {
    Vector3f vector = new Vector3f(1f, 2f, 3f);

    assertThat(1f, equalTo(vector.x));
    assertThat(2f, equalTo(vector.y));
    assertThat(3f, equalTo(vector.z));
  }
}
