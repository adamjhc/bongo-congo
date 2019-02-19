package com.knightlore.client.render.world;

import com.knightlore.client.render.opengl.AnimatedTexture;
import com.knightlore.client.render.opengl.RenderModel;
import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.opengl.StaticTexture;
import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.PlayerState;
import com.knightlore.game.util.CoordinateUtils;
import java.util.EnumMap;
import java.util.Map;
import org.joml.Matrix4f;

public class PlayerGameObject extends GameObject {

  /** Player transform used for moving the player around the world */
  private Transform transform;

  private Map<Direction, StaticTexture> idleTextures;
  private Map<Direction, AnimatedTexture> movingTextures;

  /**
   * Initialise the player game object
   *
   * @param textureFileName File name of the player texture
   */
  public PlayerGameObject(String textureFileName) {
    idleTextures = new EnumMap<>(Direction.class);
    movingTextures = new EnumMap<>(Direction.class);
    for (Direction direction : Direction.values()) {
      String directionPath = textureFileName + "_" + direction.getAbbreviation();
      idleTextures.put(direction, new StaticTexture(directionPath));
      movingTextures.put(direction, new AnimatedTexture(directionPath + "_run", 10, 15));
    }

    float textureHeight =
        2
            * (float) idleTextures.get(Direction.NORTH).getHeight()
            / idleTextures.get(Direction.NORTH).getWidth();
    float[] vertices =
        new float[] {
          -1f,
          textureHeight,
          0, // TOP LEFT     0
          1f,
          textureHeight,
          0, // TOP RIGHT    1
          1f,
          0,
          0, // BOTTOM RIGHT 2
          -1f,
          0,
          0, // BOTTOM LEFT  3
        };

    float[] textureCoords =
        new float[] {
          0, 0,
          1, 0,
          1, 1,
          0, 1,
        };

    int[] indices =
        new int[] {
          0, 1, 2,
          2, 3, 0
        };

    model = new RenderModel(vertices, textureCoords, indices);
    transform = new Transform();
  }

  /**
   * Get the Player transform
   *
   * @return Player transform
   */
  public Transform getTransform() {
    return transform;
  }

  /**
   * Render the player
   *
   * @param x Isometric x position of the player
   * @param y Isometric y position of the player
   * @param shaderProgram Shader program used
   * @param camera Camera projection
   */
  public void render(
      PlayerState state,
      Direction direction,
      float x,
      float y,
      ShaderProgram shaderProgram,
      Matrix4f camera) {
    transform.setPosition(CoordinateUtils.toIsometric(x, y));

    shaderProgram.bind();
    shaderProgram.setUniform("sampler", 0);
    shaderProgram.setUniform("projection", transform.getProjection(camera));

    switch (state) {
      case IDLE:
        idleTextures.get(direction).bind(0);
        break;
      case MOVING:
        movingTextures.get(direction).bind(0);
    }

    model.render();
  }
}
