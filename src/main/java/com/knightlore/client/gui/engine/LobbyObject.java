package com.knightlore.client.gui.engine;

import com.knightlore.client.gui.engine.graphics.FontTexture;

public class LobbyObject extends TextObject {
	
	private boolean highlighted = false;
	
	// ADD LOBBY VARIABLES NEEDED (list of clients etc.)

	public LobbyObject(String text, FontTexture fontTexture) {
		super(text, fontTexture);	
	}
	
    public boolean getHighlighted() {
    	return highlighted;
    }
    
    
    public void setHighlighted() {
    	highlighted = !highlighted;
    }
}
