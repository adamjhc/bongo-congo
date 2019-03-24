package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.audio.Audio;
import com.knightlore.client.gui.OptionsMenu;
import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.render.GuiRenderer;

public class OptionsScreen implements IScreen {

  private GuiRenderer guiRenderer;
  private OptionsMenu optionsMenu;

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
        Client.changeScreen(ClientState.MAIN_MENU);
      }
    } else optionsMenu.getExit().setColour(Colour.YELLOW);

    if (checkPosition(optionsMenu, optionsMenu.getIsFullscreen().getId())) {
      optionsMenu.getIsFullscreen().setColour();
      if (Mouse.isLeftButtonPressed()) {
        optionsMenu.setFullscreen();
      }
    } else optionsMenu.getIsFullscreen().setColour(Colour.YELLOW);

    if (checkPosition(optionsMenu, optionsMenu.getIsMute().getId())) {
      optionsMenu.getIsMute().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.toggle();
      }
    } else optionsMenu.getIsMute().setColour(Colour.YELLOW);

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(ClientState.MAIN_MENU);
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
