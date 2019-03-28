package com.knightlore.networking;

import java.util.UUID;

/**
 * Singular level for list level response, hold uuid, level name and creator
 *
 * @author Lewis Relph
 */
public class ListLevelObject {

  UUID uuid;
  String name;
  String created_by;

  public ListLevelObject(UUID uuid, String name, String created_by) {
    this.uuid = uuid;
    this.name = name;
    this.created_by = created_by;
  }

  /**
   * Getter for property 'uuid'.
   *
   * @return Value for property 'uuid'.
   */
  public UUID getUuid() {
    return uuid;
  }

  /**
   * Getter for property 'name'.
   *
   * @return Value for property 'name'.
   */
  public String getName() {
    return name;
  }

  /**
   * Getter for property 'created_by'.
   *
   * @return Value for property 'created_by'.
   */
  public String getCreated_by() {
    return created_by;
  }
}
