package com.knightlore.client.render.world;

import java.util.ArrayList;

public class PlayerSet {

  /** List containing all player game objects */
  private ArrayList<PlayerGameObject> playerSet;

  /** Initialise the Player set, loading the player objects */
  public PlayerSet() {
    playerSet = new ArrayList<>();
    loadPlayers();
  }

  /**
   * Get a player game object
   *
   * @param index Index of the player game object in the player set
   * @return Player game object
   */
  public PlayerGameObject getPlayer(int index) {
    return playerSet.get(index);
  }

  /** Loads the player game objects into the player set */
  private void loadPlayers() {
    playerSet.add(new PlayerGameObject("player"));
    playerSet.add(new PlayerGameObject("player"));
    playerSet.add(new PlayerGameObject("player"));
    playerSet.add(new PlayerGameObject("player"));
  }
}
