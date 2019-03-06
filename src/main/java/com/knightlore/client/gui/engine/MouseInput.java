package com.knightlore.client.gui.engine;

import static org.lwjgl.glfw.GLFW.*;

public class MouseInput {

    private double xPos;

    private double yPos;

    private boolean leftButtonPressed = false;

    private boolean rightButtonPressed = false;
    
    private boolean scrollUp = false;
    
    private boolean scrollDown = false;

    public MouseInput() {
        xPos = 0;
        yPos = 0;
    }

    public void init(Window window) {
        glfwSetCursorPosCallback(window.getWindowHandle(), (windowHandle, xPos, yPos) -> {
            this.xPos = xPos;
            this.yPos = yPos;
        });
        glfwSetMouseButtonCallback(window.getWindowHandle(), (windowHandle, button, action, mode) -> {
        	/*
            if (button == GLFW_MOUSE_BUTTON_1 && action == GLFW_RELEASE) {
            	leftButtonPressed = true;
            }
            if (button == GLFW_MOUSE_BUTTON_2 && action == GLFW_RELEASE) {
            	rightButtonPressed = true;
            } */
            leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
            rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });
        glfwSetScrollCallback(window.getWindowHandle(), (windowhandle, xoffset, yoffset) -> {
        	scrollUp = yoffset > 0;
        	scrollDown = yoffset < 0;
        });
    }

    public double getXPos() {
    	return xPos;
    }
    
    public double getYPos() {
    	return yPos;
    }
    
    public boolean scrolledUp() {
    	if (scrollUp) {
    		scrollUp = false;
    		return true;
    	}
    	return false;
    }
    
    public boolean scrolledDown() {
    	if (scrollDown) {
    		scrollDown = false;
    		return true;
    	}
    	return false;
    }

    public boolean isLeftButtonPressed() {
        if (leftButtonPressed) {
        	leftButtonPressed = false;
        	return true;
        }
        return false;
    }
    
    public boolean isLeftButtonHeld() {
    	return leftButtonPressed;
    }

    public boolean isRightButtonPressed() {
        if (rightButtonPressed) {
        	rightButtonPressed = false;
        	return true;
        }
        return false;
    }
}
