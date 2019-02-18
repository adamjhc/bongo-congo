package com.knightlore.game;

import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.Player;
import com.knightlore.game.entity.PlayerState;
import com.knightlore.game.map.Map;
import com.knightlore.game.map.MapSet;
import com.knightlore.game.map.TileSet;
import java.util.ArrayList;
import java.util.HashMap;
import org.joml.Vector3f;

public class Game {

  private HashMap<String, Level> levels;
  private String currentLevel;
  private TileSet tileSet;
  private MapSet mapSet;

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

    player.setDirection(direction);
    player.setPlayerState(PlayerState.MOVING);

    int speed = 7;
    Vector3f origPos = player.getPosition();
    Vector3f newPos = new Vector3f();
    direction.getNormalisedDirection().mul(delta, newPos).mul(speed, newPos);
    origPos.add(newPos, newPos);
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

  public void updatePlayerState(PlayerState state) {
    getCurrentLevel().getPlayers().get(0).setPlayerState(state);
  }
}
