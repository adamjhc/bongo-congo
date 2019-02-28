package com.knightlore.client.render;

import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;
import com.knightlore.client.gui.engine.Utils;
import com.knightlore.client.gui.engine.Window;
import com.knightlore.client.gui.engine.graphics.HudShaderProgram;
import com.knightlore.client.gui.engine.graphics.Mesh;
import com.knightlore.client.gui.engine.graphics.Transformation;
import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.world.GameObject;
import com.knightlore.client.render.world.PlayerGameObject;
import com.knightlore.client.render.world.TileGameObject;
import com.knightlore.client.render.world.TileGameObjectSet;
import com.knightlore.game.Game;
import com.knightlore.game.entity.Player;
import java.util.ArrayList;
import java.util.Collection;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Renderer {

  private Transformation transformation;

  /** Window reference */
  private Window window;

  /** World object used in renderer */
  private World world;

  /** Camera to view the world through */
  private Camera camera;

  /** Shader program used in rendering */
  private ShaderProgram shaderProgram;

  private TileGameObjectSet tileGameObjectSet;

  private HudShaderProgram hudShaderProgram;

  private ArrayList<TileGameObject> tileGameObjects;
  private ArrayList<PlayerGameObject> playerGameObjects;

  private float viewX;
  private float viewY;

  /**
   * Initialise the renderer
   *
   * @param window Reference to the GLFW window class
   */
  public Renderer(Window window) {
    this.window = window;

    setupOpenGL();
    setupWorld();
    setupHudShader();
  }

  private void setupOpenGL() {
    createCapabilities();
    glEnable(GL_TEXTURE_2D);
    glEnable(GL_MULTISAMPLE);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
  }

  private void setupWorld() {
    world = new World();
    camera = new Camera(window.getWidth(), window.getHeight());
    shaderProgram = new ShaderProgram("shader");
    tileGameObjectSet = new TileGameObjectSet();
    tileGameObjects = new ArrayList<>();
    playerGameObjects = new ArrayList<>();
    viewX = ((float) window.getWidth() / (World.SCALE * 2)) + 1;
    viewY = ((float) window.getHeight() / (World.SCALE * 2)) + 1;
  }

  private void setupHudShader() {
    transformation = new Transformation();
    hudShaderProgram = new HudShaderProgram();
    try {
      hudShaderProgram.createVertexShader(Utils.loadResource("/shaders/hud.vert"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      hudShaderProgram.createFragmentShader(Utils.loadResource("/shaders/hud.frag"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    hudShaderProgram.link();

    hudShaderProgram.createUniform("projModelMatrix");
    hudShaderProgram.createUniform("colour");
    hudShaderProgram.createUniform("hasTexture");
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

    if (tileGameObjects.isEmpty()) {
      tileGameObjects.addAll(
          tileGameObjectSet.fromGameModel(gameModel.getCurrentLevel().getMap().getTiles()));
      playerGameObjects.addAll(PlayerGameObject.fromGameModel(players));
    }

    players.forEach(player -> playerGameObjects.get(player.getId()).update(player));

    Vector3f isometricPosition =
        playerGameObjects
            .get(gameModel.getCurrentLevel().myPlayer().getId())
            .getIsometricPosition();

    camera.setPosition(isometricPosition.mul(-World.SCALE, new Vector3f()));

    ArrayList<GameObject> gameObjectsToDepthSort = new ArrayList<>();
    tileGameObjects.forEach(
        tileGameObject -> {
          if (isWithinView(isometricPosition, tileGameObject.getIsometricPosition())) {
            gameObjectsToDepthSort.add(tileGameObject);
          }
        });
    playerGameObjects.forEach(
        playerGameObject -> {
          if (isWithinView(isometricPosition, playerGameObject.getIsometricPosition())) {
            gameObjectsToDepthSort.add(playerGameObject);
          }
        });

    ArrayList<GameObject> depthSortedGameObjects =
        depthSort(gameModel.getCurrentLevel().getMap().getSize(), gameObjectsToDepthSort);

    depthSortedGameObjects.forEach(
        gameObject -> {
          if (gameObject instanceof PlayerGameObject) {
            ((PlayerGameObject) gameObject).render(shaderProgram, camera.getProjection());
          } else {
            ((TileGameObject) gameObject)
                .render(shaderProgram, world.getProjection(), camera.getProjection());
          }
        });
  }

  private boolean isWithinView(Vector3f playerPosition, Vector3f gameObjectPosition) {
    return playerPosition.x + viewX >= gameObjectPosition.x
        && playerPosition.y + viewY >= gameObjectPosition.y
        && playerPosition.x - viewX <= gameObjectPosition.x
        && playerPosition.y - viewY <= gameObjectPosition.y;
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
        (mapSize.x
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
      Mesh mesh = guiObject.getMesh();

      Matrix4f projModelMatrix = transformation.getOrtoProjModelMatrix(guiObject, ortho);
      hudShaderProgram.setUniform("projModelMatrix", projModelMatrix);
      hudShaderProgram.setUniform("colour", guiObject.getMesh().getMaterial().getColour());
      hudShaderProgram.setUniform(
          "hasTexture", guiObject.getMesh().getMaterial().isTextured() ? 1 : 0);

      mesh.render();
    }

    hudShaderProgram.unbind();
  }

  /** Clears the colour and depth buffers */
  private void clearBuffers() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
  }

  /** Swaps the window's buffers */
  private void swapBuffers() {
    window.swapBuffers();
  }

  public void cleanup() {
    if (hudShaderProgram != null) {
      hudShaderProgram.cleanup();
    }
  }
}
