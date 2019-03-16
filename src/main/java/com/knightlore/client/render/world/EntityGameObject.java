package com.knightlore.client.render.world;

import com.knightlore.client.render.opengl.AnimatedTexture;
import com.knightlore.client.render.opengl.RenderModel;
import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.opengl.StaticTexture;
import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.Entity;
import java.util.EnumMap;
import java.util.Map;
import org.joml.Matrix4f;
import org.joml.Vector4f;

/**
 * Abstract class for shared EntityGameObject code
 *
 * @author Adam Cox
 */
public abstract class EntityGameObject extends GameObject {

  /** Transform to keep track of projection changes */
  Transform transform;

  /** Current facing of entity */
  Direction currentDirection;

  /** Map to store idle textures */
  Map<Direction, StaticTexture> idleTextures;

  /** Map to store moving textures */
  Map<Direction, AnimatedTexture> movingTextures;

  /** Colour used to colour the texture with */
  Vector4f colour;

  /**
   * Empty base constructor
   *
   * @author Adam Cox
   */
  EntityGameObject() {}

  /**
   * Initialise EntityGameObject with texture file and colour
   *
   * @param textureFileName Prefix of the texture file name
   * @param colour Colour to colour the texture with
   * @author Adam Cox
   */
  EntityGameObject(String textureFileName, Vector4f colour) {
    this.colour = colour;

    idleTextures = new EnumMap<>(Direction.class);
    movingTextures = new EnumMap<>(Direction.class);
    for (Direction direction : Direction.values()) {
      String directionPath = textureFileName + "_" + direction.getAbbreviation();
      idleTextures.put(direction, new StaticTexture(directionPath));
      movingTextures.put(direction, new AnimatedTexture(directionPath + "_run", 10, 15));
    }

    float textureHeight = 2 * (float) idleTextures.get(Direction.NORTH).getHeight()
        / idleTextures.get(Direction.NORTH).getWidth();
    float[] vertices =
        new float[] {
          -1f, textureHeight, 0, // TOP LEFT     0
          1f, textureHeight, 0, // TOP RIGHT    1
          1f, 0, 0, // BOTTOM RIGHT 2
          -1f, 0, 0, // BOTTOM LEFT  3
        };

    model = new RenderModel(vertices, textureCoordinates, indices);
    transform = new Transform();
  }

  /**
   * Abstract method for rendering the EntityGameObject
   *
   * @param shaderProgram Shader program to use
   * @param camera Current Camera projection
   * @param worldScale Scale of the world
   * @author Adam Cox
   */
  public abstract void render(ShaderProgram shaderProgram, Matrix4f camera, int worldScale);

  /**
   * Update the EntityGameObject with a given entity
   *
   * @param entity Entity to update with
   * @author Adam Cox
   */
  public void update(Entity entity) {
    setPosition(entity.getPosition());
    currentDirection = entity.getDirection();
  }

  /**
   * Memory cleanup of textures and render model
   *
   * @author Adam Cox
   */
  public void cleanup() {
    idleTextures.values().forEach(StaticTexture::cleanup);
    movingTextures.values().forEach(AnimatedTexture::cleanup);
    model.cleanup();
  }
}
