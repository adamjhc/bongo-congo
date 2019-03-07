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
	
	private static final int SEPARATORTOP_POS = 185;
	
	private static final int SEPARATORBOT_POS = 200;
	
	private static final int SEPARATOR_GAP = 16;
	
	private static final int MAX_WIDTH = 50;
	private static final int MAX_LENGTH = 50;
	private static final int MAX_HEIGHT = 50;
	
	private GuiObject[] guiObjects;
	private TextObject[] textObjects;
	
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
    	
    	FontTexture fontTexture = new FontTexture(FONT_SMALL, CHARSET);
    	FontTexture fontTextureMedium = new FontTexture(FONT_MEDIUM, CHARSET);
    	FontTexture fontTextureTitle = new FontTexture(FONT_TITLE, CHARSET);
    	
    	this.bongo = new TextObject("Bongo", fontTextureTitle);
        this.bongo.getMesh().getMaterial().setColour(LIGHT_BLUE);
        
        this.congo = new TextObject("Congo", fontTextureTitle);
        this.congo.getMesh().getMaterial().setColour(RED);
        
        this.width = new TextObject("Width", fontTextureMedium);
        this.width.getMesh().getMaterial().setColour(YELLOW);
        
        this.length = new TextObject("Length", fontTextureMedium);
        this.length.getMesh().getMaterial().setColour(YELLOW);
        
        this.height = new TextObject("Height", fontTextureMedium);
        this.height.getMesh().getMaterial().setColour(YELLOW);
        
        this.wNum = new TextObject("10", fontTexture);
        this.wNum.getMesh().getMaterial().setColour(YELLOW);
        
        this.lNum = new TextObject("10", fontTexture);
        this.lNum.getMesh().getMaterial().setColour(YELLOW);
        
        this.hNum = new TextObject("10", fontTexture);
        this.hNum.getMesh().getMaterial().setColour(YELLOW);
        
        this.wLeft = new TextObject("<", fontTexture);
        this.wLeft.getMesh().getMaterial().setColour(YELLOW);
        
        this.wRight = new TextObject(">", fontTexture);
        this.wRight.getMesh().getMaterial().setColour(YELLOW);
        
        this.lLeft = new TextObject("<", fontTexture);
        this.lLeft.getMesh().getMaterial().setColour(YELLOW);
        
        this.lRight = new TextObject(">", fontTexture);
        this.lRight.getMesh().getMaterial().setColour(YELLOW);
        
        this.hLeft = new TextObject("<", fontTexture);
        this.hLeft.getMesh().getMaterial().setColour(YELLOW);
        
        this.hRight = new TextObject(">", fontTexture);
        this.hRight.getMesh().getMaterial().setColour(YELLOW);
        
        this.separatorTop = new TextObject("------------------------------", fontTexture);
        this.separatorTop.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0 , 1));
        
        this.separatorBottom = new TextObject("------------------------------", fontTexture);
        this.separatorBottom.getMesh().getMaterial().setColour(new Vector4f(1, 1, 0 , 1));
        
        this.createLevel = new TextObject("Create Level", fontTextureMedium);
        this.createLevel.getMesh().getMaterial().setColour(YELLOW);
        
        this.back = new TextObject("Back", fontTexture);
        this.back.getMesh().getMaterial().setColour(YELLOW);


        this.bongo.setPosition(window.getWidth()/2-bongo.getSize(), window.getHeight()/2-TITLE_POS);
        this.congo.setPosition(window.getWidth()/2, window.getHeight()/2-TITLE_POS);
        this.separatorTop.setPosition(window.getWidth()/2-separatorTop.getSize()/2, window.getHeight()/2-SEPARATORTOP_POS);
        this.separatorBottom.setPosition(window.getWidth()/2-separatorBottom.getSize()/2, window.getHeight()/2+SEPARATORBOT_POS);
        
        this.width.setPosition(window.getWidth()/2-width.getSize()/2, window.getHeight()/2-SEPARATORTOP_POS+GAP);
        this.length.setPosition(window.getWidth()/2-length.getSize()/2, window.getHeight()/2-SEPARATORTOP_POS+GAP*6);
        this.height.setPosition(window.getWidth()/2-height.getSize()/2, window.getHeight()/2-SEPARATORTOP_POS+GAP*11);
        
        this.wNum.setPosition(window.getWidth()/2-wNum.getSize()/2, window.getHeight()/2-SEPARATORTOP_POS+GAP*4);
        this.lNum.setPosition(window.getWidth()/2-lNum.getSize()/2, window.getHeight()/2-SEPARATORTOP_POS+GAP*9);
        this.hNum.setPosition(window.getWidth()/2-hNum.getSize()/2, window.getHeight()/2-SEPARATORTOP_POS+GAP*14);
        
        this.wLeft.setPosition(window.getWidth()/2-wLeft.getSize()/2-50, window.getHeight()/2-SEPARATORTOP_POS+GAP*4);
        this.wRight.setPosition(window.getWidth()/2-wRight.getSize()/2+50, window.getHeight()/2-SEPARATORTOP_POS+GAP*4);
        
        this.lLeft.setPosition(window.getWidth()/2-lLeft.getSize()/2-50, window.getHeight()/2-SEPARATORTOP_POS+GAP*9);
        this.lRight.setPosition(window.getWidth()/2-lRight.getSize()/2+50, window.getHeight()/2-SEPARATORTOP_POS+GAP*9);
        
        this.hLeft.setPosition(window.getWidth()/2-hLeft.getSize()/2-50, window.getHeight()/2-SEPARATORTOP_POS+GAP*14);
        this.hRight.setPosition(window.getWidth()/2-hRight.getSize()/2+50, window.getHeight()/2-SEPARATORTOP_POS+GAP*14);
        
        this.createLevel.setPosition(window.getWidth()/2-createLevel.getSize()/2, window.getHeight()/2+SEPARATORBOT_POS+GAP);
        
        this.back.setPosition(window.getWidth()/2-back.getSize()/2, window.getHeight()/2+SEPARATORTOP_POS);
        
        guiObjects = new GuiObject[] {bongo, congo, width, length, height, wNum, lNum, hNum, wLeft, wRight, lLeft, lRight, hLeft, hRight, separatorTop, separatorBottom, createLevel, back};
        textObjects = new TextObject[] {wLeft, wRight, lLeft, lRight, hLeft, hRight, createLevel, back};
        
	}
	
	public void incWidth() {
		int newWidth = Integer.parseInt(this.wNum.getText());
    	if (newWidth < MAX_WIDTH) newWidth ++;
    	this.wNum.setText((String.format("%02d", newWidth)));
    	this.wNum.getMesh().getMaterial().setColour(YELLOW);
	}
	
	public void decWidth() {
    	int newWidth = Integer.parseInt(this.wNum.getText());
    	if (newWidth > 1) newWidth --;
    	this.wNum.setText((String.format("%02d", newWidth)));
    	this.wNum.getMesh().getMaterial().setColour(YELLOW);
    }
	
	public void incLength() {
		int newLength = Integer.parseInt(this.lNum.getText());
    	if (newLength < MAX_LENGTH) newLength ++;
    	this.lNum.setText((String.format("%02d", newLength)));
    	this.lNum.getMesh().getMaterial().setColour(YELLOW);
	}
	
	public void decLength() {
    	int newLength = Integer.parseInt(this.lNum.getText());
    	if (newLength > 1) newLength --;
    	this.lNum.setText((String.format("%02d", newLength)));
    	this.lNum.getMesh().getMaterial().setColour(YELLOW);
    }
	
	public void incHeight() {
		int newHeight = Integer.parseInt(this.hNum.getText());
    	if (newHeight < MAX_HEIGHT) newHeight ++;
    	this.hNum.setText((String.format("%02d", newHeight)));
    	this.hNum.getMesh().getMaterial().setColour(YELLOW);
	}
	
	public void decHeight() {
    	int newHeight = Integer.parseInt(this.hNum.getText());
    	if (newHeight > 1) newHeight --;
    	this.hNum.setText((String.format("%02d", newHeight)));
    	this.hNum.getMesh().getMaterial().setColour(YELLOW);
    }
	
	public int getWidthNum() {
		return Integer.parseInt(this.wNum.getText());
	}
	
	public int getLengthNum() {
		return Integer.parseInt(this.lNum.getText());
	}
	
	public int getHeightNum() {
		return Integer.parseInt(this.hNum.getText());
	}

	public TextObject getWidth() {
		return width;
	}
	
	public TextObject getLength() {
		return length;
	}
	
	public TextObject getHeight() {
		return height;
	}
	
	public TextObject getWLeft() {
		return wLeft;
	}
	
	public TextObject getWRight() {
		return wRight;
	}
	
	public TextObject getLLeft() {
		return lLeft;
	}
	
	public TextObject getLRight() {
		return lRight;
	}
	
	public TextObject getHLeft() {
		return hLeft;
	}
	
	public TextObject getHRight() {
		return hRight;
	}
	
	public TextObject getBack() {
		return back;
	}
	
	public TextObject getCreateLevel() {
		return createLevel;
	}
	
	@Override
	public GuiObject[] getGuiObjects() {
		return guiObjects;
	}

	@Override
	public TextObject[] getTextObjects() {
		return textObjects;
	}

}
