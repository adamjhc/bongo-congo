package com.knightlore.client.render;

import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.world.TileGameObject;
import com.knightlore.client.render.world.TileSet;
import com.knightlore.game.map.Map;
import com.knightlore.game.map.Tile;
import org.joml.Matrix4f;

public class MapRenderer {

  /** Tile set used in the world */
  private TileSet tileSet;

  /**
   * Initialise the Map renderer
   *
   * @param tileSet Tile set to use in rendering
   */
  public MapRenderer(TileSet tileSet) {
    this.tileSet = tileSet;
  }

  /**
   * Renders the map; left to right, back to front and bottom to top
   *
   * @param map The game Map object to render
   * @param shaderProgram The shader program to render the map with
   * @param world The world to render the map in
   * @param camera The camera to show the map on
   */
  public void render(Map map, ShaderProgram shaderProgram, Matrix4f world, Matrix4f camera) {
    Tile[][][] tiles = map.getTiles();

    for (int z = 0; z < tiles.length; z++) {
      for (int y = tiles[z].length - 1; y >= 0; y--) {
        for (int x = tiles[z][y].length - 1; x >= 0; x--) {
          TileGameObject gameObject = tileSet.getTile(tiles[z][y][x].getId());
          gameObject.render(x + z, y + z, shaderProgram, world, camera);
        }
      }
    }
  }
}
