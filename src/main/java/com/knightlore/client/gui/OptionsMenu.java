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
	
	private static final Font FONT = new Font("Press Start 2P", Font.PLAIN, 15);
	
	private static final Font FONT_TITLE = new Font("Press Start 2P", Font.PLAIN, 72);
	
	private static final String CHARSET = "ISO-8859-1";
	
    private GuiObject[] guiObjects;
    
    private final TextObject bongo;
    
    private final TextObject congo;
    
    private final TextObject incVolume;
    
    private final TextObject decVolume;
    
    private final TextObject volume;
    
    public OptionsMenu(Window window) throws Exception {
    	InputStream myStream = new BufferedInputStream(new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"));
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    	
    	FontTexture fontTexture = new FontTexture(FONT, CHARSET);
    	FontTexture fontTextureTitle = new FontTexture(FONT_TITLE, CHARSET);
    	
    	this.bongo = new TextObject("Bongo", fontTextureTitle);
        this.bongo.getMesh().getMaterial().setColour(new Vector4f(0.29f, 0.92f, 0.95f, 1));
        
        this.congo = new TextObject("Congo", fontTextureTitle);
        this.congo.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.incVolume = new TextObject("+", fontTexture);
        this.incVolume.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.decVolume = new TextObject("-", fontTexture);
        this.decVolume.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.volume = new TextObject("100", fontTexture);
        this.volume.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        //ADD VOLUME CONTROL FOR MUSIC AND IN-GAME SOUNDS SEPARATELY
        //GAME CONTROLS?
        
        this.bongo.setPosition(window.getWidth()/2-360, window.getHeight()/2-300, 0);
        this.congo.setPosition(window.getWidth()/2, window.getHeight()/2-300, 0);
        this.incVolume.setPosition(window.getWidth()/2-50, window.getHeight()/2+100, 0);
        this.decVolume.setPosition(window.getWidth()/2+40, window.getHeight()/2+100, 0);
        this.volume.setPosition(window.getWidth()/2-22, window.getHeight()/2+100, 0);
        
        guiObjects = new GuiObject[]{bongo, congo, incVolume, decVolume, volume};
    }

    @Override
    public GuiObject[] getGuiObjects() {
        return guiObjects;
    }
}
