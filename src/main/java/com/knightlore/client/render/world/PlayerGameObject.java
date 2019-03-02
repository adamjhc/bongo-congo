package com.knightlore.client.render.world;

import com.knightlore.client.render.opengl.AnimatedTexture;
import com.knightlore.client.render.opengl.RenderModel;
import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.opengl.StaticTexture;
import com.knightlore.client.render.opengl.Texture;
import com.knightlore.game.entity.Direction;
import com.knightlore.game.entity.Player;
import com.knightlore.game.entity.PlayerState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class PlayerGameObject extends GameObject {

  private static ArrayList<Vector3f> availableColours;
  private static int inc = 0;

  static {
    availableColours = new ArrayList<>();
    availableColours.add(new Vector3f(0, 0, 1));
    availableColours.add(new Vector3f(0, 1, 0));
    availableColours.add(new Vector3f(0, 1, 1));
    availableColours.add(new Vector3f(1, 0, 0));
    availableColours.add(new Vector3f(1, 0, 1));
    availableColours.add(new Vector3f(1, 1, 0));
    availableColours.add(new Vector3f(1, 1, 1));
  }

  private int id;

  /** Player transform used for moving the player around the world */
  private Transform transform;

  private Direction currentDirection;
  private PlayerState currentPlayerState;

  private Map<Direction, StaticTexture> idleTextures;
  private Map<Direction, AnimatedTexture> movingTextures;
  private Vector3f colour;

  /**
   * Initialise the player game object
   *
   * @param textureFileName File name of the player texture
   */
  public PlayerGameObject(String textureFileName) {
    id = inc;
    inc++;

    colour = availableColours.get(new Random().nextInt(availableColours.size()));
    availableColours.remove(colour);

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

  public static List<PlayerGameObject> fromGameModel(Collection<Player> players) {
    List<PlayerGameObject> playerGameObjects = new ArrayList<>();

    players.forEach(
        player -> {
          PlayerGameObject playerGameObject = new PlayerGameObject("player");
          playerGameObject.setPosition(player.getPosition());
          playerGameObjects.add(playerGameObject);
        });

    return playerGameObjects;
  }

  public int getId() {
    return id;
  }

  public void update(Player player) {
    setPosition(player.getPosition());
    currentDirection = player.getDirection();
    currentPlayerState = player.getPlayerState();
  }

  /**
   * Render the player
   *
   * @param camera Camera projection
   */
  public void render(ShaderProgram shaderProgram, Matrix4f camera) {
    transform.setPosition(getIsometricPosition());

    shaderProgram.bind();
    shaderProgram.setUniform("sampler", 0);
    shaderProgram.setUniform("projection", transform.getProjection(camera));
    shaderProgram.setUniform("colour", colour);

    switch (currentPlayerState) {
      case IDLE:
        idleTextures.get(currentDirection).bind(0);
        break;
      case MOVING:
        movingTextures.get(currentDirection).bind(0);
        break;
      case CLIMBING:
        break;
    }

    model.render();
  }

  public void cleanup() {
    idleTextures.values().forEach(StaticTexture::cleanup);
    movingTextures.values().forEach(AnimatedTexture::cleanup);
  }
}
