package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.gui.Lobby;
import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.LobbyObject;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.LobbyCache;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.networking.ListGameObject;
import java.util.Collection;

public class LobbyScreen implements IScreen {

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

    if (lobbyData.getIsCreator()) {
      lobby.getStart().setRender(true);
    }
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
      lobby.refreshPlayers(game.getUsernames());
    }

    if (lobbyData.getIsCreator()) {
      if (checkPosition(lobby, lobby.getStart().getId())) {
        lobby.getStart().setColour();
        if (Mouse.isLeftButtonPressed()) {
          GameConnection.instance.startGame();

          Client.changeScreen(ClientState.GAME);
        }
      } else lobby.getStart().setColour(Colour.YELLOW);
    }
  }

  @Override
  public void render() {
    lobby.updateSize(lobbyData.getIsCreator());

    guiRenderer.render(lobby);
  }

  @Override
  public void cleanUp() {
    lobby.cleanup();
  }
}
