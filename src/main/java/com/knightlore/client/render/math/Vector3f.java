package com.knightlore.client.render.math;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

public class Vector3f extends org.joml.Vector3f {
  public FloatBuffer toBuffer() {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(3);
    buffer.put(this.x).put(this.y).put(this.z);
    buffer.flip();
    return buffer;
  }
}
