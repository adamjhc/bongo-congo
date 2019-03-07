package com.knightlore.leveleditor;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import org.joml.Vector4f;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.gui.engine.Window;
import com.knightlore.client.gui.engine.graphics.FontTexture;

public class LevelEditorHud implements IGui {
	
	private static final Font FONT_LARGE = new Font("Press Start 2P", Font.PLAIN, 40);
	
	private static final String CHARSET = "ISO-8859-1";
	
	private final TextObject save;
	
	private GuiObject[] guiObjects;

	public LevelEditorHud(Window window) throws Exception {
		InputStream myStream = new BufferedInputStream(new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"));
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    	
    	FontTexture fontTextureLarge = new FontTexture(FONT_LARGE, CHARSET);
    	
    	this.save = new TextObject("Save", fontTextureLarge);
    	this.save.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    	
    	this.save.setPosition(window.getWidth()/2+460, window.getHeight()/2-350, 0);
    	
    	guiObjects = new GuiObject[] {save};
	}
	
	@Override
	public GuiObject[] getGuiObjects() {
		return guiObjects;
	}

}
