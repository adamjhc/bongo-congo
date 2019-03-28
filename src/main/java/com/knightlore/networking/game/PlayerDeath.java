package com.knightlore.networking.game;

/**
 * Notify server of player death
 *
 * @author Lewis Relph
 */
public class PlayerDeath {

  public String playerID;

  public PlayerDeath(String playerID) {
    this.playerID = playerID;
  }
}
