package com.knightlore.client.gui.screen;

import static com.knightlore.client.util.GuiUtils.checkPosition;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.gui.Highscore;
import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.render.GuiRenderer;

public class HighscoreScreen implements IScreen {

  private GuiRenderer guiRenderer;
  private Highscore highscoreGui;

  public HighscoreScreen(GuiRenderer guiRenderer) {
    this.guiRenderer = guiRenderer;

    highscoreGui = new Highscore();
  }

  @Override
  public void startup(Object... args) {

    // set highscores
    highscoreGui.setHighscores(new TextObject[0]);
  }

  @Override
  public void input() {
    if (checkPosition(highscoreGui, highscoreGui.getBack().getId())) {
      highscoreGui.getBack().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Client.changeScreen(ClientState.MAIN_MENU, false);
        return;
      }
    } else highscoreGui.getBack().setColour(Colour.YELLOW);

    if (checkPosition(highscoreGui, highscoreGui.getNextPage().getId())) {
      highscoreGui.getNextPage().setColour();
      if (Mouse.isLeftButtonPressed()) {
        highscoreGui.incPage();
      }
    } else highscoreGui.getNextPage().setColour(Colour.YELLOW);

    if (checkPosition(highscoreGui, highscoreGui.getLastPage().getId())) {
      highscoreGui.getLastPage().setColour();
      if (Mouse.isLeftButtonPressed()) {
        highscoreGui.decPage();
      }
    } else highscoreGui.getLastPage().setColour(Colour.YELLOW);

    if (Keyboard.isKeyPressed(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(ClientState.MAIN_MENU, false);
    }
  }

  @Override
  public void render() {
    highscoreGui.updateSize();

    guiRenderer.render(highscoreGui);
  }

  @Override
  public void cleanUp() {
    highscoreGui.cleanup();
  }
}
