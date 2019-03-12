package com.knightlore.client.render.world;

import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.game.entity.Enemy;
import com.knightlore.game.entity.EnemyState;
import com.knightlore.game.entity.Entity;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class EnemyGameObject extends EntityGameObject {

  private EnemyState currentState;

  EnemyGameObject(String textureFileName, Vector4f colour) {
    super(textureFileName, colour);
  }

  EnemyGameObject(EnemyGameObject copy) {
    model = copy.model;
    idleTextures = copy.idleTextures;
    movingTextures = copy.movingTextures;
    currentDirection = copy.currentDirection;
    currentState = copy.currentState;
    transform = copy.transform;
    colour = copy.colour;
  }

  @Override
  public void update(Entity entity) {
    super.update(entity);
    currentState = ((Enemy) entity).getCurrentState();
  }

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
