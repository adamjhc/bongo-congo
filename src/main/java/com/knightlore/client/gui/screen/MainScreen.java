package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.audio.Audio;
import com.knightlore.client.gui.MainMenu;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.io.Window;
import com.knightlore.client.render.GuiRenderer;
import org.joml.Vector4f;

public class MainScreen implements IScreen {

  MainMenu menu;
  GuiRenderer renderer;

  public MainScreen(GuiRenderer renderer) {
    menu = new MainMenu();
    this.renderer = renderer;
  }

  @Override
  public void startup(Object... args) {
    Audio.toggle();
  }

  @Override
  public void input() {
    // SINGEPLAYER BUTTON
    if (checkPosition(menu, "Singleplayer", "")) {
      menu.getSingleplayer().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.toggle();
        Client.changeScreen(ClientState.GAME);
      }
    } else menu.getSingleplayer().setColour(new Vector4f(1, 1, 0, 1));

    // MULTIPLAYER BUTTON
    if (checkPosition(menu, "Multiplayer", "")) {
      menu.getMultiplayer().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Client.changeScreen(ClientState.SERVER_MENU);
      }
    } else menu.getMultiplayer().setColour(new Vector4f(1, 1, 0, 1));

    // LEVEL EDITOR BUTTON
    if (checkPosition(menu, "Level Editor", "")) {
      menu.getLevelEditor().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Client.changeScreen(ClientState.PRE_EDITOR);
      }
    } else menu.getLevelEditor().setColour(new Vector4f(1, 1, 0, 1));

    // OPTIONS BUTTON
    if (checkPosition(menu, "Options", "")) {
      menu.getOptions().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Client.changeScreen(ClientState.OPTIONS_MENU);
      }
    } else menu.getOptions().setColour(new Vector4f(1, 1, 0, 1));

    // QUIT BUTTON
    if (checkPosition(menu, "Quit", "")) {
      menu.getQuit().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Window.setShouldClose();
      }
    } else menu.getQuit().setColour(new Vector4f(1, 1, 0, 1));

    if (Mouse.getXPos() > menu.getSound().getPositionX()
        && Mouse.getYPos() > menu.getSound().getPositionY()) {
      if (Mouse.isLeftButtonPressed()) {
        menu.getSoundMute().setRender();
        Audio.toggle();
      }
    }

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Window.setShouldClose();
    }
  }

  @Override
  public void render() {
    renderer.render(menu);
  }

  @Override
  public void cleanUp() {
    menu.cleanup();
  }
}
