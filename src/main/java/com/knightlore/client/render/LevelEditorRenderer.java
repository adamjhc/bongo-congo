package com.knightlore.client.render;

import com.knightlore.client.io.Window;
import com.knightlore.client.leveleditor.LevelEditorHud;
import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.world.TileGameObject;
import com.knightlore.client.render.world.TileGameObjectSet;
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

  private World world;
  private Camera camera;
  private ShaderProgram shaderProgram;

  private float viewX;
  private float viewY;

  private GuiRenderer hudRenderer;

  private int currentTileX;
  private int currentTileY;
  private int currentTileZ;

  public LevelEditorRenderer() {
    super();

    currentTileX = 0;
    currentTileY = 0;
    currentTileZ = 0;

    setupWorld();
    setupHud();
  }

  private void setupWorld() {
    world = new World();
    camera = new Camera(Window.getWidth(), Window.getHeight());
    shaderProgram = new ShaderProgram("world");

    calculateView();
  }

  private void setupHud() {
    hudRenderer = new GuiRenderer();
  }

  public void addToCameraPosition(Vector3f cameraChange, Vector3i mapSize) {
    camera.updatePosition(camera.getWorldPosition().add(cameraChange, new Vector3f()), world.getScale(), mapSize);
  }

  public void render(LevelMap levelMap, LevelEditorHud hud) {
    clearBuffers();

    renderMap(levelMap);
    hudRenderer.renderGui(hud);

    Window.swapBuffers();
  }

  private void renderMap(LevelMap levelMap) {
    Tile[][][] tiles = levelMap.getTiles();

    for (int z = 0; z < tiles.length; z++) {
      for (int y = tiles[z].length - 1; y >= 0; y--) {
        for (int x = tiles[z][y].length - 1; x >= 0; x--) {
          Vector3f isoTilePos = CoordinateUtils.toIsometric(x, y, z);
          if (isWithinView(isoTilePos)) {
            TileGameObject tileGameObject = TileGameObjectSet.getTile(tiles[z][y][x].getIndex());
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

  public void zoomIn(Vector3i mapSize) {
    int scale = world.getScale();
    if (scale != 96) {
      world.setScale(scale + 12);
      calculateView();
      camera.updatePosition(camera.getWorldPosition(), world.getScale(), mapSize);
    }
  }

  public void zoomOut(Vector3i mapSize) {
    int scale = world.getScale();
    if (scale != 12) {
      world.setScale(scale - 12);
      calculateView();
      camera.updatePosition(camera.getWorldPosition(), world.getScale(), mapSize);
    }
  }

  private void calculateView() {
    viewX = ((float) Window.getWidth() / (world.getScale() * 2)) + 1;
    viewY = ((float) Window.getHeight() / (world.getScale() * 2)) + 2;
  }

  @Override
  protected void cleanup() {
    shaderProgram.cleanup();
  }
}
