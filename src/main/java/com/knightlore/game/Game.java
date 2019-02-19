package com.knightlore.game;

import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.Player;
import com.knightlore.game.entity.PlayerState;
import com.knightlore.game.map.Map;
import com.knightlore.game.map.MapSet;
import java.util.ArrayList;
import org.joml.Vector3f;

public class Game {

  String uuid;
  ArrayList<Level> levels;
  Integer currentLevelIndex;
  GameState currentState;

  public Game(String uuid, MapSet mapSet) {
    levels = new ArrayList<>();
    this.uuid = uuid;
    this.currentState = GameState.LOBBY;
    this.levels = new ArrayList<>();
    // createNewLevel(mapSet.getMap(0));
  }

  public Level getCurrentLevel() {
    return this.levels.get(this.currentLevelIndex);
  }

  public int addLevel(Level level) {
    if (this.currentLevelIndex == null) {
      this.currentLevelIndex = 0;
    }
    this.levels.add(level);

    return this.levels.size() - 1;
  }

  public GameState getState() {
    return currentState;
  }

  public void setState(GameState state) {
    this.currentState = state;
  }

  public void setLevel(int index) {
    this.currentLevelIndex = index;
  }

  public void incrementLevel() {
    this.currentLevelIndex += 1;
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

  public void createNewLevel(Map map) {
    ArrayList<Player> players = new ArrayList<>();
    players.add(new Player());

    levels.add(new Level(map, players));

    if (currentLevelIndex == null) {
      currentLevelIndex = 0;
    }
  }

  public void updatePlayerState(PlayerState state) {
    getCurrentLevel().getPlayers().get(0).setPlayerState(state);
  }
}
