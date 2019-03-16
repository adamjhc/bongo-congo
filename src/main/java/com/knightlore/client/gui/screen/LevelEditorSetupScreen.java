package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.gui.PreLevelEditor;
import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.game.map.LevelMap;
import com.knightlore.game.map.TileSet;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.joml.Vector4f;

public class LevelEditorSetupScreen implements IScreen {

  private GuiRenderer guiRenderer;
  private PreLevelEditor preLevelEditor;

  public LevelEditorSetupScreen(GuiRenderer guiRenderer) {
    this.guiRenderer = guiRenderer;
    preLevelEditor = new PreLevelEditor();
  }

  @Override
  public void input() {
    if (checkPosition(preLevelEditor, preLevelEditor.getWLeft().getId())) {
      preLevelEditor.getWLeft().setColour();
      if (Mouse.isLeftButtonPressed()) {
        preLevelEditor.decWidth();
      }
    } else preLevelEditor.getWLeft().setColour(Colour.YELLOW);

    if (checkPosition(preLevelEditor, preLevelEditor.getWRight().getId())) {
      preLevelEditor.getWRight().setColour();
      if (Mouse.isLeftButtonPressed()) {
        preLevelEditor.incWidth();
      }
    } else preLevelEditor.getWRight().setColour(Colour.YELLOW);

    if (checkPosition(preLevelEditor, preLevelEditor.getLLeft().getId())) {
      preLevelEditor.getLLeft().setColour();
      if (Mouse.isLeftButtonPressed()) {
        preLevelEditor.decLength();
      }
    } else preLevelEditor.getLLeft().setColour(Colour.YELLOW);

    if (checkPosition(preLevelEditor, preLevelEditor.getLRight().getId())) {
      preLevelEditor.getLRight().setColour();
      if (Mouse.isLeftButtonPressed()) {
        preLevelEditor.incLength();
      }
    } else preLevelEditor.getLRight().setColour(Colour.YELLOW);

    if (checkPosition(preLevelEditor, preLevelEditor.getHLeft().getId())) {
      preLevelEditor.getHLeft().setColour();
      if (Mouse.isLeftButtonPressed()) {
        preLevelEditor.decHeight();
      }
    } else preLevelEditor.getHLeft().setColour(Colour.YELLOW);

    if (checkPosition(preLevelEditor, preLevelEditor.getHRight().getId())) {
      preLevelEditor.getHRight().setColour();
      if (Mouse.isLeftButtonPressed()) {
        preLevelEditor.incHeight();
      }
    } else preLevelEditor.getHRight().setColour(Colour.YELLOW);

    if (checkPosition(preLevelEditor, preLevelEditor.getCreateLevel().getId())) {
      preLevelEditor.getCreateLevel().setColour();
      if (Mouse.isLeftButtonPressed()) {
        LevelMap editorMap =
            initialiseMap(
                preLevelEditor.getWidthNum(),
                preLevelEditor.getLengthNum(),
                preLevelEditor.getHeightNum());
        Client.changeScreen(ClientState.LEVEL_EDITOR, editorMap);
      }
    } else preLevelEditor.getCreateLevel().setColour(Colour.YELLOW);

    if (checkPosition(preLevelEditor, preLevelEditor.getBack().getId())) {
      preLevelEditor.getBack().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Client.changeScreen(ClientState.MAIN_MENU);
      }
    } else preLevelEditor.getBack().setColour(new Vector4f(1, 1, 0, 1));
    
    if (checkPosition(preLevelEditor, preLevelEditor.getLoadLevel().getId(), "")) {
    	preLevelEditor.getLoadLevel().setColour();
    	if (Mouse.isLeftButtonPressed()) {
    		Client.changeScreen(ClientState.LOADING_LEVEL);
    	}
    } else preLevelEditor.getLoadLevel().setColour(new Vector4f(1, 1, 0, 1));
    
    
    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(ClientState.MAIN_MENU);
    }
  }

  @Override
  public void render() {
  	preLevelEditor.updateSize();
  	
    guiRenderer.render(preLevelEditor);
  }

  @Override
  public void cleanUp() {
    preLevelEditor.cleanup();
  }

  private LevelMap initialiseMap(int width, int length, int height) {
    int[][][] emptyMap = new int[height][length][width];
    for (int i = 0; i < emptyMap.length; i++) {
      for (int j = 0; j < emptyMap[i].length; j++) {
        for (int k = 0; k < emptyMap[i][j].length; k++) {
          if (i == 0) {
            emptyMap[i][j][k] = 1;
          } else {
            emptyMap[i][j][k] = 0;
          }
        }
      }
    }
    return (new LevelMap(emptyMap, (new TileSet())));
  }
}
