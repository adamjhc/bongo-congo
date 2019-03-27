package com.knightlore.client.gui.engine.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.knightlore.client.gui.engine.GuiObject;

/**
 * Performs transformations on text using orthographic projection
 * 
 * @author Joseph
 *
 */
public class Transformation {

		/** Orthographic matrix */
    private final Matrix4f orthoMatrix;

    /**
     * Initilise matrix
     * 
     * @author Joseph
     * 
     */
    public Transformation() {
        orthoMatrix = new Matrix4f();
    }

    /**
     * Sets up the ortho matrix
     * 
     * @param left Left side screen coordinate
     * @param right Right side screen coordinate
     * @param bottom Bottom side screen coordinate
     * @param top Top side screen coordinate
     * @return Orthographic matrix
     * @author Joseph
     * 
     */
    public final Matrix4f getOrthoProjectionMatrix(float left, float right, float bottom, float top) {
        orthoMatrix.identity();
        orthoMatrix.setOrtho2D(left, right, bottom, top);
        return orthoMatrix;
    }

    /**
     * Performs transformations on gui object
     * 
     * @param guiObject The object to perform the transformation on
     * @param orthoMatrix Orthographic matrix
     * @return The created model matrix
     * @author Joseph
     * 
     */
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