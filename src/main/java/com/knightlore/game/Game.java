package com.knightlore.game;

import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.Player;
import com.knightlore.game.map.Map;
import com.knightlore.game.map.MapSet;
import com.knightlore.game.map.TileSet;
import com.knightlore.game.math.Vector3f;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import static com.knightlore.game.entity.Direction.NORTH_EAST;

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

  public void movePlayerInDirection(Direction direction, float delta) {
    Player player = getCurrentLevel().getPlayers().get(0);
    float speed = 0.05f;

    player.setDirection(direction);
    Vector3f origPos = player.getPosition();
    Vector3f newPos = origPos;
    switch (direction) {
      case NORTH_EAST:
        newPos.set(origPos.x + speed, origPos.y + speed, origPos.z);
        break;
      case EAST:
        newPos.set(origPos.x + speed,origPos.y, origPos.z );
        break;
      case SOUTH_EAST:
        newPos.set(origPos.x - speed,origPos.y + speed, origPos.z);
        break;
      case SOUTH:
        newPos.set(origPos.x,origPos.y - speed, origPos.z );
        break;
      case SOUTH_WEST:
        newPos.set(origPos.x - speed,origPos.y - speed, origPos.z);
        break;
      case WEST:
        newPos.set(origPos.x - speed, origPos.y, origPos.z );
        break;
      case NORTH_WEST:
        newPos.set(origPos.x + speed,origPos.y - speed, origPos.z);
        break;
      case NORTH:
        newPos.set(origPos.x,origPos.y + speed, origPos.z );
        break;
    }
    player.update(origPos, newPos);

  }

  private void createNewLevel(String uuid, Map map) {
    ArrayList<Player> players = new ArrayList<>();
    players.add(new Player());

    levels.put(uuid, new Level(map, players));

    if (currentLevel == null) {
      currentLevel = uuid;
    }
  }

}
