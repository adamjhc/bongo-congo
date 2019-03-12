package com.knightlore.client.gui.screen;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PERIOD;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_COMMA;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.io.Window;
import com.knightlore.client.leveleditor.LevelEditorHud;
import com.knightlore.client.render.LevelEditorRenderer;
import com.knightlore.game.GameModel;
import com.knightlore.game.map.LevelMap;
import com.knightlore.game.map.TileType;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static com.knightlore.client.util.GuiUtils.checkPosition;

public class LevelEditorScreen implements IScreen {

  private int WIDTH;
  private int HEIGHT;
  private int LENGTH;

  private LevelEditorRenderer levelEditorRenderer;
  private LevelEditorHud levelEditorHud;

  private GameModel gameModel;
  private Vector3f cameraPosition;
  private int currentTileX, currentTileY, currentTileZ;
  private LevelMap editorMap;

  public LevelEditorScreen(LevelEditorRenderer levelEditorRenderer) {
    this.levelEditorRenderer = levelEditorRenderer;
    levelEditorHud = new LevelEditorHud();
  }

  @Override
  public void startup(Object... args) {
    editorMap = (LevelMap) args[0];
    WIDTH = editorMap.getTiles()[0][0].length;
    LENGTH = editorMap.getTiles()[0].length;
    HEIGHT = editorMap.getTiles().length;
    gameModel = new GameModel("");
    cameraPosition = new Vector3f(0, 0, 0);
    currentTileX = 0;
    currentTileY = 0;
    currentTileZ = 0;
  }

  @Override
  public void input() {
    cameraControl();
    levelEditorInput();
  }

  @Override
  public void render() {
	levelEditorHud.updateSize();
    levelEditorRenderer.render(editorMap, cameraPosition, levelEditorHud);
  }

  @Override
  public void cleanUp() {
    levelEditorHud.cleanup();
  }

  private void cameraControl() {
    if (Mouse.getXPos() <= 5) {
      cameraPosition.add(-0.1f, 0.1f, 0);
    } else if (Mouse.getXPos() >= Window.getWidth() - 5) {
      cameraPosition.add(0.1f, -0.1f, 0);
    }

    if (Mouse.getYPos() <= 5) {
      cameraPosition.add(0.1f, 0.1f, 0);
    } else if (Mouse.getYPos() >= Window.getHeight() - 5) {
      cameraPosition.add(-0.1f, -0.1f, 0);
    }
  }

  private void levelEditorInput() {
    if (Keyboard.isKeyReleased(GLFW_KEY_W)) {
      if (currentTileX != editorMap.getTiles()[currentTileZ][currentTileY].length - 1) {
        currentTileX += 1;
      }
    } else if (Keyboard.isKeyReleased(GLFW_KEY_S)) {
      if (currentTileX != 0) {
        currentTileX -= 1;
      }
    } else if (Keyboard.isKeyReleased(GLFW_KEY_A)) {
      if (currentTileY != editorMap.getTiles()[currentTileZ].length - 1) {
        currentTileY += 1;
      }
    } else if (Keyboard.isKeyReleased(GLFW_KEY_D)) {
      if (currentTileY != 0) {
        currentTileY -= 1;
      }
    } else if (Keyboard.isKeyReleased(GLFW_KEY_E)) {
      if (currentTileZ != editorMap.getTiles().length - 1) {
        currentTileZ += 1;
      }
    } else if (Keyboard.isKeyReleased(GLFW_KEY_Q)) {
      if (currentTileZ != 0) {
        currentTileZ -= 1;
      }
    } else if (Keyboard.isKeyReleased(GLFW_KEY_ENTER)) {
    	editorMap.resetRotation();
      try {
        gameModel.overwriteCurrentLevel(editorMap);
      } catch (Exception e) {
        gameModel.createNewLevel(editorMap);
      } finally {
        gameModel.addPlayer("1");
        Client.changeScreen(ClientState.TESTING_LEVEL, gameModel);
      }
    } else if (Keyboard.isKeyReleased(GLFW_KEY_COMMA)) {
    	editorMap.rotate(true);
    } else if (Keyboard.isKeyReleased(GLFW_KEY_PERIOD)) {
    	editorMap.rotate(false);
    } else if (Keyboard.isKeyReleased(GLFW_KEY_SPACE)) {
    	editorMap.resetRotation();
    }

    if (checkPosition(levelEditorHud, levelEditorHud.getSave().getId(), "")) {
  	  levelEditorHud.getSave().setColour();
  	  if (Mouse.isLeftButtonPressed()) {
  		  try {
  			  editorMap.resetRotation();
  			  save(false);
  		  } catch (Exception e) {
  			  e.printStackTrace();
  		  }
  	  }
    } else levelEditorHud.getSave().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(levelEditorHud, levelEditorHud.getEmpty().getId(), "")) {
    	levelEditorHud.getEmpty().setColour();
    	if (Mouse.isLeftButtonPressed()) {
    	editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
                TileType.values()[0]);
    	}
    } else levelEditorHud.getEmpty().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(levelEditorHud, levelEditorHud.getFloor().getId(), "")) {
    	levelEditorHud.getFloor().setColour();
    	if (Mouse.isLeftButtonPressed()) {
    		editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
    	            TileType.values()[1]);
    	}
    } else levelEditorHud.getFloor().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(levelEditorHud, levelEditorHud.getSlab().getId(), "")) {
    	levelEditorHud.getSlab().setColour();
    	if (Mouse.isLeftButtonPressed()) {
    		editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
    	            TileType.values()[2]);
    	}
    } else levelEditorHud.getSlab().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(levelEditorHud, levelEditorHud.getBlock().getId(), "")) {
    	levelEditorHud.getBlock().setColour();
    	if (Mouse.isLeftButtonPressed()) {
    		editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
    	            TileType.values()[3]);
    	}
    } else levelEditorHud.getBlock().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(levelEditorHud, levelEditorHud.getHazard().getId(), "")) {
    	levelEditorHud.getHazard().setColour();
    	if (Mouse.isLeftButtonPressed()) {
    		editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
    	            TileType.values()[4]);
    	}
    } else levelEditorHud.getHazard().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(levelEditorHud, levelEditorHud.getFinish().getId(), "")) {
    	levelEditorHud.getFinish().setColour();
    	if (Mouse.isLeftButtonPressed()) {
    		editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
    	            TileType.values()[5]);
    	}
    } else levelEditorHud.getFinish().setColour(new Vector4f(1, 1, 0, 1));

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(ClientState.MAIN_MENU);
    }

    levelEditorRenderer.setCurrentTiles(currentTileX, currentTileY, currentTileZ);
  }

  private void save(boolean levelIsComplete) throws IOException {
	  String filePath = "customMaps/";
	  if (levelIsComplete) {
		  filePath = filePath + "playable/";
	  } else {
		  filePath = filePath + "unplayable/";
	  }

	  GsonBuilder builder = new GsonBuilder();
	  Gson gson = builder.create();

	  String jsonString = gson.toJson(editorMap);
	  System.out.println(jsonString);

	  BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + "customMap.umap"));
	  writer.write(jsonString);
	  writer.close();
  }
}
