package com.knightlore.networking.server;

/**
 * Broadcast player joined to others in the lobby
 *
 * @author Lewis Relph
 */
public class PlayerJoined {

  public String session;

  public PlayerJoined(String session) {
    this.session = session;
  }
}
