package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.audio.Audio;
import com.knightlore.client.audio.Audio.AudioName;
import com.knightlore.client.gui.Lobby;
import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.LobbyObject;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.LobbyCache;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.networking.ListGameObject;
import java.util.Collection;

/**
 * Handles the lobby screen startup, input, updates, rendering and cleanup
 * 
 * @author Joseph, Adam C
 *
 */
public class LobbyScreen implements IScreen {

	/** Menu interaction sound */
  private static final AudioName SELECT = AudioName.SOUND_MENUSELECT;

  /** The gui renderer */
  private GuiRenderer guiRenderer;
  /** The lobby gui */
  private Lobby lobby;

  /** The lobby object associated with the lobby */
  private LobbyObject lobbyData;
  /** The game associated with the lobby */
  private ListGameObject game;

  /**
   * Initialise the renderers and gui
   * 
   * @param guiRenderer
   * @author Joseph
   * 
   */
  public LobbyScreen(GuiRenderer guiRenderer) {
    this.guiRenderer = guiRenderer;
    lobby = new Lobby();
  }

  /**
   * Initialise values when screen starts
   * 
   * @author Joseph
   * 
   */
  @Override
  public void startup(Object... args) {
    lobbyData = (LobbyObject) args[0];
    game = lobbyData.getGame();

    if (lobbyData.getIsCreator()) {
      lobby.getStart().setRender(true);
    }
  }

  /**
   * Check for user input
   * 
   * @author Joseph
   * 
   */
  @Override
  public void input() {
    if (checkPosition(lobby, lobby.getExit().getId())) {
      lobby.getExit().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        // Delete connection
        GameConnection.instance.close();
        Client.changeScreen(ClientState.LOBBY_MENU, false);
      }
    } else lobby.getExit().setColour(Colour.YELLOW);
  }

  /**
   * Update lobby screen
   * 
   * @author Joseph
   * 
   */
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
          Audio.play(SELECT);
          GameConnection.instance.startGame();
        }
      } else lobby.getStart().setColour(Colour.YELLOW);
    }

    if (GameConnection.gameModel != null) {
      com.knightlore.client.Client.changeScreen(ClientState.GAME, true);
    }
  }

  /**
   * Render the screen and update text positions
   * 
   * @author Joseph
   * 
   */
  @Override
  public void render() {
    lobby.updateSize(lobbyData.getIsCreator());

    guiRenderer.render(lobby);
  }

  /**
   * Cleanup the gui
   * 
   * @author Joseph
   * 
   */
  @Override
  public void cleanUp() {
    lobby.cleanup();
  }
}
