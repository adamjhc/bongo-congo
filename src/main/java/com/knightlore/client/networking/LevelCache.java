package com.knightlore.client.networking;

import com.knightlore.networking.ListLevelObject;
import com.knightlore.networking.ListLevelsResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class LevelCache {

  public static LevelCache instance = new LevelCache();

  private HashMap<UUID, ListLevelObject> levels;

  public LevelCache() {
    this.levels = new HashMap<>();
  }

  public Collection<ListLevelObject> getLevels() {
    return this.levels.values();
  }

  public void setLevels(ListLevelsResponse response) {
    this.levels = response.getLevels();
  }
}
