package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.audio.Audio;
import com.knightlore.client.gui.Colour;
import com.knightlore.client.gui.MainMenu;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.io.Window;
import com.knightlore.client.render.GuiRenderer;

public class MainScreen implements IScreen {

  MainMenu menu;
  GuiRenderer renderer;

  public MainScreen(GuiRenderer renderer) {
    menu = new MainMenu();
    this.renderer = renderer;

    Audio.restart();
  }

  @Override
  public void startup(Object... args) {}

  @Override
  public void input() {
    // SINGLEPLAYER BUTTON
    if (checkPosition(menu, menu.getSingleplayer().getId(), "")) {
      menu.getSingleplayer().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Client.changeScreen(ClientState.GAME);
      }
    } else menu.getSingleplayer().setColour(Colour.YELLOW);

    // MULTIPLAYER BUTTON
    if (checkPosition(menu, menu.getMultiplayer().getId(), "")) {
      menu.getMultiplayer().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Client.changeScreen(ClientState.SERVER_MENU);
      }
    } else menu.getMultiplayer().setColour(Colour.YELLOW);

    // LEVEL EDITOR BUTTON
    if (checkPosition(menu, menu.getLevelEditor().getId(), "")) {
      menu.getLevelEditor().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Client.changeScreen(ClientState.PRE_EDITOR);
      }
    } else menu.getLevelEditor().setColour(Colour.YELLOW);

    // OPTIONS BUTTON
    if (checkPosition(menu, menu.getOptions().getId(), "")) {
      menu.getOptions().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Client.changeScreen(ClientState.OPTIONS_MENU);
      }
    } else menu.getOptions().setColour(Colour.YELLOW);

    // QUIT BUTTON
    if (checkPosition(menu, menu.getQuit().getId(), "")) {
      menu.getQuit().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Window.setShouldClose();
      }
    } else menu.getQuit().setColour(Colour.YELLOW);

    if (Mouse.getXPos() > menu.getSound().getPositionX()
        && Mouse.getYPos() > menu.getSound().getPositionY()) {
      if (Mouse.isLeftButtonPressed()) {
        Audio.toggle();
      }
    }

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Window.setShouldClose();
    }
  }

  @Override
  public void render() {
    menu.getSoundMute().setRender(!Audio.isOn());

    renderer.render(menu);
  }

  @Override
  public void cleanUp() {
    menu.cleanup();
  }
}
