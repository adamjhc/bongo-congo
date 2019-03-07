package com.knightlore.client.gui;

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

public class Lobby implements IGui {
	
	private static final Font FONT_SMALL = new Font("Press Start 2P", Font.PLAIN, 15);
	
	private static final Font FONT_TITLE = new Font("Press Start 2P", Font.PLAIN, 72);
	
	private static final String CHARSET = "ISO-8859-1";
	
	private static final Vector4f YELLOW = new Vector4f(1, 1, 0, 1);
	
	private static final Vector4f RED = new Vector4f(1, 0, 0, 1);
	
	private static final Vector4f LIGHT_BLUE = new Vector4f(0.29f, 0.92f, 0.95f, 1);
	
	private static final int TITLE_POS = 300;
	
    private final TextObject bongo;
    
    private final TextObject congo;
	
    private GuiObject[] guiObjects;
    
    private TextObject[] textObjects;
    
    public Lobby(Window window) throws Exception {
    	InputStream myStream = new BufferedInputStream(new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"));
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    	
    	FontTexture fontSmall = new FontTexture(FONT_SMALL, CHARSET);
    	FontTexture fontTitle = new FontTexture(FONT_TITLE, CHARSET);
    	
    	this.bongo = new TextObject("Bongo", fontTitle);
        this.bongo.setColour(LIGHT_BLUE);
        
        this.congo = new TextObject("Congo", fontTitle);
        this.congo.setColour(RED);
        
        this.bongo.setPosition(window.getWidth()/2-bongo.getSize(), window.getHeight()/2-TITLE_POS);
        this.congo.setPosition(window.getWidth()/2, window.getHeight()/2-TITLE_POS);
    	
        guiObjects = new GuiObject[]{bongo, congo};
    }
    
    @Override
    public TextObject[] getTextObjects() {
    	return textObjects;
    }

    @Override
    public GuiObject[] getGuiObjects() {
        return guiObjects;
    }
}
