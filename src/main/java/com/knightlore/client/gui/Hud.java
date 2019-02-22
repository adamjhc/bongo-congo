package com.knightlore.client.gui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;

import org.joml.Vector4f;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.gui.engine.Window;
import com.knightlore.client.gui.engine.graphics.FontTexture;

public class Hud implements IGui {

    private static final Font FONT = new Font("Press Start 2P", Font.PLAIN, 15);
    
    private static final Font FONT_BACKGROUND = new Font("Press Start 2P", Font.PLAIN, 50);
    
    private static final Font FONT_25 = new Font("Press Start 2P", Font.PLAIN, 30);
    
    private static final Font FONT_LARGE = new Font("Press Start 2P", Font.PLAIN, 40);
    
    private static final String CHARSET = "ISO-8859-1";
    
    private static final int MAX_SCORE = 99999999;

    private GuiObject[] guiObjects;

    private final TextObject player1Score;
    
    private final TextObject player1Lives;
    
    private final TextObject player2Score;

    private final TextObject player2Lives;
    
    private final TextObject counter;
    
    private final TextObject exit;

    private final TextObject soundOn;
    
    private final TextObject soundOff;
    
    private final TextObject backgroundTop;
    
    private final TextObject backgroundBottom;
    
    private final TextObject backgroundLeft;
    
    private final TextObject backgroundRight;

    public Hud(Window window) throws Exception {
    	InputStream myStream = new BufferedInputStream(new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"));
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    	
        FontTexture fontTexture = new FontTexture(FONT, CHARSET);
        FontTexture fontTextureLarge = new FontTexture(FONT_LARGE, CHARSET);
        FontTexture fontTexture25 = new FontTexture(FONT_25, CHARSET);
        FontTexture backgroundFont = new FontTexture(FONT_BACKGROUND, CHARSET);
        
        this.player1Score = new TextObject("P1:00000000", fontTexture);
        this.player1Score.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
        
        this.player2Score = new TextObject("P2:00000000", fontTexture);
        this.player2Score.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
        
        this.player1Lives = new TextObject("***", fontTexture);
        this.player1Lives.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.player2Lives = new TextObject("***", fontTexture);
        this.player2Lives.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.counter = new TextObject("Time: 90", fontTexture25);
        this.counter.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.exit = new TextObject("Exit", fontTexture);
        this.exit.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));

        this.soundOn = new TextObject("(", fontTextureLarge);
        this.soundOn.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
        
        this.backgroundTop = new TextObject("))))))))))))))))))))))))))", backgroundFont);
        this.backgroundTop.getMesh().getMaterial().setColour(new Vector4f(0, 0, 0, 1));
        
        this.backgroundBottom = new TextObject("))))))))))))))))))))))))))", backgroundFont);
        this.backgroundBottom.getMesh().getMaterial().setColour(new Vector4f(0, 0, 0, 1));
        
        this.backgroundLeft = new TextObject("))))))))))))))))))))))))))", backgroundFont);
        this.backgroundLeft.getMesh().getMaterial().setColour(new Vector4f(0, 0, 0, 1));
        
        this.backgroundRight = new TextObject("))))))))))))))))))))))))))", backgroundFont);
        this.backgroundRight.getMesh().getMaterial().setColour(new Vector4f(0, 0, 0, 1));
        
        
        this.soundOff = new TextObject("/", fontTexture25);
        this.soundOff.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        this.player1Score.setPosition(5, 5, 0);
        this.player2Score.setPosition(window.getWidth()-170, 5, 0);
        this.player1Lives.setPosition(5, 20, 0);
        this.player2Lives.setPosition(window.getWidth()-170, 20, 0);
        this.counter.setPosition(window.getWidth()/2-118, window.getHeight()-30, 0);
        this.exit.setPosition(5, window.getHeight()-20, 0);
        this.soundOn.setPosition(window.getWidth()-40, window.getHeight()-40, 0);
        this.soundOff.setPosition(window.getWidth()-30, window.getHeight()-30, 0);
        this.backgroundTop.setPosition(0, -10, 0);
        this.backgroundBottom.setPosition(window.getWidth(), window.getHeight()+10, 0);
        this.backgroundLeft.setPosition(-10, window.getHeight(), 0);
        this.backgroundRight.setPosition(window.getWidth()+10, 0, 0);
        
        this.backgroundBottom.setRotation(0, 0, 180);
        this.backgroundLeft.setRotation(0, 0, 90);
        this.backgroundRight.setRotation(0, 0, 270);

        //guiObjects = new GuiObject[]{backgroundTop, backgroundBottom, backgroundLeft, backgroundRight, player1Score, player2Score, counter, player1Lives,
        //		player2Lives, exit, soundOn};
        guiObjects = new GuiObject[]{player1Score, player2Score, counter, player1Lives,
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
    
    public void setSound() {
    	this.soundOn.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }
    
    public void setRestoreSound() {
    	this.soundOn.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
    }
    
    public void setSoundOff() {
    	if (guiObjects.length == 7) {
	    	GuiObject[] guiObjectsNew = new GuiObject[guiObjects.length +1];
	    	for (int i = 0; i < guiObjects.length; i++) {
	    		guiObjectsNew[i] = guiObjects[i];
	    	}
	    	guiObjectsNew[guiObjects.length] = soundOff;
	    	guiObjects = guiObjectsNew.clone();
    	}
    	else if (guiObjects.length == 8) {
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
