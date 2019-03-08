package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.gui.ServerMenu;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.render.GuiRenderer;
import org.joml.Vector4f;

public class ServerScreen implements IScreen {

  private GuiRenderer guiRenderer;
  private ServerMenu menu;

  public ServerScreen(GuiRenderer guiRenderer) {
    this.guiRenderer = guiRenderer;
    menu = new ServerMenu();
  }

  @Override
  public void input() {
    if (checkPosition(menu, menu.getSeparatorTop().getId(), 
    		menu.getSeparatorBot().getId())) {
      if (Mouse.scrolledDown()) {
        menu.moveDown();
      }
      if (Mouse.scrolledUp()) {
        menu.moveUp();
      }
      if (Mouse.isLeftButtonPressed()) {
        menu.highlight();
      }
    }

    if (checkPosition(menu, menu.getCreate().getId())) {
      menu.getCreate().setColour();
      if (Mouse.isLeftButtonPressed()) {
        menu.createServer();
      }
    } else menu.getCreate().setColour(new Vector4f(1, 1, 0, 1));

    if (checkPosition(menu, menu.getExit().getId())) {
      menu.getExit().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Client.changeScreen(ClientState.MAIN_MENU);
      }
    } else menu.getExit().setColour(new Vector4f(1, 1, 0, 1));

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(ClientState.MAIN_MENU);
    }
  }

  @Override
  public void render() {
    guiRenderer.render(menu);
  }

  @Override
  public void cleanUp() {
    menu.cleanup();
  }
}
