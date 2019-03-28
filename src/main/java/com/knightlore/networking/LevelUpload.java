package com.knightlore.networking;

import com.knightlore.game.Level;

/**
 * Object to upload a new level from the level editor
 *
 * @author Lewis Relph
 */
public class LevelUpload {

  public Level level;
  public String name;

  public LevelUpload(Level level, String name) {
    this.level = level;
    this.name = name;
  }
}
