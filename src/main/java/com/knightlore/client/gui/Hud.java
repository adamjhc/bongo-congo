package com.knightlore.client.gui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;

import org.joml.Vector3f;
import org.joml.Vector4f;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.gui.engine.Window;
import com.knightlore.client.gui.engine.graphics.FontTexture;

public class Hud implements IGui {

    private static final Font FONT = new Font("Press Start 2P", Font.PLAIN, 15);

    private static final Font FONT_LIVES = new Font("Press Start 2P", Font.PLAIN, 20);
    
    private static final Font FONT_BACKGROUND = new Font("Press Start 2P", Font.PLAIN, 48);
    
    private static final Font FONT_25 = new Font("Press Start 2P", Font.PLAIN, 30);
    
    private static final Font FONT_LARGE = new Font("Press Start 2P", Font.PLAIN, 40);
    
    private static final String CHARSET = "ISO-8859-1";
    
    private static final int MAX_SCORE = 99999999;

    private GuiObject[] guiObjects;

    private final TextObject player1Score;
    
    private final TextObject player1Lives;
    
    private final TextObject player2Score;

    private final TextObject player2Lives;
    
    private final TextObject player3Score;
    
    private final TextObject player3Lives;
    
    private final TextObject player4Score;
    
    private final TextObject player4Lives;
    
    private final TextObject player5Score;
    
    private final TextObject player5Lives;
    
    private final TextObject player6Score;
    
    private final TextObject player6Lives;
    
    private final TextObject counter;
    
    private final TextObject exit;

    private final TextObject soundOn;
    
    private final TextObject soundOff;
    
    public Hud(Window window) throws Exception {
    	InputStream myStream = new BufferedInputStream(new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"));
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    	
        FontTexture fontTexture = new FontTexture(FONT, CHARSET);
        FontTexture fontTextureLarge = new FontTexture(FONT_LARGE, CHARSET);
        FontTexture fontTexture25 = new FontTexture(FONT_25, CHARSET);
        FontTexture backgroundFont = new FontTexture(FONT_BACKGROUND, CHARSET);
        FontTexture fontTextureLives = new FontTexture(FONT_LIVES, CHARSET);
        
        this.player1Score = new TextObject("P1:00000000", fontTexture);
        this.player1Score.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
        
        this.player2Score = new TextObject("P2:00000000", fontTexture);
        this.player2Score.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.player3Score = new TextObject("P3:00000000", fontTexture);
        this.player3Score.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.player4Score = new TextObject("P4:00000000", fontTexture);
        this.player4Score.getMesh().getMaterial().setColour(new Vector4f(0, 1, 1, 1));
        
        this.player5Score = new TextObject("P5:00000000", fontTexture);
        this.player5Score.getMesh().getMaterial().setColour(new Vector4f(1, 0, 1, 1));
        
        this.player6Score = new TextObject("P6:00000000", fontTexture);
        this.player6Score.getMesh().getMaterial().setColour(new Vector4f(0, 0, 1, 1));
        
        this.player1Lives = new TextObject("***", fontTextureLives);
        this.player1Lives.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.player2Lives = new TextObject("***", fontTextureLives);
        this.player2Lives.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.player3Lives = new TextObject("***", fontTextureLives);
        this.player3Lives.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));

