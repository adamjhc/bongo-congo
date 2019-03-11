package com.knightlore.client.render;

import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.io.Window;
import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.world.EnemyGameObject;
import com.knightlore.client.render.world.EnemyGameObjectSet;
import com.knightlore.client.render.world.GameObject;
import com.knightlore.client.render.world.PlayerGameObject;
import com.knightlore.client.render.world.TileGameObject;
import com.knightlore.client.render.world.TileGameObjectSet;
import com.knightlore.game.GameModel;
import com.knightlore.game.entity.Enemy;
import com.knightlore.game.entity.Player;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class GameRenderer extends Renderer {

  private GuiRenderer hudRenderer;

  /** World object used in renderer */
  private World world;

  /** Camera to view the world through */
  private Camera camera;

  /** Shader program used in rendering */
  private ShaderProgram worldShaderProgram;

  private ShaderProgram playerShaderProgram;

  private List<TileGameObject> tileGameObjects;
  private List<PlayerGameObject> playerGameObjects;
  private List<EnemyGameObject> enemyGameObjects;

  private float viewX;
  private float viewY;

  /** Initialise the renderer */
  public GameRenderer() {
    super();

    setupWorld();
    setupHud();
  }

  private void setupWorld() {
    world = new World();
    camera = new Camera(Window.getWidth(), Window.getHeight());
    worldShaderProgram = new ShaderProgram("world");
    playerShaderProgram = new ShaderProgram("player");
    tileGameObjects = new ArrayList<>();
    playerGameObjects = new ArrayList<>();
    enemyGameObjects = new ArrayList<>();
    viewX = ((float) Window.getWidth() / (world.getScale() * 2)) + 1;
    viewY = ((float) Window.getHeight() / (world.getScale() * 2)) + 2;
  }

  private void setupHud() {
    hudRenderer = new GuiRenderer();
  }

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
   */
  public void render(GameModel gameModel, IGui hud) {
    clearBuffers();

    renderGame(gameModel);
    hudRenderer.renderGui(hud);

    swapBuffers();
  }

  private void renderGame(GameModel gameModel) {
    Collection<Player> players = gameModel.getPlayers().values();
    Collection<Enemy> enemies = gameModel.getCurrentLevel().getEnemies();

    players.forEach(player -> playerGameObjects.get(player.getId()).update(player));
    enemies.forEach(enemy -> enemyGameObjects.get(enemy.getId()).update(enemy));

    Vector3f isometricPosition =
        playerGameObjects.get(gameModel.myPlayer().getId()).getIsometricPosition();

    camera.setPosition(isometricPosition.mul(-world.getScale(), new Vector3f()));

    ArrayList<GameObject> gameObjectsToDepthSort = new ArrayList<>();
    tileGameObjects.forEach(
        tileGameObject ->
            ifWithinViewAddTo(gameObjectsToDepthSort, isometricPosition, tileGameObject));
    playerGameObjects.forEach(
        playerGameObject ->
            ifWithinViewAddTo(gameObjectsToDepthSort, isometricPosition, playerGameObject));
    enemyGameObjects.forEach(
        enemyGameObject ->
            ifWithinViewAddTo(gameObjectsToDepthSort, isometricPosition, enemyGameObject));

    ArrayList<GameObject> depthSortedGameObjects =
        depthSort(gameModel.getCurrentLevel().getLevelMap().getSize(), gameObjectsToDepthSort);

    depthSortedGameObjects.forEach(
        gameObject -> {
          if (gameObject instanceof PlayerGameObject) {
            ((PlayerGameObject) gameObject).render(playerShaderProgram, camera.getProjection(), world.getScale());
          } else if (gameObject instanceof EnemyGameObject) {
            ((EnemyGameObject) gameObject).render(worldShaderProgram, camera.getProjection(), world.getScale());
          } else {
            ((TileGameObject) gameObject)
                .render(worldShaderProgram, world.getProjection(), camera.getProjection());
          }
        });
  }

  private void ifWithinViewAddTo(
      ArrayList<GameObject> gameObjectsToDepthSort,
      Vector3f isometricPosition,
      GameObject playerGameObject) {
    if (isWithinView(isometricPosition, playerGameObject.getIsometricPosition())) {
      gameObjectsToDepthSort.add(playerGameObject);
    }
  }

  private boolean isWithinView(Vector3f playerPosition, Vector3f gameObjectPosition) {
    return playerPosition.x + viewX >= gameObjectPosition.x
        && playerPosition.y + viewY >= gameObjectPosition.y
        && playerPosition.x - viewX <= gameObjectPosition.x - gameObjectPosition.z
        && playerPosition.y - viewY <= gameObjectPosition.y - gameObjectPosition.z;
  }

  private ArrayList<GameObject> depthSort(Vector3i mapSize, ArrayList<GameObject> gameObjects) {
    // initalise the buckets
    ArrayList<ArrayList<GameObject>> buckets = new ArrayList<>();
    for (int i = 0; i < getScreenDepth(mapSize, new Vector3f(0, 0, mapSize.z - 1)) + 1; i++) {
      buckets.add(new ArrayList<>());
    }

    // distribute to buckets
    gameObjects.forEach(
        gameObject -> {
          if (gameObject instanceof TileGameObject && ((TileGameObject) gameObject).isFloor()) {
            buckets
                .get(getLevelScreenDepth(mapSize, gameObject.getModelPosition().z))
                .add(gameObject);
          } else {
            buckets.get(getScreenDepth(mapSize, gameObject.getModelPosition())).add(gameObject);
          }
        });

    // flatten
    ArrayList<GameObject> depthSortedGameObjects = new ArrayList<>();
    buckets.forEach(depthSortedGameObjects::addAll);
    return depthSortedGameObjects;
  }

  private int getScreenDepth(Vector3i mapSize, Vector3f position) {
    return (int)
        Math.ceil(
            mapSize.x
                - position.x
                + mapSize.y
                - position.y
                + getLevelScreenDepth(mapSize, position.z));
  }

  private int getLevelScreenDepth(Vector3i mapSize, float z) {
    return (int) z * (mapSize.x + mapSize.y);
  }

  @Override
  public void cleanup() {
    playerShaderProgram.cleanup();
    worldShaderProgram.cleanup();
    tileGameObjects.forEach(TileGameObject::cleanup);
    playerGameObjects.forEach(PlayerGameObject::cleanup);
  }
}
