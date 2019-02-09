package com.knightlore.game.math;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

public class Vector3f extends org.joml.Vector3f {

  public Vector3f() {
    super();
  }

  public Vector3f(float d) {
    super(d);
  }

  public Vector3f(float x, float y, float z) {
    super(x, y, z);
  }

  public Vector3f(org.joml.Vector3f vector3f) {
    super(vector3f);
  }

  public Vector3f mul(float scalar, Vector3f target) {
    return new Vector3f(super.mul(scalar, target));
  }

  public FloatBuffer toBuffer() {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(3);
    buffer.put(this.x).put(this.y).put(this.z);
    buffer.flip();
    return buffer;
  }
}
