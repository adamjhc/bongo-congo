package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_COMMA;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
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
import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.io.Window;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.client.render.LevelEditorRenderer;
import com.knightlore.game.Level;
import com.knightlore.game.map.LevelMap;
import com.knightlore.game.map.TileType;
import java.util.ArrayList;
import java.util.List;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * The screen used for creating and editing your own levels
 * @author Adam W
 *
 */
public class LevelEditorScreen implements IScreen {

  /** The sound that plays whenever the user clicks on a GUI element */
  private static final AudioName SELECT = AudioName.SOUND_MENUSELECT;

  /** The renderer used for rendering gui elements */
  private GuiRenderer guiRenderer;

  /** The renderer used for the level */
  private LevelEditorRenderer levelEditorRenderer;

  /** The GUI displayed on the screen */
  private LevelEditorHud levelEditorHud;

  /** The x coordinate of the currently selected tile */
  private int currentTileX;

  /** The y coordinate of the currently selected tile */
  private int currentTileY;

  /** The z coordinate of the currently selected tile */
  private int currentTileZ;

  /** The Map being edited */
  private LevelMap editorMap;

  /**
   * Initialise LevelEditorScreeen
   * @param guiRenderer renderer used to render gui elements
   * @param levelEditorRenderer renderer used to render editor map
   * @author Adam W
   */
  public LevelEditorScreen(GuiRenderer guiRenderer, LevelEditorRenderer levelEditorRenderer) {
    this.guiRenderer = guiRenderer;
    this.levelEditorRenderer = levelEditorRenderer;
    levelEditorHud = new LevelEditorHud();
  }

  /** Method to setup the level once the level editor is started
   * @author Adam W 
   */
  @Override
  public void startup(Object... args) {
    editorMap = (LevelMap) args[0];
    currentTileX = 0;
    currentTileY = 0;
    currentTileZ = 0;
    editorMap.setTime(60);
    if (Audio.getCurrentMusic() != Audio.AudioName.MUSIC_EDITOR)
      Audio.stop(Audio.getCurrentMusic());
    Audio.play(Audio.AudioName.MUSIC_EDITOR);
  }

  /** Method to process user input 
   * @author Adam W
   */
  @Override
  public void input() {
    cameraControl();
    levelEditorInput();
  }

  /** Method to process GUI and level rendering 
   * @author Adam W
   */
  @Override
  public void render() {
    levelEditorHud.updateSize();
    levelEditorRenderer.render(editorMap);
    guiRenderer.render(levelEditorHud);
  }

  /** Method to clean the GUI 
   * @author Adam W
   */
  @Override
  public void cleanUp() {
    levelEditorHud.cleanup();
  }

  /** Method to process camera movement from mouse movement 
   * @author Adam W
   */
  private void cameraControl() {
    if (Mouse.isInScreen()) {
      if (Mouse.getXPos() <= 15) {
        levelEditorRenderer.addToCameraPosition(new Vector3f(-0.1f, 0, 0), editorMap.getSize());
      } else if (Mouse.getXPos() >= Window.getWidth() - 15) {
        levelEditorRenderer.addToCameraPosition(new Vector3f(0.1f, 0, 0), editorMap.getSize());
      }

      if (Mouse.getYPos() <= 15) {
        levelEditorRenderer.addToCameraPosition(new Vector3f(0, 0.1f, 0), editorMap.getSize());
      } else if (Mouse.getYPos() >= Window.getHeight() - 15) {
        levelEditorRenderer.addToCameraPosition(new Vector3f(0, -0.1f, 0), editorMap.getSize());
      }
    }
  }

  /** Method to process keyboard input 
   * @author Adam W
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
      editorMap.setTime(levelEditorHud.getTime());
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
    } else if (Keyboard.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
        levelEditorHud.moveScore(35, levelEditorHud.getControlsSideGap());
      } else {
        levelEditorHud.moveScore(-10, levelEditorHud.getControlsHide());
      }

    if (checkPosition(levelEditorHud, levelEditorHud.getSave().getId(), "")) {
      levelEditorHud.getSave().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        try {
          editorMap.resetRotation();
          editorMap.setTime(levelEditorHud.getTime());
          Client.changeScreen(ClientState.NAMING_LEVEL, false, editorMap, false);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } else levelEditorHud.getSave().setColour(Colour.YELLOW);

    if (checkPosition(levelEditorHud, levelEditorHud.getEmpty().getId(), "")) {
      levelEditorHud.getEmpty().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
            TileType.values()[0]);
      }
    } else levelEditorHud.getEmpty().setColour(Colour.YELLOW);

    if (checkPosition(levelEditorHud, levelEditorHud.getFloor().getId(), "")) {
      levelEditorHud.getFloor().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
            TileType.values()[1]);
      }
    } else levelEditorHud.getFloor().setColour(Colour.YELLOW);

    if (checkPosition(levelEditorHud, levelEditorHud.getSlab().getId(), "")) {
      levelEditorHud.getSlab().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
            TileType.values()[2]);
      }
    } else levelEditorHud.getSlab().setColour(Colour.YELLOW);

    if (checkPosition(levelEditorHud, levelEditorHud.getBlock().getId(), "")) {
      levelEditorHud.getBlock().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
            TileType.values()[3]);
      }
    } else levelEditorHud.getBlock().setColour(Colour.YELLOW);

    if (checkPosition(levelEditorHud, levelEditorHud.getHazard().getId(), "")) {
      levelEditorHud.getHazard().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
            TileType.values()[4]);
      }
    } else levelEditorHud.getHazard().setColour(Colour.YELLOW);

    if (checkPosition(levelEditorHud, levelEditorHud.getFinish().getId(), "")) {
      levelEditorHud.getFinish().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
            TileType.values()[5]);
      }
    } else levelEditorHud.getFinish().setColour(Colour.YELLOW);

    if (checkPosition(levelEditorHud, levelEditorHud.getWalker().getId(), "")) {
      levelEditorHud.getWalker().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
            TileType.values()[6]);
      }
    } else levelEditorHud.getWalker().setColour(Colour.YELLOW);

    if (checkPosition(levelEditorHud, levelEditorHud.getRandomer().getId(), "")) {
      levelEditorHud.getRandomer().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
            TileType.values()[7]);
      }
    } else levelEditorHud.getRandomer().setColour(Colour.YELLOW);

    if (checkPosition(levelEditorHud, levelEditorHud.getCircler().getId(), "")) {
      levelEditorHud.getCircler().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        editorMap.getTiles()[currentTileZ][currentTileY][currentTileX].setType(
            TileType.values()[8]);
      }
    } else levelEditorHud.getCircler().setColour(Colour.YELLOW);
    
    if (checkPosition(levelEditorHud, levelEditorHud.getInc().getId())) {
    	levelEditorHud.getInc().setColour();
    	if (Mouse.isLeftButtonHeld()) {
    		Audio.play(SELECT);
    		levelEditorHud.incTime();
    	}
    } else levelEditorHud.getInc().setColour(Colour.YELLOW);
    
    if (checkPosition(levelEditorHud, levelEditorHud.getDec().getId())) {
    	levelEditorHud.getDec().setColour();
    	if (Mouse.isLeftButtonHeld()) {
    		Audio.play(SELECT);
    		levelEditorHud.decTime();
    	}
    } else levelEditorHud.getDec().setColour(Colour.YELLOW);

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(ClientState.MAIN_MENU, false);
      return;
    }

    levelEditorRenderer.setCurrentTiles(currentTileX, currentTileY, currentTileZ);
  }
}
