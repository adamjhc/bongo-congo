package com.knightlore.client.gui.engine;

import com.knightlore.client.gui.engine.graphics.FontTexture;
import com.knightlore.networking.server.ListGameObject;

/**
 * Special type of text object for a lobby that holds a game
 * 
 * @author Joseph
 *
 */
public class LobbyObject extends TextObject {
	
	/** Is the lobby highlighted */
	private boolean highlighted;
	/** Was this lobby created by a client */
	private boolean isCreator;
	
	/** Corresponding game for the lobby */
	private ListGameObject game;
	
	/**
	 * Initialises the object
	 * 
	 * @param text The text displayed on screen
	 * @param fontTexture The font texture used
	 * @param game Corresponding game for the lobby
	 * @author Joseph
	 * 
	 */
	public LobbyObject(String text, FontTexture fontTexture, ListGameObject game) {
		super(text, fontTexture);	
		this.highlighted = false;
		this.isCreator = false;
		this.game = game;
	}
	
	/**
	 * Lobby created without game
	 * 
	 * @param text The text displayed on screen
	 * @param fontTexture The font texture used
	 * @author Joseph
	 * 
	 */
	public LobbyObject(String text, FontTexture fontTexture) {
		super(text, fontTexture);	
		this.highlighted = false;
		this.game = null;
	}
	
	/**
	 * Return if the lobby is currently highlighted
	 * 
	 * @return Highlighted
	 * @author Joseph
	 * 
	 */
	public boolean getHighlighted() {
    return highlighted;
  }
    
	/**
	 * Swaps the highlighted boolean
	 * 
	 * @author Joseph
	 * 
	 */
  public void setHighlighted() {
    highlighted = !highlighted;
  }
  
  /**
   * Returns if the lobby was created by a client
   * 
   * @return isCreator
   * @author Joseph
   * 
   */
  public boolean getIsCreator() {
  	return isCreator;
  }
  
  /**
   * Change the creator boolean
   * 
   * @author Joseph
   * 
   */
  public void setIsCreator(boolean creator) {
  	this.isCreator = creator;
  }
   
  /**
   * Return the attached game
   * 
   * @return Game
   * @author Joseph
   * 
   */
  public ListGameObject getGame() {
  	return game;
  }
}
