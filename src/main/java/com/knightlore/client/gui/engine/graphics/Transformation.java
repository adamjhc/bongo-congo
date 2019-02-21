package com.knightlore.client.gui.engine.graphics;

import org.joml.Matrix4f;

import com.knightlore.client.gui.engine.GuiObject;

public class Transformation {

    private final Matrix4f orthoMatrix;

    public Transformation() {
        orthoMatrix = new Matrix4f();
    }

    public final Matrix4f getOrthoProjectionMatrix(float left, float right, float bottom, float top) {
        orthoMatrix.identity();
        orthoMatrix.setOrtho2D(left, right, bottom, top);
        return orthoMatrix;
    }

    public Matrix4f getOrtoProjModelMatrix(GuiObject guiObject, Matrix4f orthoMatrix) {
        Matrix4f modelMatrix = new Matrix4f();
        modelMatrix.identity().translate(guiObject.getPosition()).
                scale(guiObject.getScale());
        Matrix4f orthoMatrixCurr = new Matrix4f(orthoMatrix);
        orthoMatrixCurr.mul(modelMatrix);
        return orthoMatrixCurr;
    }
}