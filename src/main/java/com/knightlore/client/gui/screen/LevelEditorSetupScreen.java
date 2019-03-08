package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.gui.PreLevelEditor;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.game.map.LevelMap;
import com.knightlore.game.map.TileSet;
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
    if (checkPosition(preLevelEditor, "wLeft", "")) {
      preLevelEditor.getWLeft().setColour();
      if (Mouse.isLeftButtonPressed()) {
        preLevelEditor.decWidth();
      }
    } else preLevelEditor.getWLeft().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(preLevelEditor, "wRight", "")) {
      preLevelEditor.getWRight().setColour();
      if (Mouse.isLeftButtonPressed()) {
        preLevelEditor.incWidth();
      }
    } else preLevelEditor.getWRight().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(preLevelEditor, "lLeft", "")) {
      preLevelEditor.getLLeft().setColour();
      if (Mouse.isLeftButtonPressed()) {
        preLevelEditor.decLength();
      }
    } else preLevelEditor.getLLeft().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(preLevelEditor, "lRight", "")) {
      preLevelEditor.getLRight().setColour();
      if (Mouse.isLeftButtonPressed()) {
        preLevelEditor.incLength();
      }
    } else preLevelEditor.getLRight().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(preLevelEditor, "hLeft", "")) {
      preLevelEditor.getHLeft().setColour();
      if (Mouse.isLeftButtonPressed()) {
        preLevelEditor.decHeight();
      }
    } else preLevelEditor.getHLeft().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(preLevelEditor, "hRight", "")) {
      preLevelEditor.getHRight().setColour();
      if (Mouse.isLeftButtonPressed()) {
        preLevelEditor.incHeight();
      }
    } else preLevelEditor.getHRight().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(preLevelEditor, "Create Level", "")) {
      preLevelEditor.getCreateLevel().setColour();
      if (Mouse.isLeftButtonPressed()) {
        LevelMap editorMap =
            initialiseMap(
                preLevelEditor.getWidthNum(),
                preLevelEditor.getLengthNum(),
                preLevelEditor.getHeightNum());
        Client.changeScreen(ClientState.LEVEL_EDITOR, editorMap);
      }
    } else preLevelEditor.getCreateLevel().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(preLevelEditor, "Back", "")) {
      preLevelEditor.getBack().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Client.changeScreen(ClientState.MAIN_MENU);
      }
    } else preLevelEditor.getBack().setColour(new Vector4f(1, 1, 0, 1));
  }

  @Override
  public void render() {
    guiRenderer.render(preLevelEditor);
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
