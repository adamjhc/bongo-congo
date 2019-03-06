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

public class MainMenu implements IGui {
	
	private static final Font FONT_SMALL = new Font("Press Start 2P", Font.PLAIN, 15);
	
    private static final Font FONT_MEDIUM = new Font("Press Start 2P", Font.PLAIN, 30);
    
    private static final Font FONT_LARGE = new Font("Press Start 2P", Font.PLAIN, 40);
	
	private static final Font FONT_TITLE = new Font("Press Start 2P", Font.PLAIN, 72);
	
	private static final String CHARSET = "ISO-8859-1";
	
    private GuiObject[] guiObjects;
    
    private final TextObject singleplayer;
    
    private final TextObject multiplayer;
    
    private final TextObject quit;
    
    private final TextObject bongo;
    
    private final TextObject congo;
    
    private final TextObject soundOn;
    
    private final TextObject soundOff;
    
    private final TextObject options;
    
    public MainMenu(Window window) throws Exception {
    	InputStream myStream = new BufferedInputStream(new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"));
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    	
    	FontTexture fontSmall = new FontTexture(FONT_SMALL, CHARSET);
        FontTexture fontMedium = new FontTexture(FONT_MEDIUM, CHARSET);
        FontTexture fontLarge = new FontTexture(FONT_LARGE, CHARSET);
    	FontTexture fontTitle = new FontTexture(FONT_TITLE, CHARSET);
    	
    	this.bongo = new TextObject("Bongo", fontTitle);
        this.bongo.getMesh().getMaterial().setColour(new Vector4f(0.29f, 0.92f, 0.95f, 1));
        
        this.congo = new TextObject("Congo", fontTitle);
        this.congo.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.singleplayer = new TextObject("Singleplayer", fontSmall);
        this.singleplayer.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.multiplayer = new TextObject("Multiplayer", fontSmall);
        this.multiplayer.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.soundOn = new TextObject("(", fontLarge);
        this.soundOn.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
        
        this.soundOff = new TextObject("/", fontMedium);
        this.soundOff.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.quit = new TextObject("Quit", fontSmall);
        this.quit.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.options = new TextObject("Options", fontSmall);
        this.options.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        
        this.bongo.setPosition(window.getWidth()/2-360, window.getHeight()/2-300, 0);
        this.congo.setPosition(window.getWidth()/2, window.getHeight()/2-300, 0);
        
        this.singleplayer.setPosition(window.getWidth()/2 - 90, window.getHeight()/2+100, 0);
        this.multiplayer.setPosition(window.getWidth()/2 - 82, window.getHeight()/2+120, 0);
        
        this.options.setPosition(window.getWidth()/2-50, window.getHeight()/2+140, 0);
        
        this.soundOn.setPosition(window.getWidth()-40, window.getHeight()-40, 0);
        this.soundOff.setPosition(window.getWidth()-30, window.getHeight()-30, 0);
        
        this.quit.setPosition(window.getWidth()/2-30, window.getHeight()/2+160, 0);
        
        this.soundOff.setRender();
        
        guiObjects = new GuiObject[]{bongo, congo, singleplayer, multiplayer, quit, options, soundOn, soundOff};
    }
    
    public TextObject getSingleplayer() {
    	return singleplayer;
    }

    public TextObject getMultiplayer() {
    	return multiplayer;
    }
    
    public TextObject getQuit() {
    	return quit;
    }
    
    public TextObject getOptions() {
    	return options;
    }
    
    public void toggleSound() {
    	this.soundOff.setRender();
    }
    
    @Override
    public GuiObject[] getGuiObjects() {
        return guiObjects;
    }

}
