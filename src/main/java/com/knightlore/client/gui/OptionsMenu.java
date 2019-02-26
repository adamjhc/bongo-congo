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

public class OptionsMenu implements IGui {
	
	private static final Font FONT_TITLE = new Font("Press Start 2P", Font.PLAIN, 72);
	
	private static final String CHARSET = "ISO-8859-1";
	
    private GuiObject[] guiObjects;
    
    private final TextObject bongo;
    
    private final TextObject congo;
    
    public OptionsMenu(Window window) throws Exception {
    	InputStream myStream = new BufferedInputStream(new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"));
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    	
    	FontTexture fontTextureTitle = new FontTexture(FONT_TITLE, CHARSET);
    	
    	this.bongo = new TextObject("Bongo", fontTextureTitle);
        this.bongo.getMesh().getMaterial().setColour(new Vector4f(0.29f, 0.92f, 0.95f, 1));
        
        this.congo = new TextObject("Congo", fontTextureTitle);
        this.congo.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.bongo.setPosition(window.getWidth()/2-360, window.getHeight()/2-300, 0);
        this.congo.setPosition(window.getWidth()/2, window.getHeight()/2-300, 0);
        
        guiObjects = new GuiObject[]{bongo, congo};
    }

    @Override
    public GuiObject[] getGuiObjects() {
        return guiObjects;
    }
}
