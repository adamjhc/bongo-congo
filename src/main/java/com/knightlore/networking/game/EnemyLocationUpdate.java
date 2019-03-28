package com.knightlore.networking.game;

import java.util.HashMap;

/**
 * Repository for holding multiple enemy locaiton update objects
 *
 * @author Lewis Relph
 */
public class EnemyLocationUpdate {

  public HashMap<Integer, EnemyLocationUpdateObject> updates;

  public EnemyLocationUpdate() {
    this.updates = new HashMap<>();
  }

    /**
     * Add an enemy to our bank based on update object
     * @param id
     * @param loc
     */
  public void addEnemy(Integer id, EnemyLocationUpdateObject loc) {
    updates.put(id, loc);
  }
}
