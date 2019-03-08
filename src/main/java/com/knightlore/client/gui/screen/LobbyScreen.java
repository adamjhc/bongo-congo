package com.knightlore.client.gui.screen;

import com.knightlore.client.gui.Lobby;
import com.knightlore.client.render.GuiRenderer;

public class LobbyScreen implements IScreen {

  private GuiRenderer guiRenderer;
  private Lobby lobby;

  public LobbyScreen(GuiRenderer guiRenderer) {
    this.guiRenderer = guiRenderer;
    lobby = new Lobby();
  }

  @Override
  public void input() {}

  @Override
  public void render() {
    guiRenderer.render(lobby);
  }
}
