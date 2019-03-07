package com.knightlore.client.gui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import org.joml.Vector4f;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.gui.engine.Window;
import com.knightlore.client.gui.engine.graphics.FontTexture;

public class PreLevelEditor implements IGui {
	
	private static final Font FONT = new Font("Press Start 2P", Font.PLAIN, 15);
	private static final Font FONT_MEDIUM = new Font("Press Start 2P", Font.PLAIN, 30);
	private static final Font FONT_TITLE = new Font("Press Start 2P", Font.PLAIN, 72);
	private static final String CHARSET = "ISO-8859-1";
	
	private static final int MAX_WIDTH = 50;
	private static final int MAX_LENGTH = 50;
	private static final int MAX_HEIGHT = 50;
	
	private GuiObject[] guiObjects;
	
	private final TextObject bongo;
	private final TextObject congo;
	
	private final TextObject width;
	private final TextObject length;
	private final TextObject height;
	
	private final TextObject wNum;
	private final TextObject lNum;
	private final TextObject hNum;
	
	private final TextObject wLeft;
	private final TextObject wRight;
	private final TextObject lLeft;
	private final TextObject lRight;
	private final TextObject hLeft;
	private final TextObject hRight;
	
	private final TextObject separatorTop;
	private final TextObject separatorBottom;
	
	private final TextObject createLevel;
	private final TextObject back;
	
