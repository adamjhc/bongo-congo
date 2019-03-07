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
	
	private static final Font FONT_SMALL = new Font("Press Start 2P", Font.PLAIN, 16);
	
    private static final Font FONT_MEDIUM = new Font("Press Start 2P", Font.PLAIN, 30);
    
    private static final Font FONT_LARGE = new Font("Press Start 2P", Font.PLAIN, 40);
	
	private static final Font FONT_TITLE = new Font("Press Start 2P", Font.PLAIN, 72);
	
	private static final String CHARSET = "ISO-8859-1";
	
	private static final Vector4f YELLOW = new Vector4f(1, 1, 0, 1);
	
	private static final Vector4f RED = new Vector4f(1, 0, 0, 1);
	
	private static final Vector4f LIGHT_BLUE = new Vector4f(0.29f, 0.92f, 0.95f, 1);
	
	private static final int TITLE_POS = 300;
	
	private static final int MENU_POS = 100;
	
	private static final int MENU_GAP = 20;
	
    private GuiObject[] guiObjects;
    
    private TextObject[] textObjects;
    
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
        this.bongo.setColour(LIGHT_BLUE);
        
        this.congo = new TextObject("Congo", fontTitle);
        this.congo.setColour(RED);
        
        this.singleplayer = new TextObject("Singleplayer", fontSmall);
        this.singleplayer.setColour(YELLOW);
        
        this.multiplayer = new TextObject("Multiplayer", fontSmall);
        this.multiplayer.setColour(YELLOW);
        
        this.soundOn = new TextObject("(", fontLarge);
        this.soundOn.setColour();
        
        this.soundOff = new TextObject("/", fontMedium);
        this.soundOff.setColour(RED);
        
        this.quit = new TextObject("Quit", fontSmall);
        this.quit.setColour(YELLOW);
        
        this.options = new TextObject("Options", fontSmall);
        this.options.setColour(YELLOW);
        
        
        this.bongo.setPosition(window.getWidth()/2-bongo.getSize(), window.getHeight()/2-TITLE_POS);
        this.congo.setPosition(window.getWidth()/2, window.getHeight()/2-TITLE_POS);
        this.singleplayer.setPosition(window.getWidth()/2 - singleplayer.getSize()/2, window.getHeight()/2+MENU_POS);
        this.multiplayer.setPosition(window.getWidth()/2 - multiplayer.getSize()/2, window.getHeight()/2+MENU_POS+MENU_GAP);
        this.options.setPosition(window.getWidth()/2- options.getSize()/2, window.getHeight()/2+MENU_POS+MENU_GAP*2);
        this.soundOn.setPosition(window.getWidth()- soundOn.getSize(), window.getHeight()- soundOn.getHeight());
        this.soundOff.setPosition(window.getWidth()- soundOff.getSize(), window.getHeight()- soundOff.getHeight());
        this.quit.setPosition(window.getWidth()/2- quit.getSize()/2, window.getHeight()/2+MENU_POS+MENU_GAP*3);
        
        this.soundOff.setRender();
        
        guiObjects = new GuiObject[]{bongo, congo, singleplayer, multiplayer, quit, options, soundOn, soundOff};
        textObjects = new TextObject[]{singleplayer, multiplayer, options, quit, soundOn};
    }
    
    public TextObject getSingleplayer() {
    	return singleplayer;
    }

    public TextObject getMultiplayer() {
    	return multiplayer;
    }
    
    public TextObject getSound() {
    	return soundOn;
    }
    
    public TextObject getSoundMute() {
    	return soundOff;
    }
    
    public TextObject getQuit() {
    	return quit;
    }
    
    public TextObject getOptions() {
    	return options;
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
