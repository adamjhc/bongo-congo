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
import com.knightlore.client.render.world.PlayerSet;
import com.knightlore.client.render.world.TileSet;
import com.knightlore.game.Game;
import com.knightlore.game.entity.Player;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Renderer {

  /** Window reference */
  private Window window;

  /** World object used in renderer */
  private World world;

  /** Camera to view the world through */
  private Camera camera;

  /** Shader program used in rendering */
  private ShaderProgram shaderProgram;

  /** Renderer used for the map */
  private MapRenderer mapRenderer;

  /** Player set of active players */
  private PlayerSet playerSet;

  /** Renderer used for the players */
  private PlayerRenderer playerRenderer;
  
  private final Transformation transformation;
  
  private HudShaderProgram hudShaderProgram;

  /**
   * Initialise the renderer
   *
   * @param window Reference to the GLFW window class
   */
  public Renderer(Window window) {
    this.window = window;

    // Setting up OpenGL
    createCapabilities();
    glEnable(GL_TEXTURE_2D);
    glEnable(GL_MULTISAMPLE);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    // Setting up the world
    world = new World();
    camera = new Camera(window.getWidth(), window.getHeight());
    shaderProgram = new ShaderProgram("shader");
    mapRenderer = new MapRenderer(new TileSet());
    playerSet = new PlayerSet();
    playerRenderer = new PlayerRenderer(playerSet);
    
    transformation = new Transformation();
    
    setupHudShader();
  }
  
  private void setupHudShader() {
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
	    camera.setPosition(
	            playerSet.getPlayer(gameModel.getCurrentLevel().myPlayer().getId()).getTransform().getPosition().mul(-world.getScale(), new Vector3f()));

	        mapRenderer.render(
	            gameModel.getCurrentLevel().getMap(),
	            shaderProgram,
	            world.getProjection(),
	            camera.getProjection());

	        for (Player player : gameModel.getCurrentLevel().getPlayers().values()) {
	        	playerRenderer.render(player, shaderProgram, camera.getProjection());
	        }
  }
  
  private void renderGui(Window window, IGui gui) {
      hudShaderProgram.bind();

      Matrix4f ortho = transformation.getOrthoProjectionMatrix(0, window.getWidth(), window.getHeight(), 0);
      for (GuiObject guiObject : gui.getGuiObjects()) {
    	  if (guiObject.getRender()) {
              Mesh mesh = guiObject.getMesh();

              Matrix4f projModelMatrix = transformation.getOrtoProjModelMatrix(guiObject, ortho);
              hudShaderProgram.setUniform("projModelMatrix", projModelMatrix);
              hudShaderProgram.setUniform("colour", guiObject.getMesh().getMaterial().getColour());
              hudShaderProgram.setUniform("hasTexture", guiObject.getMesh().getMaterial().isTextured() ? 1 : 0);

              mesh.render();
    	  }
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

