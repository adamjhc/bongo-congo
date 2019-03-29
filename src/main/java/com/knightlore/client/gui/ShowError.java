package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.Gui;
import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Window;

/**
 * Screen to show error messages
 *
 * @author Adam Cox, Joseph
 */
public class ShowError extends Gui {

  /** Position of the bottom separator line */
  private static final int SEPARATOR_BOT_POS = 200;

  /** Error message text */
  private TextObject errorMessage;

  /** Exit text */
  private TextObject exit;

  /**
   * Create gui objects
   *
   * @author Adam Cox
   */
  public ShowError() {
    errorMessage = new TextObject("", SMALL);

    exit = new TextObject("Exit", SMALL);
    exit.setColour(Colour.YELLOW);

    updateSize();

    textObjects = new TextObject[] {exit};
    guiObjects = new GuiObject[] {errorMessage, exit};
  }

  /**
   * Return exit
   *
   * @return Exit
   * @author Adam Cox
   */
  public TextObject getExit() {
    return exit;
  }

  /**
   * Set the error message
   *
   * @param errorMessageText text to display
   * @author Adam Cox
   */
  public void setErrorMessageText(String errorMessageText) {
    errorMessage.setText(errorMessageText);
  }

  /**
   * Updates the position of the gui objects
   *
   * @author Adam Cox
   */
  public void updateSize() {
    errorMessage.setPosition(
        Window.getHalfWidth() - errorMessage.getSize() / 2, Window.getHalfHeight());

    exit.setPosition(
        Window.getHalfWidth() - exit.getSize() / 2,
        Window.getHalfHeight() + SEPARATOR_BOT_POS + GAP * 2);
  }
}
