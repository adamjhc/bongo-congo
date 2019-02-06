package com.knightlore.game.math;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

public class Matrix4f extends org.joml.Matrix4f {

  public Matrix4f() {
    super();
  }

  private Matrix4f(org.joml.Matrix4f matrix4f) {
    super(matrix4f);
  }

  public FloatBuffer toBuffer() {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
    return this.get(buffer);
  }

  public Matrix4f translate(Vector3f vector3f) {
    return new Matrix4f(super.translate(vector3f));
  }

  public Matrix4f setTranslation(Vector3f vector3f) {
    return new Matrix4f(super.setTranslation(vector3f));
  }

  public Matrix4f setOrtho2D(float left, float right, float bottom, float top) {
    return new Matrix4f(super.setOrtho2D(left, right, bottom, top));
  }
}
