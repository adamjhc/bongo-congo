package com.knightlore.client.gui.screen;

import com.knightlore.client.render.GuiRenderer;

public class LevelSelectScreen implements IScreen {

  private GuiRenderer guiRenderer;

  public LevelSelectScreen(GuiRenderer guiRenderer) {
    this.guiRenderer = guiRenderer;
  }

  @Override
  public void input() {}

  @Override
  public void render() {}

  @Override
  public void cleanUp() {}
}
