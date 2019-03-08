package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.audio.Audio;
import com.knightlore.client.gui.OptionsMenu;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.render.GuiRenderer;
import org.joml.Vector4f;

public class OptionsScreen implements IScreen {

  private GuiRenderer guiRenderer;
  private OptionsMenu optionsMenu;

  public OptionsScreen(GuiRenderer guiRenderer) {
    this.guiRenderer = guiRenderer;
    optionsMenu = new OptionsMenu();
  }

  @Override
  public void input() {
    if (checkPosition(optionsMenu, ">", "")) {
      optionsMenu.getIncVolume().setColour();
      if (Mouse.isLeftButtonHeld()) {
        optionsMenu.incVolume();
        Audio.incVolume();
      }
    } else optionsMenu.getIncVolume().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(optionsMenu, "<", "")) {
      optionsMenu.getDecVolume().setColour();
      if (Mouse.isLeftButtonHeld()) {
        optionsMenu.decVolume();
        Audio.decVolume();
      }
    } else optionsMenu.getDecVolume().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(optionsMenu, "Exit", "")) {
      optionsMenu.getExit().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Client.changeScreen(ClientState.MAIN_MENU);
      }
    } else optionsMenu.getExit().setColour(new Vector4f(1, 1, 0, 1));

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(ClientState.MAIN_MENU);
    }
  }

  @Override
  public void render() {
    guiRenderer.render(optionsMenu);
  }

  @Override
  public void cleanUp() {
    optionsMenu.cleanup();
  }
}