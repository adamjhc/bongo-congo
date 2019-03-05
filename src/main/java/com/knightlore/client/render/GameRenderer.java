package com.knightlore.client.render;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.Window;
import com.knightlore.client.gui.engine.graphics.Mesh;
import com.knightlore.client.gui.engine.graphics.Transformation;
import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.world.EnemyGameObject;
import com.knightlore.client.render.world.EnemyGameObjectSet;
import com.knightlore.client.render.world.GameObject;
import com.knightlore.client.render.world.PlayerGameObject;
import com.knightlore.client.render.world.TileGameObject;
import com.knightlore.client.render.world.TileGameObjectSet;
import com.knightlore.game.Game;
import com.knightlore.game.entity.Enemy;
import com.knightlore.game.entity.Player;
import java.util.ArrayList;
import java.util.Collection;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class GameRenderer extends Renderer {

  private Transformation transformation;

  /** World object used in renderer */
  private World world;

  /** Camera to view the world through */
  private Camera camera;

  /** Shader program used in rendering */
  private ShaderProgram worldShaderProgram;

  private ShaderProgram playerShaderProgram;

  private ShaderProgram hudShaderProgram;

  private ArrayList<TileGameObject> tileGameObjects;
  private ArrayList<PlayerGameObject> playerGameObjects;
  private ArrayList<EnemyGameObject> enemyGameObjects;

  private float viewX;
  private float viewY;

  /**
   * Initialise the renderer
   *
   * @param window Reference to the GLFW window class
   */
  public GameRenderer(Window window) {
    super(window);

    setupWorld();
    setupHud();
  }

  private void setupWorld() {
    world = new World();
    camera = new Camera(window.getWidth(), window.getHeight());
    worldShaderProgram = new ShaderProgram("world");
    playerShaderProgram = new ShaderProgram("player");
    tileGameObjects = new ArrayList<>();
    playerGameObjects = new ArrayList<>();
    enemyGameObjects = new ArrayList<>();
    viewX = ((float) window.getWidth() / (World.SCALE * 2)) + 1;
    viewY = ((float) window.getHeight() / (World.SCALE * 2)) + 2;
  }

  private void setupHud() {
    transformation = new Transformation();
    hudShaderProgram = new ShaderProgram("hud");
  }

  /**
   * Render the game model
   *
   * @param gameModel Game model to render
   */
  public void render(Game gameModel, Window window, IGui gui) {
    clearBuffers();

    renderGame(gameModel);
    renderGui(window, gui);

    swapBuffers();
  }

  public void render(Window window, IGui gui) {
    clearBuffers();

    renderGui(window, gui);

    swapBuffers();
  }

  private void renderGame(Game gameModel) {
    Collection<Player> players = gameModel.getCurrentLevel().getPlayers().values();
    Collection<Enemy> enemies = gameModel.getCurrentLevel().getEnemies();

    if (tileGameObjects.isEmpty()) {
      tileGameObjects.addAll(
          TileGameObjectSet.fromGameModel(gameModel.getCurrentLevel().getMap().getTiles()));
      playerGameObjects.addAll(PlayerGameObject.fromGameModel(players));
      enemyGameObjects.addAll(EnemyGameObjectSet.fromGameModel(enemies));
    }

    players.forEach(player -> playerGameObjects.get(player.getId()).update(player));
    enemies.forEach(enemy -> enemyGameObjects.get(enemy.getId()).update(enemy));

    Vector3f isometricPosition =
        playerGameObjects
            .get(gameModel.getCurrentLevel().myPlayer().getId())
            .getIsometricPosition();

    camera.setPosition(isometricPosition.mul(-World.SCALE, new Vector3f()));

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
        depthSort(gameModel.getCurrentLevel().getMap().getSize(), gameObjectsToDepthSort);

    depthSortedGameObjects.forEach(
        gameObject -> {
          if (gameObject instanceof PlayerGameObject) {
            ((PlayerGameObject) gameObject).render(playerShaderProgram, camera.getProjection());
          } else if (gameObject instanceof EnemyGameObject) {
            ((EnemyGameObject) gameObject).render(worldShaderProgram, camera.getProjection());
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

  private void renderGui(Window window, IGui gui) {
    hudShaderProgram.bind();

    Matrix4f ortho =
        transformation.getOrthoProjectionMatrix(0, window.getWidth(), window.getHeight(), 0);
    for (GuiObject guiObject : gui.getGuiObjects()) {
      if (guiObject.getRender()) {
        Mesh mesh = guiObject.getMesh();

        Matrix4f projModelMatrix = transformation.getOrtoProjModelMatrix(guiObject, ortho);
        hudShaderProgram.setUniform("projModelMatrix", projModelMatrix);
        hudShaderProgram.setUniform("colour", guiObject.getMesh().getMaterial().getColour());
        hudShaderProgram.setUniform(
            "hasTexture", guiObject.getMesh().getMaterial().isTextured() ? 1 : 0);

        mesh.render();
      }
    }
  }

  @Override
  public void cleanup() {
    hudShaderProgram.cleanup();
    playerShaderProgram.cleanup();
    worldShaderProgram.cleanup();
    tileGameObjects.forEach(TileGameObject::cleanup);
    playerGameObjects.forEach(PlayerGameObject::cleanup);
  }
}
