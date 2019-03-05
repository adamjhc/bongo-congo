package com.knightlore.client.render.world;

import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.game.entity.EnemyState;
import org.joml.Matrix4f;

public class EnemyGameObject extends EntityGameObject {

  EnemyGameObject(EnemyGameObject copy) {
    model = copy.model;
    idleTextures = copy.idleTextures;
    movingTextures = copy.movingTextures;
    currentDirection = copy.currentDirection;
    currentState = copy.currentState;
    transform = copy.transform;
  }

  @Override
  public void render(ShaderProgram shaderProgram, Matrix4f camera) {
    transform.setPosition(isometricPosition);

    shaderProgram.bind();
    shaderProgram.setUniform("sampler", 0);
    shaderProgram.setUniform("projection", transform.getProjection(camera));

    switch ((EnemyState) currentState) {
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
