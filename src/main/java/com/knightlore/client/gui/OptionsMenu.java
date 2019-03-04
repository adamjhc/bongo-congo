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
	
	private static final Font FONT_LARGE = new Font("Press Start 2P", Font.PLAIN, 30);
	
	private static final String CHARSET = "ISO-8859-1";
	
	private static final int MAX_VOLUME = 100;
	
    private GuiObject[] guiObjects;
    
    private final TextObject bongo;
    
    private final TextObject congo;
    
    private final TextObject musicVolume;
    
    private final TextObject incVolume;
    
    private final TextObject decVolume;
    
    private final TextObject volume;
    
    private final TextObject exit;
    
    private final TextObject options;
    
    private final TextObject separatorTop;
    
    private final TextObject separatorBot;
    
    public OptionsMenu(Window window) throws Exception {
    	InputStream myStream = new BufferedInputStream(new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"));
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    	
    	FontTexture fontTexture = new FontTexture(FONT, CHARSET);
    	FontTexture fontTextureLarge = new FontTexture(FONT_LARGE, CHARSET);
    	FontTexture fontTextureTitle = new FontTexture(FONT_TITLE, CHARSET);
    	
    	this.bongo = new TextObject("Bongo", fontTextureTitle);
        this.bongo.getMesh().getMaterial().setColour(new Vector4f(0.29f, 0.92f, 0.95f, 1));
        
        this.congo = new TextObject("Congo", fontTextureTitle);
        this.congo.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.incVolume = new TextObject(">", fontTextureLarge);
        this.incVolume.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.decVolume = new TextObject("<", fontTextureLarge);
        this.decVolume.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.volume = new TextObject("000", fontTextureLarge);
        this.volume.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.exit = new TextObject("Exit", fontTexture);
        this.exit.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.options = new TextObject("Game Options", fontTexture);
        this.options.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.separatorTop = new TextObject("------------------------------", fontTexture);
        this.separatorTop.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0 , 1));
        
        this.separatorBot = new TextObject("------------------------------", fontTexture);
        this.separatorBot.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0 , 1));
        
        this.musicVolume = new TextObject("Menu music volume", fontTexture);
        this.musicVolume.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        //ADD VOLUME CONTROL FOR MUSIC AND IN-GAME SOUNDS SEPARATELY
        //GAME CONTROLS?
        int width = (volume.getText().length())*30/2;
        this.bongo.setPosition(window.getWidth()/2-360, window.getHeight()/2-300, 0);
        this.congo.setPosition(window.getWidth()/2, window.getHeight()/2-300, 0);
        this.volume.setPosition(window.getWidth()/2-width, window.getHeight()/2-145, 0);
        width = (incVolume.getText().length())*30/2;
        this.incVolume.setPosition(window.getWidth()/2-width+100, window.getHeight()/2-145, 0);
        width = (decVolume.getText().length())*30/2;
        this.decVolume.setPosition(window.getWidth()/2-width-100, window.getHeight()/2-145, 0);
        width = (exit.getText().length())*15/2;
        this.exit.setPosition(window.getWidth()/2-width, window.getHeight()/2+255, 0);	
        this.options.setPosition(window.getWidth()/2-90, window.getHeight()/2-200, 0);
        this.separatorTop.setPosition(window.getWidth()/2-225, window.getHeight()/2-185, 0);
        this.separatorBot.setPosition(window.getWidth()/2-225, window.getHeight()/2+240, 0);
        width = (musicVolume.getText().length())*15/2;
        this.musicVolume.setPosition(window.getWidth()/2-width, window.getHeight()/2-165, 0);
        
        
        guiObjects = new GuiObject[]{bongo, congo, incVolume, decVolume, volume, exit, options, separatorTop, separatorBot, musicVolume};
    }
    
    public void incVolume() {
    	int volume = Integer.parseInt(this.volume.getText());
    	if (volume < MAX_VOLUME) volume ++;
    	this.volume.setText((String.format("%03d", volume)));
    	this.volume.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }
    
    public void decVolume() {
    	int volume = Integer.parseInt(this.volume.getText());
    	if (volume > 0) volume --;
    	this.volume.setText((String.format("%03d", volume)));
    	this.volume.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }
    
    public void setIncVol() {
    	this.incVolume.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
    }
    
    public void setRestoreIncVol() {
    	this.incVolume.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }
    
    public void setDecVol() {
    	this.decVolume.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
    }
    
    public void setRestoreDecVol() {
    	this.decVolume.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }
    
    public void setExit() {
    	this.exit.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
    }
    
    public void setRestoreExit() {
    	this.exit.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }

    @Override
    public GuiObject[] getGuiObjects() {
        return guiObjects;
    }
}
