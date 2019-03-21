package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.gui.LobbyMenu;
import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.LobbyObject;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.io.Window;
import com.knightlore.client.networking.GameConnection;
import com.knightlore.client.networking.ServerConnection;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.networking.ListGameObject;

import java.util.concurrent.TimeUnit;

public class LobbySelectScreen implements IScreen {

  private GuiRenderer guiRenderer;
  private LobbyMenu menu;

  public LobbySelectScreen(GuiRenderer guiRenderer) {
    this.guiRenderer = guiRenderer;
    menu = new LobbyMenu();
  }

  @Override
  public void startup(Object... args) {
    menu.refreshLobbies();
  }

  @Override
  public void input() {
    if (checkPosition(menu, menu.getSeparatorTop().getId(), menu.getSeparatorBot().getId())) {
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
        ServerConnection.instance.requestGame();

        while (GameConnection.instance == null) {
          try {
            TimeUnit.SECONDS.sleep(1);
          } catch (InterruptedException e) {
            // Shouldn't happen
          }
        }
        ListGameObject test1 = new ListGameObject(GameConnection.instance.uuid,
                GameConnection.instance.getIP(),
                GameConnection.instance.port(),
                "Test");

        LobbyObject test = new LobbyObject( "Test", menu.SMALL, test1);

        // Sent off register request
        GameConnection.instance.register();

        Client.changeScreen(ClientState.LOBBY, test);
      }
    } else menu.getCreate().setColour(Colour.YELLOW);

    if (checkPosition(menu, menu.getExit().getId())) {
      menu.getExit().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Client.changeScreen(ClientState.MAIN_MENU);
      }
    } else menu.getExit().setColour(Colour.YELLOW);

    if (checkPosition(menu, menu.getRefresh().getId())) {
      menu.getRefresh().setColour();
      if (Mouse.isLeftButtonPressed()) {
        menu.refreshLobbies();
      }
    } else menu.getRefresh().setColour(Colour.YELLOW);

    if (checkPosition(menu, menu.getJoin().getId())) {
      menu.getJoin().setColour();
      if (Mouse.isLeftButtonPressed()) {
        if (menu.getHighlighted() != null) {
          Client.changeScreen(ClientState.LOBBY, menu.getHighlighted());
        }
      }
    } else menu.getJoin().setColour(Colour.YELLOW);

    if (Keyboard.isKeyReleased(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(ClientState.MAIN_MENU);
    }
  }

  @Override
  public void render() {
    menu.updateSize();

    guiRenderer.render(menu);
  }

  @Override
  public void cleanUp() {
    menu.cleanup();
  }
}