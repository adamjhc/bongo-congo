package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_COMMA;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_PERIOD;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.audio.Audio;
import com.knightlore.client.audio.Audio.AudioName;
import com.knightlore.client.gui.LevelEditorHud;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.io.Window;
import com.knightlore.client.render.LevelEditorRenderer;
import com.knightlore.game.GameModel;
import com.knightlore.game.Level;
import com.knightlore.game.map.LevelMap;
import com.knightlore.game.map.TileType;
import java.util.ArrayList;
import java.util.List;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class LevelEditorScreen implements IScreen {
	
  /**
   * The sound that plays whenever the user clicks on a GUI element
   */
  private static final AudioName SELECT = AudioName.SOUND_MENUSELECT;

  /**
   * The renderer used for the level
   */
  private LevelEditorRenderer levelEditorRenderer;
  
  /**
   * The GUI displayed on the screen
   */
  private LevelEditorHud levelEditorHud;
  
  /**
   * The x coordinate of the currently selected tile
   */
  private int currentTileX;
  
  /** 
   * The y coordinate of the currently selected tile
   */
  private int currentTileY;
  
  /**
   * The z coordinate of the currently selected tile
   */
  private int currentTileZ;
  
  /**
   * The Map being edited
   */
  private LevelMap editorMap;

  public LevelEditorScreen(LevelEditorRenderer levelEditorRenderer) {
    this.levelEditorRenderer = levelEditorRenderer;
    levelEditorHud = new LevelEditorHud();
  }

  /**
   * Method to setup the level once the level editor is started
   */
  @Override
  public void startup(Object... args) {
    editorMap = (LevelMap) args[0];
    currentTileX = 0;
    currentTileY = 0;
    currentTileZ = 0;
    if (Audio.getCurrentMusic() != Audio.AudioName.MUSIC_EDITOR)
    	Audio.stop(Audio.getCurrentMusic());
    Audio.play(Audio.AudioName.MUSIC_EDITOR);
  }

  /**
   * Method to process user input
   */
  @Override
  public void input() {
    cameraControl();
    levelEditorInput();
  }

  /**
   * Method to process GUI and level rendering
   */
  @Override
  public void render() {
    levelEditorHud.updateSize();
    levelEditorRenderer.render(editorMap, levelEditorHud);
  }

  /**
   * Method to clean the GUI
   */
  @Override
  public void cleanUp() {
    levelEditorHud.cleanup();
  }

  /**
   * Method to process camera movement from mouse movement
   */
  private void cameraControl() {
    if (Mouse.isInScreen()) {
      if (Mouse.getXPos() <= 5) {
        levelEditorRenderer.addToCameraPosition(new Vector3f(-0.1f, 0, 0), editorMap.getSize());
      } else if (Mouse.getXPos() >= Window.getWidth() - 5) {
        levelEditorRenderer.addToCameraPosition(new Vector3f(0.1f, 0, 0), editorMap.getSize());
      }

      if (Mouse.getYPos() <= 5) {
        levelEditorRenderer.addToCameraPosition(new Vector3f(0, 0.1f, 0), editorMap.getSize());
      } else if (Mouse.getYPos() >= Window.getHeight() - 5) {
        levelEditorRenderer.addToCameraPosition(new Vector3f(0, -0.1f, 0), editorMap.getSize());
      }
    }
  }

  /**
   * Method to process keyboard input
   */
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
      Client.showLoadingScreen();
      editorMap.resetRotation();
      List<Level> levelList = new ArrayList<>();
      levelList.add(new Level(editorMap));
      Client.changeScreen(ClientState.TESTING_LEVEL, true, levelList);
      return;
    } else if (Keyboard.isKeyReleased(GLFW_KEY_COMMA)) {
      editorMap.rotate(true);
    } else if (Keyboard.isKeyReleased(GLFW_KEY_PERIOD)) {
      editorMap.rotate(false);
    } else if (Keyboard.isKeyReleased(GLFW_KEY_Z)) {
      levelEditorRenderer.zoomIn(editorMap.getSize());
    } else if (Keyboard.isKeyReleased(GLFW_KEY_X)) {
      levelEditorRenderer.zoomOut(editorMap.getSize());
    }

    if (checkPosition(levelEditorHud, levelEditorHud.getSave().getId(), "")) {
      levelEditorHud.getSave().setColour();
      if (Mouse.isLeftButtonPressed()) {
    	Audio.play(SELECT);
        try {
          editorMap.resetRotation();
          Client.changeScreen(ClientState.NAMING_LEVEL, false, editorMap, false);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } else levelEditorHud.getSave().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(levelEditorHud, levelEditorHud.getEmpty().getId(), "")) {
      levelEditorHud.getEmpty().setColour();
      if (Mouse.isLeftButtonPressed()) {
    	Audio.play(SELECT);
        editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
            TileType.values()[0]);
      }
    } else levelEditorHud.getEmpty().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(levelEditorHud, levelEditorHud.getFloor().getId(), "")) {
      levelEditorHud.getFloor().setColour();
      if (Mouse.isLeftButtonPressed()) {
    	Audio.play(SELECT);
        editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
            TileType.values()[1]);
      }
    } else levelEditorHud.getFloor().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(levelEditorHud, levelEditorHud.getSlab().getId(), "")) {
      levelEditorHud.getSlab().setColour();
      if (Mouse.isLeftButtonPressed()) {
    	Audio.play(SELECT);
        editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
            TileType.values()[2]);
      }
    } else levelEditorHud.getSlab().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(levelEditorHud, levelEditorHud.getBlock().getId(), "")) {
      levelEditorHud.getBlock().setColour();
      if (Mouse.isLeftButtonPressed()) {
    	Audio.play(SELECT);
        editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
            TileType.values()[3]);
      }
    } else levelEditorHud.getBlock().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(levelEditorHud, levelEditorHud.getHazard().getId(), "")) {
      levelEditorHud.getHazard().setColour();
      if (Mouse.isLeftButtonPressed()) {
    	Audio.play(SELECT);
        editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
            TileType.values()[4]);
      }
    } else levelEditorHud.getHazard().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(levelEditorHud, levelEditorHud.getFinish().getId(), "")) {
      levelEditorHud.getFinish().setColour();
      if (Mouse.isLeftButtonPressed()) {
    	Audio.play(SELECT);
        editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
            TileType.values()[5]);
      }
    } else levelEditorHud.getFinish().setColour(new Vector4f(1, 1, 0, 1));
    
    if (checkPosition(levelEditorHud, levelEditorHud.getWalker().getId(), "")) {
    	levelEditorHud.getWalker().setColour();
    	if (Mouse.isLeftButtonPressed()) {
    		Audio.play(SELECT);
    		editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
    				TileType.values()[6]);
    	}
    } else levelEditorHud.getWalker().setColour(new Vector4f(1, 1, 0, 1));
    
    if (checkPosition(levelEditorHud, levelEditorHud.getRandomer().getId(), "")) {
    	levelEditorHud.getRandomer().setColour();
    	if (Mouse.isLeftButtonPressed()) {
    		Audio.play(SELECT);
    		editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
    				TileType.values()[7]);
    	}
    } else levelEditorHud.getRandomer().setColour(new Vector4f(1, 1, 0, 1));
    
    if (checkPosition(levelEditorHud, levelEditorHud.getCircler().getId(), "")) {
    	levelEditorHud.getCircler().setColour();
    	if (Mouse.isLeftButtonPressed()) {
    		Audio.play(SELECT);
    		editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
    				TileType.values()[8]);
    	}
    } else levelEditorHud.getCircler().setColour(new Vector4f(1, 1, 0, 1));
    
    if (checkPosition(levelEditorHud, levelEditorHud.getCharger().getId(), "")) {
    	levelEditorHud.getCharger().setColour();
    	if (Mouse.isLeftButtonPressed()) {
    		Audio.play(SELECT);
    		editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
    				TileType.values()[9]);
    	}
    } else levelEditorHud.getCharger().setColour(new Vector4f(1, 1, 0, 1));

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(ClientState.MAIN_MENU, false);
      return;
    }

    levelEditorRenderer.setCurrentTiles(currentTileX, currentTileY, currentTileZ);
  }
}
