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

  public void render(LevelMap levelMap, Vector3f cameraPosition, LevelEditorHud hud) {
    clearBuffers();

    renderMap(levelMap, cameraPosition);
    hudRenderer.renderGui(hud);

    swapBuffers();
  }

  private void renderMap(LevelMap levelMap, Vector3f cameraPosition) {
    Vector3f cameraIsoPos = CoordinateUtils.toIsometric(cameraPosition);
    camera.setPosition(cameraIsoPos.mul(-world.getScale(), new Vector3f()));

    Tile[][][] tiles = levelMap.getTiles();

    for (int z = 0; z < tiles.length; z++) {
      for (int y = tiles[z].length - 1; y >= 0; y--) {
        for (int x = tiles[z][y].length - 1; x >= 0; x--) {
          Vector3f isoTilePos = CoordinateUtils.toIsometric(x, y, z);
          if (isWithinView(cameraIsoPos, isoTilePos)) {
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

  private boolean isWithinView(Vector3f isometricPosition, Vector3f gameObjectPosition) {
    return isometricPosition.x + viewX >= gameObjectPosition.x
        && isometricPosition.y + viewY >= gameObjectPosition.y
        && isometricPosition.x - viewX <= gameObjectPosition.x - gameObjectPosition.z
        && isometricPosition.y - viewY <= gameObjectPosition.y - gameObjectPosition.z;
  }

  public void setCurrentTiles(int x, int y, int z) {
    currentTileX = x;
    currentTileY = y;
    currentTileZ = z;
  }
  
  public void zoomIn() {
	  int scale = world.getScale();
	  if (scale != 96) {
		  world.setScale(scale + 12);
		  calculateView();
	  }
  }
  
  public void zoomOut() {
	  int scale = world.getScale();
	  if (scale != 12) {
		  world.setScale(scale - 12);
		  calculateView();
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
