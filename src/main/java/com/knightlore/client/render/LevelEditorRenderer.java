package com.knightlore.client.render;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glLoadMatrixf;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import com.knightlore.client.io.Mouse;
import com.knightlore.client.io.Window;
import com.knightlore.client.leveleditor.LevelEditorHud;
import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.world.TileGameObject;
import com.knightlore.client.render.world.TileGameObjectSet;
import com.knightlore.game.map.LevelMap;
import com.knightlore.game.map.Tile;
import com.knightlore.game.util.CoordinateUtils;
import org.joml.Matrix3x2f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.system.MemoryStack;

public class LevelEditorRenderer extends Renderer {

  private World world;
  private Camera camera;
  private ShaderProgram shaderProgram;

  private float viewX;
  private float viewY;

  private GuiRenderer hudRenderer;
  
  private int currentTileX ;
  private int currentTileY;
  private int currentTileZ;
  
  private Vector3i mouseTilePos;

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

    // Create a camera matrix:
    Matrix3x2f m =
        new Matrix3x2f()
            .view(0, Window.getWidth(), 0, Window.getHeight())
            .scale(World.SCALE)
            .translate(-cameraIsoPos.x, -cameraIsoPos.y);

    // Load matrix into OpenGL's matrix stack.
    // We just use the GL_PROJECTION stack here:
    glMatrixMode(GL_PROJECTION);
    try (MemoryStack frame = MemoryStack.stackPush()) {
      // Call glLoadMatrixf with a column-major buffer holding the 4x4 matrix
      glLoadMatrixf(m.get4x4(frame.mallocFloat(16)));
    }

    // GL_MODELVIEW not used here, so set to identity:
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();

    // Unproject a position in window coordinates to world coordinates:
    Vector3f mouseWorldPos =
        new Vector3f(
            m.unproject(
                (float) Mouse.getXPos(),
                Window.getHeight() - (float) Mouse.getYPos(),
                new int[] {0, 0, Window.getWidth(), Window.getHeight()},
                new Vector2f()),
            0);

    mouseTilePos =
        CoordinateUtils.getTileCoord(CoordinateUtils.toCartesian(mouseWorldPos.x, mouseWorldPos.y));

    Tile[][][] tiles = levelMap.getTiles();

    for (int z = 0; z < tiles.length; z++) {
      for (int y = tiles[z].length - 1; y >= 0; y--) {
        for (int x = tiles[z][y].length - 1; x >= 0; x--) {
          Vector3f modelPosition = new Vector3f(x, y, z);
          Vector3f isoTilePos = CoordinateUtils.toIsometric(modelPosition);
          if (isWithinView(cameraIsoPos, isoTilePos)) {
            TileGameObject tileGameObject = TileGameObjectSet.getTile(tiles[z][y][x].getIndex());
            tileGameObject.setIsometricPosition(isoTilePos);
            tileGameObject.setModelPosition(modelPosition);

            //int highlight = mouseTilePos.equals(currentTileX + 14, currentTileY + 1, currentTileZ) ? 1 : 0;
            int highlight = (x == currentTileX-14 && y == currentTileY-1 && z == currentTileZ) ? 1 : 0;
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
  
  public Vector3i getMouseTilePos() {
	  return mouseTilePos;
  }

  @Override
  protected void cleanup() {
    shaderProgram.cleanup();
  }
}
