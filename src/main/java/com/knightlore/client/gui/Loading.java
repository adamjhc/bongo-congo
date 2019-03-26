package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

/**
 * The loading screen displayed when transitioning between screens
 * 
 * @author Adam C, Joseph
 *
 */
public class Loading implements IGui {
	
	/** List of the text objects that have user interaction */
  private TextObject[] textObjects;
  /** List of all the gui objects */
  private GuiObject[] guiObjects;

  /** Loading text */
  private TextObject loading;

  /**
   * Create gui objects
   * 
   * @author Adam C
   * 
   */
  public Loading() {
    loading = new TextObject("Loading...", TITLE);
    updateSize();

    textObjects = new TextObject[0];
    guiObjects = new GuiObject[] {loading};
  }
  
  /**
   * Updates the position of the gui objects
   * 
   * @author Adam C
   * 
   */
  public void updateSize() {
    loading.setPosition(
    		Window.getHalfWidth() - loading.getSize() / 2, Window.getHalfHeight());
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
