package com.knightlore.client.gui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.gui.engine.Window;
import com.knightlore.client.gui.engine.graphics.FontTexture;

public class OptionsMenu implements IGui {
	
	private static final int SEPARATORTOP_POS = 185;
	
	private static final int SEPARATORBOT_POS = 200;
	
	private static final int SEPARATOR_GAP = 16;
	
	private static final int MAX_VOLUME = 100;
	
    private GuiObject[] guiObjects;
    
    private TextObject[] textObjects;
    
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
    	
    	FontTexture fontSmall = new FontTexture(FONT_SMALL, CHARSET);
    	FontTexture fontLarge = new FontTexture(FONT_LARGE, CHARSET);
    	FontTexture fontTitle = new FontTexture(FONT_TITLE, CHARSET);
    	
    	this.bongo = new TextObject("Bongo", fontTitle);
        this.bongo.setColour(LIGHT_BLUE);
        
        this.congo = new TextObject("Congo", fontTitle);
        this.congo.setColour(RED);
        
        this.incVolume = new TextObject(">", fontLarge);
        this.incVolume.setColour(YELLOW);
        
        this.decVolume = new TextObject("<", fontLarge);
        this.decVolume.setColour(YELLOW);
        
        this.volume = new TextObject("000", fontLarge);
        this.volume.setColour(YELLOW);
        
        this.exit = new TextObject("Exit", fontSmall);
        this.exit.setColour(YELLOW);
        
        this.options = new TextObject("Game Options", fontSmall);
        this.options.setColour(YELLOW);
        
        this.separatorTop = new TextObject("------------------------------", fontSmall);
        this.separatorTop.setColour(YELLOW);
        
        this.separatorBot = new TextObject("------------------------------", fontSmall);
        this.separatorBot.setColour(YELLOW);
        
        this.musicVolume = new TextObject("Game music volume", fontSmall);
        this.musicVolume.setColour(YELLOW);
        
        this.bongo.setPosition(window.getWidth()/2-bongo.getSize(), window.getHeight()/2-TITLE_POS);
        this.congo.setPosition(window.getWidth()/2, window.getHeight()/2-TITLE_POS);
        this.volume.setPosition(window.getWidth()/2-volume.getSize()/2, window.getHeight()/2-SEPARATORTOP_POS+GAP*2);
        this.incVolume.setPosition(window.getWidth()/2-incVolume.getSize()/2+100, window.getHeight()/2-SEPARATORTOP_POS+GAP*2);
        this.decVolume.setPosition(window.getWidth()/2-decVolume.getSize()/2-100, window.getHeight()/2-SEPARATORTOP_POS+GAP*2);
        this.exit.setPosition(window.getWidth()/2-exit.getSize()/2, window.getHeight()/2+SEPARATORBOT_POS+SEPARATOR_GAP);	
        this.options.setPosition(window.getWidth()/2-options.getSize()/2, window.getHeight()/2-SEPARATORTOP_POS-SEPARATOR_GAP);
        this.separatorTop.setPosition(window.getWidth()/2-separatorTop.getSize()/2, window.getHeight()/2-SEPARATORTOP_POS);
        this.separatorBot.setPosition(window.getWidth()/2-separatorBot.getSize()/2, window.getHeight()/2+SEPARATORBOT_POS);
        this.musicVolume.setPosition(window.getWidth()/2-musicVolume.getSize()/2, window.getHeight()/2-SEPARATORTOP_POS+GAP);
        
        
        guiObjects = new GuiObject[]{bongo, congo, incVolume, decVolume, volume, exit, options, separatorTop, separatorBot, musicVolume};
        textObjects = new TextObject[]{incVolume, decVolume, exit};
    }
    
    public void incVolume() {
    	int volume = Integer.parseInt(this.volume.getText());
    	if (volume < MAX_VOLUME) volume ++;
    	this.volume.setText((String.format("%03d", volume)));
    	this.volume.getMesh().getMaterial().setColour(YELLOW);
    }
    
    public void decVolume() {
    	int volume = Integer.parseInt(this.volume.getText());
    	if (volume > 0) volume --;
    	this.volume.setText((String.format("%03d", volume)));
    	this.volume.getMesh().getMaterial().setColour(YELLOW);
    }

    public TextObject getIncVolume() {
    	return incVolume;
    }
    
    public TextObject getDecVolume() {
    	return decVolume;
    }
    
    public TextObject getExit() {
    	return exit;
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
