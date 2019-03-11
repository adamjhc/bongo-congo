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
import org.joml.Vector3f;
import org.joml.Vector4f;

public abstract class EntityGameObject extends GameObject {

  Transform transform;

  Direction currentDirection;

  Map<Direction, StaticTexture> idleTextures;
  Map<Direction, AnimatedTexture> movingTextures;
  Vector4f colour;

  EntityGameObject() {}

  public EntityGameObject(String textureFileName, Vector4f colour) {
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

  public abstract void render(ShaderProgram shaderProgram, Matrix4f camera, int worldScale);

  public void update(Entity entity) {
    setPosition(entity.getPosition());
    currentDirection = entity.getDirection();
  }

  public void cleanup() {
    idleTextures.values().forEach(StaticTexture::cleanup);
    movingTextures.values().forEach(AnimatedTexture::cleanup);
    model.cleanup();
  }
}
