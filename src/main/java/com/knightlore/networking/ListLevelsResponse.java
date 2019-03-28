package com.knightlore.networking;

import java.util.HashMap;
import java.util.UUID;

/**
 * Response to list level request, repository for list level objects
 *
 * @author Lewis Relph
 */
public class ListLevelsResponse {

  HashMap<UUID, ListLevelObject> levels;

  /** Constructs a new ListLevelsResponse. */
  public ListLevelsResponse() {
    levels = new HashMap<>();
  }

  public void addLevel(UUID uuid, String name, String created_by) {
    this.levels.put(uuid, new ListLevelObject(uuid, name, created_by));
  }

  /**
   * Getter for property 'levels'.
   *
   * @return Value for property 'levels'.
   */
  public HashMap<UUID, ListLevelObject> getLevels() {
    return levels;
  }
}
