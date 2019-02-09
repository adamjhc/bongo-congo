package com.knightlore.client.render.world;

import java.util.ArrayList;

public class PlayerSet {

  private ArrayList<PlayerGameObject> playerSet;

  public PlayerSet() {
    playerSet = new ArrayList<>();
    loadPlayers();
  }

  public PlayerGameObject getPlayer(int index) {
    return playerSet.get(index);
  }

  private void loadPlayers() {
    playerSet.add(new PlayerGameObject("player_1"));
  }
}
