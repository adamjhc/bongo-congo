package com.knightlore.client.gui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import org.joml.Vector4f;

import com.knightlore.client.gui.engine.GameItem;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextItem;
import com.knightlore.client.gui.engine.Window;
import com.knightlore.client.gui.engine.graphics.FontTexture;

public class Menu implements IGui {
	
	private static final Font FONT = new Font("Press Start 2P", Font.PLAIN, 15);
	
	private static final Font FONT_TITLE = new Font("Press Start 2P", Font.PLAIN, 72);
	
	private static final String CHARSET = "ISO-8859-1";
	
    private GameItem[] gameItems;
    
    private final TextItem singleplayer;
    
    private final TextItem multiplayer;
    
    private final TextItem bongo;
    
    private final TextItem congo;
    
    public Menu(Window window) throws Exception {
    	InputStream myStream = new BufferedInputStream(new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"));
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    	
    	FontTexture fontTexture = new FontTexture(FONT, CHARSET);
    	FontTexture fontTextureTitle = new FontTexture(FONT_TITLE, CHARSET);
    	
    	this.bongo = new TextItem("Bongo", fontTextureTitle);
        this.bongo.getMesh().getMaterial().setColour(new Vector4f(0.29f, 0.92f, 0.95f, 1));
        
        this.congo = new TextItem("Congo", fontTextureTitle);
        this.congo.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.singleplayer = new TextItem("Singleplayer", fontTexture);
        this.singleplayer.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.multiplayer = new TextItem("Multiplayer", fontTexture);
        this.multiplayer.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        
        
        this.bongo.setPosition(window.getWidth()/2-360, window.getHeight()/2-200, 0);
        this.congo.setPosition(window.getWidth()/2, window.getHeight()/2-200, 0);
        
        this.singleplayer.setPosition(window.getWidth()/2 - 90, window.getHeight()/2+100, 0);
        this.multiplayer.setPosition(window.getWidth()/2 - 82, window.getHeight()/2+120, 0);
        
        gameItems = new GameItem[]{bongo, congo, singleplayer, multiplayer};
    }
    
    public void setSingleplayer() {
    	this.singleplayer.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
    }
    
    public void setRestoreSingleplayer() {
    	this.singleplayer.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }
    
    @Override
    public GameItem[] getGameItems() {
        return gameItems;
    }

}
