package com.knightlore.game.math;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

public class Vector3f extends org.joml.Vector3f {
  public Vector3f(float x, float y, float z) {
    super(x, y, z);
  }

  public FloatBuffer toBuffer() {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(3);
    buffer.put(this.x).put(this.y).put(this.z);
    buffer.flip();
    return buffer;
  }
}
