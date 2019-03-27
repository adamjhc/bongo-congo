package com.knightlore.client.render;

import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.io.Window;
import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.world.EnemyGameObject;
import com.knightlore.client.render.world.EnemyGameObjectSet;
import com.knightlore.client.render.world.EntityGameObject;
import com.knightlore.client.render.world.GameObject;
import com.knightlore.client.render.world.PlayerGameObject;
import com.knightlore.client.render.world.TileGameObject;
import com.knightlore.client.render.world.TileGameObjectSet;
import com.knightlore.game.GameModel;
import com.knightlore.game.entity.Enemy;
import com.knightlore.game.entity.EnemyState;
import com.knightlore.game.entity.Player;
import com.knightlore.game.entity.PlayerState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.joml.Vector3f;
import org.joml.Vector3i;

/**
 * Renderer used in normal gameplay
 *
 * @author Adam Cox
 */
public class GameRenderer extends Renderer {

  /** Used for rendering HUD */
  private GuiRenderer hudRenderer;

  /** World object used in renderer */
  private World world;

  /** Camera to view the world through */
  private Camera camera;

  /** Shader program used in rendering world */
  private ShaderProgram worldShaderProgram;

  /** Shader program used in rendering players */
  private ShaderProgram playerShaderProgram;

  /** TileGameObjects in world */
  private List<TileGameObject> tileGameObjects;

  /** PlayerGameObjects in world */
  private List<PlayerGameObject> playerGameObjects;

  /** EnemyGameObjects in world */
  private List<EnemyGameObject> enemyGameObjects;

  /** Isometric x distance from camera to render */
  private float viewX;

  /** Isometric y distance from camera to render */
  private float viewY;

  /**
   * Initialise the renderer
   *
   * @author Adam Cox
   */
  public GameRenderer() {
    super();

    setupWorld();
    setupHud();
  }

  /**
   * Setup render world
   *
   * @author Adam Cox
   */
  private void setupWorld() {
    world = new World();
    camera = new Camera(Window.getWidth(), Window.getHeight());
    worldShaderProgram = new ShaderProgram("world");
    playerShaderProgram = new ShaderProgram("player");
    tileGameObjects = new ArrayList<>();
    playerGameObjects = new ArrayList<>();
    enemyGameObjects = new ArrayList<>();
    viewX = (Window.getWidth() / (world.getScale() * 2f)) + 1;
    viewY = (Window.getHeight() / (world.getScale() * 2f)) + 2;
  }

  /**
   * Setup hud for game
   *
   * @author Adam Cox
   */
  private void setupHud() {
    hudRenderer = new GuiRenderer();
  }

  /**
   * Convert game model entities into GameObjects
   *
   * @param gameModel GameModel object to get entities from
   * @author Adam Cox
   */
  public void init(GameModel gameModel) {
    playerGameObjects = PlayerGameObject.fromGameModel(gameModel.getPlayers().values());
    tileGameObjects =
        TileGameObjectSet.fromGameModel(gameModel.getCurrentLevel().getLevelMap().getTiles());
    enemyGameObjects = EnemyGameObjectSet.fromGameModel(gameModel.getCurrentLevel().getEnemies());
  }

  /**
   * Render the game model
   *
   * @param gameModel GameModel model to render
   * @author Adam Cox
   */
  public void render(GameModel gameModel, IGui hud) {
    renderGame(gameModel);
    hudRenderer.render(hud);
  }

  /**
   * Render the game
   *
   * @param gameModel GameModel object to render
   * @author Adam Cox
   */
  private void renderGame(GameModel gameModel) {
    Collection<Player> players = gameModel.getPlayers().values();
    Collection<Enemy> enemies = gameModel.getCurrentLevel().getEnemies();

    players.forEach(player -> playerGameObjects.get(player.getId()).update(player));
    enemies.forEach(enemy -> enemyGameObjects.get(enemy.getId()).update(enemy));

    Vector3f isometricPosition =
        playerGameObjects.get(gameModel.myPlayer().getId()).getIsometricPosition();

    camera.updatePosition(
        isometricPosition, world.getScale(), gameModel.getCurrentLevel().getLevelMap().getSize());

    List<GameObject> gameObjectsToDepthSort = new ArrayList<>();
    tileGameObjects.forEach(
        tileGameObject -> ifWithinViewAddTo(gameObjectsToDepthSort, tileGameObject));
    playerGameObjects.forEach(
        playerGameObject -> ifWithinViewAddTo(gameObjectsToDepthSort, playerGameObject));
    enemyGameObjects.forEach(
        enemyGameObject -> ifWithinViewAddTo(gameObjectsToDepthSort, enemyGameObject));

    List<GameObject> depthSortedGameObjects =
        depthSort(gameModel.getCurrentLevel().getLevelMap().getSize(), gameObjectsToDepthSort);

    depthSortedGameObjects.forEach(
        gameObject -> {
          if (gameObject instanceof EntityGameObject) {
            ((EntityGameObject) gameObject)
                .render(playerShaderProgram, camera.getProjection(), world.getScale());
          } else {
            ((TileGameObject) gameObject)
                .render(worldShaderProgram, world.getProjection(), camera.getProjection());
          }
        });
  }

