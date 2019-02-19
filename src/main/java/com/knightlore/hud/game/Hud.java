package com.knightlore.hud.game;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;

import org.joml.Vector4f;
import com.knightlore.hud.engine.GameItem;
import com.knightlore.hud.engine.IHud;
import com.knightlore.hud.engine.TextItem;
import com.knightlore.hud.engine.Window;
import com.knightlore.hud.engine.graphics.FontTexture;

public class Hud implements IHud {

    private static final Font FONT = new Font("Press Start 2P", Font.PLAIN, 15);
    
    private static final Font FONT_25 = new Font("Press Start 2P", Font.PLAIN, 30);
    
    private static final Font FONT_LARGE = new Font("Press Start 2P", Font.PLAIN, 40);
    
    private static final Font FONT_TITLE = new Font("Press Start 2P", Font.PLAIN, 72);

    private static final String CHARSET = "ISO-8859-1";
    
    private static final int MAX_SCORE = 99999999;

    private GameItem[] gameItems;

    private final TextItem player1Score;
    
    private final TextItem player1Lives;
    
    private final TextItem player2Score;

    private final TextItem player2Lives;
    
    private final TextItem counter;
    
    private final TextItem exit;
    
    private final TextItem singleplayer;
    
    private final TextItem multiplayer;
    
    private final TextItem soundOn;
    
    private final TextItem soundOff;
    
    private final TextItem bongo;
    
    private final TextItem congo;

    public Hud(Window window) throws Exception {
    	InputStream myStream = new BufferedInputStream(new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"));
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    	
        FontTexture fontTexture = new FontTexture(FONT, CHARSET);
        FontTexture fontTextureLarge = new FontTexture(FONT_LARGE, CHARSET);
        FontTexture fontTexture25 = new FontTexture(FONT_25, CHARSET);
        FontTexture fontTextureTitle = new FontTexture(FONT_TITLE, CHARSET);
        
        this.player1Score = new TextItem("P1:00000000", fontTexture);
        this.player1Score.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
        
        this.player2Score = new TextItem("P2:00000000", fontTexture);
        this.player2Score.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
        
        this.player1Lives = new TextItem("***", fontTexture);
        this.player1Lives.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.player2Lives = new TextItem("***", fontTexture);
        this.player2Lives.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.counter = new TextItem("Time: 90", fontTexture25);
        this.counter.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.exit = new TextItem("Exit", fontTexture);
        this.exit.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.singleplayer = new TextItem("Singleplayer", fontTexture);
        this.singleplayer.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.multiplayer = new TextItem("Multiplayer", fontTexture);
        this.multiplayer.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.soundOn = new TextItem("(", fontTextureLarge);
        this.soundOn.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
        
        this.soundOff = new TextItem("/", fontTexture25);
        this.soundOff.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.bongo = new TextItem("Bongo", fontTextureTitle);
        this.bongo.getMesh().getMaterial().setColour(new Vector4f(0.29f, 0.92f, 0.95f, 1));
        
        this.congo = new TextItem("Congo", fontTextureTitle);
        this.congo.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.player1Score.setPosition(5, 5, 0);
        this.player2Score.setPosition(window.getWidth()-170, 5, 0);
        this.player1Lives.setPosition(5, 20, 0);
        this.player2Lives.setPosition(window.getWidth()-170, 20, 0);
        this.counter.setPosition(window.getWidth()/2-118, window.getHeight()-30, 0);
        this.exit.setPosition(5, window.getHeight()-20, 0);
        this.singleplayer.setPosition(window.getWidth()/2 - 90, window.getHeight()/2+100, 0);
        this.multiplayer.setPosition(window.getWidth()/2 - 82, window.getHeight()/2+120, 0);
        this.soundOn.setPosition(window.getWidth()-40, window.getHeight()-40, 0);
        this.soundOff.setPosition(window.getWidth()-30, window.getHeight()-30, 0);
        this.bongo.setPosition(window.getWidth()/2-360, window.getHeight()/2-200, 0);
        this.congo.setPosition(window.getWidth()/2, window.getHeight()/2-200, 0);

        //gameItems = new GameItem[]{player1Score, player2Score, counter, player1Lives,
        //		player2Lives, exit, singleplayer, multiplayer, bongo, congo, soundOn};
        gameItems = new GameItem[]{player1Score, player2Score, counter, player1Lives,
        		player2Lives, exit, soundOn};
    }

    public void setP1Score(String statusText) {
        this.player1Score.setText(statusText);
    }
    
    public void setP2Score(String statusText) {
        this.player2Score.setText(statusText);
    }
    
    public void setCounter(String statusText) {
    	this.counter.setText(statusText);
    	this.counter.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }
    
    public void setExit() {
    	this.exit.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
    }
    
    public void setRestoreExit() {
    	this.exit.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }
    
    public void setSingleplayer() {
    	this.singleplayer.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
    }
    
    public void setRestoreSingleplayer() {
    	this.singleplayer.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }
    
    public void setSound() {
    	this.soundOn.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }
    
    public void setRestoreSound() {
    	this.soundOn.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
    }
    
    public void setSoundOff() {
    	if (gameItems.length == 7) {
	    	GameItem[] gameItemsNew = new GameItem[gameItems.length +1];
	    	for (int i = 0; i < gameItems.length; i++) {
	    		gameItemsNew[i] = gameItems[i];
	    	}
	    	gameItemsNew[gameItems.length] = soundOff;
	    	gameItems = gameItemsNew.clone();
    	}
    	else if (gameItems.length == 8) {
	    	GameItem[] gameItemsNew = new GameItem[gameItems.length -1];
	    	for (int i = 0, k = 0; i < gameItems.length; i++) {
	    		if (i == gameItems.length-1) {
	    			continue;
	    		}
	    		gameItemsNew[k++] = gameItems[i];
	    	}
	    	gameItems = gameItemsNew.clone();
    	}
    }
    
    public void setP1Lives() {
    	String text = player1Lives.getText();
    	if (text.length() > 0 && text != "Dead") {
    		if (text.length() == 1) {
    			this.player1Lives.setText("Dead");
    		} else {
    			text = text.substring(0, text.length()-1);
    			this.player1Lives.setText(text);
    		}
    		this.player1Lives.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
    	}
    }
    
    public void setP1Score() {
    	int score = Integer.parseInt(player1Score.getText().substring(3, player1Score.getText().length()));
    	int increment = ThreadLocalRandom.current().nextInt(100, 10000 + 1);
    	score += increment;
    	if (score > MAX_SCORE) {
    		score = MAX_SCORE;
    	}
    	String text = String.format("%08d", score);
    	this.player1Score.setText("P1:"+text);
    }
    
    @Override
    public GameItem[] getGameItems() {
        return gameItems;
    }
    
    public void deleteGameItem() {
    	if (gameItems.length == 7) {
	    	GameItem[] gameItemsNew = new GameItem[gameItems.length -1];
	    	for (int i = 0, k = 0; i < gameItems.length; i++) {
	    		if (i == gameItems.length-1) {
	    			continue;
	    		}
	    		gameItemsNew[k++] = gameItems[i];
	    	}
	    	gameItems = gameItemsNew.clone();
    	}
    }
}