        this.player4Lives = new TextObject("***", fontTextureLives);
        this.player4Lives.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));

        this.player5Lives = new TextObject("***", fontTextureLives);
        this.player5Lives.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.player6Lives = new TextObject("***", fontTextureLives);
        this.player6Lives.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.counter = new TextObject("90", fontTexture25);
        this.counter.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.exit = new TextObject("Exit", fontTexture);
        this.exit.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));

        this.soundOn = new TextObject("(", fontTextureLarge);
        this.soundOn.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
        
        this.soundOff = new TextObject("/", fontTexture25);
        this.soundOff.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.player1Score.setPosition(5, 5, 0);
        this.player2Score.setPosition(-230, 25, 0);
        this.player3Score.setPosition(-230, 45, 0);
        this.player4Score.setPosition(-230, 65, 0);
        this.player5Score.setPosition(-230, 85, 0);
        this.player6Score.setPosition(-230, 105, 0);
        
        this.player1Lives.setPosition(175, 1, 0);
        this.player2Lives.setPosition(-60, 21, 0);
        this.player3Lives.setPosition(-60, 41, 0);
        this.player4Lives.setPosition(-60, 61, 0);
        this.player5Lives.setPosition(-60, 81, 0);
        this.player6Lives.setPosition(-60, 101, 0);
        
        this.counter.setPosition(1175, window.getHeight()-30, 0);
        this.exit.setPosition(5, window.getHeight()-20, 0);
        this.soundOn.setPosition(window.getWidth()-40, window.getHeight()-40, 0);
        this.soundOff.setPosition(window.getWidth()-30, window.getHeight()-30, 0);
        
        //toggleScore(false);
        toggleSound();
        
        guiObjects = new GuiObject[]{player1Score, player2Score, player3Score, player4Score, player5Score, player6Score, player1Lives,
        		player2Lives, player3Lives, player4Lives, player5Lives, player6Lives, counter, soundOn, soundOff};
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
    
    public void setSound() {
    	this.soundOn.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }
    
    public void setRestoreSound() {
    	this.soundOn.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
    }
    
    public void toggleScore(boolean state) {
        this.player2Lives.setRender(state);
        this.player2Score.setRender(state);
        this.player3Lives.setRender(state);
        this.player3Score.setRender(state);
        this.player4Lives.setRender(state);
        this.player4Score.setRender(state);
        this.player5Lives.setRender(state);
        this.player5Score.setRender(state);
        this.player6Lives.setRender(state);
        this.player6Score.setRender(state);
    }
    
    public void moveScore(float move, float targetXPos) {
    	float xPosScore = this.player2Score.getPositionX();
    	float xPosLives = this.player2Lives.getPositionX();
    	
    	if (xPosScore < targetXPos && move > 0) {
    		setPosition(move, xPosScore, xPosLives);
    		if (this.player2Score.getPositionX() > targetXPos) {
    			setPosition(0, targetXPos, targetXPos+170);
    		}
    	}
    	else if (xPosScore > targetXPos && move < 0) {
    		setPosition(move, xPosScore, xPosLives);
    	}
    }
    
    public void setPosition(float move, float xPosScore, float xPosLives) {
		this.player2Score.setPositionX(xPosScore+move);
		this.player3Score.setPositionX(xPosScore+move);
		this.player4Score.setPositionX(xPosScore+move);
		this.player5Score.setPositionX(xPosScore+move);
		this.player6Score.setPositionX(xPosScore+move);
		this.player2Lives.setPositionX(xPosLives+move);
		this.player3Lives.setPositionX(xPosLives+move);
		this.player4Lives.setPositionX(xPosLives+move);
		this.player5Lives.setPositionX(xPosLives+move);
		this.player6Lives.setPositionX(xPosLives+move);
    }
    
    public void toggleSound() {
    	this.soundOff.setRender();
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
    
    public void resetP1Lives() {
    	this.player1Lives.setText("***");
    	this.player1Lives.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
    }
    
    public boolean isP1Dead() {
    	if (player1Lives.getText() == "Dead") {
    		return true;
    	}
    	else return false;
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
    public GuiObject[] getGuiObjects() {
        return guiObjects;
    }
    
    public void deleteGameItem() {
    	if (guiObjects.length == 7) {
	    	GuiObject[] guiObjectsNew = new GuiObject[guiObjects.length -1];
	    	for (int i = 0, k = 0; i < guiObjects.length; i++) {
	    		if (i == guiObjects.length-1) {
	    			continue;
	    		}
	    		guiObjectsNew[k++] = guiObjects[i];
	    	}
	    	guiObjects = guiObjectsNew.clone();
    	}
    }
}
