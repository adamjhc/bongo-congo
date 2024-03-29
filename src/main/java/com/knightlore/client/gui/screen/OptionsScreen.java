package com.knightlore.client.gui.screen;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.audio.Audio;
import com.knightlore.client.audio.Audio.AudioName;
import com.knightlore.client.gui.OptionsMenu;
import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.render.GuiRenderer;

import static com.knightlore.client.util.GuiUtils.checkPosition;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

/**
 * Handles the options screen startup, input, updates, rendering and cleanup
 *
 * @author Joseph, Adam Cox
 */
public class OptionsScreen implements IScreen {

  /** Audio clip name used for selection */
  private static final AudioName SELECT = AudioName.SOUND_MENUSELECT;

  /** Renderer used for rendering gui elements */
  private GuiRenderer guiRenderer;

  /** Gui elements to render */
  private OptionsMenu optionsMenu;

  /**
   * Initialise OptionsScreen
   *
   * @param guiRenderer renderer used for rendering gui elements
   */
  public OptionsScreen(GuiRenderer guiRenderer) {
    this.guiRenderer = guiRenderer;
    optionsMenu = new OptionsMenu();
  }

  @Override
  public void input() {
    if (checkPosition(optionsMenu, optionsMenu.getIncVolume().getId())) {
      optionsMenu.getIncVolume().setColour();
      if (Mouse.isLeftButtonHeld()) {
        optionsMenu.incVolume();
        Audio.incVolume();
      }
    } else optionsMenu.getIncVolume().setColour(Colour.YELLOW);

    if (checkPosition(optionsMenu, optionsMenu.getDecVolume().getId())) {
      optionsMenu.getDecVolume().setColour();
      if (Mouse.isLeftButtonHeld()) {
        optionsMenu.decVolume();
        Audio.decVolume();
      }
    } else optionsMenu.getDecVolume().setColour(Colour.YELLOW);

    if (checkPosition(optionsMenu, optionsMenu.getExit().getId())) {
      optionsMenu.getExit().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        Client.changeScreen(ClientState.MAIN_MENU, false);
        return;
      }
    } else optionsMenu.getExit().setColour(Colour.YELLOW);

    if (checkPosition(optionsMenu, optionsMenu.getIsFullscreen().getId())) {
      optionsMenu.getIsFullscreen().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(SELECT);
        optionsMenu.setFullscreen();
      }
    } else optionsMenu.getIsFullscreen().setColour(Colour.YELLOW);

    if (checkPosition(optionsMenu, optionsMenu.getIsMute().getId())) {
      optionsMenu.getIsMute().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.toggle();
        Audio.play(SELECT);
      }
    } else optionsMenu.getIsMute().setColour(Colour.YELLOW);

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(ClientState.MAIN_MENU, false);
    }
  }

  @Override
  public void update(float delta) {
    optionsMenu.setMute();
  }

  @Override
  public void render() {
    optionsMenu.updateSize();

    guiRenderer.render(optionsMenu);
  }

  @Override
  public void cleanUp() {
    optionsMenu.cleanup();
  }
}
