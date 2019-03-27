package com.knightlore.client.gui.screen;

import static com.knightlore.client.gui.engine.IGui.SMALL;
import static com.knightlore.client.util.GuiUtils.checkPosition;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import com.knightlore.client.Client;
import com.knightlore.client.ClientState;
import com.knightlore.client.audio.Audio;
import com.knightlore.client.gui.HighScore;
import com.knightlore.client.gui.engine.Colour;
import com.knightlore.client.gui.engine.TextObject;
import com.knightlore.client.io.Keyboard;
import com.knightlore.client.io.Mouse;
import com.knightlore.client.networking.HighScoreCache;
import com.knightlore.client.render.GuiRenderer;
import com.knightlore.networking.HighScoreResponseObject;
import java.util.List;

public class HighScoreScreen implements IScreen {

  private GuiRenderer guiRenderer;
  private HighScore highScoreGui;

  public HighScoreScreen(GuiRenderer guiRenderer) {
    this.guiRenderer = guiRenderer;

    highScoreGui = new HighScore();
  }

  @Override
  public void startup(Object... args) {
    List<HighScoreResponseObject> highScores = HighScoreCache.instance.getScores();

    TextObject[] highScoreTextObjects = new TextObject[highScores.size()];

    for (int i = 0; i < highScores.size(); i++) {
      HighScoreResponseObject highScoreDisplay = highScores.get(i);
      highScoreTextObjects[i] =
          new TextObject(
              String.format("%-15.15s %8d", highScoreDisplay.username, highScoreDisplay.score),
              SMALL);
    }

    highScoreGui.setHighScores(highScoreTextObjects);
  }

  @Override
  public void input() {
    if (checkPosition(highScoreGui, highScoreGui.getBack().getId())) {
      highScoreGui.getBack().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(Audio.AudioName.SOUND_MENUSELECT);
        Client.changeScreen(ClientState.MAIN_MENU, false);
        return;
      }
    } else highScoreGui.getBack().setColour(Colour.YELLOW);

    if (checkPosition(highScoreGui, highScoreGui.getNextPage().getId())) {
      highScoreGui.getNextPage().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(Audio.AudioName.SOUND_MENUSELECT);
        highScoreGui.incPage();
      }
    } else highScoreGui.getNextPage().setColour(Colour.YELLOW);

    if (checkPosition(highScoreGui, highScoreGui.getLastPage().getId())) {
      highScoreGui.getLastPage().setColour();
      if (Mouse.isLeftButtonPressed()) {
        Audio.play(Audio.AudioName.SOUND_MENUSELECT);
        highScoreGui.decPage();
      }
    } else highScoreGui.getLastPage().setColour(Colour.YELLOW);

    if (Keyboard.isKeyPressed(GLFW_KEY_ESCAPE)) {
      Client.changeScreen(ClientState.MAIN_MENU, false);
    }
  }

  @Override
  public void render() {
    highScoreGui.updateSize();

    guiRenderer.render(highScoreGui);
  }

  @Override
  public void cleanUp() {
    highScoreGui.cleanup();
  }
}
