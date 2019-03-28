package com.knightlore.networking.server;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.UUID;

/**
 * Response to list game request, repository for list game objects
 *
 * @author Lewis Relph
 */
public class ListGameResponse {

  HashMap<UUID, ListGameObject> games;

  public ListGameResponse() {
    games = new HashMap<>();
  }

  public void addGame(UUID uuid, InetAddress ip, int port, String name) {
    this.games.put(uuid, new ListGameObject(uuid, ip, port, name));
  }

  public void addGame(ListGameObject game) {
    this.games.put(game.uuid, game);
  }

  public HashMap<UUID, ListGameObject> getGames() {
    return games;
  }
}
