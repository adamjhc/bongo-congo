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
	
	private static final Font FONT = new Font("Press Start 2P", Font.PLAIN, 15);
	
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
    
    private final TextObject levelEditor;
    
    public MainMenu(Window window) throws Exception {
    	InputStream myStream = new BufferedInputStream(new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"));
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    	
    	FontTexture fontTexture = new FontTexture(FONT, CHARSET);
        FontTexture fontTextureLarge = new FontTexture(FONT_LARGE, CHARSET);
        FontTexture fontTextureMedium = new FontTexture(FONT_MEDIUM, CHARSET);
    	FontTexture fontTextureTitle = new FontTexture(FONT_TITLE, CHARSET);
    	
    	this.bongo = new TextObject("Bongo", fontTextureTitle);
        this.bongo.getMesh().getMaterial().setColour(new Vector4f(0.29f, 0.92f, 0.95f, 1));
        
        this.congo = new TextObject("Congo", fontTextureTitle);
        this.congo.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.singleplayer = new TextObject("Singleplayer", fontTexture);
        this.singleplayer.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.multiplayer = new TextObject("Multiplayer", fontTexture);
        this.multiplayer.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.soundOn = new TextObject("(", fontTextureLarge);
        this.soundOn.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
        
        this.soundOff = new TextObject("/", fontTextureMedium);
        this.soundOff.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.quit = new TextObject("Quit", fontTexture);
        this.quit.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.options = new TextObject("Options", fontTexture);
        this.options.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.levelEditor = new TextObject("Level Editor", fontTexture);
        this.levelEditor.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        
        
        this.bongo.setPosition(window.getWidth()/2-360, window.getHeight()/2-300, 0);
        this.congo.setPosition(window.getWidth()/2, window.getHeight()/2-300, 0);
        
        this.singleplayer.setPosition(window.getWidth()/2 - 90, window.getHeight()/2+100, 0);
        this.multiplayer.setPosition(window.getWidth()/2 - 82, window.getHeight()/2+120, 0);
        this.levelEditor.setPosition(window.getWidth()/2 - 86, window.getHeight()/2+140, 0);
        
        this.options.setPosition(window.getWidth()/2-50, window.getHeight()/2+160, 0);
        
        this.soundOn.setPosition(window.getWidth()-40, window.getHeight()-40, 0);
        this.soundOff.setPosition(window.getWidth()-30, window.getHeight()-30, 0);
        
        this.quit.setPosition(window.getWidth()/2-30, window.getHeight()/2+180, 0);
        
        this.soundOff.setRender();
        
        guiObjects = new GuiObject[]{bongo, congo, singleplayer, multiplayer, quit, options, soundOn, soundOff, levelEditor};
    }
    
    public void setSingleplayer() {
    	this.singleplayer.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
    }
    
    public void setRestoreSingleplayer() {
    	this.singleplayer.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }
    
    public void setMultiplayer() {
    	this.multiplayer.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
    }
    
    public void setRestoreMultiplayer() {
    	this.multiplayer.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }
    
    public void setQuit() {
    	this.quit.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
    }
    
    public void setRestoreQuit() {
    	this.quit.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }
    
    public void setOptions() {
    	this.options.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
    }
    
    public void setRestoreOptions() {
    	this.options.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }
    
    public void setLevelEditor() {
    	this.levelEditor.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
    }
    
    public void setRestoreLevelEditor() {
    	this.levelEditor.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }
    
    public void toggleSound() {
    	this.soundOff.setRender();
    }
    
    @Override
    public GuiObject[] getGuiObjects() {
        return guiObjects;
    }

}
