package com.knightlore.client.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

public class BufferUtils {

  public static IntBuffer createBuffer(MemoryStack stack, int[] data) {
    IntBuffer buffer = stack.mallocInt(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  public static FloatBuffer createBuffer(MemoryStack stack, float[] data) {
    FloatBuffer buffer = stack.mallocFloat(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }

  public static FloatBuffer createBuffer(MemoryStack stack, Vector3f data) {
    FloatBuffer buffer = stack.mallocFloat(3);
    buffer.put(data.x).put(data.y).put(data.z);
    buffer.flip();
    return buffer;
  }

  public static FloatBuffer createBuffer(MemoryStack stack, Vector4f data) {
    FloatBuffer buffer = stack.mallocFloat(4);
    buffer.put(data.x).put(data.y).put(data.z).put(data.w);
    buffer.flip();
    return buffer;
  }

  public static FloatBuffer createBuffer(MemoryStack stack, Matrix4f data) {
    FloatBuffer buffer = stack.mallocFloat(16);
    return data.get(buffer);
  }
}
