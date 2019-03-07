package com.knightlore.client.gui.engine;

import java.awt.Font;

import org.joml.Vector4f;

public interface IGui {
	
	public static final Font FONT_SMALL = new Font("Press Start 2P", Font.PLAIN, 16);
	
    public static final Font FONT_MEDIUM = new Font("Press Start 2P", Font.PLAIN, 30);
    
    public static final Font FONT_LARGE = new Font("Press Start 2P", Font.PLAIN, 40);
	
	public static final Font FONT_TITLE = new Font("Press Start 2P", Font.PLAIN, 72);
	
	public static final Font FONT_LIVES = new Font("Press Start 2P", Font.PLAIN, 20);
	
	public static final String CHARSET = "ISO-8859-1";
	
	public static final Vector4f YELLOW = new Vector4f(1, 1, 0, 1);
	
	public static final Vector4f RED = new Vector4f(1, 0, 0, 1);
	
	public static final Vector4f LIGHT_BLUE = new Vector4f(0.29f, 0.92f, 0.95f, 1);
	
	public static final int TITLE_POS = 300;
	
	public static final int GAP = 20;
	
	TextObject[] getTextObjects();

    GuiObject[] getGuiObjects();

    default void cleanup() {
        GuiObject[] guiObjects = getGuiObjects();
        for (GuiObject guiObject : guiObjects) {
            guiObject.getMesh().cleanUp();
        }
    }
}
