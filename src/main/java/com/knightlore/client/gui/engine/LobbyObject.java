package com.knightlore.client.gui.engine;

import com.knightlore.client.gui.engine.graphics.FontTexture;
import com.knightlore.networking.ListGameObject;

public class LobbyObject extends TextObject {
	
	private boolean highlighted;
	
	private ListGameObject game;
	
	// ADD LOBBY VARIABLES NEEDED (list of clients etc.)

	public LobbyObject(String text, FontTexture fontTexture, ListGameObject game) {
		super(text, fontTexture);	
		this.highlighted = false;
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
    
  public ListGameObject getGame() {
  	return game;
  }
}
