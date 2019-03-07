package com.knightlore.client.gui.engine;

public interface IGui {
	
	TextObject[] getTextObjects();

    GuiObject[] getGuiObjects();

    default void cleanup() {
        GuiObject[] guiObjects = getGuiObjects();
        for (GuiObject guiObject : guiObjects) {
            guiObject.getMesh().cleanUp();
        }
    }
}
