package com.knightlore.game.model.math;

import java.nio.FloatBuffer;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

public class Matrix4f extends org.joml.Matrix4f {

  public Matrix4f() {
    super();
  }

  public Matrix4f(org.joml.Matrix4f matrix4f) {
    super(matrix4f);
  }

  public FloatBuffer toBuffer() {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
    return this.get(buffer);
  }

  public Matrix4f translate(Vector3f vector3f) {
    return new Matrix4f(super.translate(vector3f));
  }
}
