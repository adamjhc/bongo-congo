package com.knightlore.client.gui.screen;

import com.knightlore.client.gui.Lobby;
import com.knightlore.client.gui.engine.LobbyObject;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.game.GameModel;

public class LobbyScreen implements IScreen {

  public static GameModel gameModel;

  private GuiRenderer guiRenderer;
  private Lobby lobby;
  
  private LobbyObject lobbyData;

  public LobbyScreen(GuiRenderer guiRenderer) {
    this.guiRenderer = guiRenderer;
    lobby = new Lobby();
  }
  
  @Override
  public void startup(Object... args) {
  	lobbyData = (LobbyObject) args[0];
  }
  
  @Override
  public void input() {}
  
  @Override
  public void update(float delta) {
  	lobby.setLobbyName(lobbyData.getText().substring(4, lobbyData.getText().length() - 4));
  }

  @Override
  public void render() {
  	lobby.updateSize();
  	
    guiRenderer.render(lobby);
  }

  @Override
  public void cleanUp() {
    lobby.cleanup();
  }
}
