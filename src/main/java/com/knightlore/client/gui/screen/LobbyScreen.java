package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;

import java.util.Collection;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.gui.Lobby;
import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.LobbyObject;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.networking.LobbyCache;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.game.GameModel;
import com.knightlore.networking.ListGameObject;

public class LobbyScreen implements IScreen {

  public static GameModel gameModel;

  private GuiRenderer guiRenderer;
  private Lobby lobby;
  
  private LobbyObject lobbyData;
  
  private ListGameObject game;

  public LobbyScreen(GuiRenderer guiRenderer) {
    this.guiRenderer = guiRenderer;
    lobby = new Lobby();
  }
  
  @Override
  public void startup(Object... args) {
  	lobbyData = (LobbyObject) args[0];
  	game = lobbyData.getGame();
  	lobby.refreshPlayers(game.getUsernames());
  }
  
  @Override
  public void input() {
    if (checkPosition(lobby, lobby.getExit().getId())) {
      lobby.getExit().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Client.changeScreen(ClientState.LOBBY_MENU);
      }
    } else lobby.getExit().setColour(Colour.YELLOW);
  }
  
  @Override
  public void update(float delta) {
  	Collection<ListGameObject> games = LobbyCache.instance.getGames();
  	for (ListGameObject game : games) {
  		if (game.getUuid() == this.game.getUuid()) {
  			this.game = game;
  			break;
  		}
  	}
  	
  	if (lobbyData.getGame() != null) {
  		lobby.setLobbyName(game.getName());
  	} else {
  		lobby.setLobbyName(lobbyData.getText());
  	}
  	lobby.refreshPlayers(game.getUsernames());
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
