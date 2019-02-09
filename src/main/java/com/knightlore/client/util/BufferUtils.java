package com.knightlore.client.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtils {

  public static FloatBuffer createBuffer(float[] data) {
    FloatBuffer buffer = org.lwjgl.BufferUtils.createFloatBuffer(data.length);
    buffer.put(data);
    buffer.flip();

    return buffer;
  }

  public static IntBuffer createBuffer(int[] data) {
    IntBuffer buffer = org.lwjgl.BufferUtils.createIntBuffer(data.length);
    buffer.put(data);
    buffer.flip();

    return buffer;
  }
}
