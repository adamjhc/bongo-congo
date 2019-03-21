package com.knightlore.client.gui.screen;

import com.knightlore.client.gui.Loading;
import com.knightlore.client.render.GuiRenderer;

public class LoadingScreen implements IScreen {

  private GuiRenderer guiRenderer;
  private Loading loading;
  
  public LoadingScreen(GuiRenderer guiRenderer) {
  	this.guiRenderer = guiRenderer;
  	loading = new Loading();
  }
  
  @Override
  public void startup(Object... args) {
  	
  }
  
  @Override
  public void update(float delta) {
  	
  }
  
  @Override
  public void render() {
  	guiRenderer.render(loading);
  }
  
	@Override
	public void cleanUp() {
		loading.cleanup();
	}
}
