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

import com.knightlore.client.io.Window;
import com.knightlore.client.render.opengl.ShaderProgram;
import com.knightlore.client.render.world.TileSet;
import com.knightlore.game.Game;
import com.knightlore.game.entity.Player;
import com.knightlore.game.math.Vector3f;

public class Renderer {

  private Window window;
  private World world;
  private Camera camera;
  private ShaderProgram shaderProgram;
  private MapRenderer mapRenderer;
  private PlayerRenderer playerRenderer;
  private TileSet tileSet;

  public Renderer(Window window) {
    this.window = window;

    // Setting up OpenGL
    createCapabilities();
    glEnable(GL_TEXTURE_2D);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    // Setting up the world
    world = new World();
    camera = new Camera(window.getWidth(), window.getHeight());
    camera.addPosition(new Vector3f(0, -250, 0));
    shaderProgram = new ShaderProgram("shader");
    tileSet = new TileSet();
    mapRenderer = new MapRenderer(tileSet);
    playerRenderer = new PlayerRenderer();
  }

  public void render(Game gameModel) {
    clearBuffers();

    mapRenderer.render(
        gameModel.getCurrentLevel().getMap(),
        shaderProgram,
        world.getProjection(),
        camera.getProjection());

//    for (Player player : gameModel.getCurrentLevel().getPlayers()) {
//      playerRenderer.render(shaderProgram, world.getProjection(), camera.getProjection(), player);
//    }

    swapBuffers();
  }

  private void clearBuffers() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
  }

  private void swapBuffers() {
    window.swapBuffers();
  }
}
