package com.knightlore.client.networking;

import com.knightlore.networking.server.ListGameObject;
import com.knightlore.networking.server.ListGameResponse;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

/**
 * Store for retrieved lobbys from server
 *
 * @author Lewis Relph
 */
public class LobbyCache {

  public static LobbyCache instance;
  public int cacheBuster;
  HashMap<UUID, ListGameObject> games;

  /** Default constructor */
  public LobbyCache() {
    cacheBuster = 0;
    this.games = new HashMap<>();
  }

  /**
   * Getter for games
   *
   * @return
   */
  public Collection<ListGameObject> getGames() {
    return this.games.values();
  }

  /**
   * Setter for games
   *
   * @param response
   */
  public void setGames(ListGameResponse response) {
    this.games = response.getGames();
    cacheBuster += 1;
  }

  /**
   * Getter for individual game from gamestash
   *
   * @param uuid
   * @return
   */
  public ListGameObject getGame(UUID uuid) {
    return games.get(uuid);
  }
}
