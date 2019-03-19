package com.knightlore.client.gui.engine;

import com.knightlore.client.gui.engine.graphics.FontTexture;
import com.knightlore.networking.ListGameObject;

public class LobbyObject extends TextObject {
	
	private boolean highlighted;
	
	private boolean isCreator;
	
	private ListGameObject game;
	
	// ADD LOBBY VARIABLES NEEDED (list of clients etc.)

	public LobbyObject(String text, FontTexture fontTexture, ListGameObject game) {
		super(text, fontTexture);	
		this.highlighted = false;
		this.isCreator = false;
		this.game = game;
	}
	
	public LobbyObject(String text, FontTexture fontTexture) {
		super(text, fontTexture);	
		this.highlighted = false;
		this.game = null;
	}
	
	public boolean getHighlighted() {
    return highlighted;
  }
    
  public void setHighlighted() {
    highlighted = !highlighted;
  }
  
  public boolean getIsCreator() {
  	return isCreator;
  }
  
  public void setIsCreator(boolean creator) {
  	this.isCreator = creator;
  }
    
  public ListGameObject getGame() {
  	return game;
  }
}
