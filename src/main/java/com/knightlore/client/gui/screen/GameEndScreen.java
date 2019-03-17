package com.knightlore.client.gui.screen;

import com.knightlore.client.gui.GameEnd;
import com.knightlore.client.render.GuiRenderer;

public class GameEndScreen implements IScreen{
	
  private GuiRenderer guiRenderer;
  private GameEnd gameEnd;

  public GameEndScreen(GuiRenderer guiRenderer) {
    this.guiRenderer = guiRenderer;
    gameEnd = new GameEnd();
  }
	
  @Override
  public void input() {
  	
  }

  @Override
  public void render() {
  	gameEnd.updateSize();
  	
  	guiRenderer.render(gameEnd);
  }

  @Override
  public void cleanUp() {
  	gameEnd.cleanup();
  }
}
