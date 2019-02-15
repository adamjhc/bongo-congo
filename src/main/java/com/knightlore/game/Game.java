package com.knightlore.game;

import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.Player;
import com.knightlore.game.map.Map;
import com.knightlore.game.map.MapSet;
import com.knightlore.game.map.TileSet;
import com.knightlore.game.util.CoordinateUtils;

import org.joml.Vector3f;

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

  public void movePlayerInDirection(Direction direction, float delta) {
    Player player = getCurrentLevel().getPlayers().get(0);
    float speed = 0.1f;

    player.setDirection(direction);
    Vector3f origPos = player.getPosition();
    Vector3f newPos = new Vector3f(origPos);

    switch (direction) {
      case NORTH_EAST:
        newPos.set(newPos.x + speed, newPos.y + speed, newPos.z);
        break;
      case EAST:
        newPos.set(newPos.x + speed ,newPos.y, newPos.z );
        break;
      case SOUTH_EAST:
        newPos.set(newPos.x - speed*0.7f,newPos.y + speed*0.7f, newPos.z);
        break;
      case SOUTH:
        newPos.set(newPos.x,newPos.y - speed, newPos.z );
        break;
      case SOUTH_WEST:
        newPos.set(newPos.x - speed,newPos.y - speed, newPos.z);
        break;
      case WEST:
        newPos.set(newPos.x - speed, newPos.y, newPos.z );
        break;
      case NORTH_WEST:
        newPos.set(newPos.x + speed*0.7f,newPos.y - speed*0.7f, newPos.z);
        break;
      case NORTH:
        newPos.set(newPos.x,newPos.y + speed, newPos.z );
        break;
    }
    player.update(origPos, newPos, getCurrentLevel().getMap());

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
