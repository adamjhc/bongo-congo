package com.knightlore.client.gui.screen;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_0;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_3;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_4;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_5;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_6;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_7;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_8;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_9;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

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

public class LevelEditorScreen implements IScreen {

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
    if (Keyboard.isKeyReleased(GLFW_KEY_KP_9)) {
      if (currentTileX != editorMap.getTiles()[currentTileZ][currentTileY].length - 1) {
        currentTileX += 1;
      }
    } else if (Keyboard.isKeyReleased(GLFW_KEY_KP_1)) {
      if (currentTileX != 0) {
        currentTileX -= 1;
      }
    } else if (Keyboard.isKeyReleased(GLFW_KEY_KP_7)) {
      if (currentTileY != editorMap.getTiles()[currentTileZ].length - 1) {
        currentTileY += 1;
      }
    } else if (Keyboard.isKeyReleased(GLFW_KEY_KP_3)) {
      if (currentTileY != 0) {
        currentTileY -= 1;
      }
    } else if (Keyboard.isKeyReleased(GLFW_KEY_KP_5)) {
      if (currentTileZ != editorMap.getTiles().length - 1) {
        currentTileZ += 1;
      }
    } else if (Keyboard.isKeyReleased(GLFW_KEY_KP_0)) {
      if (currentTileZ != 0) {
        currentTileZ -= 1;
      }
    } else if (Keyboard.isKeyReleased(GLFW_KEY_KP_8)) {
      if (currentTileX != editorMap.getTiles()[currentTileZ][currentTileY].length - 1
          && currentTileY != editorMap.getTiles()[currentTileZ].length - 1) {
        currentTileX += 1;
        currentTileY += 1;
      }
    } else if (Keyboard.isKeyReleased(GLFW_KEY_KP_2)) {
      if (currentTileX != 0 && currentTileY != 0) {
        currentTileX -= 1;
        currentTileY -= 1;
      }
    } else if (Keyboard.isKeyReleased(GLFW_KEY_KP_4)) {
      if (currentTileX != 0 && currentTileY != editorMap.getTiles()[currentTileZ].length - 1) {
        currentTileX -= 1;
        currentTileY += 1;
      }
    } else if (Keyboard.isKeyReleased(GLFW_KEY_KP_6)) {
      if (currentTileX != editorMap.getTiles()[currentTileZ][currentTileY].length - 1
          && currentTileY != 0) {
        currentTileX += 1;
        currentTileY -= 1;
      }
    } else if (Keyboard.isKeyReleased(GLFW_KEY_SPACE)) {
      int id = editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].getType().ordinal();
      if (id == 5) {
        editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
            TileType.values()[0]);
      } else {
        editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
            TileType.values()[id + 1]);
      }
    } else if (Keyboard.isKeyReleased(GLFW_KEY_ENTER)) {
      try {
        gameModel.overwriteCurrentLevel(editorMap);
      } catch (Exception e) {
        gameModel.createNewLevel(editorMap);
      } finally {
        gameModel.addPlayer("1");
        Client.changeScreen(ClientState.TESTING_LEVEL, gameModel);
      }
    }

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(ClientState.MAIN_MENU);
    }
  }
}
