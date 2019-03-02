package com.knightlore.client.gui;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.joml.Vector4f;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.gui.engine.Window;
import com.knightlore.client.gui.engine.graphics.FontTexture;

public class ServerMenu implements IGui {
	
	private static final Font FONT = new Font("Press Start 2P", Font.PLAIN, 15);
	
	private static final Font FONT_TITLE = new Font("Press Start 2P", Font.PLAIN, 72);
	
	private static final String CHARSET = "ISO-8859-1";
	
    private GuiObject[] guiObjects;
    
    private final TextObject bongo;
    
    private final TextObject congo;
    
    private final TextObject multiplayer;
    
    private final TextObject separatorTop;
    
    private final TextObject separatorBot;
    
    private final TextObject join;
    
    private final TextObject create;
    
    private final ArrayList<TextObject> servers;
    
    private int length;
    
    public ServerMenu(Window window) throws Exception {
    	InputStream myStream = new BufferedInputStream(new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"));
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    	
    	FontTexture fontTexture = new FontTexture(FONT, CHARSET);
    	FontTexture fontTextureTitle = new FontTexture(FONT_TITLE, CHARSET);
    	
//    	Toolkit toolkit =  Toolkit.getDefaultToolkit();
//    	FontMetrics fontMetrics = toolkit.getFontMetrics(FONT_TITLE);
//    	
//    	String s = "Bongo";
//    	char[] chars = s.toCharArray();
//    	
//    	System.out.println(fontMetrics.charsWidth(chars, 0, 5));
    	
    	this.bongo = new TextObject("Bongo", fontTextureTitle);
        this.bongo.getMesh().getMaterial().setColour(new Vector4f(0.29f, 0.92f, 0.95f, 1));
        
        this.congo = new TextObject("Congo", fontTextureTitle);
        this.congo.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.multiplayer = new TextObject("Play Multiplayer", fontTexture);
        this.multiplayer.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.separatorTop = new TextObject("------------------------------", fontTexture);
        this.separatorTop.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0 , 1));
        
        this.separatorBot = new TextObject("------------------------------", fontTexture);
        this.separatorBot.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0 , 1));
        
        this.join = new TextObject("Join game", fontTexture);
        this.join.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.create = new TextObject("Create game", fontTexture);
        this.create.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.bongo.setPosition(window.getWidth()/2-360, window.getHeight()/2-300, 0);
        this.congo.setPosition(window.getWidth()/2, window.getHeight()/2-300, 0);
        
        this.multiplayer.setPosition(window.getWidth()/2-120, window.getHeight()/2-200, 0);
        this.separatorTop.setPosition(window.getWidth()/2-225, window.getHeight()/2-185, 0);
        this.separatorBot.setPosition(window.getWidth()/2-225, window.getHeight()/2+200, 0);
        this.join.setPosition(window.getWidth()/2-190, window.getHeight()/2+215, 0);
        this.create.setPosition(window.getWidth()/2+55, window.getHeight()/2+215, 0);
        
        servers = new ArrayList<TextObject>();
        
        for (int i = 0; i < 25; i++) {
        	servers.add(new TextObject(i+"'s "+"Server", fontTexture));
        }
        
        int yPos = 165;
        for (int i = 0; i < servers.size(); i++) {
        	int width = (servers.get(i).getText().length())*15/2;
        	servers.get(i).getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        	servers.get(i).setPosition(window.getWidth()/2-width, window.getHeight()/2-yPos, 0);
        	yPos -= 20;
        }
        
        
        // RECEIVE LIST OF CLIENT NAMES
        // CREATE LOBBY
        // JOIN LOBBY
        // PLAYER COUNT IN LOBBY
        
        guiObjects = new GuiObject[]{bongo, congo, multiplayer, separatorTop, separatorBot, join, create};
        length = guiObjects.length;
        
        addServers();
    }
    
    public void addServers() {
    	if (servers.size() <= 18) {
        	GuiObject[] guiObjectsNew = new GuiObject[guiObjects.length + servers.size()];
        	for (int i = 0; i < guiObjects.length; i++) {
        		guiObjectsNew[i] = guiObjects[i];
        	}
        	for (int i = guiObjects.length; i < guiObjects.length + servers.size(); i++) {
        		guiObjectsNew[i] = servers.get(i - guiObjects.length);
        	}
        	guiObjects = guiObjectsNew.clone();
    	} else {
        	GuiObject[] guiObjectsNew = new GuiObject[guiObjects.length + 18];
        	for (int i = 0; i < guiObjects.length; i++) {
        		guiObjectsNew[i] = guiObjects[i];
        	}
        	for (int i = guiObjects.length; i < guiObjects.length + 18; i++) {
        		guiObjectsNew[i] = servers.get(i - guiObjects.length);
        	}
        	guiObjects = guiObjectsNew.clone();
    	}
    }
    
    public void moveDown() {
    	if (servers.size() > 18) {
    		GuiObject[] guiObjectsNew = new GuiObject[length + 18];
        	for (int i = 0; i < length; i++) {
        		guiObjectsNew[i] = guiObjects[i];
        	}
        	for (int i = length; i < length + 18; i++) {
        		guiObjectsNew[i] = servers.get((i - length) + 1);
        	}
        	guiObjects = guiObjectsNew.clone();
        	
        	for (int i = 0; i < servers.size(); i++) {
        		servers.get(i).setPositionY(servers.get(i).getPositionY()-20);
        	}
    	}	
    }

    @Override
    public GuiObject[] getGuiObjects() {
        return guiObjects;
    }
}
