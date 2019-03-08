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

  public LevelEditorRenderer() {
    super();

    setupWorld();
    setupHud();
  }

  private void setupWorld() {
    world = new World();
    camera = new Camera(Window.getWidth(), Window.getHeight());
    shaderProgram = new ShaderProgram("world");

    viewX = ((float) Window.getWidth() / (World.SCALE * 2)) + 1;
    viewY = ((float) Window.getHeight() / (World.SCALE * 2)) + 2;
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
    camera.setPosition(cameraIsoPos.mul(-World.SCALE, new Vector3f()));

    Tile[][][] tiles = levelMap.getTiles();

    for (int z = 0; z < tiles.length; z++) {
      for (int y = tiles[z].length - 1; y >= 0; y--) {
        for (int x = tiles[z][y].length - 1; x >= 0; x--) {
          Vector3f isoTilePos = CoordinateUtils.toIsometric(new Vector3f(x, y, z));
          if (isWithinView(cameraIsoPos, isoTilePos)) {
            TileGameObject tileGameObject = TileGameObjectSet.getTile(tiles[z][y][x].getIndex());
            tileGameObject.setIsometricPosition(isoTilePos);
            tileGameObject.render(shaderProgram, world.getProjection(), camera.getProjection());
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

  @Override
  protected void cleanup() {
    shaderProgram.cleanup();
  }
}
