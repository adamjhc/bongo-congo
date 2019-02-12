package com.knightlore.hud.game;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import org.joml.Vector4f;
import com.knightlore.hud.engine.GameItem;
import com.knightlore.hud.engine.IHud;
import com.knightlore.hud.engine.TextItem;
import com.knightlore.hud.engine.Window;
import com.knightlore.hud.engine.graph.FontTexture;

public class Hud implements IHud {

    private static final Font FONT = new Font("Press Start 2P", Font.PLAIN, 15);

    private static final String CHARSET = "ISO-8859-1";

    private final GameItem[] gameItems;

    private final TextItem player1Score;
    
    private final TextItem player1Lives;
    
    private final TextItem player2Score;

    private final TextItem player2Lives;
    
    private final TextItem counter;

    public Hud(String statusText) throws Exception {
    	InputStream myStream = new BufferedInputStream(new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"));
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    	
        FontTexture fontTexture = new FontTexture(FONT, CHARSET);
        this.player1Score = new TextItem(statusText, fontTexture);
        this.player1Score.getMesh().getMaterial().setAmbientColour(new Vector4f(1, 1, 1, 1));
        
        this.player2Score = new TextItem("P2:00000000", fontTexture);
        this.player2Score.getMesh().getMaterial().setAmbientColour(new Vector4f(1, 1, 1, 1));
        
        this.player1Lives = new TextItem("***", fontTexture);
        this.player1Lives.getMesh().getMaterial().setAmbientColour(new Vector4f(1, 0, 0, 1));
        
        this.player2Lives = new TextItem("***", fontTexture);
        this.player2Lives.getMesh().getMaterial().setAmbientColour(new Vector4f(1, 0, 0, 1));
        
        this.counter = new TextItem("Time: 90", fontTexture);
        this.counter.getMesh().getMaterial().setAmbientColour(new Vector4f(1, 1, 0, 1));

        gameItems = new GameItem[]{player1Score, player2Score, counter, player1Lives, player2Lives};
    }

    public void setStatusText(String statusText) {
        this.player1Score.setText(statusText);
    }
    
    public void setStatusText2(String statusText) {
        this.player2Score.setText(statusText);
    }
    
    public void setCounter(String statusText) {
    	this.counter.setText(statusText);
    	this.counter.getMesh().getMaterial().setAmbientColour(new Vector4f(1, 1, 0, 1));
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
    		this.player1Lives.getMesh().getMaterial().setAmbientColour(new Vector4f(1, 0, 0, 1));
    	}
    }
    
    @Override
    public GameItem[] getGameItems() {
        return gameItems;
    }
   
    public void updateSize(Window window) {
        this.player1Score.setPosition(5, 5, 0);
        this.player2Score.setPosition(430, 5, 0);
        this.player1Lives.setPosition(5, 20, 0);
        this.player2Lives.setPosition(430, 20, 0);
        this.counter.setPosition(240, 5, 0);
    }
}