  /**
   * Adds GameObject to given list if it is within view
   *
   * @param gameObjectsToDepthSort List to add to
   * @param gameObject GameObject to test
   * @author Adam Cox
   */
  private void ifWithinViewAddTo(List<GameObject> gameObjectsToDepthSort, GameObject gameObject) {
    if (isWithinView(gameObject.getIsometricPosition())) {
      gameObjectsToDepthSort.add(gameObject);
    }
  }

  /**
   * Returns whether a GameObject is within view of the camera
   *
   * @param gameObjectPosition isometric position of the game object
   * @return whether a GameObject is within view of the camera
   * @author Adam Cox
   */
  private boolean isWithinView(Vector3f gameObjectPosition) {
    Vector3f cameraPosition = camera.getWorldPosition();
    return cameraPosition.x + viewX >= gameObjectPosition.x
        && cameraPosition.y + viewY >= gameObjectPosition.y
        && cameraPosition.x - viewX <= gameObjectPosition.x - gameObjectPosition.z
        && cameraPosition.y - viewY <= gameObjectPosition.y - gameObjectPosition.z;
  }

  /**
   * Depth sorts given game objects
   *
   * @param mapSize Size of the map
   * @param gameObjects GameObjects to depth sort
   * @return List of Depth-sorted GameObjects
   */
  private List<GameObject> depthSort(Vector3i mapSize, List<GameObject> gameObjects) {
    List<ArrayList<GameObject>> buckets = new ArrayList<>();
    for (int i = -1; i < getScreenDepth(mapSize, new Vector3f(0, 0, mapSize.z - 1)) + 1; i++) {
      buckets.add(i + 1, new ArrayList<>());
    }

    gameObjects.forEach(
        gameObject -> {
          if (gameObject instanceof PlayerGameObject
              && (((PlayerGameObject) gameObject).getCurrentState() == PlayerState.CLIMBING
                  || ((PlayerGameObject) gameObject).getCurrentState() == PlayerState.IDLE)) {
            buckets
                .get(
                    getScreenDepth(
                        mapSize, gameObject.getModelPosition().sub(1, 1, 0, new Vector3f())))
                .add(gameObject);
          } else if (gameObject instanceof EnemyGameObject
              && ((EnemyGameObject) gameObject).getCurrentState() == EnemyState.IDLE) {
            buckets
                .get(
                    getScreenDepth(
                        mapSize, gameObject.getModelPosition().sub(1, 1, 0, new Vector3f())))
                .add(gameObject);
          } else {
            buckets.get(getScreenDepth(mapSize, gameObject.getModelPosition()) + 1).add(gameObject);
          }
        });

    ArrayList<GameObject> depthSortedGameObjects = new ArrayList<>();
    buckets.forEach(depthSortedGameObjects::addAll);
    return depthSortedGameObjects;
  }

  /**
   * Get screen depth of game object
   *
   * @param mapSize Size of the map
   * @param position Model position of the game object
   * @return Screen depth
   * @author Adam Cox
   */
  private int getScreenDepth(Vector3i mapSize, Vector3f position) {
    return (int) Math.ceil(mapSize.x - position.x + mapSize.y - position.y + position.z);
  }

  /**
   * Get screen depth of floor level
   *
   * @param mapSize Size of map
   * @param z Level of floor
   * @return Screen depth of floor level
   * @author Adam Cox
   */
  private int getLevelScreenDepth(Vector3i mapSize, float z) {
    return (int) z * (mapSize.x + mapSize.y);
  }

  /**
   * Memory cleanup of shader program and game objects
   *
   * @author Adam Cox
   */
  @Override
  public void cleanup() {
    playerShaderProgram.cleanup();
    worldShaderProgram.cleanup();
    tileGameObjects.forEach(TileGameObject::cleanup);
    playerGameObjects.forEach(PlayerGameObject::cleanup);
    enemyGameObjects.forEach(EnemyGameObject::cleanup);
  }
}
