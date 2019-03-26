package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.gui.ShowError;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.render.GuiRenderer;

public class ShowErrorScreen implements IScreen {

  private GuiRenderer guiRenderer;
  private ShowError showError;

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
    if (checkPosition(showError, showError.getExit().getId()) && Mouse.isLeftButtonPressed()) {
      Client.changeScreen(ClientState.MAIN_MENU, false);
    }
  }

  @Override
  public void render() {
    guiRenderer.render(showError);
  }

  @Override
  public void cleanUp() {
    showError.cleanup();
  }
}
