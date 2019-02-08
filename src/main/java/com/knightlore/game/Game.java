package com.knightlore.game;

import com.knightlore.game.map.MapSet;
import com.knightlore.game.map.TileSet;
import java.util.HashMap;

public class Game {

  String uuid;
  HashMap<String, Level> levels;
  String currentLevel;
  TileSet tileSet;
  MapSet mapSet;

  public Game() {
    tileSet = new TileSet();
    mapSet = new MapSet(tileSet);

    levels = new HashMap<>();
    levels.put("1", new Level(mapSet.getMap(0)));
    currentLevel = "1";
  }

  public Level getCurrentLevel() {
    return levels.get(currentLevel);
  }
}
