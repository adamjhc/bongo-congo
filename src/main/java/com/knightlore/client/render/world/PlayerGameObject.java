package com.knightlore.client.render.world;

import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.game.entity.Entity;
import com.knightlore.game.entity.Player;
import com.knightlore.game.entity.PlayerState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class PlayerGameObject extends EntityGameObject {

  private PlayerState currentState;
  private Vector3f colour;

  /**
   * Initialise the player game object
   *
   * @param textureFileName File name of the player texture
   */
  private PlayerGameObject(String textureFileName) {
    super(textureFileName);
  }

  public static List<PlayerGameObject> fromGameModel(Collection<Player> players) {
    List<PlayerGameObject> playerGameObjects = new ArrayList<>();

    players.forEach(
        player -> {
          PlayerGameObject playerGameObject = new PlayerGameObject("player");
          playerGameObject.setPosition(player.getPosition());
          playerGameObject.setColour(player.getColour());
          playerGameObjects.add(playerGameObject);
        });

    return playerGameObjects;
  }

  @Override
  public void update(Entity entity) {
    super.update(entity);
    currentState = ((Player) entity).getCurrentState();
  }

  /**
   * Render the player
   *
   * @param camera Camera projection
   */
  public void render(ShaderProgram shaderProgram, Matrix4f camera) {
    transform.setPosition(isometricPosition);

    shaderProgram.bind();
    shaderProgram.setUniform("sampler", 0);
    shaderProgram.setUniform("projection", transform.getProjection(camera));
    shaderProgram.setUniform("colour", colour);

    switch (currentState) {
      case IDLE:
        idleTextures.get(currentDirection).bind(0);
        break;
      case MOVING:
        movingTextures.get(currentDirection).bind(0);
        break;
      case CLIMBING:
        break;
      default:
        return;
    }

    model.render();
  }

  private void setColour(Vector3f colour) {
    this.colour = colour;
  }
}
