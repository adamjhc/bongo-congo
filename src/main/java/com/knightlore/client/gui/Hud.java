package com.knightlore.client.gui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import org.joml.Vector3f;
import org.joml.Vector4f;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.gui.engine.Window;
import com.knightlore.client.gui.engine.graphics.FontTexture;

public class Hud implements IGui {

    private static final Font FONT_SMALL = new Font("Press Start 2P", Font.PLAIN, 15);
    
    private static final Font FONT_MEDIUM = new Font("Press Start 2P", Font.PLAIN, 30);
    
    private static final Font FONT_LARGE = new Font("Press Start 2P", Font.PLAIN, 40);
    
    private static final Font FONT_LIVES = new Font("Press Start 2P", Font.PLAIN, 20);
    
    private static final String CHARSET = "ISO-8859-1";
    
    private static final int MAX_SCORE = 99999999;

    private GuiObject[] guiObjects;
    
    private TextObject[] textObjects;

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
    
    private final TextObject soundOn;
    
    private final TextObject soundOff;
    
    public Hud(Window window) throws Exception {
    	InputStream myStream = new BufferedInputStream(new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"));
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    	
        FontTexture fontSmall = new FontTexture(FONT_SMALL, CHARSET);
        FontTexture fontMedium = new FontTexture(FONT_MEDIUM, CHARSET);
        FontTexture fontLarge = new FontTexture(FONT_LARGE, CHARSET);
        FontTexture fontLives = new FontTexture(FONT_LIVES, CHARSET);
        
        this.player1Score = new TextObject("P1:00000000", fontSmall);
        this.player1Score.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
        
        this.player2Score = new TextObject("P2:00000000", fontSmall);
        this.player2Score.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.player3Score = new TextObject("P3:00000000", fontSmall);
        this.player3Score.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.player4Score = new TextObject("P4:00000000", fontSmall);
        this.player4Score.getMesh().getMaterial().setColour(new Vector4f(0, 1, 1, 1));
        
        this.player5Score = new TextObject("P5:00000000", fontSmall);
        this.player5Score.getMesh().getMaterial().setColour(new Vector4f(1, 0, 1, 1));
        
        this.player6Score = new TextObject("P6:00000000", fontSmall);
        this.player6Score.getMesh().getMaterial().setColour(new Vector4f(0, 0, 1, 1));
        
        this.player1Lives = new TextObject("***", fontLives);
        this.player1Lives.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.player2Lives = new TextObject("***", fontLives);
        this.player2Lives.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.player3Lives = new TextObject("***", fontLives);
        this.player3Lives.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));

        this.player4Lives = new TextObject("***", fontLives);
        this.player4Lives.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));

        this.player5Lives = new TextObject("***", fontLives);
        this.player5Lives.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.player6Lives = new TextObject("***", fontLives);
        this.player6Lives.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.counter = new TextObject("90", fontMedium);
        this.counter.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.soundOn = new TextObject("(", fontLarge);
        this.soundOn.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
        
        this.soundOff = new TextObject("/", fontMedium);
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
        this.soundOn.setPosition(window.getWidth()-40, window.getHeight()-40, 0);
        this.soundOff.setPosition(window.getWidth()-30, window.getHeight()-30, 0);
        
        this.soundOff.setRender();
        
        guiObjects = new GuiObject[]{player1Score, player2Score, player3Score, player4Score, player5Score, player6Score, player1Lives,
        		player2Lives, player3Lives, player4Lives, player5Lives, player6Lives, counter, soundOn, soundOff};
        textObjects = new TextObject[]{};
    }
    
    public void moveScore(float move, float targetXPos) {
    	float xPosScore = player2Score.getPositionX();
    	float xPosLives = player2Lives.getPositionX();
    	
    	if (xPosScore < targetXPos && move > 0) {
    		setPosition(move, xPosScore, xPosLives);
    		if (player2Score.getPositionX() > targetXPos) {
    			setPosition(0, targetXPos, targetXPos+170);
    		}
    	}
    	else if (xPosScore > targetXPos && move < 0) {
    		setPosition(move, xPosScore, xPosLives);
    	}
    }
    
    public void setPosition(float move, float xPosScore, float xPosLives) {
		player2Score.setPositionX(xPosScore+move);
		player3Score.setPositionX(xPosScore+move);
		player4Score.setPositionX(xPosScore+move);
		player5Score.setPositionX(xPosScore+move);
		player6Score.setPositionX(xPosScore+move);
		player2Lives.setPositionX(xPosLives+move);
		player3Lives.setPositionX(xPosLives+move);
		player4Lives.setPositionX(xPosLives+move);
		player5Lives.setPositionX(xPosLives+move);
		player6Lives.setPositionX(xPosLives+move);
    }
    
    public void setCounter(String statusText) {
    	this.counter.setText(statusText);
    	this.counter.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }
    
    public void setP1Lives(int lives) {
    	if (lives <= 0) {
    		this.player1Lives.setText("Dead");
    	}
    	else {
    		String livesText = "";
    		for (int i = 0; i < lives; i++) {
    			livesText += "*";
    		}
    		this.player1Lives.setText(livesText);
    	}
    	this.player1Lives.setColour(new Vector4f(1, 0, 0, 1));
    }
    
    public void setP1Score(int score) {
    	if (score > MAX_SCORE) {
    		score = MAX_SCORE;
    	}
    	String text = String.format("%08d", score);
    	this.player1Score.setText("P1:"+text);
    }
    
    public TextObject getCounter() {
    	return counter;
    }
    
    public TextObject getP1Score() {
    	return player1Score;
    }
    
    public TextObject getSoundMute() {
    	return soundOff;
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
