package com.knightlore.client.render.world;

import com.knightlore.client.render.opengl.AnimatedTexture;
import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.opengl.StaticTexture;
import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.Entity;
import com.knightlore.game.entity.Player;
import com.knightlore.game.entity.PlayerState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * GameObject used for the Player entities
 *
 * @author Adam Cox
 */
public class PlayerGameObject extends EntityGameObject {

  /** Map for climbing textures */
  private Map<Direction, AnimatedTexture> climbingTextures;

  /** Map for rolling textures */
  private Map<Direction, AnimatedTexture> rollingTextures;

  /** Texture used in falling */
  private AnimatedTexture fallingTexture;

  /** Texture used when dead */
  private StaticTexture deadTexture;

  /** Current state of the player entity */
  private PlayerState currentState;

  /**
   * Initialise the player game object
   *
   * @param textureFileName File name of the player texture
   * @author Adam Cox
   */
  private PlayerGameObject(String textureFileName, Vector3f position, Vector4f colour) {
    super(textureFileName, colour);

    climbingTextures = new EnumMap<>(Direction.class);
    rollingTextures = new EnumMap<>(Direction.class);

    for (Direction direction : Direction.values()) {
      String directionPath = textureFileName + "_" + direction.getAbbreviation();
      climbingTextures.put(direction, new AnimatedTexture(directionPath + "_run", 10, 20));
      rollingTextures.put(direction, new AnimatedTexture(directionPath + "_roll", 10, 20, false));
    }
    fallingTexture = new AnimatedTexture(textureFileName + "_fall", 10, 20);
    deadTexture = new StaticTexture(textureFileName + "_dead");

    setPosition(position);
  }

  /**
   * Generate list of PlayerGameObjects based on collection of player entities from game model
   *
   * @param players Collection of player entities from game model
   * @return list of PlayerGameObjects
   * @author Adam Cox
   */
  public static List<PlayerGameObject> fromGameModel(Collection<Player> players) {
    List<PlayerGameObject> playerGameObjects = new ArrayList<>();

    players.forEach(
        player -> {
          PlayerGameObject playerGameObject =
              new PlayerGameObject("player", player.getPosition(), player.getColour());
          playerGameObjects.add(playerGameObject);
        });

    System.out.println("X");
    return playerGameObjects;
  }

  public PlayerState getCurrentState() {
    return currentState;
  }

  /**
   * Update with entity
   *
   * @param entity Entity to update with
   * @author Adam Cox
   */
  @Override
  public void update(Entity entity) {
    super.update(entity);
    currentState = ((Player) entity).getPlayerState();
  }

  /**
   * Render the player
   *
   * @param camera Camera projection
   * @author Adam Cox
   */
  public void render(ShaderProgram shaderProgram, Matrix4f camera, int worldScale) {
    transform.setPosition(isometricPosition);

    shaderProgram.bind();
    shaderProgram.setUniform("sampler", 0);
    shaderProgram.setUniform("projection", transform.getProjection(camera, worldScale));
    shaderProgram.setUniform("colour", colour);

    switch (currentState) {
      case IDLE:
        idleTextures.get(currentDirection).bind(0);
        resetUnloopedAnimatedTextures();
        break;
      case MOVING:
        movingTextures.get(currentDirection).bind(0);
        resetUnloopedAnimatedTextures();
        break;
      case CLIMBING:
        climbingTextures.get(currentDirection).bind(0);
        break;
      case FALLING:
        fallingTexture.bind(0);
        break;
      case ROLLING:
        rollingTextures.get(currentDirection).bind(0);
        break;
      case DEAD:
        deadTexture.bind(0);
        break;
    }

    model.render();
  }

  /**
   * Reset un looped animated textures
   *
   * @author Adam Cox
   */
  private void resetUnloopedAnimatedTextures() {
    rollingTextures.forEach((direction, animatedTexture) -> animatedTexture.reset());
  }
}
