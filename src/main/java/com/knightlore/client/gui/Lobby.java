package com.knightlore.client.gui;

import com.knightlore.client.gui.engine.GuiObject;
import com.knightlore.client.gui.engine.IGui;

public class Lobby implements IGui {
	
    private GuiObject[] gameItems;

    @Override
    public GuiObject[] getGuiObjects() {
        return gameItems;
    }
}
