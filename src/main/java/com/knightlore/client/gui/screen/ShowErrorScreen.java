package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.gui.ShowError;
import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.render.GuiRenderer;

public class ShowErrorScreen implements IScreen {

  /** Renderer used for rendering gui elements */
  private GuiRenderer guiRenderer;

  /** Gui elements to render */
  private ShowError showError;

  /**
   * Initialise ShowErrorScreen
   *
   * @param guiRenderer renderer used for rendering gui elements
   * @author Adam Cox
   */
  public ShowErrorScreen(GuiRenderer guiRenderer) {
    this.guiRenderer = guiRenderer;
    showError = new ShowError();
  }

  @Override
  public void startup(Object... args) {
    String errorMessage = (String) args[0];
    showError.setErrorMessageText(errorMessage);
  }

  @Override
  public void input() {
    if (checkPosition(showError, showError.getExit().getId())) {
      showError.getExit().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Client.changeScreen(ClientState.MAIN_MENU, false);
      }
    } else showError.getExit().setColour(Colour.YELLOW);
  }

  @Override
  public void render() {
    showError.updateSize();

    guiRenderer.render(showError);
  }

  @Override
  public void cleanUp() {
    showError.cleanup();
  }
}
