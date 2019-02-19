package com.knightlore.client.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

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

  public static FloatBuffer createBuffer(Vector3f data) {
    FloatBuffer buffer = org.lwjgl.BufferUtils.createFloatBuffer(3);
    buffer.put(data.x).put(data.y).put(data.z);
    buffer.flip();
    return buffer;
  }

  public static FloatBuffer createBuffer(Vector4f data) {
    FloatBuffer buffer = org.lwjgl.BufferUtils.createFloatBuffer(4);
    buffer.put(data.x).put(data.y).put(data.z).put(data.w);
    buffer.flip();
    return buffer;
  }

  public static FloatBuffer createBuffer(Matrix4f data) {
    FloatBuffer buffer = org.lwjgl.BufferUtils.createFloatBuffer(16);
    return data.get(buffer);
  }
}
