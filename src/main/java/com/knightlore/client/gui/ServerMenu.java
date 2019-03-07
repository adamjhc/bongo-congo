package com.knightlore.client.gui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.joml.Vector4f;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.LobbyObject;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.gui.engine.Window;
import com.knightlore.client.gui.engine.graphics.FontTexture;

public class ServerMenu implements IGui {
	
	private static final Font FONT_SMALL = new Font("Press Start 2P", Font.PLAIN, 15);
	
	private static final Font FONT_TITLE = new Font("Press Start 2P", Font.PLAIN, 72);
	
	private static final String CHARSET = "ISO-8859-1";
	
	private static final int MAX_SERVERS = 18;
	
    private GuiObject[] guiObjects;
    
    private TextObject[] textObjects;
    
    private final TextObject bongo;
    
    private final TextObject congo;
    
    private final TextObject multiplayer;
    
    private final TextObject separatorTop;
    
    private final TextObject separatorBot;
    
    private final TextObject join;
    
    private final TextObject create;
    
    private final TextObject exit;
    
    private final ArrayList<LobbyObject> servers;
    
    private int length;
    
    private int current = 0;
    
    private int yPos = 165;
    
    public ServerMenu(Window window) throws Exception {
    	InputStream myStream = new BufferedInputStream(new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"));
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    	
    	FontTexture fontSmall = new FontTexture(FONT_SMALL, CHARSET);
    	FontTexture fontTitle = new FontTexture(FONT_TITLE, CHARSET);
    	
    	this.bongo = new TextObject("Bongo", fontTitle);
        this.bongo.getMesh().getMaterial().setColour(new Vector4f(0.29f, 0.92f, 0.95f, 1));
        
        this.congo = new TextObject("Congo", fontTitle);
        this.congo.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.multiplayer = new TextObject("Play Multiplayer", fontSmall);
        this.multiplayer.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.separatorTop = new TextObject("------------------------------", fontSmall);
        this.separatorTop.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0 , 1));
        
