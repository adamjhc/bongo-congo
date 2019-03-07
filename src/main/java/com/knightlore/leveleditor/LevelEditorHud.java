package com.knightlore.leveleditor;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;

public class LevelEditorHud implements IGui {
	
	
	private GuiObject[] guiObjects;

	@Override
	public GuiObject[] getGuiObjects() {
		return guiObjects;
	}

}
