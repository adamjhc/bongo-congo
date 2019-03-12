package com.knightlore.client.gui.screen;

import com.knightlore.client.gui.Lobby;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.game.GameModel;

public class LobbyScreen implements IScreen {

  public static GameModel gameModel;

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

  @Override
  public void cleanUp() {
    lobby.cleanup();
  }
}
