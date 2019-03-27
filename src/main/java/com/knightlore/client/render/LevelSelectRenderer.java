package com.knightlore.client.render;

import com.knightlore.client.io.Window;
import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.world.EditorTileGameObjectSet;
import com.knightlore.client.render.world.TileGameObject;
import com.knightlore.game.map.LevelMap;
import com.knightlore.game.map.Tile;
import com.knightlore.game.util.CoordinateUtils;
import org.joml.Vector3f;

public class LevelSelectRenderer extends Renderer {

  private World world;
  private Camera camera;
  private ShaderProgram shaderProgram;

  private float viewX;
  private float viewY;

  public LevelSelectRenderer() {
    super();

    world = new World();
    camera = new Camera(Window.getWidth(), Window.getHeight());
    shaderProgram = new ShaderProgram("world");

    calculateView();
  }

  public void setCameraPosition(Vector3f position) {
    camera.setPosition(position, world.getScale());
  }

  public void setWorldScale(int worldScale) {
    world.setScale(worldScale);
    calculateView();
  }

  public void render(LevelMap selectedMap) {
    if (selectedMap != null) {
      renderMap(selectedMap);
    }
  }

  private void renderMap(LevelMap levelMap) {
    Tile[][][] tiles = levelMap.getTiles();

    for (int z = 0; z < tiles.length; z++) {
      for (int y = tiles[z].length - 1; y >= 0; y--) {
        for (int x = tiles[z][y].length - 1; x >= 0; x--) {
          Vector3f isoTilePos = CoordinateUtils.toIsometric(x, y, z);
          if (isWithinView(isoTilePos)) {
            TileGameObject tileGameObject =
                EditorTileGameObjectSet.getTile(tiles[z][y][x].getIndex());
            tileGameObject.setIsometricPosition(isoTilePos);
            tileGameObject.render(shaderProgram, world.getProjection(), camera.getProjection());
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

  private void calculateView() {
    viewX = (Window.getWidth() / (world.getScale() * 2f)) + 1;
    viewY = (Window.getHeight() / (world.getScale() * 2f)) + 2;
  }

  @Override
  public void cleanup() {
    shaderProgram.cleanup();
  }
}
