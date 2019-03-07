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
	
	private static final Font FONT_SMALL = new Font("Press Start 2P", Font.PLAIN, 16);
	
	private static final Font FONT_TITLE = new Font("Press Start 2P", Font.PLAIN, 72);
	
	private static final String CHARSET = "ISO-8859-1";
	
	private static final int MAX_SERVERS = 18;
	
	private static final int TITLE_POS = 300;
	
	private static final int SEPARATORTOP_POS = 185;
	
	private static final int SEPARATORBOT_POS = 200;
	
	private static final int SEPARATOR_GAP = 16;
	
	private static final int SERVER_GAP = 20;
	
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
    
    private int yPos = SEPARATORTOP_POS-SERVER_GAP;
    
    public ServerMenu(Window window) throws Exception {
    	InputStream myStream = new BufferedInputStream(new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"));
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    	
    	FontTexture fontSmall = new FontTexture(FONT_SMALL, CHARSET);
    	FontTexture fontTitle = new FontTexture(FONT_TITLE, CHARSET);
    	
    	this.bongo = new TextObject("Bongo", fontTitle);
        this.bongo.setColour(new Vector4f(0.29f, 0.92f, 0.95f, 1));
        
        this.congo = new TextObject("Congo", fontTitle);
        this.congo.setColour(new Vector4f(1, 0, 0, 1));
        
        this.multiplayer = new TextObject("Play Multiplayer", fontSmall);
        this.multiplayer.setColour(new Vector4f(1, 1, 0, 1));
        
        this.separatorTop = new TextObject("------------------------------", fontSmall);
        this.separatorTop.setColour(new Vector4f(1, 1, 0 , 1));
        
        this.separatorBot = new TextObject("------------------------------", fontSmall);
        this.separatorBot.setColour(new Vector4f(1, 1, 0 , 1));
        
        this.join = new TextObject("Join game", fontSmall);
        this.join.setColour(new Vector4f(1, 1, 0, 1));
        
        this.create = new TextObject("Create game", fontSmall);
        this.create.setColour(new Vector4f(1, 1, 0, 1));
        
        this.exit = new TextObject("Exit", fontSmall);
        this.exit.setColour(new Vector4f(1, 1, 0, 1));
        
        this.bongo.setPosition(window.getWidth()/2-bongo.getSize(), window.getHeight()/2-TITLE_POS);
        this.congo.setPosition(window.getWidth()/2, window.getHeight()/2-TITLE_POS);
        this.multiplayer.setPosition(window.getWidth()/2-multiplayer.getSize()/2, window.getHeight()/2-SEPARATORTOP_POS-SEPARATOR_GAP);
        this.separatorTop.setPosition(window.getWidth()/2-separatorTop.getSize()/2, window.getHeight()/2-SEPARATORTOP_POS);
        this.separatorBot.setPosition(window.getWidth()/2-separatorTop.getSize()/2, window.getHeight()/2+SEPARATORBOT_POS);
        this.create.setPosition(window.getWidth()/2-create.getSize()/2, window.getHeight()/2+SEPARATORBOT_POS+SERVER_GAP);
        this.join.setPosition(window.getWidth()/2-join.getSize()/2, window.getHeight()/2+SEPARATORBOT_POS+SERVER_GAP*2);
        this.exit.setPosition(window.getWidth()/2-exit.getSize()/2, window.getHeight()/2+SEPARATORBOT_POS+SERVER_GAP*3);	
        
        servers = new ArrayList<LobbyObject>();
        
        for (int i = 0; i < 10; i++) {
        	servers.add(new LobbyObject(i+"'s "+"Server", fontSmall));
        }
        
        for (int i = 0; i < servers.size(); i++) {
        	servers.get(i).setColour(new Vector4f(1, 1, 0, 1));
        	servers.get(i).setPosition(window.getWidth()/2-servers.get(i).getSize()/2, window.getHeight()/2-yPos);
        	yPos -= SERVER_GAP;
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
    	newServer.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    	newServer.setPosition(window.getWidth()/2-newServer.getSize()/2, window.getHeight()/2-yPos-(current*SERVER_GAP));
    	yPos -= SERVER_GAP;
    	
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
        		servers.get(i).setPositionY(servers.get(i).getPositionY()-SERVER_GAP);
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
        		servers.get(i).setPositionY(servers.get(i).getPositionY()+SERVER_GAP);
        	}	
    	}	
    }
    
    public void highlight(Window window, double yPos) {
    	double pos = (yPos-(window.getHeight()/2-(SEPARATORTOP_POS-SERVER_GAP*2)))/SERVER_GAP;
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
    			servers.get(i).setPositionX(window.getWidth()/2-servers.get(i).getSize()/2);
    			servers.get(i).setColour(new Vector4f(1, 1, 0, 1));
    		}
    	}
    }
    
    public void setHighlight(Window window, int listPos) {
    	resetHighlight(window);
		servers.get(listPos+current).setHighlighted();
		servers.get(listPos+current).setText("=== "+servers.get(listPos+current).getText()+" ===");
		servers.get(listPos+current).setPositionX(window.getWidth()/2-servers.get(listPos+current).getSize()/2);
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
