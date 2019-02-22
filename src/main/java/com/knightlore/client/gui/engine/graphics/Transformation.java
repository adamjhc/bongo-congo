package com.knightlore.client.gui.engine.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

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
    	Vector3f rotation = guiObject.getRotation();
        Matrix4f modelMatrix = new Matrix4f();
        modelMatrix.identity().translate(guiObject.getPosition()).
        		rotateX((float)Math.toRadians(-rotation.x)).
        		rotateY((float)Math.toRadians(-rotation.y)).
        		rotateZ((float)Math.toRadians(-rotation.z)).
                scale(guiObject.getScale());
        Matrix4f orthoMatrixCurr = new Matrix4f(orthoMatrix);
        orthoMatrixCurr.mul(modelMatrix);
        return orthoMatrixCurr;
    }
}