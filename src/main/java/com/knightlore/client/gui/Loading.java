package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Gui;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

/**
 * The loading screen displayed when transitioning between screens
 *
 * @author Adam C, Joseph
 */
public class Loading extends Gui {

  /** Loading text */
  private TextObject loading;

  /**
   * Create gui objects
   *
   * @author Adam Cox
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
   * @author Adam Cox
   */
  public void updateSize() {
    loading.setPosition(Window.getHalfWidth() - loading.getSize() / 2, Window.getHalfHeight());
  }
}