	public PreLevelEditor(Window window) throws Exception {
		InputStream myStream = new BufferedInputStream(new FileInputStream("src/main/resources/fonts/Press Start 2P.ttf"));
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, myStream));
    	
    	FontTexture fontTexture = new FontTexture(FONT, CHARSET);
    	FontTexture fontTextureMedium = new FontTexture(FONT_MEDIUM, CHARSET);
    	FontTexture fontTextureTitle = new FontTexture(FONT_TITLE, CHARSET);
    	
    	this.bongo = new TextObject("Bongo", fontTextureTitle);
        this.bongo.getMesh().getMaterial().setColour(new Vector4f(0.29f, 0.92f, 0.95f, 1));
        
        this.congo = new TextObject("Congo", fontTextureTitle);
        this.congo.getMesh().getMaterial().setColour(new Vector4f(1, 0, 0, 1));
        
        this.width = new TextObject("Width", fontTextureMedium);
        this.width.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.length = new TextObject("Length", fontTextureMedium);
        this.length.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.height = new TextObject("Height", fontTextureMedium);
        this.height.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.wNum = new TextObject("10", fontTexture);
        this.wNum.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.lNum = new TextObject("10", fontTexture);
        this.lNum.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.hNum = new TextObject("10", fontTexture);
        this.hNum.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.wLeft = new TextObject("<", fontTexture);
        this.wLeft.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.wRight = new TextObject(">", fontTexture);
        this.wRight.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.lLeft = new TextObject("<", fontTexture);
        this.lLeft.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.lRight = new TextObject(">", fontTexture);
        this.lRight.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.hLeft = new TextObject("<", fontTexture);
        this.hLeft.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.hRight = new TextObject(">", fontTexture);
        this.hRight.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.separatorTop = new TextObject("------------------------------", fontTexture);
        this.separatorTop.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0 , 1));
        
        this.separatorBottom = new TextObject("------------------------------", fontTexture);
        this.separatorBottom.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0 , 1));
        
        this.createLevel = new TextObject("Create Level", fontTextureMedium);
        this.createLevel.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
        
        this.back = new TextObject("Back", fontTexture);
        this.back.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));


        this.bongo.setPosition(window.getWidth()/2-360, window.getHeight()/2-300, 0);
        this.congo.setPosition(window.getWidth()/2, window.getHeight()/2-300, 0);
        this.separatorTop.setPosition(window.getWidth()/2-225, window.getHeight()/2-185, 0);
        this.separatorBottom.setPosition(window.getWidth()/2-225, window.getHeight()/2+240, 0);
        
        this.width.setPosition(window.getWidth()/2-80, window.getHeight()/2-145, 0);
        this.length.setPosition(window.getWidth()/2-94, window.getHeight()/2-35, 0);
        this.height.setPosition(window.getWidth()/2-94, window.getHeight()/2+75, 0);
        
        this.wNum.setPosition(window.getWidth()/2-22, window.getHeight()/2-90, 0);
        this.lNum.setPosition(window.getWidth()/2-22, window.getHeight()/2+20, 0);
        this.hNum.setPosition(window.getWidth()/2-22, window.getHeight()/2+130, 0);
        
        this.wLeft.setPosition(window.getWidth()/2-52, window.getHeight()/2-90, 0);
        this.wRight.setPosition(window.getWidth()/2+22, window.getHeight()/2-90, 0);
        
        this.lLeft.setPosition(window.getWidth()/2-52, window.getHeight()/2+20, 0);
        this.lRight.setPosition(window.getWidth()/2+22, window.getHeight()/2+20, 0);
        
        this.hLeft.setPosition(window.getWidth()/2-52, window.getHeight()/2+130, 0);
        this.hRight.setPosition(window.getWidth()/2+22, window.getHeight()/2+130, 0);
        
        this.createLevel.setPosition(window.getWidth()/2-175, window.getHeight()/2+210, 0);
        
        this.back.setPosition(window.getWidth()/2-30, window.getHeight()/2+260, 0);
        
        guiObjects = new GuiObject[] {bongo, congo, width, length, height, wNum, lNum, hNum, wLeft, wRight, lLeft, lRight, hLeft, hRight, separatorTop, separatorBottom, createLevel, back};
        
	}
	
	public void incWidth() {
		int newWidth = Integer.parseInt(this.wNum.getText());
    	if (newWidth < MAX_WIDTH) newWidth ++;
    	this.wNum.setText((String.format("%02d", newWidth)));
    	this.wNum.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
	}
	
	public void decWidth() {
    	int newWidth = Integer.parseInt(this.wNum.getText());
    	if (newWidth > 1) newWidth --;
    	this.wNum.setText((String.format("%02d", newWidth)));
    	this.wNum.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }
	
	public void incLength() {
		int newLength = Integer.parseInt(this.lNum.getText());
    	if (newLength < MAX_LENGTH) newLength ++;
    	this.lNum.setText((String.format("%02d", newLength)));
    	this.lNum.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
	}
	
	public void decLength() {
    	int newLength = Integer.parseInt(this.lNum.getText());
    	if (newLength > 1) newLength --;
    	this.lNum.setText((String.format("%02d", newLength)));
    	this.lNum.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }
	
	public void incHeight() {
		int newHeight = Integer.parseInt(this.hNum.getText());
    	if (newHeight < MAX_HEIGHT) newHeight ++;
    	this.hNum.setText((String.format("%02d", newHeight)));
    	this.hNum.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
	}
	
	public void decHeight() {
    	int newHeight = Integer.parseInt(this.hNum.getText());
    	if (newHeight > 1) newHeight --;
    	this.hNum.setText((String.format("%02d", newHeight)));
    	this.hNum.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
    }
	
	public void setWRight() {
		this.wRight.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
	}
	
	public void setRestoreWRight() {
		this.wRight.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
	}
	
	public void setHRight() {
		this.hRight.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
	}
	
	public void setRestoreHRight() {
		this.hRight.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
	}
	
	public void setLRight() {
		this.lRight.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
	}
	
	public void setRestoreLRight() {
		this.lRight.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
	}
	
	public void setWLeft() {
		this.wLeft.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
	}
	
	public void setRestoreWLeft() {
		this.wLeft.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
	}
	
	public void setHLeft() {
		this.hLeft.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
	}
	
	public void setRestoreHLeft() {
		this.hLeft.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
	}
	
	public void setLLeft() {
		this.lLeft.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
	}
	
	public void setRestoreLLeft() {
		this.lLeft.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
	}
	
	public void setCreateLevel() {
		this.createLevel.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
	}
	
	public void setRestoreCreateLevel() {
		this.createLevel.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
	}
	
	public void setBack() {
		this.back.getMesh().getMaterial().setColour(new Vector4f(1, 1, 1, 1));
	}
	
	public void setRestoreBack() {
		this.back.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0, 1));
	}
	
	public int getWidth() {
		return Integer.parseInt(this.wNum.getText());
	}
	
	public int getLength() {
		return Integer.parseInt(this.lNum.getText());
	}
	
	public int getHeight() {
		return Integer.parseInt(this.hNum.getText());
	}

	@Override
	public GuiObject[] getGuiObjects() {
		return guiObjects;
	}

}
