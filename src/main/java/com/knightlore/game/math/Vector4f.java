package com.knightlore.game.math;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

public class Vector4f extends org.joml.Vector4f {
  public FloatBuffer toBuffer() {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
    buffer.put(this.x).put(this.y).put(this.z).put(this.w);
    buffer.flip();
    return buffer;
  }
}