        this.separatorBot = new TextObject("------------------------------", fontSmall);
        this.separatorBot.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0 , 1));
        
        this.join = new TextObject("Join game", fontSmall);
        this.join.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.create = new TextObject("Create game", fontSmall);
        this.create.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.exit = new TextObject("Exit", fontSmall);
        this.exit.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.bongo.setPosition(window.getWidth()/2-360, window.getHeight()/2-300, 0);
        this.congo.setPosition(window.getWidth()/2, window.getHeight()/2-300, 0);
        this.multiplayer.setPosition(window.getWidth()/2-120, window.getHeight()/2-200, 0);
        this.separatorTop.setPosition(window.getWidth()/2-225, window.getHeight()/2-185, 0);
        this.separatorBot.setPosition(window.getWidth()/2-225, window.getHeight()/2+200, 0);
        float width = (join.getText().length())*15/2;
        this.join.setPosition(window.getWidth()/2-width, window.getHeight()/2+235, 0);
        width = (create.getText().length())*15/2;
        this.create.setPosition(window.getWidth()/2-width, window.getHeight()/2+215, 0);
        this.exit.setPosition(window.getWidth()/2-30, window.getHeight()/2+255, 0);	
        
        servers = new ArrayList<LobbyObject>();
        
        for (int i = 0; i < 10; i++) {
        	servers.add(new LobbyObject(i+"'s "+"Server", fontSmall));
        }
        
        for (int i = 0; i < servers.size(); i++) {
        	width = (servers.get(i).getText().length())*15/2;
        	servers.get(i).getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        	servers.get(i).setPosition(window.getWidth()/2-width, window.getHeight()/2-yPos, 0);
        	yPos -= 20;
        }
        
        guiObjects = new GuiObject[]{bongo, congo, multiplayer, separatorTop, separatorBot, join, create, exit};
        length = guiObjects.length;
        
        textObjects = new TextObject[]{join, create, exit, separatorTop, separatorBot};
        
        addServers();
    }
    
    public void createServer(Window window) {
    	FontTexture fontTexture = null;
		try {
			fontTexture = new FontTexture(FONT_SMALL, CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	LobbyObject newServer = new LobbyObject("New Server "+servers.size(), fontTexture);
    	float width = (newServer.getText().length())*15/2;
    	newServer.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    	newServer.setPosition(window.getWidth()/2-width, window.getHeight()/2-yPos-(current*20), 0);
    	yPos -= 20;
    	
    	servers.add(newServer);
    	reAddServers();
    	for (int i = 0; i < servers.size(); i++) moveDown();
    }
    
    public void reAddServers() {
    	if (servers.size() <= MAX_SERVERS) {
        	GuiObject[] guiObjectsNew = new GuiObject[length + servers.size()];
        	for (int i = 0; i < length; i++) {
        		guiObjectsNew[i] = guiObjects[i];
        	}
        	for (int i = length; i < length + servers.size(); i++) {
        		guiObjectsNew[i] = servers.get(i - length);
        	}
        	guiObjects = guiObjectsNew.clone();
    	} 
    }
    
    public void addServers() {
    	if (servers.size() <= MAX_SERVERS) {
        	GuiObject[] guiObjectsNew = new GuiObject[guiObjects.length + servers.size()];
        	for (int i = 0; i < guiObjects.length; i++) {
        		guiObjectsNew[i] = guiObjects[i];
        	}
        	for (int i = guiObjects.length; i < guiObjects.length + servers.size(); i++) {
        		guiObjectsNew[i] = servers.get(i - guiObjects.length);
        	}
        	guiObjects = guiObjectsNew.clone();
    	} else {
        	GuiObject[] guiObjectsNew = new GuiObject[guiObjects.length + MAX_SERVERS];
        	for (int i = 0; i < guiObjects.length; i++) {
        		guiObjectsNew[i] = guiObjects[i];
        	}
        	for (int i = guiObjects.length; i < guiObjects.length + MAX_SERVERS; i++) {
        		guiObjectsNew[i] = servers.get(i - guiObjects.length);
        	}
        	guiObjects = guiObjectsNew.clone();
    	}
    }
    
    public void moveDown() {
    	if (servers.size() > MAX_SERVERS && current < servers.size()-MAX_SERVERS) {
    		current ++;
    		GuiObject[] guiObjectsNew = new GuiObject[length + MAX_SERVERS];
        	for (int i = 0; i < length; i++) {
        		guiObjectsNew[i] = guiObjects[i];
        	}
        	for (int i = length; i < length + MAX_SERVERS; i++) {
        		guiObjectsNew[i] = servers.get((i - length) + current);
        	}
        	guiObjects = guiObjectsNew.clone();
        	
        	for (int i = 0; i < servers.size(); i++) {
        		servers.get(i).setPositionY(servers.get(i).getPositionY()-20);
        	}
    	}	
    }
    
    public void moveUp() {
    	if (current > 0) {
    		current --;

    		GuiObject[] guiObjectsNew = new GuiObject[length + MAX_SERVERS];
        	for (int i = 0; i < length; i++) {
        		guiObjectsNew[i] = guiObjects[i];
        	}
        	for (int i = length; i < length + MAX_SERVERS; i++) {
        		guiObjectsNew[i] = servers.get((i - length) + current);
        	}
        	guiObjects = guiObjectsNew.clone();
        	
        	for (int i = 0; i < servers.size(); i++) {
        		servers.get(i).setPositionY(servers.get(i).getPositionY()+20);
        	}	
    	}	
    }
    
    public void highlight(Window window, double yPos) {
    	double pos = (yPos-(window.getHeight()/2-145))/20;
    	int posInt = (int) Math.ceil(pos);
    	if (posInt >= 0 && posInt < Math.min(MAX_SERVERS,servers.size())) {
    		setHighlight(window, posInt);
    	}
    }
    
    public void resetHighlight(Window window) {
    	for (int i = 0; i < servers.size(); i++) {
    		if (servers.get(i).getHighlighted() == true) {
    			servers.get(i).setHighlighted();
    			servers.get(i).setText(servers.get(i).getText().substring(4, servers.get(i).getText().length()-4));
    			float width = servers.get(i).getText().length()*15/2;
    			servers.get(i).setPositionX(window.getWidth()/2-width);
    			servers.get(i).getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    		}
    	}
    }
    
    public void setHighlight(Window window, int listPos) {
    	resetHighlight(window);
		servers.get(listPos+current).setHighlighted();
		servers.get(listPos+current).setText("=== "+servers.get(listPos+current).getText()+" ===");
		float width = servers.get(listPos+current).getText().length()*15/2;
		servers.get(listPos+current).setPositionX(window.getWidth()/2-width);
    }
    
    public TextObject getCreate() {
    	return create;
    }
    
    public TextObject getSeparatorTop() {
    	return separatorTop;
    }
    
    public TextObject getSeparatorBot() {
    	return separatorBot;
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
