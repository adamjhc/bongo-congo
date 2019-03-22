package com.knightlore.client.render.world;

import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.game.entity.Enemy;
import com.knightlore.game.entity.EnemyState;
import com.knightlore.game.entity.Entity;
import org.joml.Matrix4f;
import org.joml.Vector4f;

/**
 * GameObject for Enemy entities
 *
 * @author Adam Cox
 */
public class EnemyGameObject extends EntityGameObject {

  /** Current state of the entity */
  private EnemyState currentState;

  /**
   * Initialise the EnemyGameObject
   *
   * @param textureFileName Prefix of the texture file name
   * @param colour Colour used for the texture
   * @author Adam Cox
   */
  EnemyGameObject(String textureFileName, Vector4f colour) {
    super(textureFileName, colour);
    currentState = EnemyState.IDLE;
  }

  /**
   * Copy constructor
   *
   * @param copy Object to copy fields from
   * @author Adam Cox
   */
  EnemyGameObject(EnemyGameObject copy) {
    model = copy.model;
    idleTextures = copy.idleTextures;
    movingTextures = copy.movingTextures;
    currentDirection = copy.currentDirection;
    currentState = copy.currentState;
    transform = copy.transform;
    colour = copy.colour;
  }

  /**
   * Update the GameObject with the relevant entity
   *
   * @param entity Entity to update the GameObject with
   * @author Adam Cox
   */
  @Override
  public void update(Entity entity) {
    super.update(entity);
    currentState = ((Enemy) entity).getCurrentState();
  }

  /**
   * Render the EnemyGameObject to screen
   *
   * @param shaderProgram Shader program to use
   * @param camera Current Camera projection
   * @param worldScale scale of the world
   * @author Adam Cox
   */
  @Override
  public void render(ShaderProgram shaderProgram, Matrix4f camera, int worldScale) {
    transform.setPosition(isometricPosition);

    shaderProgram.bind();
    shaderProgram.setUniform("sampler", 0);
    shaderProgram.setUniform("projection", transform.getProjection(camera, worldScale));
    shaderProgram.setUniform("colour", colour);

    switch (currentState) {
      case IDLE:
        idleTextures.get(currentDirection).bind(0);
        break;
      case MOVING:
        movingTextures.get(currentDirection).bind(0);
        break;
      default:
        return;
    }

    model.render();
  }
}
