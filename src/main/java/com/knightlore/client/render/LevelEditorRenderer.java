package com.knightlore.client.render;

import com.knightlore.client.io.Window;
import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.world.EditorTileGameObjectSet;
import com.knightlore.client.render.world.TileGameObject;
import com.knightlore.game.map.LevelMap;
import com.knightlore.game.map.Tile;
import com.knightlore.game.util.CoordinateUtils;
import org.joml.Vector3f;
import org.joml.Vector3i;

/**
 * Renderer used for Level editor
 *
 * @author Adam Cox, Adam Worwood
 */
public class LevelEditorRenderer extends Renderer {

  /** The world being rendered */
  private World world;
  /** The camera used as a viewpoint for the world */
  private Camera camera;
  /** The shader program used to render the world's shaders */
  private ShaderProgram shaderProgram;

  private float viewX;
  private float viewY;

  /** The x coordinate of the currently selected tile in the level editor */
  private int currentTileX;
  /** The y coordinate of the currently selected tile in the level editor */
  private int currentTileY;
  /** The z coordinate of the currently selected tile in the level editor */
  private int currentTileZ;

  public LevelEditorRenderer() {
    super();

    currentTileX = 0;
    currentTileY = 0;
    currentTileZ = 0;

    world = new World();
    camera = new Camera(Window.getWidth(), Window.getHeight());
    shaderProgram = new ShaderProgram("world");

    calculateView();
  }

  public void addToCameraPosition(Vector3f cameraChange, Vector3i mapSize) {
    camera.updatePosition(
        camera.getWorldPosition().add(cameraChange, new Vector3f()), world.getScale(), mapSize);
  }

  public void render(LevelMap levelMap) {
    Tile[][][] tiles = levelMap.getTiles();

    for (int z = 0; z < tiles.length; z++) {
      for (int y = tiles[z].length - 1; y >= 0; y--) {
        for (int x = tiles[z][y].length - 1; x >= 0; x--) {
          Vector3f isoTilePos = CoordinateUtils.toIsometric(x, y, z);
          if (isWithinView(isoTilePos)) {
            TileGameObject tileGameObject =
                EditorTileGameObjectSet.getTile(tiles[z][y][x].getIndex());
            tileGameObject.setIsometricPosition(isoTilePos);

            int highlight = (x == currentTileX && y == currentTileY && z == currentTileZ) ? 1 : 0;
            tileGameObject.render(
                shaderProgram, world.getProjection(), camera.getProjection(), highlight);
          }
        }
      }
    }
  }

  private boolean isWithinView(Vector3f gameObjectPosition) {
    Vector3f cameraPosition = camera.getWorldPosition();
    return cameraPosition.x + viewX >= gameObjectPosition.x
        && cameraPosition.y + viewY >= gameObjectPosition.y
        && cameraPosition.x - viewX <= gameObjectPosition.x - gameObjectPosition.z
        && cameraPosition.y - viewY <= gameObjectPosition.y - gameObjectPosition.z;
  }

  public void setCurrentTiles(int x, int y, int z) {
    currentTileX = x;
    currentTileY = y;
    currentTileZ = z;
  }

  /**
   * enlarges the scale of the world, giving the impression of moving the camera closer
   *
   * @param mapSize the size of the map being enlarged
   * @author Adam W
   */
  public void zoomIn(Vector3i mapSize) {
    int scale = world.getScale();
    if (scale != 96) {
      world.setScale(scale + 12);
      calculateView();
      camera.updatePosition(camera.getWorldPosition(), world.getScale(), mapSize);
    }
  }

  /**
   * shrinks the scale of the world, giving the impression of moving the camera further away
   *
   * @param mapSize the size of the map being shrunk
   * @author Adam W
   */
  public void zoomOut(Vector3i mapSize) {
    int scale = world.getScale();
    if (scale != 12) {
      world.setScale(scale - 12);
      calculateView();
      camera.updatePosition(camera.getWorldPosition(), world.getScale(), mapSize);
    }
  }

  private void calculateView() {
    viewX = (Window.getWidth() / (world.getScale() * 2f)) + 1;
    viewY = (Window.getHeight() / (world.getScale() * 2f)) + 2;
  }

  @Override
  public void cleanup() {
    shaderProgram.cleanup();
  }
}
