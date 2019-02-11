package com.knightlore.game;

import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.Player;
import com.knightlore.game.map.Map;
import com.knightlore.game.map.MapSet;
import com.knightlore.game.map.TileSet;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {

  HashMap<String, Level> levels;
  String currentLevel;
  TileSet tileSet;
  MapSet mapSet;

  public Game() {
    tileSet = new TileSet();
    mapSet = new MapSet(tileSet);
    levels = new HashMap<>();

    createNewLevel("1", mapSet.getMap(0));
  }

  public Level getCurrentLevel() {
    return levels.get(currentLevel);
  }

  public void update(float delta) {}

  public void movePlayerInDirection(Direction direction, float delta) {}

  private void createNewLevel(String uuid, Map map) {
    ArrayList<Player> players = new ArrayList<>();
    players.add(new Player());

    levels.put(uuid, new Level(map, players));

    if (currentLevel == null) {
      currentLevel = uuid;
    }
  }
}
